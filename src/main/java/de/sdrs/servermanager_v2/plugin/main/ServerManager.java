package de.sdrs.servermanager_v2.plugin.main;

import org.bukkit.plugin.java.JavaPlugin;
import de.sdrs.servermanager_v2.api.SMAPI;

public final class ServerManager extends JavaPlugin {

    public static ServerManager plugin;
    @Override
    public void onEnable() {
        plugin = this;
        SMAPI.register(this);
    }

    @Override
    public void onDisable() {
        SMAPI.unregister(this);
    }

    public static String getDir() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        return plugin.getDataFolder().getPath();
    }
}
