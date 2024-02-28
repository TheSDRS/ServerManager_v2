package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.world.SmapiWorld;
import de.sdrs.servermanager_v2.api.world.SmapiWorlds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

import static java.lang.Long.parseLong;

public class worldCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.world")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length >= 2) {
                        if (!SmapiWorlds.getAll().containsKey(args[1])) {
                            SmapiWorld smapiWorld = new SmapiWorld(args[1]);
                            if (args.length >= 3) {
                                smapiWorld.setEnvironment(World.Environment.valueOf(args[2]));
                                if (args.length >= 4) {
                                    smapiWorld.setWorldType(WorldType.valueOf(args[3]));
                                    if (args.length >= 5) {
                                        smapiWorld.setSeed(parseLong(args[4]));
                                    }
                                }
                            }
                            sender.sendMessage(ChatColor.GREEN + "Creating World " + ChatColor.GOLD + args[1] + ChatColor.GREEN + "...");
                            smapiWorld.load();
                            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "DONE!");
                        } else {
                            SMAPI.message().Error(new ErrorHandling().WorldAlreadyExists(sender, args[1]));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/world create {worldName}"));
                    }
                } else if (args[0].equalsIgnoreCase("import")) {
                    if (args.length == 2) {
                        if (!SmapiWorlds.getAll().containsKey(args[1])) {
                            sender.sendMessage(ChatColor.GREEN + "Importing World " + ChatColor.GOLD + args[1] + ChatColor.GREEN + "...");
                            SmapiWorlds.loadCustomWorld(args[1]);
                            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "DONE!");
                        } else {
                            SMAPI.message().Error(new ErrorHandling().WorldAlreadyExists(sender, args[1]));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/world import {worldName}"));
                    }
                } else if (args[0].equalsIgnoreCase("load")) {
                    if (args.length == 2) {
                        if (SmapiWorlds.getAll().containsKey(args[1])) {
                            SmapiWorld smapiWorld = new SmapiWorld(args[1]);
                            sender.sendMessage(ChatColor.GREEN + "Loading World " + ChatColor.GOLD + args[1] + ChatColor.GREEN + "...");
                            smapiWorld.load();
                            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "DONE!");
                        } else {
                            SMAPI.message().Error(new ErrorHandling().WorldNotFound(sender, args[1]));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/world load {worldName}"));
                    }
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length == 2) {
                        if (SmapiWorlds.getAll().containsKey(args[1])) {
                            sender.sendMessage(ChatColor.RED + "deleting world " + ChatColor.GRAY + args[1]);
                            SmapiWorld smapiWorld = new SmapiWorld(Objects.requireNonNull(Bukkit.getWorld(args[1])));
                            smapiWorld.delete();
                            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DONE!");
                        } else {
                            SMAPI.message().Error(new ErrorHandling().WorldNotFound(sender, args[1]));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().Command(sender, "/world delete {worldName}"));
                    }
                } else {
                    SMAPI.message().Error(new ErrorHandling().Command(sender, "/world {args}"));
                }
            } else {
                SMAPI.message().Error(new ErrorHandling().Command(sender, "/world {args}"));
            }
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.world", sender));
        }
        return true;
    }
}
