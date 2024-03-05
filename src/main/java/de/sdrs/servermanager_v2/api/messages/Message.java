package de.sdrs.servermanager_v2.api.messages;

import de.sdrs.servermanager_v2.api.messages.error.Error;
import de.sdrs.servermanager_v2.api.util.ConfigData;
import org.bukkit.plugin.Plugin;

public interface Message {
    public void Error(Error error);
    public String PlayerMSG(PlayerMessage msg);
    public void MissingPlugin(String message);
    public void debug(String msg);
    public void info(String msg);
}
