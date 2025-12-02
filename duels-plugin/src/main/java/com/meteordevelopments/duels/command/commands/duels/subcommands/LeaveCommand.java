package com.meteordevelopments.duels.command.commands.duels.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.command.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveCommand extends BaseCommand {

    public LeaveCommand(final DuelsPlugin plugin) {
        super(plugin, "leave", null, "Leave the arena after winning a duel.", 1, true);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final Player player = (Player) sender;
        
        // Check if player has a PlayerInfo (means they're in loot time after winning)
        if (playerManager.get(player) == null) {
            lang.sendMessage(sender, "ERROR.duel.not-in-match");
            return;
        }
        
        // Find which arena they were in by checking all arenas
        ArenaImpl targetArena = null;
        for (com.meteordevelopments.duels.api.arena.Arena arenaApi : arenaManager.getArenas()) {
            ArenaImpl arena = (ArenaImpl) arenaApi;
            if (arena.getBound1() != null && arena.getBound2() != null) {
                // Check if player is within arena bounds
                if (isPlayerInArena(player, arena)) {
                    targetArena = arena;
                    break;
                }
            }
        }
        
        if (targetArena == null) {
            lang.sendMessage(sender, "ERROR.duel.not-in-match");
            return;
        }

        // Remove PlayerInfo and restore player
        playerManager.get(player).restore(player);
        playerManager.remove(player);
        
        // Teleport to lobby
        teleport.tryTeleport(player, playerManager.getLobby());
        
        // Trigger arena regeneration if enabled
        targetArena.triggerRegenerationAfterLeave();
        
        lang.sendMessage(sender, "COMMAND.duels.leave");
    }
    
    private boolean isPlayerInArena(Player player, ArenaImpl arena) {
        if (!player.getWorld().equals(arena.getBound1().getWorld())) {
            return false;
        }
        
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        
        double minX = Math.min(arena.getBound1().getX(), arena.getBound2().getX());
        double maxX = Math.max(arena.getBound1().getX(), arena.getBound2().getX());
        double minY = Math.min(arena.getBound1().getY(), arena.getBound2().getY());
        double maxY = Math.max(arena.getBound1().getY(), arena.getBound2().getY());
        double minZ = Math.min(arena.getBound1().getZ(), arena.getBound2().getZ());
        double maxZ = Math.max(arena.getBound1().getZ(), arena.getBound2().getZ());
        
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return null;
    }
}
