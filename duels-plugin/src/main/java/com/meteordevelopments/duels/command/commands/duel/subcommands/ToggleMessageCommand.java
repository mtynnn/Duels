package com.meteordevelopments.duels.command.commands.duel.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.Permissions;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.data.UserData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessageCommand extends BaseCommand {

    public ToggleMessageCommand(final DuelsPlugin plugin) {
        super(plugin, "togglemessage", null, "Toggles duel messages.", Permissions.TOGGLE, 0, true);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final UserData user = userManager.get((Player) sender);

        if (user == null) {
            lang.sendMessage(sender, "ERROR.data.load-failure");
            return;
        }

        user.setDuelMessages(!user.isDuelMessages());
        lang.sendMessage(sender, "COMMAND.duel.toggle.messages." + (user.isDuelMessages() ? "enabled" : "disabled"));
    }
}
