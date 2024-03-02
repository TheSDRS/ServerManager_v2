package de.sdrs.servermanager_v2.api.player;

import de.sdrs.servermanager_v2.api.warps.Warp;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public interface PlayerActions {

    public void save();
    public void saveLocation();
    public HashMap<Object, Object> location();
    public void print();
    public void playerJoined(PlayerJoinEvent e);
    public void addPermission(String permission);
    public void removePermission(String permission);
    public List<Object> getPermissions();
    public HashMap<Object, Object> getSavedLocation();
    public void setRole(String roleName);
    public String getRole();
    public void setListName(String prefix, String suffix);
    public String getPrefix();
    public String getSuffix();
    public void warpPlayer(Warp warp);
    public void warpPlayer(String warp);
}
