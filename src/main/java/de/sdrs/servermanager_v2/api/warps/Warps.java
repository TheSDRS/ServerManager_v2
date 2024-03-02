package de.sdrs.servermanager_v2.api.warps;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Warps {

    private static List<Warp> warps;
    private static HashMap<Object, Object> warpObjects;

    public Warps() {
        SMAPI smapi = new SMAPI();
        warpObjects = smapi.readFromYAML(ServerManager.getDir() + "/warps.yml");
        for (Object w : warpObjects.keySet()) {
            Warp warp = new Warp((String) w);
            warps.add(warp);
        }
    }

    private static void save() {
        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/warps.yml", warpObjects);
    }

    public static List<Warp> getWarps() {
        return warps;
    }

    public static HashMap<Object, Object> getWarpObjects() {
        return warpObjects;
    }

    public static void addWarp(Warp warp) {
        warps.add(warp);
        warpObjects.put(warp.getName(), warp.toHashMap());
        save();
    }
}
