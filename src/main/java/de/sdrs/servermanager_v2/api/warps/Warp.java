package de.sdrs.servermanager_v2.api.warps;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

public class Warp {

    private static HashMap<Object, Object> warp = new HashMap<>();
    private static HashMap<Object, Object> warps = new HashMap<>();
    private static String warpName;
    private static String owner;
    private static int visits;
    private static HashMap<Object, Object> location = new HashMap<>();

    public Warp(String name) {
        warpName = name;
        SMAPI smapi = new SMAPI();
        warps = Warps.getWarpObjects();
        if (warps == null) {
            warps = new HashMap<>();
            setOwner("System");
            World defaultWorld = Bukkit.getWorld((String) SMAPI.config().getFromCFG(ConfigData.DefaultWorld));
            setLocation(new Location(defaultWorld, defaultWorld.getSpawnLocation().getX(), defaultWorld.getSpawnLocation().getY(), defaultWorld.getSpawnLocation().getZ(), defaultWorld.getSpawnLocation().getYaw(), defaultWorld.getSpawnLocation().getPitch()));
            setVisits(0);
            save();
        } else if (warps.isEmpty()) {
            setOwner("System");
            World defaultWorld = Bukkit.getWorld((String) SMAPI.config().getFromCFG(ConfigData.DefaultWorld));
            setLocation(new Location(defaultWorld, defaultWorld.getSpawnLocation().getX(), defaultWorld.getSpawnLocation().getY(), defaultWorld.getSpawnLocation().getZ(), defaultWorld.getSpawnLocation().getYaw(), defaultWorld.getSpawnLocation().getPitch()));
            setVisits(0);
            save();
        } else {
            warp = (HashMap<Object, Object>) warps.get(warpName);
            owner = (String) warp.get("owner");
            visits = (int) warp.get("visits");
            location = (HashMap<Object, Object>) warp.get("location");
        }
    }

    public static void save() {
        if (warp.containsKey("owner")) {
            warp.replace("owner", owner);
        } else {
            warp.put("owner", owner);
        }
        if (warp.containsKey("visits")) {
            warp.replace("visits", visits);
        } else {
            warp.put("visits", visits);
        }
        if (warp.containsKey("location")) {
            warp.replace("location", location);
        } else {
            warp.put("location", location);
        }

        if (warps.containsKey(warpName)) {
            warps.replace(warpName, warp);
        } else {
            warps.put(warpName, warp);
        }

        SMAPI smapi = new SMAPI();
        smapi.writeToYAML(ServerManager.getDir() + "/warps.yml", warps);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setLocation(Location loc) {
        if (location.isEmpty()) {
            location.put("world", loc.getWorld().getName());
            location.put("x", loc.getX());
            location.put("y", loc.getY());
            location.put("z", loc.getZ());
            location.put("yaw", loc.getYaw());
            location.put("pitch", loc.getPitch());
        } else {
            location.replace("world", loc.getWorld().getName());
            location.replace("x", loc.getX());
            location.replace("y", loc.getY());
            location.replace("z", loc.getZ());
            location.replace("yaw", loc.getYaw());
            location.replace("pitch", loc.getPitch());
        }
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld((String) location.get("world")), (Double) location.get("x"),(Double) location.get("y"),(Double) location.get("z"),(float) location.get("yaw"),(float) location.get("pitch"));
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getName() {
        return warpName;
    }

    public HashMap<Object, Object> toHashMap() {
        return warp;
    }
}
