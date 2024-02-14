package de.sdrs.servermanager_v2.api.player;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public interface PlayerActions {

    public void save();
    public void saveLocation();
    public void print();
    public void playerJoined(PlayerJoinEvent e);
}
