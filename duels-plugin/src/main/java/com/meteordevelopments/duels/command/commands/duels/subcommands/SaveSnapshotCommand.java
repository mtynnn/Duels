package com.meteordevelopments.duels.command.commands.duels.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SaveSnapshotCommand extends BaseCommand {

    public SaveSnapshotCommand(final DuelsPlugin plugin) {
        super(plugin, "savesnapshot", "savesnapshot [name]", "Saves the current state of an arena for regeneration.", 2, false);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final String name = StringUtil.join(args, " ", 1, args.length).replace("-", " ");
        final ArenaImpl arena = arenaManager.get(name);

        if (arena == null) {
            lang.sendMessage(sender, "ERROR.arena.not-found", "name", name);
            return;
        }

        if (arena.getBound1() == null || arena.getBound2() == null) {
            lang.sendMessage(sender, "ERROR.arena.no-bounds-set");
            return;
        }

        if (arena.getSnapshot() == null) {
            lang.sendMessage(sender, "ERROR.arena.snapshot-not-initialized");
            return;
        }

        sender.sendMessage(lang.getMessage("PREFIX") + " §7Guardando snapshot de arena §e" + name + "§7...");
        
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            boolean success = arena.getSnapshot().save();
            
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (success) {
                    lang.sendMessage(sender, "COMMAND.duels.savesnapshot.success", "name", name);
                } else {
                    lang.sendMessage(sender, "COMMAND.duels.savesnapshot.failure", "name", name);
                }
            });
        });
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        if (args.length == 2) {
            return handleTabCompletion(args[1], arenaManager.getNames());
        }
        return null;
    }
}
