package de.sdrs.servermanager_v2.api.world;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.World;

import java.util.HashMap;

public class SmapiWorld {

    private HashMap<Object, Object> worlds = new HashMap<>();
    private HashMap<Object, Object> smapiWorldInfo = new HashMap<>();
    private String worldName;

    public SmapiWorld(World world) {
        SMAPI smapi = new SMAPI();
        worlds = smapi.readFromYAML(ServerManager.getDir() + "/worlds.yml");
        worldName = world.getName();
        if (worlds == null) {
            smapiWorldInfo = makeSmapiWorld(world);
            worlds = makeFirstWorld();
            save();
        } else {
            if (worlds.containsKey(worldName)) {
                smapiWorldInfo = (HashMap<Object, Object>) worlds.get(worldName);
                save();
            } else {
                smapiWorldInfo = makeSmapiWorld(world);
                worlds.put(worldName, smapiWorldInfo);
                save();
            }
        }
    }

    private void save() {
        SMAPI smapi = new SMAPI();
        worlds.replace(worldName, smapiWorldInfo);
        smapi.writeToYAML(ServerManager.getDir() + "/worlds.yml", worlds);
    }

    private HashMap<Object, Object> makeFirstWorld() {
        HashMap<Object, Object> tmpWorlds = new HashMap<>();
        tmpWorlds.put(worldName, smapiWorldInfo);
        return tmpWorlds;
    }

    private HashMap<Object, Object> makeSmapiWorld(World world) {
        HashMap<Object, Object> smapiWorldInfo = new HashMap<>();
        smapiWorldInfo.put("seed", world.getSeed());
        smapiWorldInfo.put("environment", world.getEnvironment().name());
        smapiWorldInfo.put("type", world.getWorldType().getName());
        return smapiWorldInfo;
    }
}
