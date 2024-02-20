package de.sdrs.servermanager_v2.api.roles;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Roles {
    public static void createRole(String name, String prefix) {
        SMAPI smapi = new SMAPI();

        HashMap<Object, Object> role = new HashMap<>();
        HashMap<Object, Object> roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        List<String> permissions = new ArrayList<>();

        role.put("name", name);
        role.put("prefix", prefix);
        role.put("permissions", permissions);

        if (roles == null) {
            HashMap<Object, Object> tmpRoles = new HashMap<>();
            tmpRoles.put(name.toLowerCase(), role);
            roles = tmpRoles;
        } else {
            roles.put(name.toLowerCase(), role);
        }

        smapi.writeToYAML(ServerManager.getDir() + "/roles.yml", roles);
    }
}
