package com.meteordevelopments.duels.command.commands.duel.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewRequestCommand extends BaseCommand {

    public ViewRequestCommand(final DuelsPlugin plugin) {
        super(plugin, "viewrequest", "viewrequest <player>", "Opens the request view GUI.", 2, true);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final Player player = (Player) sender;

        if (args.length < 2) {
            lang.sendMessage(sender, "ERROR.command.invalid-usage");
            return;
        }

        final Player target = Bukkit.getPlayerExact(args[1]);

        if (target == null || !player.canSee(target)) {
            lang.sendMessage(sender, "ERROR.player.not-found", "name", args[1]);
            return;
        }

        // Open the request view GUI
        requestManager.openRequestView(player, target);
    }
}
