package me.evanf.securitychest.listeners;

import me.evanf.securitychest.SecurityChest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestInteractions implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() != Material.TRAPPED_CHEST) {
            return;
        }
        if (!(e.getBlock().getState() instanceof TileState)) {
            return;
        }
        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(
                ChatColor.WHITE + "Security Chest")
                && e.getItemInHand().getAmount() > 0) {

            TileState state = (TileState)e.getBlock().getState();
            PersistentDataContainer container = state.getPersistentDataContainer();

            NamespacedKey key = new NamespacedKey(
                    SecurityChest.getPlugin(SecurityChest.class),
                    "secure-chest");

            container.set(key, PersistentDataType.STRING,
                    e.getPlayer().getUniqueId().toString());

            state.update();

            e.getPlayer().sendMessage(ChatColor.ITALIC
                    + "Security Chest has been placed.");
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (!e.hasBlock()) {
            return;
        }
        if (e.getClickedBlock().getType() != Material.TRAPPED_CHEST) {
            return;
        }
        if (!(e.getClickedBlock().getState() instanceof TileState)) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            TileState state = (TileState)e.getClickedBlock().getState();
            PersistentDataContainer container = state.getPersistentDataContainer();

            NamespacedKey key = new NamespacedKey(
                    SecurityChest.getPlugin(SecurityChest.class),
                    "secure-chest");

            if (!container.has(key, PersistentDataType.STRING)) {
                return;
            }

            if ((e.getPlayer().getUniqueId().toString().equalsIgnoreCase(
                    container.get(key, PersistentDataType.STRING)))
                    || (e.getPlayer().isOp())
                    || hasPerm(e.getPlayer())) {
                e.setCancelled(false);
            }
            else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED
                        + "This chest is locked!");
            }
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != Material.TRAPPED_CHEST) {
            return;
        }
        if (!(e.getBlock().getState() instanceof TileState)) {
            return;
        }

        TileState state = (TileState)e.getBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(
                JavaPlugin.getPlugin(
                        SecurityChest.class), "secure-chest");

        if (!container.has(key, PersistentDataType.STRING)) {
            return;
        }

        if (e.getPlayer().getUniqueId().toString().equalsIgnoreCase(
                container.get(key, PersistentDataType.STRING))
                || e.getPlayer().isOp()
                || hasPerm(e.getPlayer())) {
            e.setCancelled(false);
        }
        else {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED
                    + "This chest can't be broken!");
        }
    }
    private boolean hasPerm(Player p) {
        if (SecurityChest.config.getBoolean("security_chest_perm")) {
            return p.hasPermission("security_chest_perm.access");
        }
        else {
            return true;
        }
    }
}