package de.sdrs.servermanager_v2.api;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SMAPI extends JavaPlugin {
    public static String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ServerManager" + ChatColor.GOLD + "]: ";

    public static boolean register(Plugin plugin) {
        if (plugin.isEnabled() && plugin.isNaggable()) {
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
