package com.bengboo.nospawn.events;
import com.bengboo.nospawn.Haven;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.Math;
import java.util.Arrays;


public class EntitySpawns implements Listener {

    int[][] repellers;
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
        if (Arrays.asList(banned_mobs).contains(event.getEntityType())) {
            Location loc = event.getLocation();
            int event_x = loc.getBlockX();
            int event_z = loc.getBlockZ();

            for (int index = 0; index < plugin.repellers.length; index++) {
                int diff_x = (event_x - plugin.repellers[index][0]) * (event_x - plugin.repellers[index][0]);
                int diff_z = (event_z - plugin.repellers[index][2]) * (event_z - plugin.repellers[index][2]);
                double distance = Math.sqrt(diff_x + diff_z);

                if (distance < plugin.repellers[index][3]) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
