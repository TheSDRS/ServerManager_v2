package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class playerCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.player")) {
            if (args.length >= 1) {
                 if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                     Player player = Bukkit.getPlayer(args[0]);
                     PlayerActions playerActions = new PlayerData(player);
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("addPermission")) {
                            if (args.length == 3) {
                                playerActions.addPermission(args[2]);
                                sender.sendMessage(ChatColor.GREEN + "Added permission " + ChatColor.GRAY + args[2].toLowerCase() + ChatColor.GREEN + " to " + ChatColor.AQUA + player.getName());
                            } else {
                                SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName} addPermission {permission}"));
                            }
                        } else if (args[1].equalsIgnoreCase("removePermission")) {
                            if (args.length == 3) {
                                if (playerActions.getPermissions().contains(args[2].toLowerCase())) {
                                    playerActions.removePermission(args[2]);
                                    sender.sendMessage(ChatColor.RED + "Removed permission " + ChatColor.GRAY + args[2].toLowerCase() + ChatColor.RED + " from " + ChatColor.AQUA + player.getName());
                                } else {
                                    SMAPI.message().Error(new ErrorHandling().PermissionNotFound(sender, args[2]));
                                }
                            } else {
                                SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName} removePermission {permission}"));
                            }
                        } else if (args[1].equalsIgnoreCase("setRole")) {
                            if (args.length == 3) {
                                if (Roles.getAll().containsKey(args[2].toLowerCase())) {
                                    playerActions.setRole(args[2]);
                                    Role role = new Role(args[2]);
                                    sender.sendMessage(ChatColor.GREEN + "Changed the role of " + ChatColor.AQUA + player.getName() + ChatColor.GREEN + " to " + role.getColor() + role.getName());
                                } else {
                                    SMAPI.message().Error(new ErrorHandling().RoleNotFound(sender, args[2]));
                                }
                            } else {
                                SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName} setRole {roleName}"));
                            }
                        } else {
                            SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName} {addPermission | removePermission | setRole}"));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName} {addPermission | removePermission | setRole}"));
                    }
                 } else {
                     SMAPI.message().Error(new ErrorHandling().PlayerNotFound(sender, args[0]));
                 }
            } else {
                SMAPI.message().Error(new ErrorHandling().Command(sender, "/player {playerName}"));
            }
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.player", sender));
        }
        return true;
    }
}