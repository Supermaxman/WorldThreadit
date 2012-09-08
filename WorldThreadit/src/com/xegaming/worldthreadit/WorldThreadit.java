package com.xegaming.worldthreadit;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.xegaming.worldthreadit.commands.BaseCommandExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class WorldThreadit extends JavaPlugin implements Listener {

    //Required
    public static final Map<String, Location> rloc = new HashMap<String, Location>();
    public static final Map<String, Location> lloc = new HashMap<String, Location>();
    public BlockQueue bq;
    public static Logger log;
    public static WorldThreadit plugin;
    @Override
    public void onDisable() {
        log.info("Shutting down blockqueue, please wait.");
        bq.shouldrun = false;
        while (bq.isAlive()) {
            //wait
        }

        log.info("WorldThreadit Disabled.");
    }

    @Override
    public void onEnable() {
    	WorldThreadit.plugin = this;
        log = getLogger();
        getServer().getPluginManager().registerEvents(new WorldThreadit(), this);
        bq = new BlockQueue(this);
        bq.start();
        log.info("WorldThreadit enabled!");
        getCommand("wt").setExecutor(new BaseCommandExecutor(this));
    }
    
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            if ((player.getItemInHand().getType() == Material.GOLD_AXE) && (player.isOp())) {
                Location loc = event.getClickedBlock().getLocation();
                rloc.put(player.getName(), loc);
                player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Location 2 Placed.");
                event.setCancelled(true);
            }

        } else if (action == Action.LEFT_CLICK_BLOCK) {
            if ((player.getItemInHand().getType() == Material.GOLD_AXE) && (player.isOp())) {
                Location loc = event.getClickedBlock().getLocation();
                lloc.put(player.getName(), loc);
                player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Location 1 Placed.");
                event.setCancelled(true);
            }

        }
    }


}