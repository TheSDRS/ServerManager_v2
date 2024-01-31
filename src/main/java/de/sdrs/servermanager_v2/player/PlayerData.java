package de.sdrs.servermanager_v2.player;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerData implements PlayerActions {

    private static HashMap<Object, Object> data = new HashMap<>();
    private static HashMap<Object, Object> playerData = new HashMap<>();
    private String playerID;

    public PlayerData(Player player) {
        SMAPI smapi = new SMAPI();
        HashMap<Object, Object> tmpData = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        playerID = player.getUniqueId().toString();
        if (tmpData == null || !tmpData.containsKey(playerID)) {
            createPlayer(player);
        } else {
            playerData = (HashMap<Object, Object>) tmpData.get(playerID);
        }
    }

    private void createPlayer(Player player) {
        HashMap<Object, Object> pData = new HashMap<>();
        HashMap<Object,Object> location = new HashMap<>();

        location.put("world", player.getWorld().getName());
        location.put("x", player.getLocation().getX());
        location.put("y", player.getLocation().getY());
        location.put("z", player.getLocation().getZ());
        location.put("yaw", player.getLocation().getYaw());
        location.put("pitch", player.getLocation().getPitch());

        pData.put("name", player.getName());
        pData.put("location", location);

        data.put(playerID, pData);

        savePlayerData();
    }

    public void add(Object key,Object value) {
        playerData.put(key, value);
        data.replace(playerID, playerData);
        savePlayerData();
    }

    public static List<String> get() {
        List<String> tmp = new ArrayList<>();
        for (Object key : data.keySet()) {
            tmp.add((String) data.get(key));
        }
        for (Object key : data.keySet()) {
            tmp.add((String) playerData.get(key));
        }
        return tmp;
    }
    @Override
    public void savePlayerData() {
        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/players.yml", data);
    }

    @Override
    public void saveLocation() {
        playerData.replace("location", Bukkit.getPlayer((String) playerData.get("name")).getLocation());
        data.replace(playerID, playerData);
        savePlayerData();
    }
}
