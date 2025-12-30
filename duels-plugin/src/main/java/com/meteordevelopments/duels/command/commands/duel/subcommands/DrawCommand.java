package com.meteordevelopments.duels.command.commands.duel.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.match.DuelMatch;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DrawCommand extends BaseCommand {

    public DrawCommand(final DuelsPlugin plugin) {
        super(plugin, "draw", "draw", "Requests or accepts a draw.", 0, true);
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

        if (match.isFinished()) {
            return;
        }

        // Check if any opponent has requested a draw
        Player requestSender = null;
        for (final Player p : match.getAlivePlayers()) {
            if (!p.equals(player) && match.hasPendingDrawRequest(p)) {
                requestSender = p;
                break;
            }
        }

        if (requestSender != null) {
            // Accept the draw
            match.removePendingDrawRequest(requestSender);
            duelManager.endMatchAsDraw(match, arena);
            return;
        }

        // Validate sending a new request
        if (!match.canRequestDraw(player)) {
            lang.sendMessage(player, "DUEL.draw.limit-reached");
            return;
        }

        if (match.hasPendingDrawRequest(player)) {
            // Already sent and pending
            lang.sendMessage(player, "DUEL.draw.limit-reached");
            return;
        }

        match.addPendingDrawRequest(player);
        match.markDrawRequestUsed(player);

        // Notify opponents
        for (final Player p : match.getAlivePlayers()) {
            if (!p.equals(player)) {
                duelManager.sendDrawRequest(player, p);
            }
        }
    }
}
