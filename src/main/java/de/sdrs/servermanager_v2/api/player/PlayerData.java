package de.sdrs.servermanager_v2.api.player;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.PlayerMessage;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Objects;

public class PlayerData implements PlayerActions {

    private static HashMap<Object, Object> data = new HashMap<>();
    private static HashMap<Object, Object> playerData = new HashMap<>();
    private String playerID;

    public PlayerData(Player player) {
        SMAPI smapi = new SMAPI();
        data = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        playerID = player.getUniqueId().toString();
        if (data == null || !data.containsKey(playerID)) {
            createPlayer(player);
        } else {
            playerData = (HashMap<Object, Object>) data.get(playerID);
        }
    }

    private void createPlayer(Player player) {
        HashMap<Object,Object> location = new HashMap<>();

        location.put("world", player.getWorld().getName());
        location.put("x", player.getLocation().getX());
        location.put("y", player.getLocation().getY());
        location.put("z", player.getLocation().getZ());
        location.put("yaw", player.getLocation().getYaw());
        location.put("pitch", player.getLocation().getPitch());

        playerData.put("name", player.getName());
        playerData.put("location", location);

        data.put(playerID, playerData);

        save();
    }

    public void add(Object key,Object value) {
        playerData.put(key, value);
        data.replace(playerID, playerData);
        save();
    }
    @Override
    public void save() {
        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/players.yml", data);
    }

    @Override
    public void saveLocation() {
        playerData.replace("location", Objects.requireNonNull(Bukkit.getPlayer((String) playerData.get("name"))).getLocation());
        data.replace(playerID, playerData);
        save();
    }

    @Override
    public void print() {
        Bukkit.getConsoleSender().sendMessage(String.valueOf(playerData));
    }

    @Override
    public void playerJoined(PlayerJoinEvent e) {
        e.setJoinMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), SMAPI.message().PlayerMSG(PlayerMessage.Join)));
    }
}
