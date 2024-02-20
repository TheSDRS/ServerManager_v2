package de.sdrs.servermanager_v2.api.roles;

import java.util.List;

public interface PlayerRole {
    public List<String> getPermissions();
    public boolean hasPermission(String permission);
    public void addPermission(String permission);
    public void setPrefix(String prefix);
    public String getPrefix();
}
