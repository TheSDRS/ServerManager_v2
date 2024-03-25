package de.sdrs.servermanager_v2.api.util.trees;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabTree implements Tree {

    private String name;
    private List<Branch> branches = new ArrayList<>();
    private List<Leaf> leaves = new ArrayList<>();

    public TabTree(String name) {
        this.name = name;
    }

    @Override
    public List<String> complete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();

        for (Leaf leaf : leaves) {
            if (leaf.getCondition() || !leaf.hasCondition()) {
                if (leaf.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase()) && args.length == leaf.getLayer()) {
                    result.add(leaf.getName());
                }
            }
        }

        for (Branch branch : branches) {
            if (branch.getCondition() || !branch.hasCondition()) {
                if (args.length >= 2 && args[branch.getLayer() - 1].equalsIgnoreCase(branch.getName())) {
                    return branch.complete(sender, command, label, args);
                } else if (branch.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase()) && args.length == branch.getLayer()) {
                    result.add(branch.getName());
                }
            }
        }

        List<String> tmp = new ArrayList<>();
        for (String arg : args) {
            for (String res : result) {
                if (arg.equalsIgnoreCase(res)) {
                    if (!tmp.contains(res)) {
                        tmp.add(res);
                    }
                }
            }
        }

        for (String arg : tmp) {
            result.remove(arg);
        }

        if (!result.isEmpty()) {
            return result;
        }

        return null;
    }

    @Override
    public Branch addBranch(String arg) {
        Branch branch = new TabBranch(arg);
        branch.setLayer(1);
        branches.add(branch);
        return branch;
    }

    @Override
    public Branch addBranch(Branch branch) {
        branch.setLayer(1);
        branches.add(branch);
        return branch;
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
    public void addBranches(List<String> args) {
        for (String a : args) {
            addBranch(a);
        }
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
        leaf.setLayer(1);
        return leaf;
    }

    @Override
    public Leaf addLeaf(Leaf leaf) {
        leaf.setLayer(1);
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
            addLeaf(a);
        }
    }

    @Override
    public List<Leaf> getLeaves() {
        return leaves;
    }

    @Override
    public Tree getTree() {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getString() {
        List<String> result = new ArrayList<>();
        for (Leaf leaf : leaves) {
            result.add(String.valueOf(leaf.getString()));
        }
        for (Branch branch : branches) {
            result.add(String.valueOf(branch.getString()));
        }
        return result;
    }
}
