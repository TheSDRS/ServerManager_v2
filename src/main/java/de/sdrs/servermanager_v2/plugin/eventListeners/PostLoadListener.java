package de.sdrs.servermanager_v2.plugin.eventListeners;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import de.sdrs.servermanager_v2.api.util.trees.TabBranch;
import de.sdrs.servermanager_v2.api.util.trees.TabLeaf;
import de.sdrs.servermanager_v2.api.util.trees.TabTree;
import de.sdrs.servermanager_v2.api.util.trees.Tree;
import de.sdrs.servermanager_v2.api.warps.Warp;
import de.sdrs.servermanager_v2.api.world.SmapiWorlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class PostLoadListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLoad(ServerLoadEvent event) {
        SmapiWorlds.createDefaultWorlds();
        if ((boolean) SMAPI.config().getFromCFG(ConfigData.loadAllWorlds)) {
            SmapiWorlds.loadAll();
        } else {
            SMAPI.message().info("loadAllWorlds is set to 'false'");
        }
    }
}
