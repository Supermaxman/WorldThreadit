package com.xegaming.worldthreadit;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.xegaming.worldthreadit.commands.BaseCommandExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class WorldThreadit extends JavaPlugin implements Listener {

    //Required
    private static final Map<String, Location> rloc = new HashMap<String, Location>();
    private static final Map<String, Location> lloc = new HashMap<String, Location>();
    public BlockQueue bq;
    public static Logger log;

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
        log = getLogger();
        getServer().getPluginManager().registerEvents(new WorldThreadit(), this);
        bq = new BlockQueue(this);
        bq.start();
        log.info("WorldThreadit enabled!");
        //getCommand("wt").setExecutor(new BaseCommandExecutor(this));
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && command.getName().equalsIgnoreCase("wt")) {
            if (sender.isOp()) {

                final Player p = (Player) sender;
                final World world = p.getWorld();

                if (args[0].equalsIgnoreCase("set")) {
                    final Location rl = rloc.get(p.getName());
                    final Location ll = lloc.get(p.getName());

                    if ((rl == null) || (ll == null)) {
                        Util.sendMessage(p, ChatColor.RED + "Locations not set.");
                        return true;
                    }
                    if (args.length != 2) {
                        Util.sendMessage(p, ChatColor.RED + "Arguement Error.");
                        return true;
                    }
                    
                    Material m;
                    try {
                        m = Material.getMaterial(Integer.parseInt(args[1]));
                    } catch (Exception e) {
                        String s = args[1].toUpperCase();
                        m = Material.getMaterial(s);
                    }
                    
                    
                    if (m == null) {
                        Util.sendMessage(p, ChatColor.RED + "Arguement Error.");
                        return true;
                    }
                    if (!m.isBlock()) {
                        Util.sendMessage(p, ChatColor.RED + "Cannot Set This Material.");
                        return true;
                    }
                    if ((m == Material.LAVA) || (m == Material.WATER) || (m == Material.STATIONARY_WATER || (m == Material.STATIONARY_LAVA))) {
                        Util.sendMessage(p, ChatColor.RED + "Water and Lava Not Allowed.");
                        return true;
                    }
                    if ((m == Material.GLASS)) {
                        Util.sendMessage(p, ChatColor.RED + "Glass Not Allowed.");
                        return true;
                    }
                    Util.sendMessage(p, ChatColor.GREEN + "Edit queued.");

                    new setCommand(this, p, ll, rl, world, m);
                    return true;

                } else if (args[0].equalsIgnoreCase("replace")) {
                    Util.sendMessage(p, ChatColor.RED + "Removed until set is stable.");


                    return true;

                } else if (args[0].equalsIgnoreCase("wand")) {
                    p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
                } else if (args[0].equalsIgnoreCase("expand")) {

                    if (args.length == 3) {
                        BlockFace side;
                        try {
                            side = BlockFace.valueOf(args[2].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            Util.sendMessage(p, ChatColor.RED + "That is not a Direction.");
                            return true;
                        }
                        int amt;
                        try {
                            amt = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            Util.sendMessage(p, ChatColor.RED + "That is not a Number.");
                            return true;
                        }
                        if (!rloc.containsKey(p.getName()) && !lloc.containsKey(p.getName())) {
                            Util.sendMessage(p, ChatColor.RED + "You have not made an area selection yet.");
                            return true;
                        }
                        final Location rl = rloc.get(p.getName());
                        final Location ll = lloc.get(p.getName());
                        Vector v = rl.toVector().subtract(p.getLocation().toVector());
                        Vector v2 = ll.toVector().subtract(p.getLocation().toVector());
                        /*
                         * -x = north
                         * +x = south
                         * +z = west
                         * -z = east
                         */


                        if (side == BlockFace.UP) {
                            if (v.getY() > v2.getY()) {
                                if (rl.getY() + amt >= 255) {
                                    rloc.put(p.getName(), new Location(rl.getWorld(), rl.getBlock().getX(), 254, rl.getBlock().getZ()));
                                } else {
                                    rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                                }
                            } else {
                                if (ll.getY() + amt >= 255) {
                                    lloc.put(p.getName(), new Location(ll.getWorld(), ll.getBlock().getX(), 254, ll.getBlock().getZ()));
                                } else {
                                    lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                                }
                            }
                        } else if (side == BlockFace.DOWN) {
                            if (v.getY() < v2.getY()) {
                                if (rl.getY() - amt <= 0) {
                                    rloc.put(p.getName(), new Location(rl.getWorld(), rl.getBlock().getX(), 0, rl.getBlock().getZ()));
                                } else {
                                    rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                                }
                            } else {
                                if (ll.getY() - amt <= 0) {
                                    lloc.put(p.getName(), new Location(ll.getWorld(), ll.getBlock().getX(), 0, ll.getBlock().getZ()));
                                } else {
                                    lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                                }
                            }
                        } else if (side == BlockFace.NORTH) {
                            if (v.getX() < v2.getX()) {
                                rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                            } else {
                                lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                            }
                        } else if (side == BlockFace.SOUTH) {
                            if (v.getX() > v2.getX()) {
                                rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                            } else {
                                lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                            }
                        } else if (side == BlockFace.EAST) {
                            if (v.getZ() < v2.getZ()) {
                                rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                            } else {
                                lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                            }
                        } else if (side == BlockFace.WEST) {
                            if (v.getZ() > v2.getZ()) {
                                rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                            } else {
                                lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                            }
                        } else {
                            Util.sendMessage(p, ChatColor.RED + "Direction Not Allowed.");
                            return true;
                        }

                        Util.sendMessage(p, ChatColor.GREEN + "Expanded " + amt + " " + side.toString().toLowerCase() + ".");

                    } else if (args.length == 2) {


                    } else {
                        Util.sendMessage(p, ChatColor.RED + "Incorrect Syntax.");
                    }


                }
            }
        }
        return true;
    }

    @SuppressWarnings("UnusedDeclaration")
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