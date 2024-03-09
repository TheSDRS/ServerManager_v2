package de.sdrs.servermanager_v2.api.warps;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.*;

public class Warps {

    public static HashMap<Object, Object> getWarpObjects() {
        SMAPI smapi = new SMAPI();
        return smapi.readFromYAML(ServerManager.getDir() + "/warps.yml");
    }

    public static Set<Object> getAllIDs() {
        SMAPI smapi = new SMAPI();
        return smapi.readFromYAML(ServerManager.getDir() + "/warps.yml").keySet();
    }
}
