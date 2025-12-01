package com.meteordevelopments.duels.gui.request;

import com.meteordevelopments.duels.DuelsPlugin;
import com.meteordevelopments.duels.gui.request.buttons.RequestAcceptButton;
import com.meteordevelopments.duels.gui.request.buttons.RequestDenyButton;
import com.meteordevelopments.duels.gui.request.buttons.RequestInfoButton;
import com.meteordevelopments.duels.request.RequestImpl;
import com.meteordevelopments.duels.util.compat.Items;
import com.meteordevelopments.duels.util.gui.SinglePageGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RequestViewGui extends SinglePageGui<DuelsPlugin> {

    // CONFIGURACIÓN DE POSICIONES DE BOTONES
    // Modifica estos valores para cambiar dónde aparecen los botones en el menú
    // El menú tiene 3 filas (27 slots): Fila 1: 0-8, Fila 2: 9-17, Fila 3: 18-26
    private static final int DETAILS_SLOT = 13;  // Botón de información (centro)
    private static final int[] ACCEPT_SLOTS = {16};  // Botones de aceptar (derecha)
    private static final int[] DENY_SLOTS = {10};    // Botones de rechazar (izquierda)

    public RequestViewGui(final DuelsPlugin plugin, final Player viewer, final RequestImpl request) {
        super(plugin, getTitle(plugin, request), 3);
        
        // Fill background with black glass pane
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, Items.from("BLACK_STAINED_GLASS_PANE", (short) 0));
        }
        
        // Set info button
        set(DETAILS_SLOT, new RequestInfoButton(plugin, request));
        
        // Set accept buttons
        final RequestAcceptButton acceptButton = new RequestAcceptButton(plugin, request);
        for (int slot : ACCEPT_SLOTS) {
            set(slot, acceptButton);
        }
        
        // Set deny buttons
        final RequestDenyButton denyButton = new RequestDenyButton(plugin, request);
        for (int slot : DENY_SLOTS) {
            set(slot, denyButton);
        }
    }
    
    private static String getTitle(final DuelsPlugin plugin, final RequestImpl request) {
        String title = plugin.getLang().getMessage("GUI.request-view.title");
        
        // Get sender name
        final Player sender = Bukkit.getPlayer(request.getSender());
        final String senderName = sender != null ? sender.getName() : Bukkit.getOfflinePlayer(request.getSender()).getName();
        
        // Replace %sender% placeholder
        return title.replace("%sender%", senderName != null ? senderName : "Unknown");
    }
}
