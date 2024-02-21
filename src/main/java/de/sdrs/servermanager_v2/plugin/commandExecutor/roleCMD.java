package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class roleCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.role")) {
            if (args.length >= 1) {
                if (Roles.getAll().containsKey(args[0].toLowerCase())) {
                    Role role = new Role(args[0]);
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("addPermission")) {
                            if (args.length == 3) {
                                role.addPermission(args[2]);
                                sender.sendMessage(ChatColor.GREEN + "Added permission " + ChatColor.GRAY + args[2].toLowerCase() + ChatColor.GREEN + " to " + role.getColor() + role.getName());
                            } else {
                                SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} addPermission {permission}"));
                            }
                        } else if (args[1].equalsIgnoreCase("removePermission")) {
                            if (args.length == 3) {
                                if (role.getPermissions().contains(args[2])) {
                                    role.removePermission(args[2]);
                                    sender.sendMessage(ChatColor.RED + "Removed permission " + ChatColor.GRAY + args[2].toLowerCase() + ChatColor.RED + " from " + role.getColor() + role.getName());
                                } else {
                                    SMAPI.message().Error(new ErrorHandling().PermissionNotFound(sender, args[2]));
                                }
                            } else {
                                SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} removePermission {permission}"));
                            }
                        } else {
                            SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} {addPermission | removePermission}"));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} {addPermission | removePermission}"));
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length == 4) {
                        if (!Roles.getAll().containsKey(args[1])) {
                            String name = args[1];
                            String prefix = args[2];
                            String color = args[3].replace('&', '§');
                            Roles.createRole(name, prefix, color);
                            sender.sendMessage(ChatColor.GREEN + "Created new role " + color + name);
                        } else {
                            SMAPI.message().Error(new ErrorHandling().RoleAlreadyExists(sender, args[1]));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/role create {roleName} {rolePrefix} {roleColor}"));
                    }
                } else {
                    SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {create | [roleName]}"));
                }
            } else {
                SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {create | [roleName]}"));
            }
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.role", sender));
        }
        return true;
    }
}
