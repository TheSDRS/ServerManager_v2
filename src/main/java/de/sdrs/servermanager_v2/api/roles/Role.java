package de.sdrs.servermanager_v2.api.roles;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.ServerManagerAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Role implements PlayerRole {

    private HashMap<Object, Object> roles = new HashMap<>();
    private HashMap<Object,Object> role = new HashMap<>();
    private List<String> permissions = new ArrayList<>();
    private String ID;

    public Role(String roleName) {
        ServerManagerAPI smapi = new SMAPI();
        ID = roleName.toLowerCase();
        roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        role = (HashMap<Object, Object>) roles.get(roleName);
        permissions = (List<String>) role.get("permissions");
    }

    private void save() {
        SMAPI smapi = new SMAPI();
        roles.replace(ID.toLowerCase(), role);
        smapi.writeToYAML(ServerManager.getDir() + "/roles.yml", roles);
    }

    @Override
    public String Name() {
        return (String) role.get("name");
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public boolean hasPermission(String permission) {
        if (permissions.contains(permission)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addPermission(String permission) {
        permissions.add(permission);
        role.replace("permissions", permissions);
    }
}
