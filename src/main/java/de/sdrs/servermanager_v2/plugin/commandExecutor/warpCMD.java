package de.sdrs.servermanager_v2.plugin.commandExecutor;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import de.sdrs.servermanager_v2.api.warps.Warp;
import de.sdrs.servermanager_v2.api.warps.Warps;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Comparator;
import java.util.List;

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
                            } else {
                                SMAPI.message().Error(new ErrorHandling().WarpExists(player));
                            }
                        } else {
                            SMAPI.message().Error(new ErrorHandling().Command(sender, "/warp create [warpName]"));
                        }
                    } else {
                        SMAPI.message().Error(new ErrorHandling().needToBePlayer(sender));
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
                            } else {
                                SMAPI.message().Error(new ErrorHandling().NotWarpOwner((Player) sender));
                            }
                        } else if (args[1].equalsIgnoreCase("set")) {
                            if (sender instanceof Player) {
                                Player player = (Player) sender;
                                if (player.getName().equals(warp.getOwner())) {
                                    warp.setLocation(player.getLocation());
                                } else {
                                    SMAPI.message().Error(new ErrorHandling().NotWarpOwner(player));
                                }
                            } else {
                                SMAPI.message().Error(new ErrorHandling().needToBePlayer(sender));
                            }
                        } else {
                            SMAPI.message().Error(new ErrorHandling().Command(sender, "/warp [warpName] [args]"));
                        }
                    } else {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            player.teleport(warp.getLocation());
                            warp.setVisits(warp.getVisits() + 1);
                            warp.save();
                        } else {
                            SMAPI.message().Error(new ErrorHandling().needToBePlayer(sender));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    Object[] tmpIDs = Warps.getAllIDs().toArray();
                    sender.sendMessage(ChatColor.GREEN + "A list of some warps:");
                    sender.sendMessage("----------------------");
                    int count = 1;
                    if (tmpIDs.length < (int) SMAPI.config().getFromCFG(ConfigData.maxWarpListLength)) {
                        for (Object o : Warps.getWarpObjects().keySet()) {
                            HashMap<Object, Object> tmp = (HashMap<Object, Object>) Warps.getWarpObjects().get(o);
                            sender.sendMessage(ChatColor.GOLD + " " + count + ". " + tmp.get("name"));
                            count++;
                        }
                    } else {
                        for (int i = 0; i < (int) SMAPI.config().getFromCFG(ConfigData.maxWarpListLength) + 1; i++) {
                            HashMap<Object, Object> tmp = (HashMap<Object, Object>) Warps.getWarpObjects().get(tmpIDs[i]);
                            sender.sendMessage(ChatColor.GOLD + " " + i + ". " + tmp.get("name"));
                        }
                    }

                } else if (args[0].equalsIgnoreCase("top")) {
                    HashMap<String, String> tmpMap = new HashMap<>();
                    LinkedHashMap<Object, Object> sortedMap = new LinkedHashMap<>();

                    for (Object o : Warps.getWarpObjects().keySet()) {
                        HashMap<Object, Object> tmpWarpMap = (HashMap<Object, Object>) Warps.getWarpObjects().get(o);
                        tmpMap.put((String) o, tmpWarpMap.get("visits").toString());
                    }

                    List<String> list = new ArrayList<>();
                    for (Map.Entry<String, String> o : tmpMap.entrySet()) {
                        list.add(o.getValue().toString());
                    }
                    Collections.sort(list, new Comparator<String>() {
                        public int compare(String str, String str2) {
                            return (str2).compareTo(str);
                        }
                    });
                    for (String str : list) {
                        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
                            if (entry.getValue().equals(str)) {
                                sortedMap.put(entry.getKey(), str);
                            }
                        }
                    }

                    sender.sendMessage("----------------------");
                    sender.sendMessage(ChatColor.GREEN + "The top warps ware:");
                    sender.sendMessage("----------------------");

                    int count = 1;
                    if (list.size() < (int) SMAPI.config().getFromCFG(ConfigData.maxWarpListLength)) {
                        for (Object o : sortedMap.entrySet()) {
                            String tmpStr = StringUtils.substringBefore(o.toString(), "=");
                            HashMap<Object, Object> tmp = (HashMap<Object, Object>) Warps.getWarpObjects().get(tmpStr);
                            sender.sendMessage(ChatColor.GOLD + "  " + count + ". " + tmp.get("name"));
                            count++;
                        }
                    } else {
                        for (int i = 0; i < (int) SMAPI.config().getFromCFG(ConfigData.maxWarpListLength) + 1; i++) {
                            String name = list.get(i);
                            name = StringUtils.substringBefore(name, "=");
                            HashMap<Object, Object> tmp = (HashMap<Object, Object>) Warps.getWarpObjects().get(name);
                            sender.sendMessage(ChatColor.GOLD + "  " + count + ". " + tmp.get("name"));
                            count++;
                        }
                    }
                    SMAPI.message().debug(sortedMap.toString());
                } else {
                    SMAPI.message().Error(new ErrorHandling().Command(sender, "/warp [args]"));
                }
            } else {
                SMAPI.message().Error(new ErrorHandling().Command(sender, "/warp [args]"));
            }
        } else {
            SMAPI.message().Error(new ErrorHandling().missingPermission("servermanager.command.warp", sender));
        }
        return true;
    }
}
