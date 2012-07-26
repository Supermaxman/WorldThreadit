package com.xegaming.worldthreadit;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WorldThreadit extends JavaPlugin implements Listener {

    //Required
    private static final Map<String, Location> rloc = new HashMap<String, Location>();
    private static final Map<String, Location> lloc = new HashMap<String, Location>();
    public BlockQueue bq;
    public static Logger log;

    @Override
    public void onDisable() {
        bq.interrupt();
        getLogger().info("WorldThreadit Disabled.");
    }

    @Override
    public void onEnable() {
        log = getLogger();
        getServer().getPluginManager().registerEvents(new WorldThreadit(), this);
        PluginDescriptionFile pdfFile = this.getDescription();
        bq = new BlockQueue(this);
        bq.start();
        getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
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
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Locations not set.");
                        return true;
                    }
                    if (args.length != 2) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
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
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
                        return true;
                    }
                    if ((m == Material.LAVA) || (m == Material.WATER)) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Water and Lava Not Allowed.");
                        return true;
                    }
                    p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Edit queued.");

                    new setCommand(this, p, ll, rl, world, m);
                    return true;

                } else if (args[0].equalsIgnoreCase("replace")) {
                    final Location rl = rloc.get(p.getName());
                    final Location ll = lloc.get(p.getName());

                    if ((rl == null) || (ll == null)) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Locations not set.");
                        return true;
                    }
                    if (args.length != 3) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
                        return true;
                    }

                    Material m;
                    try {
                        m = Material.getMaterial(Integer.parseInt(args[1]));
                    } catch (Exception e) {
                        String s = args[1].toUpperCase();
                        m = Material.getMaterial(s);
                    }
                    Material r;
                    try {
                        r = Material.getMaterial(Integer.parseInt(args[2]));
                    } catch (Exception e) {
                        String s = args[2].toUpperCase();
                        r = Material.getMaterial(s);
                    }

                    if (m == null || r == null) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
                        return true;
                    }

                    if ((r == Material.LAVA) || (r == Material.WATER)) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Water and Lava Not Allowed.");
                        return true;
                    }
                    p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Edit queued.");

                    //final Vector min = Vector.getMinimum(ll.toVector(), rl.toVector());
                    //final Vector max = Vector.getMaximum(ll.toVector(), rl.toVector());
                    new replaceCommand(this, p, ll, rl, world, m, r);
                    return true;

                } else if (args[0].equalsIgnoreCase("wand")) {
                    p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
                } else if (args[0].equalsIgnoreCase("expand")) {
                	
                	if (args.length==3){
                		BlockFace side = null;
                		try{
                			side = BlockFace.valueOf(args[2].toUpperCase());
                		}catch(IllegalArgumentException e){
                            p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "That is not a Direction.");
                            return true;
                		}
                		int amt;
                		try{
                			amt = Integer.parseInt(args[1]);
                		}catch(NumberFormatException e){
                            p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "That is not a Number.");
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
                        
                        
                        
                        if(side == BlockFace.UP){
                        	if(v.getY()>v2.getY()){
                        		if(rl.getY()+amt>=255){
                        			rloc.put(p.getName(), new Location(rl.getWorld(),rl.getBlock().getX(),254,rl.getBlock().getZ()));
                        		}else{
                            		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        		}
                        	}else{
                        		if(ll.getY()+amt>=255){
                        			lloc.put(p.getName(), new Location(ll.getWorld(),ll.getBlock().getX(),254,ll.getBlock().getZ()));
                        		}else{
                            		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        		}
                        	}
                        }else if(side == BlockFace.DOWN){
                        	if(v.getY()<v2.getY()){
                        		if(rl.getY()-amt<=0){
                        			rloc.put(p.getName(), new Location(rl.getWorld(),rl.getBlock().getX(),0,rl.getBlock().getZ()));
                        		}else{
                            		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        		}
                        	}else{
                        		if(ll.getY()-amt<=0){
                        			lloc.put(p.getName(), new Location(ll.getWorld(),ll.getBlock().getX(),0,ll.getBlock().getZ()));
                        		}else{
                            		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        		}                        	}
                        }else if(side == BlockFace.NORTH){
                        	if(v.getX()<v2.getX()){
                        		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        	}else{
                        		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        	}
                        }else if(side == BlockFace.SOUTH){
                        	if(v.getX()>v2.getX()){
                        		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        	}else{
                        		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        	}
                        }else if(side == BlockFace.EAST){
                        	if(v.getZ()<v2.getZ()){
                        		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        	}else{
                        		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        	}
                        }else if(side == BlockFace.WEST){
                        	if(v.getZ()>v2.getZ()){
                        		rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                        	}else{
                        		lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                        	}
                        }else{
                            p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Direction Not Allowed.");
                        	return true;
                        }
                        
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Expanded "+amt+" "+side.toString().toLowerCase()+".");
                        
                	}else{
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Incorrect Syntax.");
                	}
                	
                	
                }
            }
        }
        return true;
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