package de.sdrs.servermanager_v2.api.util.trees;

import de.sdrs.servermanager_v2.api.SMAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabLeaf implements Leaf {

    private String name;
    private int layer;
    private boolean condition = false;
    private boolean hasCondition = false;
    private CompletionObject completionObject;


    public TabLeaf(String name) {
        SMAPI smapi = new SMAPI();
        this.name = name;
    }

    @Override
    public String complete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == layer) {
            return getName();
        }
        return null;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getString() {
        List<String> result = new ArrayList<>();
        result.add(name);
        result.add(String.valueOf(condition));
        return result;
    }

    @Override
    public void setCompletionObject(CompletionObject completionObject) {
        this.completionObject = completionObject;
    }

    @Override
    public CompletionObject getCompletionObject() {
        return completionObject;
    }

    @Override
    public boolean hasCondition() {
        return hasCondition;
    }

    @Override
    public void condition(boolean condition) {
        this.condition = condition;
        this.hasCondition = true;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }
}
