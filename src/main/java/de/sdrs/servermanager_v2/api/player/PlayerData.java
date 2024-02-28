package de.sdrs.servermanager_v2.api.player;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.PlayerMessage;
import de.sdrs.servermanager_v2.api.permissions.Permissions;
import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerData implements PlayerActions {

    private static HashMap<Object, Object> data = new HashMap<>();
    private static HashMap<Object, Object> playerData = new HashMap<>();
    private static List<Object> permissions = new ArrayList<>();
    private String playerID;
    private Player currentPlayer;
    private String playerPrefix;
    private String playerSuffix;

    public PlayerData(Player player) {
        SMAPI smapi = new SMAPI();
        data = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        playerID = player.getUniqueId().toString();
        currentPlayer = player;
        if (data == null) {
            data = createFirstPlayer(player);
            save();
        } else if (!data.containsKey(playerID)) {
            createPlayer(player);
        } else {
            playerData = (HashMap<Object, Object>) data.get(playerID);
            permissions = (List<Object>) playerData.get("permissions");
            playerPrefix = (String) playerData.get("prefix");
            playerSuffix = (String) playerData.get("suffix");
        }
    }

    private void createPlayer(Player player) {


        playerData.put("name", player.getName());
        playerData.put("prefix", "");
        playerData.put("suffix", "");
        playerData.put("location", location());
        playerData.put("role", SMAPI.config().getFromCFG(ConfigData.DefaultRole));
        playerData.put("permissions", permissions);

        data.put(playerID, playerData);

        save();
    }

    private HashMap<Object, Object> createFirstPlayer(Player player) {
        HashMap<Object, Object> tmpData = new HashMap<>();

        playerData.put("name", player.getName());
        playerData.put("prefix", "");
        playerData.put("suffix", "");
        playerData.put("location", location());
        playerData.put("permissions", permissions);
        playerData.put("role", SMAPI.config().getFromCFG(ConfigData.DefaultRole));

        tmpData.put(playerID, playerData);
        return tmpData;
    }

    @Override
    public void save() {
        SMAPI smapi = new SMAPI();
        data.replace(playerID, playerData);
        smapi.writeToYAML(ServerManager.getDir() + "/players.yml", data);
    }

    @Override
    public void saveLocation() {
        playerData.replace("location", location());
        save();
    }

    @Override
    public HashMap<Object, Object> location() {
        HashMap<Object,Object> location = new HashMap<>();

        location.put("world", currentPlayer.getWorld().getName());
        location.put("x", currentPlayer.getLocation().getX());
        location.put("y", currentPlayer.getLocation().getY());
        location.put("z", currentPlayer.getLocation().getZ());
        location.put("yaw", currentPlayer.getLocation().getYaw());
        location.put("pitch", currentPlayer.getLocation().getPitch());

        return location;
    }

    @Override
    public void print() {
        SMAPI.message().info(String.valueOf(playerData));
    }

    @Override
    public void playerJoined(PlayerJoinEvent e) {
        Permissions.loadPermissions(e.getPlayer());
        setRole(getRole());
        e.setJoinMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), SMAPI.message().PlayerMSG(PlayerMessage.Join)));
    }

    @Override
    public void addPermission(String permission) {
        permissions.add(permission);
        Permissions.addPermission(currentPlayer, permission);
        save();
    }

    @Override
    public void removePermission(String permission) {
        permissions.remove(permission);
        Permissions.removePermission(currentPlayer, permission);
        save();
    }

    @Override
    public List<Object> getPermissions() {
        return permissions;
    }

    @Override
    public HashMap<Object, Object> getSavedLocation() {
        HashMap<Object, Object> location = (HashMap<Object, Object>) playerData.get("location");
        return location;
    }

    @Override
    public void setRole(String roleName) {
        Role prevRole = new Role(getRole(), currentPlayer);
        prevRole.unloadPermissions();
        prevRole.hidePrefix();
        Role role = new Role(roleName, currentPlayer);
        role.loadPermissions();
        role.showPrefix();
        playerData.replace("role", roleName.toLowerCase());
        save();
    }

    @Override
    public String getRole() {
        return (String) playerData.get("role");
    }

    @Override
    public void setListName(String prefix, String suffix) {
        currentPlayer.setPlayerListName(prefix + currentPlayer.getName() + suffix);
    }

    @Override
    public String getPrefix() {
        return playerPrefix;
    }

    @Override
    public String getSuffix() {
        return playerSuffix;
    }
}
