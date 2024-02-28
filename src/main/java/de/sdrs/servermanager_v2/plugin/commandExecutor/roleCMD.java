package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import de.sdrs.servermanager_v2.api.player.Players;
import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class roleCMD implements CommandExecutor, TabCompleter {
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
                        } else if (args[1].equalsIgnoreCase("remove")) {
                            for (Player p : role.getPlayers()) {
                                PlayerActions playerActions = new PlayerData(p);
                                playerActions.setRole((String) SMAPI.config().getFromCFG(ConfigData.DefaultRole));
                            }
                            Roles.removeRole(role.getName());
                            sender.sendMessage(ChatColor.RED + "Removed role " + role.getColor() + role.getName());
                        } else {
                            SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} {arg}"));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/role {roleName} {arg}"));
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> arguments1 = new ArrayList<>();

        if (arguments1.isEmpty()) {
            arguments1.add("create");
            for (Object key : Roles.getAll().keySet()) {
                arguments1.add((String) key);
            }
        }

        List<String> results1 = new ArrayList<>();

        if (args.length == 1) {
            for (String a : arguments1) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    results1.add(a);
                }
            }
            return results1;
        }

        List<String> arguments2 = new ArrayList<>();

        if (arguments2.isEmpty() && !args[0].equalsIgnoreCase("create")) {
            arguments2.add("addPermission");
            arguments2.add("removePermission");
            arguments2.add("remove");
        }

        List<String> results2 = new ArrayList<>();

        if (args.length == 2) {
            for (String a : arguments2) {
                if (a.toLowerCase().startsWith(args[1].toLowerCase())) {
                    results2.add(a);
                }
            }
            return results2;
        }

        List<String> arguments3 = new ArrayList<>();
        if (arguments3.isEmpty()) {
            if(Roles.getAll().containsKey(args[0].toLowerCase())) {
                Role role = new Role(args[0]);
                if (args[1].equalsIgnoreCase("removePermission")) {
                    for (String perm : role.getPermissions()) {
                        arguments3.add(perm);
                    }
                }
            }
        }

        List<String> results3 = new ArrayList<>();

        if (args.length == 3 && args[1].equalsIgnoreCase("removePermission")) {
            for (String a : arguments3) {
                if (a.toLowerCase().startsWith(args[2].toLowerCase())) {
                    results3.add(a);
                }
            }
            return results3;
        }

        return null;
    }
}
