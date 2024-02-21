package de.sdrs.servermanager_v2.api.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class SmapiWorlds {
    public static void createDefaultWorlds() {
        for (World world : Bukkit.getServer().getWorlds()) {
            SmapiWorld smapiWorld = new SmapiWorld(world);
        }
    }
}
