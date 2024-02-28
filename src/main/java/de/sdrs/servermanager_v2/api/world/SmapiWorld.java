package de.sdrs.servermanager_v2.api.world;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import de.sdrs.servermanager_v2.api.util.File;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class SmapiWorld {

    private HashMap<Object, Object> worlds = new HashMap<>();
    private HashMap<Object, Object> smapiWorldInfo = new HashMap<>();
    private HashMap<Object, Object> settings = new HashMap<>();
    private String worldName;
    private World bukkitWorld;

    public SmapiWorld(World world) {
        SMAPI smapi = new SMAPI();
        bukkitWorld = world;
        worlds = smapi.readFromYAML(ServerManager.getDir() + "/worlds.yml");
        worldName = world.getName();
        if (worlds == null) {
            smapiWorldInfo = makeSmapiWorld(world);
            worlds = makeFirstWorld();
            save();
        } else {
            if (worlds.containsKey(worldName)) {
                smapiWorldInfo = (HashMap<Object, Object>) worlds.get(worldName);
                settings = (HashMap<Object, Object>) smapiWorldInfo.get("settings");
                save();
            } else {
                smapiWorldInfo = makeSmapiWorld(world);
                worlds.put(worldName, smapiWorldInfo);
                save();
            }
        }
    }

    public SmapiWorld(String name) {

        SMAPI smapi = new SMAPI();
        worlds = smapi.readFromYAML(ServerManager.getDir() + "/worlds.yml");

        worldName = name;

        if (worlds.containsKey(worldName)) {
            smapiWorldInfo = (HashMap<Object, Object>) worlds.get(worldName);
            settings = (HashMap<Object, Object>) smapiWorldInfo.get("settings");
        } else {
            setSeed(new Random().nextLong());
            setEnvironment(World.Environment.NORMAL);
            setWorldType(WorldType.NORMAL);
            setDifficulty(Difficulty.NORMAL);
            setClearWeatherDuration(0);
        }
    }

    private void save() {
        SMAPI smapi = new SMAPI();
        if (smapiWorldInfo.containsKey("settings")) {
            smapiWorldInfo.replace("settings", settings);
        } else {
            smapiWorldInfo.put("settings", settings);
        }
        if (worlds.containsKey(worldName)) {
            worlds.replace(worldName, smapiWorldInfo);
        } else {
            worlds.put(worldName, smapiWorldInfo);
        }
        smapi.writeToYAML(ServerManager.getDir() + "/worlds.yml", worlds);
    }

    private HashMap<Object, Object> makeFirstWorld() {
        HashMap<Object, Object> tmpWorlds = new HashMap<>();
        tmpWorlds.put(worldName, smapiWorldInfo);
        return tmpWorlds;
    }

    private HashMap<Object, Object> makeSmapiWorld(World world) {
        HashMap<Object, Object> smapiWorldInfo = new HashMap<>();

        settings.put("difficulty", world.getDifficulty().name());
        settings.put("clearWeatherDuration", world.getClearWeatherDuration());

        smapiWorldInfo.put("seed", world.getSeed());
        smapiWorldInfo.put("environment", world.getEnvironment().name());
        smapiWorldInfo.put("type", world.getWorldType().getName());
        smapiWorldInfo.put("settings", settings);

        return smapiWorldInfo;
    }

    public void load() {
        bukkitWorld = create();

        bukkitWorld.setDifficulty(getDifficulty());
        bukkitWorld.setClearWeatherDuration(getClearWeatherDuration());
    }

    public World create() {
        WorldCreator wc = new WorldCreator(worldName);

        wc.seed(getSeed());
        wc.environment(getEnvironment());
        wc.type(getWorldType());

        wc.createWorld();
        bukkitWorld = Bukkit.getServer().getWorld(worldName);
        return bukkitWorld;
    }

    public long getSeed() {
        return (long) smapiWorldInfo.get("seed");
    }

    public void setSeed(long seed) {
        if (smapiWorldInfo.containsKey("seed")) {
            smapiWorldInfo.replace("seed", seed);
        } else {
            smapiWorldInfo.put("seed", seed);
        }
        save();
    }

    public World.Environment getEnvironment() {
        return World.Environment.valueOf((String) smapiWorldInfo.get("environment"));
    }

    public void setEnvironment(World.Environment environment) {
        if (smapiWorldInfo.containsKey("environment")) {
            smapiWorldInfo.replace("environment", environment.name());
        } else {
            smapiWorldInfo.put("environment", environment.name());
        }
        save();
    }

    public WorldType getWorldType() {
        return WorldType.getByName((String) smapiWorldInfo.get("type"));
    }

    public void setWorldType(WorldType worldType) {
        if (smapiWorldInfo.containsKey("type")) {
            smapiWorldInfo.replace("type", worldType.getName());
        } else {
            smapiWorldInfo.put("type", worldType.getName());
        }
        save();
    }

    public Difficulty getDifficulty() {
        return Difficulty.valueOf((String) settings.get("difficulty"));
    }

    public void setDifficulty(Difficulty difficulty) {
        if (settings.containsKey("difficulty")) {
            bukkitWorld.setDifficulty(difficulty);
            settings.replace("difficulty", difficulty.name());
        } else {
            settings.put("difficulty", difficulty.name());
        }
        save();
    }

    public int getClearWeatherDuration() {
        return (Integer) settings.get("clearWeatherDuration");
    }

    public void setClearWeatherDuration(int duration) {
        if (settings.containsKey("clearWeatherDuration")) {
            bukkitWorld.setClearWeatherDuration(duration);
            settings.replace("clearWeatherDuration", duration);
        } else {
            settings.put("clearWeatherDuration", duration);
        }
        save();
    }

    public void delete() {
        World defaultWorld = Bukkit.getWorld((String) SMAPI.config().getFromCFG(ConfigData.DefaultWorld));

        while (!bukkitWorld.getPlayers().isEmpty()) {
            for (Player player : bukkitWorld.getPlayers()) {
                player.teleport(defaultWorld.getSpawnLocation());
            }
        }

        for (Chunk chunk : bukkitWorld.getLoadedChunks()) {
            chunk.unload();
        }
        Bukkit.getServer().unloadWorld(worldName, false);
        Bukkit.getScheduler().runTaskLater(ServerManager.getPlugin(), new Runnable() {
            @Override
            public void run() {
                File.delete(new java.io.File(worldName));
            }
        }, 3);

        worlds.remove(worldName);

        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/worlds.yml", worlds);
    }
}
