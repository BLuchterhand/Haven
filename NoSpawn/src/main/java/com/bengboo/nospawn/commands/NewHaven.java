package com.bengboo.nospawn.commands;

import com.bengboo.nospawn.Haven;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;

import static java.lang.Integer.parseInt;

public class NewHaven implements CommandExecutor {

    int[][] repellers;
    Haven plugin;

    public NewHaven(Haven _plugin) {
        plugin = _plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int x, y, z, radius;

        switch (args[0]) {
            case "create":
                x = parseInt(args[1]);
                y = parseInt(args[2]);
                z = parseInt(args[3]);
                radius = parseInt(args[4]);

                try {
                    plugin.repellers = Haven.addRepeller(plugin.repellers, x, y, z, radius);

                    FileWriter fw = new FileWriter(plugin.repellerFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String output = x + "," + y + "," + z + "," + radius;
                    bw.write(output);
                    bw.newLine();
                    bw.close();

                    System.out.println("WRITTEN");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "delete":
                x = parseInt(args[1]);
                y = parseInt(args[2]);
                z = parseInt(args[3]);
                radius = parseInt(args[4]);

                String line = x + "," + y + "," + z + "," + radius;
                File tempFile = new File(plugin.getDataFolder(), "repellers.list.temp");

                for (int index = 0; index < plugin.repellers.length; index++) {
                    if (x == plugin.repellers[index][0] && y == plugin.repellers[index][1] && z == plugin.repellers[index][2] && radius == plugin.repellers[index][3]){
                        plugin.repellers = (int[][]) ArrayUtils.remove(plugin.repellers, index);
                    }
                }



                try {
                    BufferedReader reader = new BufferedReader(new FileReader(plugin.repellerFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;
                    while((currentLine = reader.readLine()) != null) {
                        String trimmedLine = currentLine.trim();
                        if(trimmedLine.equals(line)) continue;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }

                    writer.close();
                    reader.close();
                    plugin.repellerFile.delete();
                    tempFile.renameTo(plugin.repellerFile);

                } catch (FileNotFoundException e) {
                    System.out.println("file not found");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Other exception");
                    e.printStackTrace();
                }

                break;

            case "list":
                for (int index = 0; index < plugin.repellers.length; index++) {
                    sender.sendMessage(plugin.repellers[index][0] + " " + plugin.repellers[index][1] + " " + plugin.repellers[index][2] + " " + plugin.repellers[index][3]);
                }

                break;

            default:
                break;
        }

        return true;
    }
}
