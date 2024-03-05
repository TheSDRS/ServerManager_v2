package de.sdrs.servermanager_v2.api.messages;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.Error;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


public class Messages implements Message, Listener {

    @Override
    public void Error(Error error) {

    }

    @Override
    public String PlayerMSG(PlayerMessage msg) {
        if (msg == PlayerMessage.Join) {
            return String.valueOf(SMAPI.config().getFromCFG(ConfigData.JoinMSG));
        } else if (msg == PlayerMessage.Leave) {
            return String.valueOf(SMAPI.config().getFromCFG(ConfigData.LeaveMSG));
        } else if (msg == PlayerMessage.Death) {
            return String.valueOf(SMAPI.config().getFromCFG(ConfigData.DeathMSG));
        }
        return "Custom Message";
    }

    @Override
    public void MissingPlugin(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(SMAPI.prefix + ChatColor.RED + message + " is missing! It is required!");
    }

    @Override
    public void debug(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(SMAPI.prefix + ChatColor.GRAY + "DEBUG " + ChatColor.WHITE + msg);
    }

    @Override
    public void info(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(SMAPI.prefix + ChatColor.GRAY + "INFO " + ChatColor.WHITE + msg);
    }
}
