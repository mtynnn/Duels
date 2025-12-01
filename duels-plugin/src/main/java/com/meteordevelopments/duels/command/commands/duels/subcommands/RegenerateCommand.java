package com.meteordevelopments.duels.command.commands.duels.subcommands;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.arena.ArenaImpl;
import com.meteordevelopments.duels.command.BaseCommand;
import com.meteordevelopments.duels.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RegenerateCommand extends BaseCommand {

    public RegenerateCommand(final DuelsPlugin plugin) {
        super(plugin, "regenerate", "regenerate [name]", "Manually regenerates an arena to its saved state.", 2, false);
    }

    @Override
    protected void execute(final CommandSender sender, final String label, final String[] args) {
        final String name = StringUtil.join(args, " ", 1, args.length).replace("-", " ");
        final ArenaImpl arena = arenaManager.get(name);

        if (arena == null) {
            lang.sendMessage(sender, "ERROR.arena.not-found", "name", name);
            return;
        }

        if (arena.getSnapshot() == null || !arena.getSnapshot().hasSnapshot()) {
            lang.sendMessage(sender, "ERROR.arena.no-snapshot");
            return;
        }

        if (arena.isUsed()) {
            lang.sendMessage(sender, "ERROR.arena.in-use");
            return;
        }

        sender.sendMessage(lang.getMessage("PREFIX") + " §7Regenerando arena §e" + name + "§7...");
        
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            boolean success = arena.getSnapshot().restore();
            
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (success) {
                    lang.sendMessage(sender, "COMMAND.duels.regenerate.success", "name", name);
                } else {
                    lang.sendMessage(sender, "COMMAND.duels.regenerate.failure", "name", name);
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
