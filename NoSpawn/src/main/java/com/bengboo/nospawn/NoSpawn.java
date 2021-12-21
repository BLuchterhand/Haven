package com.bengboo.nospawn;

import com.bengboo.nospawn.events.EntitySpawns;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public final class NoSpawn extends JavaPlugin {

    /* File Paths */
    public static File nospawn;
    private static String mainDirectory;

    private File customConfigFile;
    private File repellerFile;
    private FileConfiguration customConfig;

    public int[][] repellers = {
            {
                0, 0, 0, 0
            },
    };

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        System.out.print("Created \n");

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    private void importRepellers() {
        repellerFile = new File(getDataFolder(), "repellers.list");
        if (!repellerFile.exists()) {
            repellerFile.getParentFile().mkdirs();
            saveResource("repellers.list", false);
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(repellerFile));
            String line = reader.readLine();
            while (line != null) {
                String[] line_info = line.split(",");

                int x = parseInt(line_info[0]);
                int y = parseInt(line_info[1]);
                int z = parseInt(line_info[2]);
                int radius = parseInt(line_info[3]);

                repellers = Arrays.copyOf(repellers, repellers.length +1);
                repellers[repellers.length-1] = new int[] {x, y, z, radius};

                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig();
        importRepellers();
        getServer().getPluginManager().registerEvents(new EntitySpawns(repellers), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
