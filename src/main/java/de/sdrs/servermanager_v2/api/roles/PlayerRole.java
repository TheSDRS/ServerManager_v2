package de.sdrs.servermanager_v2.api.roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerRole {
    public String getName();
    public List<String> getPermissions();
    public boolean hasPermission(String permission);
    public void addPermission(String permission);
    public void removePermission(String permission);
    public void setPrefix(String prefix);
    public void showPrefix();
    public void hidePrefix();
    public String getPrefix();
    public String getColor();
    public void loadPermissions();
    public void loadPermissions(Player p);
    public void unloadPermissions();
    public void unloadPermissions(Player p);
}
