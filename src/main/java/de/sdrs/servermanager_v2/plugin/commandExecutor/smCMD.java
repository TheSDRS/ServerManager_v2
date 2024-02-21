package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class smCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.servermanager")) {
            sender.sendMessage(ChatColor.GOLD + "For help type [/help] documentation:" + ChatColor.UNDERLINE + " https://github.com/TheSDRS/ServerManager_v2/wiki");
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.servermanager", sender));
        }
        return true;
    }
}
