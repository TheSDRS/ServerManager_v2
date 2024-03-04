package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.warps.Warp;
import de.sdrs.servermanager_v2.api.warps.Warps;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class warpCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("servermanager.command.warp")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (args.length == 2) {
                            if (!Warps.getWarpObjects().containsKey(args[1])) {
                                Warp warp = new Warp(args[1]);
                                warp.setOwner(player.getName());
                                warp.setLocation(player.getLocation());
                                warp.save();
                            }
                        }
                    }
                } else if (Warps.getWarpObjects().containsKey(args[0].toLowerCase())) {
                    String warpName = args[0];
                    Warp warp = new Warp(warpName);
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("info")) {
                            sender.sendMessage(ChatColor.GREEN + "Info to warp " + warp.getName() + ":");
                            sender.sendMessage(ChatColor.GOLD + "Owner: " + warp.getOwner());
                            sender.sendMessage(ChatColor.GOLD + "Visits: " + warp.getVisits());
                            sender.sendMessage(ChatColor.GOLD + "Location: ");
                            sender.sendMessage(ChatColor.GOLD + "  World: " + Objects.requireNonNull(warp.getLocation().getWorld()).getName());
                            sender.sendMessage(ChatColor.GOLD + "  X: " +  warp.getLocation().getBlockX());
                            sender.sendMessage(ChatColor.GOLD + "  Y: " + warp.getLocation().getBlockY());
                            sender.sendMessage(ChatColor.GOLD + "  Z: " + warp.getLocation().getBlockZ());
                            sender.sendMessage(ChatColor.GOLD + "  Yaw: " + warp.getLocation().getYaw());
                            sender.sendMessage(ChatColor.GOLD + "  Pitch: " + warp.getLocation().getPitch());
                        } else if (args[1].equalsIgnoreCase("delete")) {
                            if (sender.getName().equals(warp.getOwner()) || sender.getName().equals("CONSOLE")) {
                                warp.delete();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
