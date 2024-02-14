package de.sdrs.servermanager_v2.api.permissions;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class Permissions {

    public static HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

    public void create(String permission) {
        Bukkit.getPluginManager().addPermission(new Permission(permission));
    }
}
