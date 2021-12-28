package com.bengboo.nospawn;

import com.bengboo.nospawn.commands.NewHaven;
import com.bengboo.nospawn.events.EntitySpawns;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public final class Haven extends JavaPlugin {

    /* File Paths */
    public static File nospawn;
    private static String mainDirectory;

    private File customConfigFile;
    public static File repellerFile;
    public static File allowedFile;
    private FileConfiguration customConfig;

    public int[][] repellers = {
            {
                0, 0, 0, 0
            },
    };

    public int[][] allowed = {
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

        allowedFile = new File(getDataFolder(), "allowed.list");
        if (!allowedFile.exists()) {
            allowedFile.getParentFile().mkdirs();
            saveResource("allowed.list", false);
        }

        BufferedReader reader;
        try {

            // Import repellers
            reader = new BufferedReader(new FileReader(repellerFile));
            String line = reader.readLine();
            while (line != null) {
                String[] line_info = line.split(",");

                int x = parseInt(line_info[0]);
                int y = parseInt(line_info[1]);
                int z = parseInt(line_info[2]);
                int radius = parseInt(line_info[3]);

                repellers = addRepeller(repellers, x, y, z, radius);
                line = reader.readLine();
            }

            // import allowed zones
            reader = new BufferedReader(new FileReader(allowedFile));
            line = reader.readLine();
            while (line != null) {
                String[] line_info = line.split(",");

                int x = parseInt(line_info[0]);
                int y = parseInt(line_info[1]);
                int z = parseInt(line_info[2]);
                int radius = parseInt(line_info[3]);

                allowed = addRepeller(allowed, x, y, z, radius);
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] addRepeller(int[][] _repellers, int x, int y, int z, int radius) throws IOException {
        int[][] new_repellers = Arrays.copyOf(_repellers, _repellers.length +1);
        new_repellers[new_repellers.length-1] = new int[] {x, y, z, radius};

        return new_repellers;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig();
        importRepellers();

        this.getCommand("haven").setExecutor(new NewHaven(this));
        getServer().getPluginManager().registerEvents(new EntitySpawns(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
