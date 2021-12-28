package com.bengboo.nospawn.events;
import com.bengboo.nospawn.Haven;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.Math;
import java.util.Arrays;


public class EntitySpawns implements Listener {

    Haven plugin;

    EntityType[] banned_mobs = {
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.CREEPER,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMAN,
            EntityType.HOGLIN,
            EntityType.SPIDER,
            EntityType.PHANTOM,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.SLIME,
            EntityType.STRAY,
            EntityType.PILLAGER,
            EntityType.WITCH,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.BAT
    };

    public EntitySpawns(Haven _plugin) {
        plugin = _plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        boolean cancel_spawn = false;

        if (Arrays.asList(banned_mobs).contains(event.getEntityType())) {
            Location loc = event.getLocation();
            int event_x = loc.getBlockX();
            int event_y = loc.getBlockY();
            int event_z = loc.getBlockZ();

            for (int index = 0; index < plugin.repellers.length; index++) {
                int diff_x = (event_x - plugin.repellers[index][0]) * (event_x - plugin.repellers[index][0]);
                int diff_z = (event_z - plugin.repellers[index][2]) * (event_z - plugin.repellers[index][2]);
                double distance = Math.sqrt(diff_x + diff_z);

                if (distance < plugin.repellers[index][3]) {
                    cancel_spawn = true;
                    for (int allowed_index = 0; allowed_index < plugin.allowed.length; allowed_index++) {
                        int allowed_x = (event_x - plugin.allowed[allowed_index][0]) * (event_x - plugin.allowed[allowed_index][0]);
                        int allowed_z = (event_z - plugin.allowed[allowed_index][2]) * (event_z - plugin.allowed[allowed_index][2]);
                        double allowed_distance = Math.sqrt(allowed_x + allowed_z);

                        int y_dist = event_y - plugin.allowed[allowed_index][1];

                        if (allowed_distance < plugin.allowed[allowed_index][3] && Math.abs(y_dist) < plugin.allowed[allowed_index][3]) {
                            cancel_spawn = false;
                            break;
                        }
                    }

                    event.setCancelled(cancel_spawn);
                }
            }
        }
    }
}
