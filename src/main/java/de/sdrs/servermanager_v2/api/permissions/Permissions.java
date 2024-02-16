package de.sdrs.servermanager_v2.api.permissions;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Permissions {

    public static HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
    private static PermissionAttachment attachment;

    public static Permission create(String permission) {
        Bukkit.getPluginManager().addPermission(new Permission(permission));
        return Bukkit.getPluginManager().getPermission(permission);
    }

    private static void addAttachment(Player player) {
        if (perms.containsKey(player.getUniqueId())) {
            attachment = perms.get(player.getUniqueId());
        } else {
            attachment = player.addAttachment(ServerManager.getPlugin());
            perms.put(player.getUniqueId(), attachment);
        }
    }

    private static PermissionAttachment getAttachment(Player player) {
        return perms.get(player.getUniqueId());
    }

    public static void addPermission(Player player, String permission) {
        addAttachment(player);
        attachment = getAttachment(player);
        attachment.setPermission(permission, true);
    }

    public static void removePermission(Player player, String permission) {
        addAttachment(player);
        attachment = getAttachment(player);
        attachment.setPermission(permission, false);
    }

    public static void loadPermissions(Player player) {
        PlayerActions playerActions = new PlayerData(player);
        List<Object> permissions = playerActions.getPermissions();
        for (Object perm : permissions) {
            addPermission(player, (String) perm);
            SMAPI.message().debug((String) perm);
        }
    }
}
