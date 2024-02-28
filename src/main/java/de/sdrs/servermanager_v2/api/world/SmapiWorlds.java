package de.sdrs.servermanager_v2.api.world;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.HashMap;
import java.util.Objects;

public class SmapiWorlds {
    public static void createDefaultWorlds() {
        for (World world : Bukkit.getServer().getWorlds()) {
            SmapiWorld smapiWorld = new SmapiWorld(world);
        }
    }

    public static HashMap<Object, Object> getAll() {
        SMAPI smapi = new SMAPI();
        return smapi.readFromYAML(ServerManager.getDir() + "/worlds.yml");
    }

    public static void loadAll() {
        for (Object world : getAll().keySet()) {
            if (Bukkit.getWorld(String.valueOf(world)) == null) {
                SmapiWorld smapiWorld = new SmapiWorld(String.valueOf(world));
                smapiWorld.load();
            }
        }
    }

    public static void loadCustomWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.createWorld();

        SmapiWorld smapiWorld = new SmapiWorld(Objects.requireNonNull(Bukkit.getWorld(worldName)));
    }
}
