package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.world.SmapiWorld;
import de.sdrs.servermanager_v2.api.world.SmapiWorlds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class smCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.servermanager")) {
            if (args.length == 1) {
                if (args[0].equals("test")) {
                    SmapiWorlds.loadCustomWorld("Lobby");
                } else if (args[0].equalsIgnoreCase("test2")) {
                    SmapiWorlds.loadCustomWorld("Lobby");
                } else if (args[0].equalsIgnoreCase("send")) {
                    Player player = (Player) sender;
                    player.teleport(Bukkit.getWorld("Lobby").getSpawnLocation());
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "For help type [/help] documentation:" + ChatColor.UNDERLINE + " https://github.com/TheSDRS/ServerManager_v2/wiki");
            }
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.servermanager", sender));
        }
        return true;
    }
}
