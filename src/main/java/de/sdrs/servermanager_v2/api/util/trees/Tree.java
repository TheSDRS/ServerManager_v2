package de.sdrs.servermanager_v2.api.util.trees;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface Tree {
    public List<String> complete(CommandSender sender, Command command, String label, String[] args);
    public Branch addBranch(String arg);
    public Branch addBranch(Branch branch);
    public Branch getBranch(String branchName);
    public void addBranches(List<String> args);
    public List<Branch> getBranches();
    public void addCompletionObjectAsBranch(CompletionObject completionObject);
    public List<Branch> getBranchesWithCO(CompletionObject completionObject);
    public void addCompletionObjectAsLeaf(CompletionObject completionObject);
    public Leaf addLeaf(String arg);
    public Leaf addLeaf(Leaf leaf);
    public Leaf getLeaf(String leafName);
    public void addLeafs(List<String> args);
    public List<Leaf> getLeaves();
    public Tree getTree();
    public String getName();
    public List<String> getString();
}
