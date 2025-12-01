package com.meteordevelopments.duels.gui.request.buttons;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.gui.BaseButton;
import com.meteordevelopments.duels.request.RequestImpl;
import com.meteordevelopments.duels.util.compat.Items;
import com.meteordevelopments.duels.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RequestInfoButton extends BaseButton {

    private final RequestImpl request;

    public RequestInfoButton(final DuelsPlugin plugin, final RequestImpl request) {
        super(plugin, ItemBuilder.of(getMaterial(plugin)).name(plugin.getLang().getMessage("GUI.request-view.buttons.details.name"), plugin.getLang()).build());
        this.request = request;
    }

    private static Material getMaterial(final DuelsPlugin plugin) {
        String materialName = plugin.getLang().getMessage("GUI.request-view.buttons.details.material");
        if (materialName != null && !materialName.isEmpty()) {
            Material mat = Material.getMaterial(materialName.toUpperCase());
            if (mat != null) {
                return mat;
            }
        }
        return Material.PAPER;
    }

    @Override
    public void update(final Player player) {
        final String kit = request.getSettings().getKit() != null ? request.getSettings().getKit().getName() : lang.getMessage("GENERAL.not-selected");
        final String ownInventory = request.getSettings().isOwnInventory() ? lang.getMessage("GENERAL.enabled") : lang.getMessage("GENERAL.disabled");
        final String arena = request.getSettings().getArena() != null ? request.getSettings().getArena().getName() : lang.getMessage("GENERAL.random");
        final String itemBetting = request.getSettings().isItemBetting() ? lang.getMessage("GENERAL.enabled") : lang.getMessage("GENERAL.disabled");
        final int betAmount = request.getSettings().getBet();

        final Player sender = org.bukkit.Bukkit.getPlayer(request.getSender());
        final String senderName = sender != null ? sender.getName() : org.bukkit.Bukkit.getOfflinePlayer(request.getSender()).getName();
        setDisplayName(lang.getMessage("GUI.request-view.buttons.details.name", "sender", senderName), lang);
        
        final String lore = lang.getMessage("GUI.request-view.buttons.details.lore",
                "kit", kit,
                "own_inventory", ownInventory,
                "arena", arena,
                "item_betting", itemBetting,
                "bet_amount", betAmount
        );
        setLore(lang, lore.split("\n"));
    }
}
