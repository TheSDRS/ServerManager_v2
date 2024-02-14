package de.sdrs.servermanager_v2.plugin.eventListeners;

import de.sdrs.servermanager_v2.api.player.PlayerActions;
import de.sdrs.servermanager_v2.api.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerActions playerActions = new PlayerData(e.getPlayer());
        playerActions.playerJoined(e);
    }
}
