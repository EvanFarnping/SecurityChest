package me.evanf.securitychest.commands;

import me.evanf.securitychest.SecurityChest;
import me.evanf.securitychest.manager.ContainerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetSecurityChest implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        else {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("getsecuritychest")) {
                if (player.isOp() || hasPerm(player)) {
                    player.getInventory().addItem(
                            new ItemStack(ContainerManager.SecurityChestItem));
                }
                else {
                    player.sendMessage(ChatColor.RED
                            + "You do not have access to this command.");
                }
                return true;
            }
        }
        return false;
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