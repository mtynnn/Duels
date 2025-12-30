package com.meteordevelopments.duels.command.commands.duel.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.match.DuelMatch;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DrawDenyCommand extends BaseCommand {

    public DrawDenyCommand(final DuelsPlugin plugin) {
        super(plugin, "drawdeny", "drawdeny", "Deny a draw request.", 0, true);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final Player player = (Player) sender;
        final ArenaImpl arena = arenaManager.get(player);

        if (arena == null || arena.getMatch() == null) {
            lang.sendMessage(sender, "ERROR.duel.not-in-match", "name", player.getName());
            return;
        }

        final DuelMatch match = arena.getMatch();

        Player requestSender = null;
        for (final Player p : match.getAlivePlayers()) {
            if (!p.equals(player) && match.hasPendingDrawRequest(p)) {
                requestSender = p;
                break;
            }
        }

        if (requestSender != null) {
            match.removePendingDrawRequest(requestSender);
            duelManager.handleDrawDeny(player, requestSender);
        } else {
            lang.sendMessage(sender, "ERROR.duel.no-request", "name", "opponent");
        }
    }
}
