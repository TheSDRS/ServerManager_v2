package de.sdrs.servermanager_v2.api.warps;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Warps {

    public static HashMap<Object, Object> getWarpObjects() {
        SMAPI smapi = new SMAPI();
        return smapi.readFromYAML(ServerManager.getDir() + "/warps.yml");
    }
}
