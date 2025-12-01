package com.meteordevelopments.duels.arena;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;

/**
 * Manages arena snapshots for regeneration using FastAsyncWorldEdit
 */
public class ArenaSnapshot {
    
    private final ArenaImpl arena;
    private Clipboard clipboard;
    private BlockVector3 origin;
    
    public ArenaSnapshot(ArenaImpl arena) {
        this.arena = arena;
    }
    
    /**
     * Save the current state of the arena
     * @return true if successful, false otherwise
     */
    public boolean save() {
        try {
            Location bound1 = arena.getBound1();
            Location bound2 = arena.getBound2();
            
            if (bound1 == null || bound2 == null) {
                return false;
            }
            
            // Convert Bukkit locations to WorldEdit positions
            com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(bound1.getWorld());
            BlockVector3 pos1 = BlockVector3.at(bound1.getBlockX(), bound1.getBlockY(), bound1.getBlockZ());
            BlockVector3 pos2 = BlockVector3.at(bound2.getBlockX(), bound2.getBlockY(), bound2.getBlockZ());
            
            // Create region
            CuboidRegion region = new CuboidRegion(world, pos1, pos2);
            this.origin = region.getMinimumPoint();
            
            // Create clipboard
            BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
            
            // Copy blocks to clipboard
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                ForwardExtentCopy copy = new ForwardExtentCopy(
                    editSession, region, clipboard, region.getMinimumPoint()
                );
                copy.setCopyingEntities(false); // Don't copy entities for performance
                copy.setCopyingBiomes(false);
                Operations.complete(copy);
            }
            
            this.clipboard = clipboard;
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Restore the arena to its saved state
     * @return true if successful, false otherwise
     */
    public boolean restore() {
        if (clipboard == null || origin == null) {
            return false;
        }
        
        try {
            Location bound1 = arena.getBound1();
            if (bound1 == null) {
                return false;
            }
            
            com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(bound1.getWorld());
            
            // Paste the clipboard
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                ForwardExtentCopy copy = new ForwardExtentCopy(
                    clipboard, clipboard.getRegion(), origin, editSession, origin
                );
                copy.setCopyingEntities(false);
                Operations.complete(copy);
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if this arena has a saved snapshot
     * @return true if snapshot exists
     */
    public boolean hasSnapshot() {
        return clipboard != null && origin != null;
    }
    
    /**
     * Clear the saved snapshot
     */
    public void clear() {
        this.clipboard = null;
        this.origin = null;
    }
}
