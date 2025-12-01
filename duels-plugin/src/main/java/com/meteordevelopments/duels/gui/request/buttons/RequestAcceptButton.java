package com.meteordevelopments.duels.gui.request.buttons;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.gui.BaseButton;
import com.meteordevelopments.duels.request.RequestImpl;
import com.meteordevelopments.duels.util.compat.Items;
import com.meteordevelopments.duels.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RequestAcceptButton extends BaseButton {

    private final RequestImpl request;

    public RequestAcceptButton(final DuelsPlugin plugin, final RequestImpl request) {
        super(plugin, ItemBuilder.of(getMaterial(plugin)).name(plugin.getLang().getMessage("GUI.request-view.buttons.accept.name"), plugin.getLang()).build());
        this.request = request;
    }

    private static Material getMaterial(final DuelsPlugin plugin) {
        String materialName = plugin.getLang().getMessage("GUI.request-view.buttons.accept.material");
        if (materialName != null && !materialName.isEmpty()) {
            Material mat = Material.getMaterial(materialName.toUpperCase());
            if (mat != null) {
                return mat;
            }
        }
        return Material.LIME_STAINED_GLASS_PANE;
    }

    @Override
    public void update(final Player player) {
        final String lore = lang.getMessage("GUI.request-view.buttons.accept.lore");
        setLore(lang, lore.split("\n"));
    }

    @Override
    public void onClick(final Player player) {
        final Player sender = org.bukkit.Bukkit.getPlayer(request.getSender());
        if (sender == null) {
            player.closeInventory();
            return;
        }
        player.closeInventory();
        player.performCommand("duel accept " + sender.getName());
    }
}
