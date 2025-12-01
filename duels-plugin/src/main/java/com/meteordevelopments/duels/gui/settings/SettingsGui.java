package com.meteordevelopments.duels.gui.settings;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.config.Config;
import com.meteordevelopments.duels.gui.BaseButton;
import com.meteordevelopments.duels.gui.settings.buttons.*;
import com.meteordevelopments.duels.util.compat.Items;
import com.meteordevelopments.duels.util.gui.SinglePageGui;
import com.meteordevelopments.duels.util.inventory.Slots;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SettingsGui extends SinglePageGui<DuelsPlugin> {

    public SettingsGui(final DuelsPlugin plugin, final Player player) {
        super(plugin, plugin.getConfiguration().getSettingsTitle().replace("%player%", player != null ? player.getName() : ""), plugin.getConfiguration().getSettingsRows());
        final Config config = plugin.getConfiguration();
        final ItemStack spacing = Items.from(config.getSettingsFillerType(), config.getSettingsFillerData());
        
        // Fill all slots with spacing item first
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, spacing);
        }
        
        // Set details button
        set(config.getSettingsDetailsSlot(), new RequestDetailsButton(plugin));

        final List<BaseButton> buttons = new ArrayList<>();

        // Add kit selector button if enabled
        if (config.isKitSelectingEnabled()) {
            set(config.getSettingsKitSelectorSlot(), new KitSelectButton(plugin));
        }

        // Add own inventory button if enabled
        if (config.isOwnInventoryEnabled()) {
            set(config.getSettingsOwnInventorySlot(), new OwnInventoryButton(plugin));
        }

        // Add arena selector button if enabled
        if (config.isArenaSelectingEnabled()) {
            set(config.getSettingsArenaSelectorSlot(), new ArenaSelectButton(plugin));
        }

        // Add item betting button if enabled
        if (config.isItemBettingEnabled()) {
            set(config.getSettingsItemBettingSlot(), new ItemBettingButton(plugin));
        }

        // Add send request buttons
        final RequestSendButton sendButton = new RequestSendButton(plugin);
        for (int slot : config.getSettingsSendRequestSlots()) {
            set(slot, sendButton);
        }

        // Add cancel buttons
        final CancelButton cancelButton = new CancelButton(plugin);
        for (int slot : config.getSettingsCancelSlots()) {
            set(slot, cancelButton);
        }
    }
}
