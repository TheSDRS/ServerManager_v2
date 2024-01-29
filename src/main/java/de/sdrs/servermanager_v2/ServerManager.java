package de.sdrs.servermanager_v2;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("lol");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
