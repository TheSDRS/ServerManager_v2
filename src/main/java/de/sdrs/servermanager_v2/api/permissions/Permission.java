package de.sdrs.servermanager_v2.api.permissions;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    private static List<String> registeredPermissions = new ArrayList<>();

    public Permission(PermissionType type, String name) {
        if (type == PermissionType.COMMAND) {
            String permission = Permissions.create("servermanager.command." + name);
            registeredPermissions.add(permission);
        } else if (type == PermissionType.UTIL) {
            String permission = Permissions.create("servermanager.util." + name);
            registeredPermissions.add(permission);
        } else {
            SMAPI.message().Error(new ErrorHandling().permissionTypeNotFound(type));
        }
    }

    public Permission(PermissionType type, String name, String pluginName) {
        if (type == PermissionType.COMMAND) {
            Permissions.create(pluginName + ".command." + name);
        } else if (type == PermissionType.UTIL) {
            Permissions.create(pluginName + ".util." + name);
        } else {
            SMAPI.message().Error(new ErrorHandling().permissionTypeNotFound(type));
        }
    }

    public static List<String> getAllRegistered() {
        return registeredPermissions;
    }
}
