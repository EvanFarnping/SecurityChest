package me.evanf.securitychest.manager;

import me.evanf.securitychest.SecurityChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ContainerManager {

    public static ItemStack SecurityChestItem;

    public static void init() {
        getSecurityChest();
    }

    private static void getSecurityChest() {
        ItemStack container = new ItemStack(Material.TRAPPED_CHEST);
        ItemMeta meta = container.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Security Chest");
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY
                + "A special unbreakable chest that only you can use!");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        container.setItemMeta(meta);

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("s-chest"),
                container);

        Material center_material = Material.valueOf(
                SecurityChest.config.getString(
                "security_chest_recipe.center_slot"));
        Material corner_materials = Material.valueOf(
                SecurityChest.config.getString(
                "security_chest_recipe.corner_slots"));
        Material top_center_material = Material.valueOf(
                SecurityChest.config.getString(
                "security_chest_recipe.center_top_slot"));
        Material side_and_bottom_materials = Material.valueOf(
                SecurityChest.config.getString(
                "security_chest_recipe.side_and_bottom_slot"));

        sr.shape("CTC",
                 "RXR",
                 "CRC");
        sr.setIngredient('T', top_center_material);
        sr.setIngredient('R', side_and_bottom_materials);
        sr.setIngredient('C', corner_materials);
        sr.setIngredient('X', center_material);
        Bukkit.getServer().addRecipe(sr);

        SecurityChestItem = container;
    }
}
