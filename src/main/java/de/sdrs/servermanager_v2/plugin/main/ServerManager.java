package de.sdrs.servermanager_v2.plugin.main;

import org.bukkit.plugin.java.JavaPlugin;
import de.sdrs.servermanager_v2.api.SMAPI;

public final class ServerManager extends JavaPlugin {

    public static ServerManager plugin;
    @Override
    public void onEnable() {
        plugin = this;
        SMAPI.register(this);
        plugin.getServer().getConsoleSender().sendMessage(SMAPI.prefix);
    }

    @Override
    public void onDisable() {
        SMAPI.unregister(this);
    }
}
