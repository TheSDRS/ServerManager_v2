package de.sdrs.servermanager_v2.api.util.trees;

import de.sdrs.servermanager_v2.api.roles.Role;
import de.sdrs.servermanager_v2.api.roles.Roles;
import de.sdrs.servermanager_v2.api.warps.Warp;
import de.sdrs.servermanager_v2.api.warps.Warps;
import de.sdrs.servermanager_v2.api.world.SmapiWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public enum CompletionObject {
    Player,
    Role,
    Warp,
    World,
    Permission,
    Block,
    Item,
    Fuel,
    Food;

    public static CompletionObject getCompletionObject(String object) {
        return CompletionObject.valueOf(object);
    }

    public static List<String> complete(CompletionObject completionObject) {
        if (completionObject == Player) {
            List<String> pNames = new ArrayList<>();
            for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                pNames.add(player.getName());
            }
            return pNames;
        } else if (completionObject == Role) {
            List<String> roles = new ArrayList<>();
            for (Object object : Roles.getAll().keySet()) {
                de.sdrs.servermanager_v2.api.roles.Role role = new Role((String) object);
                roles.add(role.getID());
            }
            return roles;
        } else if (completionObject == Warp) {
            List<String> warps = new ArrayList<>();
            for (Object object : Warps.getAllIDs()) {
                de.sdrs.servermanager_v2.api.warps.Warp warp = new Warp((String) object);
                warps.add(warp.getName());
            }
            return warps;
        } else if (completionObject == World) {
            List<String> worlds = new ArrayList<>();
            for (Object object : SmapiWorlds.getAll().keySet()) {
                worlds.add((String) object);
            }
            return worlds;
        } else if (completionObject == Permission) {
            List<String> permissions = new ArrayList<>();
            for (org.bukkit.permissions.Permission object : Bukkit.getPluginManager().getPermissions()) {
                permissions.add(object.getName());
            }
            return permissions;
        } else if (completionObject == Block) {
            List<String> blocks = new ArrayList<>();
            for (Material object : Material.values()) {
                blocks.add(object.name());
            }
            return blocks;
        } else if (completionObject == Item) {
            List<String> item = new ArrayList<>();
            for (Material object : Material.values()) {
                if (object.isItem()) {
                    item.add(object.name());
                }
            }
            return item;
        } else if (completionObject == Fuel) {
            List<String> fuels = new ArrayList<>();
            for (Material object : Material.values()) {
                if (object.isFuel()) {
                    fuels.add(object.name());
                }
            }
            return fuels;
        } else if (completionObject == Food) {
            List<String> foods = new ArrayList<>();
            for (Material object : Material.values()) {
                if (object.isEdible()) {
                    foods.add(object.name());
                }
            }
            return foods;
        }
        return null;
    }
}
