package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import de.sdrs.servermanager_v2.api.util.trees.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class playerCMD implements CommandExecutor, TabCompleter {

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


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        Tree tree = new TabTree("playerTabTree");
        tree.addCompletionObjectAsBranch(CompletionObject.Player);
        for (Branch branch : tree.getBranchesWithCO(CompletionObject.Player)) {
            branch.addBranch(new TabBranch("addPermission")).condition(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])));
            branch.addBranch(new TabBranch("removePermission")).condition(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])));
            branch.addBranch(new TabBranch("setRole")).condition(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])));

            branch.getBranch("addPermission").addCompletionObjectAsLeaf(CompletionObject.Permission);
            branch.getBranch("removePermission").addCompletionObjectAsLeaf(CompletionObject.Permission);
            branch.getBranch("setRole").addCompletionObjectAsLeaf(CompletionObject.Role);
        }
        /*List<String> arguments1 = new ArrayList<>();

        if (arguments1.isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                arguments1.add(player.getName());
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

        if (arguments2.isEmpty()) {
            arguments2.add("addPermission");
            arguments2.add("removePermission");
            arguments2.add("setRole");
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
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                PlayerActions playerActions = new PlayerData(Bukkit.getPlayer(args[0]));
                if (args[1].equalsIgnoreCase("removePermission")) {
                    for (Object perm : playerActions.getPermissions()) {
                        arguments3.add((String) perm);
                    }
                } else if (args[1].equalsIgnoreCase("setRole")) {
                    for (Object key : Roles.getAll().keySet()) {
                        arguments3.add((String) key);
                    }
                }
            }
        }

        List<String> results3 = new ArrayList<>();

        if (args.length == 3) {
            for (String a : arguments3) {
                if (a.toLowerCase().startsWith(args[2].toLowerCase())) {
                    results3.add(a);
                }
            }
            return results3;
        }*/

        return tree.complete(sender, command, label, args);
    }
}
