package de.sdrs.servermanager_v2.plugin.eventListeners;

import de.sdrs.servermanager_v2.api.world.SmapiWorlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class PostLoadListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLoad(ServerLoadEvent event) {
        SmapiWorlds.createDefaultWorlds();
    }
}