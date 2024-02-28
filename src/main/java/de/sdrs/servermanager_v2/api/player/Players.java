package de.sdrs.servermanager_v2.api.player;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Players {
    public static List<Player> getAll() {
        SMAPI smapi = new SMAPI();
        HashMap<Object, Object> playersFile = smapi.readFromYAML(ServerManager.getDir() + "/players.yml");
        List<Player> players = new ArrayList<>();
        for(Object playerID : playersFile.keySet()) {
            players.add(Bukkit.getPlayer(UUID.fromString((String) playerID)));
        }
        return players;
    }
}
