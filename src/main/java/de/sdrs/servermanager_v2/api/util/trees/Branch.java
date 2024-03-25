package de.sdrs.servermanager_v2.api.util.trees;

import org.bukkit.block.BrewingStand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface Branch {
    public List<String> complete(CommandSender sender, Command command, String label, String[] args);
    public Branch addBranch(String arg);
    public Branch addBranch(Branch branch);
    public void addBranches(List<String> args);
    public Branch getBranch(String branchName);
    public List<Branch> getBranches();
    public void addCompletionObjectAsBranch(CompletionObject completionObject);
    public List<Branch> getBranchesWithCO(CompletionObject completionObject);
     public void addCompletionObjectAsLeaf(CompletionObject completionObject);
    public Leaf addLeaf(String arg);
    public Leaf addLeaf(Leaf leaf);
    public Leaf getLeaf(String leafName);
    public void addLeafs(List<String> args);
    public List<Leaf> getLeafs();
    public void setLayer(int layer);
    public int getLayer();
    public String getName();
    public List<String> getString();
    public void setCompletionObject(CompletionObject completionObject);
    public CompletionObject getCompletionObject();
    public boolean hasCondition();
    public void condition(Boolean condition);
    public boolean getCondition();
}
