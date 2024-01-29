package de.sdrs.servermanager_v2.api;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SMAPI extends JavaPlugin {
    public static String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ServerManager" + ChatColor.GOLD + "]: ";
    public static Boolean isCoreRegistered = false;

    public static boolean register(Plugin plugin) {
        if (plugin.isEnabled() && plugin.isNaggable()) {
            if (plugin.getName() == "servermanager_v2") {
                plugin.getServer().getConsoleSender().sendMessage(prefix + "registered Core Plugin");
                isCoreRegistered = true;
                return true;
            }
            plugin.getServer().getConsoleSender().sendMessage(prefix + ChatColor.GREEN + plugin.getName() + " was successfully registered");
            return true;
        } else {
            return false;
        }
    }

    public static boolean unregister(Plugin plugin) {
        if (plugin.isEnabled() && plugin.isNaggable()) {
            plugin.getServer().getConsoleSender().sendMessage(prefix + ChatColor.RED + plugin.getName() + " was successfully unregistered");
            return true;
        } else {
            return false;
        }
    }
}
