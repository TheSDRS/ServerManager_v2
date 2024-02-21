package de.sdrs.servermanager_v2.plugin.main;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.permissions.Permission;
import de.sdrs.servermanager_v2.api.permissions.PermissionType;
import de.sdrs.servermanager_v2.plugin.commandExecutor.playerCMD;
import de.sdrs.servermanager_v2.plugin.commandExecutor.roleCMD;
import de.sdrs.servermanager_v2.plugin.commandExecutor.smCMD;

public class Enable {

    public static void commands() {
        SMAPI.createCommand("servermanager", new smCMD(), new Permission(PermissionType.COMMAND,"servermanager"));
        SMAPI.createCommand("player", new playerCMD(), new Permission(PermissionType.COMMAND, "player"));
        SMAPI.createCommand("role", new roleCMD(), new Permission(PermissionType.COMMAND, "role"));
    }
}
