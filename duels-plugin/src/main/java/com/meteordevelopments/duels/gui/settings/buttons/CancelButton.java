package com.meteordevelopments.duels.gui.settings.buttons;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.gui.BaseButton;
import com.meteordevelopments.duels.util.compat.Items;
import com.meteordevelopments.duels.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CancelButton extends BaseButton {

    public CancelButton(final DuelsPlugin plugin) {
        super(plugin, ItemBuilder.of(getMaterial(plugin)).name(plugin.getLang().getMessage("GUI.settings.buttons.cancel.name"), plugin.getLang()).build());
    }

    private static Material getMaterial(final DuelsPlugin plugin) {
        String materialName = plugin.getLang().getMessage("GUI.settings.buttons.cancel.material");
        if (materialName != null && !materialName.isEmpty()) {
            Material mat = Material.getMaterial(materialName.toUpperCase());
            if (mat != null) {
                return mat;
            }
        }
        return Items.RED_PANE.getType();
    }

    @Override
    public void onClick(final Player player) {
        player.closeInventory();
    }
}
