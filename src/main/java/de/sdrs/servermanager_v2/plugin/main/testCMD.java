package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.player.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PlayerData playerData = new PlayerData((Player) sender);
        return false;
    }
}
