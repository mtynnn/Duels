package com.meteordevelopments.duels.request;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.api.event.request.RequestSendEvent;
import com.meteordevelopments.duels.config.Config;
import com.meteordevelopments.duels.config.Lang;
import com.meteordevelopments.duels.gui.request.RequestViewGui;
import com.meteordevelopments.duels.setting.Settings;
import com.meteordevelopments.duels.util.Loadable;
import com.meteordevelopments.duels.util.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class RequestManager implements Loadable, Listener {

    private final DuelsPlugin plugin;
    private final Config config;
    private final Lang lang;
    private final Map<UUID, Map<UUID, RequestImpl>> requests = new HashMap<>();

    public RequestManager(final DuelsPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        this.lang = plugin.getLang();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void handleLoad() {
    }

    @Override
    public void handleUnload() {
        requests.clear();
    }

    private Map<UUID, RequestImpl> get(final Player player, final boolean create) {
        Map<UUID, RequestImpl> cached = requests.get(player.getUniqueId());

        if (cached == null && create) {
            requests.put(player.getUniqueId(), cached = new HashMap<>());
            return cached;
        }

        return cached;
    }

    public void send(final Player sender, final Player target, final Settings settings) {
        final RequestImpl request = new RequestImpl(sender, target, settings);
        final RequestSendEvent event = new RequestSendEvent(sender, target, request);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        final boolean isParty = request.isPartyDuel();
        get(sender, true).put(isParty ? request.getTargetParty().getOwner().getUuid() : target.getUniqueId(), request);

        final String kit = settings.getKit() != null ? settings.getKit().getName() : lang.getMessage("GENERAL.not-selected");
        final String ownInventory = settings.isOwnInventory() ? lang.getMessage("GENERAL.enabled") : lang.getMessage("GENERAL.disabled");
        final String arena = settings.getArena() != null ? settings.getArena().getName() : lang.getMessage("GENERAL.random");

        if (request.isPartyDuel()) {
            final Player targetPartyLeader = request.getTargetParty().getOwner().getPlayer();
            lang.sendMessage(targetPartyLeader, "COMMAND.duel.party-request.send.receiver-party",
                    "name", sender.getName(), "kit", kit, "own_inventory", ownInventory, "arena", arena);
            sendClickableMessage("COMMAND.duel.party-request.send.clickable-text.", sender, Collections.singleton(targetPartyLeader));
        } else {
            // Send confirmation to sender
            lang.sendMessage(sender, "COMMAND.duel.request.send.sender", "name", target.getName());
            
            if (settings.isOwnInventory()) {
                 TextBuilder
                    .of(ChatColor.translateAlternateColorCodes('&', "\n&8&m--------------------------------------------------"), null, null, Action.SHOW_TEXT, "")
                    .add(ChatColor.translateAlternateColorCodes('&', "\n&c&l⚠ ADVERTENCIA ⚠"), null, null, Action.SHOW_TEXT, "")
                    .add(ChatColor.translateAlternateColorCodes('&', "\n \n&7Estás enviando un duelo con &c&nINVENTARIO PROPIO&7."), null, null, Action.SHOW_TEXT, "")
                    .add(ChatColor.translateAlternateColorCodes('&', "\n&7Si mueres, &cperderás tus items&7.\n"), null, null, Action.SHOW_TEXT, "")
                    .add(ChatColor.translateAlternateColorCodes('&', "\n&c&lCLIC PARA [CANCELAR]"),
                            ClickEvent.Action.RUN_COMMAND, "/duel cancelrequest " + target.getName(),
                            Action.SHOW_TEXT, ChatColor.translateAlternateColorCodes('&', "&7Click para cancelar la solicitud"))
                    .add(ChatColor.translateAlternateColorCodes('&', "\n&8&m--------------------------------------------------\n"), null, null, Action.SHOW_TEXT, "")
                    .send(Collections.singleton(sender));
            }

            // Send simple message with clickable button to open GUI (only to receiver)
            TextBuilder receiverMsg = TextBuilder
                    .of(lang.getMessage("COMMAND.duel.request.send.receiver", "name", sender.getName()), null, null, Action.SHOW_TEXT, "")
                    .add(" " + lang.getMessage("COMMAND.duel.request.send.clickable-text.accept.text"),
                            ClickEvent.Action.RUN_COMMAND, "/duel viewrequest " + sender.getName(),
                            Action.SHOW_TEXT, lang.getMessage("COMMAND.duel.request.send.clickable-text.accept.hover-text"));
                            
            if (settings.isOwnInventory()) {
                receiverMsg.add(ChatColor.translateAlternateColorCodes('&', "\n&8&m--------------------------------------------------"), null, null, Action.SHOW_TEXT, "")
                           .add(ChatColor.translateAlternateColorCodes('&', "\n&c&l⚠ ADVERTENCIA ⚠"), null, null, Action.SHOW_TEXT, "")
                           .add(ChatColor.translateAlternateColorCodes('&', "\n \n&7Este duelo es con &c&nINVENTARIO PROPIO&7."), null, null, Action.SHOW_TEXT, "")
                           .add(ChatColor.translateAlternateColorCodes('&', "\n&7Si mueres, &cperderás tus items&7."), null, null, Action.SHOW_TEXT, "")
                           .add(ChatColor.translateAlternateColorCodes('&', "\n&8&m--------------------------------------------------"), null, null, Action.SHOW_TEXT, "");
            }
            
            receiverMsg.send(Collections.singleton(target));
        }
    }

    private void sendClickableMessage(final String path, final Player sender, final Collection<Player> targets) {
        TextBuilder
                .of(lang.getMessage(path + "info.text"), null, null, Action.SHOW_TEXT, lang.getMessage(path + "info.hover-text"))
                .add(lang.getMessage(path + "accept.text"),
                        ClickEvent.Action.RUN_COMMAND, "/duel accept " + sender.getName(),
                        Action.SHOW_TEXT, lang.getMessage(path + "accept.hover-text"))
                .add(lang.getMessage(path + "deny.text"),
                        ClickEvent.Action.RUN_COMMAND, "/duel deny " + sender.getName(),
                        Action.SHOW_TEXT, lang.getMessage(path + "deny.hover-text"))
                .send(targets);
    }

    public RequestImpl get(final Player sender, final Player target) {
        final Map<UUID, RequestImpl> cached = get(sender, false);

        if (cached == null) {
            return null;
        }

        final RequestImpl request = cached.get(target.getUniqueId());

        if (request == null) {
            return null;
        }

        if (System.currentTimeMillis() - request.getCreation() >= config.getExpiration() * 1000L) {
            cached.remove(target.getUniqueId());
            return null;
        }

        return request;
    }

    public boolean has(final Player sender, final Player target) {
        return get(sender, target) != null;
    }

    public RequestImpl remove(final Player sender, final Player target) {
        final Map<UUID, RequestImpl> cached = get(sender, false);

        if (cached == null) {
            return null;
        }

        final RequestImpl request = cached.remove(target.getUniqueId());

        if (request == null) {
            return null;
        }

        if (System.currentTimeMillis() - request.getCreation() >= config.getExpiration() * 1000L) {
            cached.remove(target.getUniqueId());
            return null;
        }

        return request;
    }

    public void openRequestView(final Player viewer, final Player sender) {
        final RequestImpl request = get(sender, viewer);
        
        if (request == null) {
            lang.sendMessage(viewer, "ERROR.duel.no-request", "name", sender.getName());
            return;
        }

        final RequestViewGui gui = new RequestViewGui(plugin, viewer, request);
        plugin.getGuiListener().addGui(viewer, gui);
        gui.open(viewer);
    }

    @EventHandler
    public void on(final PlayerQuitEvent event) {
        requests.remove(event.getPlayer().getUniqueId());
    }
}
