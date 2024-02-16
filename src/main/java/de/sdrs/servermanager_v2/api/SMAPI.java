package de.sdrs.servermanager_v2.api;

import de.sdrs.servermanager_v2.api.messages.Message;
import de.sdrs.servermanager_v2.api.messages.Messages;
import de.sdrs.servermanager_v2.api.permissions.Permission;
import de.sdrs.servermanager_v2.api.util.Config;
import de.sdrs.servermanager_v2.plugin.main.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SMAPI implements ServerManagerAPI {

    private static List<Plugin> registeredPlugins = new ArrayList<>();

    public static String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ServerManager" + ChatColor.GOLD + "]: ";
    public static void register(Plugin plugin) {
        if (registeredPlugins == null) {
            registeredPlugins.add(plugin);
        } else {
            if (!registeredPlugins.contains(plugin) || registeredPlugins == null) {
                registeredPlugins.add(plugin);
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "registered " + plugin.getName() + " successfully!");
            } else {
                return;
            }
        }
    }

    public static void createFiles() {
        String path = ServerManager.getDir();
        ServerManager.getPlugin().saveResource("players.yml", false);
        ServerManager.getPlugin().saveResource("config.yml", false);
        ServerManager.getPlugin().saveResource("roles.yml", false);
        ServerManager.getPlugin().saveResource("warps.yml", false);
        ServerManager.getPlugin().saveResource("worlds.yml", false);
    }

    @Override
    public void writeToYAML(String path, HashMap<Object, Object> data) {
        DumperOptions options = new DumperOptions();

        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Yaml yaml2 = new Yaml(options);
        yaml2.dump(data, writer);
    }

    @Override
    public HashMap<Object, Object> readFromYAML(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Yaml yaml = new Yaml();
        HashMap<Object, Object> data = yaml.load(inputStream);

        return data;
    }

    public static Message message() {return new Messages();}

    public static Config config() {return new Config();}

    public static void createCommand(String name, CommandExecutor executor, Permission permission) {
        ServerManager.getPlugin().getCommand(name).setExecutor(executor);
    }
}
