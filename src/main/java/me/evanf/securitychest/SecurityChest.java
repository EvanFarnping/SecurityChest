package me.evanf.securitychest;

import me.evanf.securitychest.commands.GetSecurityChest;
import me.evanf.securitychest.listeners.ChestInteractions;
import me.evanf.securitychest.manager.ContainerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SecurityChest extends JavaPlugin {

    public static SecurityChest plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = getConfig();
        ContainerManager.init();

        this.getServer().getPluginManager().registerEvents(
                new ChestInteractions(), this);

        this.getCommand("getsecuritychest").setExecutor(new GetSecurityChest());

        this.getLogger().info("Enable Security Chest Plugin "
                + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabling Security Chest Plugin "
                + this.getDescription().getVersion());
    }
}
