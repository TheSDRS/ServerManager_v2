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
    private static String ID;
    private static HashMap<Object, Object> location = new HashMap<>();

    public Warp(String name) {
        warpName = name;
        ID = warpName.toLowerCase();
        SMAPI smapi = new SMAPI();
        warps = Warps.getWarpObjects();
        if (warps == null) {
            warps = new HashMap<>();
            createDefaultWarp();
        } else if (warps.isEmpty()) {
            createDefaultWarp();
        } else if (!warps.containsKey(ID)) {
            createDefaultWarp();
        } else {
            warp = (HashMap<Object, Object>) warps.get(ID);
            owner = (String) warp.get("owner");
            visits = (int) warp.get("visits");
            location = (HashMap<Object, Object>) warp.get("location");
        }
    }

    private void createDefaultWarp() {
        setOwner("System");
        World defaultWorld = Bukkit.getWorld((String) SMAPI.config().getFromCFG(ConfigData.DefaultWorld));
        setLocation(new Location(defaultWorld, defaultWorld.getSpawnLocation().getX(), defaultWorld.getSpawnLocation().getY(), defaultWorld.getSpawnLocation().getZ(), defaultWorld.getSpawnLocation().getYaw(), defaultWorld.getSpawnLocation().getPitch()));
        setVisits(0);
        save();
    }

    public void save() {
        if (warp.containsKey("name")) {
            warp.replace("name", warpName);
        } else {
            warp.put("name", warpName);
        }
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

        if (warps.containsKey(ID)) {
            warps.replace(ID, warp);
        } else {
            warps.put(ID, warp);
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
            location.put("x", (double) loc.getX());
            location.put("y", (double) loc.getY());
            location.put("z", (double) loc.getZ());
            location.put("yaw", (float) loc.getYaw());
            location.put("pitch", (float) loc.getPitch());
        } else {
            location.replace("world", loc.getWorld().getName());
            location.replace("x", (double) loc.getX());
            location.replace("y", (double) loc.getY());
            location.replace("z", (double) loc.getZ());
            location.replace("yaw", (float) loc.getYaw());
            location.replace("pitch", (float) loc.getPitch());
        }
    }

    public Location getLocation() {
        double x = (double) location.get("x");
        double y = (double) location.get("y");
        double z = (double) location.get("z");
        double yaw = (double) location.get("yaw");
        double pitch = (double) location.get("pitch");

        return new Location(Bukkit.getWorld((String) location.get("world")), x, y, z, (float) yaw, (float) pitch);
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getVisits() {
        return visits;
    }

    public String getName() {
        return warpName;
    }

    public HashMap<Object, Object> toHashMap() {
        return warp;
    }

    public void delete() {
        SMAPI smapi = new SMAPI();

        warps.remove(ID);

        smapi.writeToYAML(ServerManager.getDir() + "/warps.yml", warps);

    }
}
