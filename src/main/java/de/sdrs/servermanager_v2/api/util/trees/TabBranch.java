package de.sdrs.servermanager_v2.api.util.trees;

import de.sdrs.servermanager_v2.api.SMAPI;
import de.sdrs.servermanager_v2.api.messages.error.ErrorHandling;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabBranch implements Branch {

    private String name;
    private int layer;
    private boolean condition = false;
    private boolean hasCondition = false;
    private CompletionObject completionObject;
    private List<Branch> branches = new ArrayList<>();
    private List<Leaf> leaves = new ArrayList<>();

    public TabBranch(String name) {
        this.name = name;
    }

    @Override
    public List<String> complete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();

        for (Leaf leaf : leaves) {
            if (leaf.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase()) && args.length == leaf.getLayer()) {
                if (leaf.getCondition() || !leaf.hasCondition()) {
                    result.add(leaf.getName());
                }
            }
        }

        for (Branch branch : branches) {
            if (args.length >= 2 && args[branch.getLayer() - 1].equalsIgnoreCase(branch.getName())) {
                if (args[args.length - 1].equalsIgnoreCase(branch.getName())) {
                    return null;
                }
                if (branch.getCondition() || branch.hasCondition()) {
                    return branch.complete(sender, command, label, args);
                }
            } else if (branch.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase()) && args.length == branch.getLayer()) {
                if (branch.getCondition() || !branch.hasCondition()) {
                    result.add(branch.getName());
                }
            }
        }

        if (!result.isEmpty()) {
            return result;
        }

        return null;
    }

    @Override
    public Branch addBranch(String arg) {
        Branch branch = new TabBranch(arg);
        branch.setLayer(layer + 1);
        branches.add(branch);
        return branch;
    }

    @Override
    public Branch addBranch(Branch branch) {
        branch.setLayer(layer + 1);
        branches.add(branch);
        return branch;
    }

    @Override
    public void addBranches(List<String> args) {
        for (String a : args) {
            addBranch(a);
        }
    }

    @Override
    public Branch getBranch(String branchName) {
        for (Branch branch : branches) {
            if (Objects.equals(branch.getName(), branchName)) {
                return branch;
            }
        }
        return null;
    }

    @Override
    public List<Branch> getBranches() {
        return branches;
    }

    @Override
    public void addCompletionObjectAsBranch(CompletionObject completionObject) {
        for (String object : Objects.requireNonNull(CompletionObject.complete(completionObject))) {
            TabBranch branch = new TabBranch(object);
            branch.setCompletionObject(completionObject);
            addBranch(branch);
        }
    }

    @Override
    public List<Branch> getBranchesWithCO(CompletionObject completionObject) {
        List<Branch> result = new ArrayList<>();
        for (Branch branch : branches) {
            if (branch.getCompletionObject() == completionObject) {
                result.add(branch);
            }
        }
        return result;
    }

    @Override
    public void addCompletionObjectAsLeaf(CompletionObject completionObject) {
        for (String object : Objects.requireNonNull(CompletionObject.complete(completionObject))) {
            TabLeaf leaf = new TabLeaf(object);
            leaf.setCompletionObject(completionObject);
            addLeaf(leaf);
        }
    }

    @Override
    public Leaf addLeaf(String arg) {
        Leaf leaf = new TabLeaf(arg);
        leaf.setLayer(layer + 1);
        leaves.add(leaf);
        return leaf;
    }

    @Override
    public Leaf addLeaf(Leaf leaf) {
        leaf.setLayer(layer + 1);
        leaves.add(leaf);
        return leaf;
    }

    @Override
    public Leaf getLeaf(String leafName) {
        for (Leaf leaf : leaves) {
            if (Objects.equals(leaf.getName(), leafName)) {
                return leaf;
            }
        }
        return null;
    }

    @Override
    public void addLeafs(List<String> args) {
        for (String a : args) {
            Leaf leaf = new TabLeaf(a);
            leaves.add(leaf);
        }
    }

    @Override
    public List<Leaf> getLeafs() {
        return leaves;
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
        List<String> result2 = new ArrayList<>();
        for (Leaf leaf : leaves) {
            result2.add(String.valueOf(leaf.getString()));
        }
        for (Branch branch : branches) {
            result2.add(String.valueOf(branch.getString()));
        }
        result.add(getName());
        result.add(String.valueOf(condition));
        result.add(String.valueOf(result2));
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
    public void condition(Boolean condition) {
        this.condition = condition;
        this.hasCondition = true;
    }

    @Override
    public boolean getCondition() {
        return condition;
    }
}
