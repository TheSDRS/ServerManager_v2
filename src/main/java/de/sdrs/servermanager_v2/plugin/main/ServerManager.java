package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.permissions.Permission;
import de.sdrs.servermanager_v2.api.permissions.PermissionType;
import de.sdrs.servermanager_v2.plugin.eventListeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerManager extends JavaPlugin implements Listener {

    public static ServerManager plugin;
    @Override
    public void onEnable() {
        plugin = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(plugin, plugin);
        } else {
            SMAPI.message().MissingPlugin(Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));
        }
        SMAPI.register(this);
        SMAPI.createFiles();
        SMAPI.createCommand("test", new testCMD(), new Permission(PermissionType.COMMAND, "test"));
        SMAPI.createCommand("test2", new test2CMD(), new Permission(PermissionType.COMMAND, "test2"));
        SMAPI.message().debug(String.valueOf(Permission.getAllRegistered()));
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);
    }

    @Override
    public void onDisable() {
    }

    public static String getDir() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        return plugin.getDataFolder().getPath();
    }

    public static ServerManager getPlugin() {return plugin;}
}
