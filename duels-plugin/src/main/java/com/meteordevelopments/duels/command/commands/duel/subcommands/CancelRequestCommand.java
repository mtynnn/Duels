package com.meteordevelopments.duels.command.commands.duel.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.Permissions;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.request.RequestImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelRequestCommand extends BaseCommand {

    public CancelRequestCommand(final DuelsPlugin plugin) {
        super(plugin, "cancelrequest", "<player>", "Cancels a sent duel request.", Permissions.DUEL, 1, true);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final Player player = (Player) sender;
        final Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            lang.sendMessage(sender, "ERROR.player.not-found", "name", args[0]);
            return;
        }

        final RequestImpl request = requestManager.remove(player, target);

        if (request == null) {
            lang.sendMessage(sender, "ERROR.duel.no-request", "name", target.getName());
            return;
        }

        lang.sendMessage(sender, "COMMAND.duel.request.cancelled", "name", target.getName());
    }
}
