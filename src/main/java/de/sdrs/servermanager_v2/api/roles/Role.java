package de.sdrs.servermanager_v2.api.roles;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.ServerManagerAPI;
import de.sdrs.servermanager_v2.api.permissions.Permissions;
import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Role implements PlayerRole {

    private HashMap<Object, Object> roles = new HashMap<>();
    private HashMap<Object,Object> role = new HashMap<>();
    private List<String> permissions = new ArrayList<>();
    private String ID;
    private Player player;
    private String color;

    public Role(String roleName, Player selPlayer) {
        ServerManagerAPI smapi = new SMAPI();
        ID = roleName.toLowerCase();
        roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        role = (HashMap<Object, Object>) roles.get(roleName.toLowerCase());
        permissions = (List<String>) role.get("permissions");
        player = selPlayer;
        color = (String) role.get("color");
    }

    public Role(String roleName) {
        ServerManagerAPI smapi = new SMAPI();
        ID = roleName.toLowerCase();
        roles = smapi.readFromYAML(ServerManager.getDir() + "/roles.yml");
        role = (HashMap<Object, Object>) roles.get(roleName.toLowerCase());
        permissions = (List<String>) role.get("permissions");
        color = (String) role.get("color");
    }

    private void save() {
        SMAPI smapi = new SMAPI();
        roles.replace(ID.toLowerCase(), role);
        smapi.writeToYAML(ServerManager.getDir() + "/roles.yml", roles);
    }

    @Override
    public String getName() {
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
        SMAPI smapi = new SMAPI();
        HashMap<Object, Object> data = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        permissions.add(permission);
        role.replace("permissions", permissions);
        for (Object key : data.keySet()) {
            HashMap<Object, Object> pData = (HashMap<Object, Object>) data.get(key);
            if (pData.get("role").equals(ID)) {
                unloadPermissions(Bukkit.getPlayer(UUID.fromString((String) key)));
                loadPermissions(Bukkit.getPlayer(UUID.fromString((String) key)));
            }
        }
        save();
    }

    @Override
    public void removePermission(String permission) {
        SMAPI smapi = new SMAPI();
        HashMap<Object, Object> data = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        permissions.remove(permission);
        role.replace("permissions", permissions);
        for (Object key : data.keySet()) {
            HashMap<Object, Object> pData = (HashMap<Object, Object>) data.get(key);
            if (pData.get("role").equals(ID)) {
                unloadPermissions(Bukkit.getPlayer(UUID.fromString((String) key)));
                loadPermissions(Bukkit.getPlayer(UUID.fromString((String) key)));
            }
        }
        save();
    }

    @Override
    public void setPrefix(String prefix) {
        role.replace("prefix", prefix);
        save();
    }

    @Override
    public void showPrefix() {
        PlayerActions playerActions = new PlayerData(player);
        playerActions.setListName(playerActions.getPrefix() + ChatColor.WHITE + "[" + color + getPrefix() + ChatColor.WHITE + "] " + color, playerActions.getSuffix());
    }

    @Override
    public void hidePrefix() {
        PlayerActions playerActions = new PlayerData(player);
        playerActions.setListName(playerActions.getPrefix().replace(ChatColor.WHITE + "[" + color + getPrefix() + ChatColor.WHITE + "] " + color, ""), playerActions.getSuffix());
    }

    @Override
    public String getPrefix() {
        return (String) role.get("prefix");
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void loadPermissions() {
        for (String perm : permissions) {
            Permissions.addPermission(player, perm);
        }
    }

    @Override
    public void loadPermissions(Player p) {
        for (String perm : permissions) {
            Permissions.addPermission(p, perm);
        }
    }

    @Override
    public void unloadPermissions() {
        for (String perm : permissions) {
            Permissions.removePermission(player, perm);
        }
    }

    @Override
    public void unloadPermissions(Player p) {
        for (String perm : permissions) {
            Permissions.removePermission(p, perm);
        }
    }
}
