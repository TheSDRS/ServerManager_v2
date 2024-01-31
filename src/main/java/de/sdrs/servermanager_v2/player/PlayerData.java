package de.sdrs.servermanager_v2.player;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerData implements PlayerActions {

    private HashMap<Object, Object> data = new HashMap<>();
    private HashMap<Object, Object> playerData = new HashMap<>();

    public PlayerData(Player player) {
        SMAPI smapi = new SMAPI();
        HashMap<Object, Object> tmpData = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        if (tmpData == null || !tmpData.containsKey(player.getUniqueId().toString())) {
            createPlayer(player);
        } else {
            playerData = (HashMap<Object, Object>) tmpData.get(player.getUniqueId().toString());
        }
    }

    private void createPlayer(Player player) {
        HashMap<Object, Object> pData = new HashMap<>();

        pData.put("name", player.getName());
        pData.put("location", player.getLocation());

        data.put(player.getUniqueId().toString(), pData);

        savePlayerData();
    }

    public void add(Object key,Object value) {
        playerData.put(key, value);
        data.replace(Bukkit.getPlayer((String) playerData.get("name")).getUniqueId().toString(), playerData);
        savePlayerData();
    }
    @Override
    public void savePlayerData() {
        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/players.yml", data);
    }

    @Override
    public void saveLocation() {
        playerData.replace("location", Bukkit.getPlayer((String) playerData.get("name")).getLocation());
        data.replace(Bukkit.getPlayer((String) playerData.get("name")).getUniqueId().toString(), playerData);
        savePlayerData();
    }
}
