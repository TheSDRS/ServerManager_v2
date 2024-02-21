package de.sdrs.servermanager_v2.api.roles;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Roles {
    public static void createRole(String name, String prefix, ChatColor color) {
        SMAPI smapi = new SMAPI();

        HashMap<Object, Object> role = new HashMap<>();
        HashMap<Object, Object> roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        List<String> permissions = new ArrayList<>();

        role.put("name", name);
        role.put("prefix", prefix);
        role.put("color", String.valueOf(color));
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

    public static void createRole(String name, String prefix, String color) {
        SMAPI smapi = new SMAPI();

        HashMap<Object, Object> role = new HashMap<>();
        HashMap<Object, Object> roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        List<String> permissions = new ArrayList<>();

        role.put("name", name);
        role.put("prefix", prefix);
        role.put("color", color);
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

    public static void checkFiles() {
        SMAPI smapi = new SMAPI();
        if (smapi.readFromYAML(ServerManager.getDir() + "/roles.yml") == null) {
            createRole("Player", "Player", ChatColor.GREEN);
            createRole("Moderator", "Mod", ChatColor.RED);
            createRole("Admin", "Admin", ChatColor.DARK_RED);
        } else if (smapi.readFromYAML(ServerManager.getDir() + "/roles.yml").isEmpty()) {
            createRole("Player", "Player", ChatColor.GREEN);
            createRole("Moderator", "Mod", ChatColor.RED);
            createRole("Admin", "Admin", ChatColor.DARK_RED);
        }
    }

    public static HashMap<Object, Object> getAll() {
        SMAPI smapi = new SMAPI();
        return smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
    }
}
