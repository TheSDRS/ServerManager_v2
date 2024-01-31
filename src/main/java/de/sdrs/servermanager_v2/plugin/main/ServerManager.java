package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.SMAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerManager extends JavaPlugin {

    public static ServerManager plugin;
    @Override
    public void onEnable() {
        plugin = this;
        SMAPI.register(this);
        this.saveResource("players.yml", false);
        this.getCommand("test").setExecutor(new testCMD());
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
}
