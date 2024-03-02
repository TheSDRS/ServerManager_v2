package de.sdrs.servermanager_v2.api.messages.error;

import de.sdrs.servermanager_v2.api.permissions.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface Error {
    public Error Command(CommandSender sender, String usage);
    public Error PlayerNotFound(CommandSender sender, String playerName);
    public Error PlayerNotFound(String playerName);
    public Error PlayerNotOnline(CommandSender sender, String playerName);
    public Error needToBePlayer(CommandSender sender);
    public Error Generic(String msg);
    public Error TargetUnavailable(CommandSender sender);
    public Error TargetWorldUnavailable(CommandSender sender);
    public Error NotWarpOwner(Player player);
    public Error WarpExists(Player player);
    public Error NoWarpsFound();
    public Error permissionTypeNotFound(PermissionType type);
    public Error missingPermission(String permission, CommandSender sender);
    public Error PermissionNotFound(CommandSender sender, String permission);
    public Error RoleNotFound(CommandSender sender, String roleName);
    public Error RoleAlreadyExists(CommandSender sender, String roleName);
    public Error WorldAlreadyExists(CommandSender sender, String worldName);
    public Error WorldNotFound(CommandSender sender, String worldName);
}
