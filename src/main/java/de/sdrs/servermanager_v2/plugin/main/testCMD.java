package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class testCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        PlayerData playerData = new PlayerData((Player) sender);
        Bukkit.getConsoleSender().sendMessage(playerData.toString());
        List data = PlayerData.get();
        for (Object i : data) {
            Bukkit.getConsoleSender().sendMessage((String) i);
        }
        return true;
    }
}
