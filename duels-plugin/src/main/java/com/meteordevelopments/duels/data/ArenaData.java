package com.meteordevelopments.duels.data;

import lombok.Getter;
import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.arena.ArenaSnapshot;

import java.util.*;

public class ArenaData {

    @Getter
    private String name;
    private boolean disabled;
    private ItemData displayed;
    private Set<String> kits = new HashSet<>();
    private Map<Integer, LocationData> positions = new HashMap<>();
    private LocationData bound1;
    private LocationData bound2;

    private ArenaData() {
    }

    public ArenaData(final ArenaImpl arena) {
        this.name = arena.getName();
        this.disabled = arena.isDisabled();
        this.displayed = ItemData.fromItemStack(arena.getDisplayed());
        arena.getKits().forEach(kit -> this.kits.add(kit.getName()));
        arena.getPositions().entrySet()
                .stream().filter(entry -> entry.getValue().getWorld() != null).forEach(entry -> positions.put(entry.getKey(), LocationData.fromLocation(entry.getValue())));
        
        // Save bounds if they exist
        if (arena.getBound1() != null && arena.getBound1().getWorld() != null) {
            this.bound1 = LocationData.fromLocation(arena.getBound1());
        }
        if (arena.getBound2() != null && arena.getBound2().getWorld() != null) {
            this.bound2 = LocationData.fromLocation(arena.getBound2());
        }
    }

    public ArenaImpl toArena(final DuelsPlugin plugin) {
        final ArenaImpl arena = new ArenaImpl(plugin, name, disabled);

        // Restore display item if it was saved (use setDisplayedWithoutSave to avoid triggering save during loading)
        if (this.displayed != null) {
            org.bukkit.inventory.ItemStack displayedItem = this.displayed.toItemStack();
            if (displayedItem != null) {
                arena.setDisplayedWithoutSave(displayedItem);
            }
        }

        // Manually bind kits and add locations to prevent saveArenas being called
        kits.stream().map(name -> plugin.getKitManager().get(name)).filter(Objects::nonNull).forEach(kit -> arena.getKits().add(kit));
        positions.forEach((key, value) -> arena.getPositions().put(key, value.toLocation()));
        
        // Restore bounds if they exist
        if (bound1 != null) {
            arena.setBound1(bound1.toLocation());
        }
        if (bound2 != null) {
            arena.setBound2(bound2.toLocation());
        }
        
        // Initialize snapshot and load it automatically if bounds exist
        ArenaSnapshot snapshot = new ArenaSnapshot(arena);
        if (bound1 != null && bound2 != null) {
            // Load snapshot asynchronously to avoid blocking server startup
            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                if (snapshot.save()) {
                    plugin.getLogger().info("[Arena Regeneration] Loaded snapshot for arena: " + arena.getName());
                } else {
                    plugin.getLogger().warning("[Arena Regeneration] Failed to load snapshot for arena: " + arena.getName());
                }
            });
        }
        arena.setSnapshot(snapshot);
        
        // Refresh GUI to add availability status to lore (custom lore is now preserved)
        arena.refreshGui(arena.isAvailable());
        
        return arena;
    }
}
