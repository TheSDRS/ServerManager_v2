package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.permissions.Permission;
import de.sdrs.servermanager_v2.api.permissions.PermissionType;
import de.sdrs.servermanager_v2.plugin.commandExecutor.*;

public class Enable {

    public static void commands() {
        SMAPI.createCommand("servermanager", new smCMD(), new Permission(PermissionType.COMMAND,"servermanager"));
        SMAPI.createCommand("player", new playerCMD(), new Permission(PermissionType.COMMAND, "player"), new playerCMD());
        SMAPI.createCommand("role", new roleCMD(), new Permission(PermissionType.COMMAND, "role"), new roleCMD());
        SMAPI.createCommand("world", new worldCMD(), new Permission(PermissionType.COMMAND, "world"));
        SMAPI.createCommand("warp", new warpCMD(), new Permission(PermissionType.COMMAND, "warp"), new warpCMD());
    }
}
