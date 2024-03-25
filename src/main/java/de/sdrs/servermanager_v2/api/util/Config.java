package de.sdrs.servermanager_v2.api.util;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;

import java.util.HashMap;
import java.util.List;

public class Config {

    private HashMap<Object, Object> cfg = new HashMap<>();
    private HashMap<Object, Object> msgs = new HashMap<>();

    public Config() {
        SMAPI smapi = new SMAPI();
        cfg = smapi.readFromYAML(ServerManager.getDir() + "/config.yml");
        msgs = (HashMap<Object, Object>) cfg.get("messages");
    }
    public HashMap<Object, Object> getCFG() {
        return cfg;
    }

    public Object getFromCFG(ConfigData data) {
        if (data == ConfigData.JoinMSG) {
            return msgs.get("JoinMessage");
        } else if (data == ConfigData.LeaveMSG) {
            return msgs.get("LeaveMessage");
        } else if (data == ConfigData.DeathMSG) {
            return msgs.get("DeathMessage");
        } else if (data == ConfigData.DefaultRole) {
            return cfg.get("defaultRole");
        } else if (data == ConfigData.DefaultWorld) {
            return cfg.get("defaultWorld");
        } else if (data == ConfigData.loadAllWorlds) {
            return (boolean) cfg.get("loadAllWorlds");
        } else if (data == ConfigData.maxWarpListLength) {
            return (int) cfg.get("maxWarpListLength");
        } else if (data == ConfigData.messagingLevel) {
            return MessagingLevel.valueOf((String) cfg.get("messagingLevel"));
        } else if (data == ConfigData.reservedWarps) {
            return (List<String>) cfg.get("reservedWarpNames");
        }
        return null;
    }
}
