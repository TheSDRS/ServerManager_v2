package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.eventListeners.JoinListener;
import de.sdrs.servermanager_v2.plugin.eventListeners.PostLoadListener;
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
            SMAPI.message().MissingPlugin("PlaceholderAPI");
        }
        SMAPI.register(this);
        SMAPI.createFiles();
        SMAPI.runChecks();
        Enable.commands();
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PostLoadListener(), plugin);
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
