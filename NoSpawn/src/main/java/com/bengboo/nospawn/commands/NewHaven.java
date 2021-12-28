package com.bengboo.nospawn.commands;

import com.bengboo.nospawn.Haven;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;

import static java.lang.Integer.parseInt;

public class NewHaven implements CommandExecutor {

    Haven plugin;

    public NewHaven(Haven _plugin) {
        plugin = _plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int x, y, z, radius;

        switch (args[0]) {
            case "allow":
                x = parseInt(args[1]);
                y = parseInt(args[2]);
                z = parseInt(args[3]);
                radius = parseInt(args[4]);

                try {
                    plugin.allowed = Haven.addRepeller(plugin.allowed, x, y, z, radius);

                    FileWriter fw = new FileWriter(plugin.allowedFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String output = x + "," + y + "," + z + "," + radius;
                    bw.write(output);
                    bw.newLine();
                    bw.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
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

                // repellers file
                File tempFile = new File(plugin.getDataFolder(), "repellers.list.temp");

                for (int index = 0; index < plugin.repellers.length; index++) {
                    if (x == plugin.repellers[index][0] && y == plugin.repellers[index][1] && z == plugin.repellers[index][2] && radius == plugin.repellers[index][3]){
                        plugin.repellers = (int[][]) ArrayUtils.remove(plugin.repellers, index);
                    }
                }

                try {
                    // repeller
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
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // allowed file
                tempFile = new File(plugin.getDataFolder(), "allowed.list.temp");

                for (int index = 0; index < plugin.allowed.length; index++) {
                    if (x == plugin.allowed[index][0] && y == plugin.allowed[index][1] && z == plugin.allowed[index][2] && radius == plugin.allowed[index][3]){
                        plugin.allowed = (int[][]) ArrayUtils.remove(plugin.allowed, index);
                    }
                }

                try {
                    // allowed
                    BufferedReader reader = new BufferedReader(new FileReader(plugin.allowedFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;
                    while((currentLine = reader.readLine()) != null) {
                        String trimmedLine = currentLine.trim();
                        if(trimmedLine.equals(line)) continue;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }

                    writer.close();
                    reader.close();
                    plugin.allowedFile.delete();
                    tempFile.renameTo(plugin.allowedFile);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "list":
                sender.sendMessage("Repellers: ");
                for (int index = 0; index < plugin.repellers.length; index++) {
                    sender.sendMessage(plugin.repellers[index][0] + " " + plugin.repellers[index][1] + " " + plugin.repellers[index][2] + " " + plugin.repellers[index][3]);
                }

                sender.sendMessage("Allowed Zones: ");
                for (int index = 0; index < plugin.allowed.length; index++) {
                    sender.sendMessage(plugin.allowed[index][0] + " " + plugin.allowed[index][1] + " " + plugin.allowed[index][2] + " " + plugin.allowed[index][3]);
                }

                break;

            default:
                break;
        }

        return true;
    }
}
