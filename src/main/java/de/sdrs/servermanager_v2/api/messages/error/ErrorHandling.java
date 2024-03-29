package de.sdrs.servermanager_v2.api.messages.error;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.permissions.PermissionType;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ErrorHandling implements Error {
    @Override
    public Error Command(CommandSender sender, String usage) {
        sender.sendMessage(ChatColor.RED + "Please use " + usage);
        return null;
    }

    @Override
    public Error PlayerNotFound(CommandSender sender, String playerName) {
        sender.sendMessage(ChatColor.RED + playerName + "was not found");
        return null;
    }

    @Override
    public Error PlayerNotFound(String playerName) {
        Bukkit.getConsoleSender().sendMessage(SMAPI.prefix + ChatColor.RED + playerName + " was not found");
        return null;
    }

    @Override
    public Error PlayerNotOnline(CommandSender sender, String playerName) {
        sender.sendMessage(ChatColor.RED + "Player " + playerName + " is currently not online");
        return null;
    }

    @Override
    public Error needToBePlayer(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You need to be a Player to do that!");
        return null;
    }

    @Override
    public Error Generic(String msg) {
        Bukkit.getConsoleSender().sendMessage(SMAPI.prefix + ChatColor.RED + "ERROR " + msg);
        return null;
    }

    @Override
    public Error TargetUnavailable(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "The target is currently unavailable");
        return null;
    }

    @Override
    public Error TargetWorldUnavailable(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "The target world is currently unavailable");
        return null;
    }

    @Override
    public Error NotWarpOwner(Player player) {
        player.sendMessage(ChatColor.RED + "Your not the warp Owner");
        return null;
    }

    @Override
    public Error WarpExists(Player player) {
        player.sendMessage(ChatColor.RED + "That warp already exists!");
        return null;
    }

    @Override
    public Error NoWarpsFound() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No warps were found");
        return null;
    }

    @Override
    public Error permissionTypeNotFound(PermissionType type) {
        Bukkit.getServer().getConsoleSender().sendMessage(  SMAPI.prefix + ChatColor.RED + "PermissionType " + type + " was not found!");
        return null;
    }

    @Override
    public Error missingPermission(String permission, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "you need following permission to do that: " + permission);
        return null;
    }

    @Override
    public Error PermissionNotFound(CommandSender sender, String permission) {
        sender.sendMessage(ChatColor.RED + "target player already hasn't the permission " + permission);
        return null;
    }

    @Override
    public Error RoleNotFound(CommandSender sender, String roleName) {
        sender.sendMessage(ChatColor.RED + "The role " + roleName + " was not found");
        return null;
    }

    @Override
    public Error RoleAlreadyExists(CommandSender sender, String roleName) {
        sender.sendMessage(ChatColor.RED + "The role " + roleName + " already exists");
        return null;
    }

    @Override
    public Error WorldAlreadyExists(CommandSender sender, String worldName) {
        sender.sendMessage(ChatColor.RED + "The world " + worldName + " already exists");
        return null;
    }

    @Override
    public Error WorldNotFound(CommandSender sender, String worldName) {
        sender.sendMessage(ChatColor.RED + "The world " + worldName + " wasn't found");
        return null;
    }
}
