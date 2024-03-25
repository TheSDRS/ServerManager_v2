package de.sdrs.servermanager_v2.api.util.trees;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public interface Leaf {
    public String complete(CommandSender sender, Command command, String label, String[] args);
    public void setLayer(int layer);
    public int getLayer();
    public String getName();
    public List<String> getString();
    public void setCompletionObject(CompletionObject completionObject);
    public CompletionObject getCompletionObject();
    public boolean hasCondition();
    public void condition(boolean condition);
    public boolean getCondition();
}
