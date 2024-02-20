package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.roles.Roles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class testCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Roles.createRole("Admin", ChatColor.RED + "Admin");
        return true;
    }
}
