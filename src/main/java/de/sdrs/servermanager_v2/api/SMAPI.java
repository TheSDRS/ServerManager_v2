package de.sdrs.servermanager_v2.api;

import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SMAPI implements ServerManagerAPI {

    private static List<Plugin> registeredPlugins = new ArrayList<>();
    public static void register(Plugin plugin) {
        if (registeredPlugins == null) {
            registeredPlugins.add(plugin);
        } else {
            if (!registeredPlugins.contains(plugin) || registeredPlugins == null) {
                registeredPlugins.add(plugin);
            } else {
                return;
            }
        }
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
}
