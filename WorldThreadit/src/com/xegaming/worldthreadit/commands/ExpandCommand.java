package com.xegaming.worldthreadit.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.xegaming.worldthreadit.Util;
import com.xegaming.worldthreadit.WorldThreadit;

public class ExpandCommand {
	
	public static void expand(Player p, String[] args){

        if (args.length == 3) {
            BlockFace side;
            try {
                side = BlockFace.valueOf(args[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                Util.sendMessage(p, ChatColor.RED + "That is not a Direction.");
                return;
            }
            int amt;
            try {
                amt = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                Util.sendMessage(p, ChatColor.RED + "That is not a Number.");
                return;
            }
            if (!WorldThreadit.rloc.containsKey(p.getName()) && !WorldThreadit.lloc.containsKey(p.getName())) {
                Util.sendMessage(p, ChatColor.RED + "You have not made an area selection yet.");
                return;
            }
            final Location rl = WorldThreadit.rloc.get(p.getName());
            final Location ll = WorldThreadit.lloc.get(p.getName());
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
                        WorldThreadit.rloc.put(p.getName(), new Location(rl.getWorld(), rl.getBlock().getX(), 254, rl.getBlock().getZ()));
                    } else {
                        WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                    }
                } else {
                    if (ll.getY() + amt >= 255) {
                        WorldThreadit.lloc.put(p.getName(), new Location(ll.getWorld(), ll.getBlock().getX(), 254, ll.getBlock().getZ()));
                    } else {
                        WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                    }
                }
            } else if (side == BlockFace.DOWN) {
                if (v.getY() < v2.getY()) {
                    if (rl.getY() - amt <= 0) {
                        WorldThreadit.rloc.put(p.getName(), new Location(rl.getWorld(), rl.getBlock().getX(), 0, rl.getBlock().getZ()));
                    } else {
                        WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                    }
                } else {
                    if (ll.getY() - amt <= 0) {
                        WorldThreadit.lloc.put(p.getName(), new Location(ll.getWorld(), ll.getBlock().getX(), 0, ll.getBlock().getZ()));
                    } else {
                        WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                    }
                }
            } else if (side == BlockFace.NORTH) {
                if (v.getX() < v2.getX()) {
                    WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                } else {
                    WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                }
            } else if (side == BlockFace.SOUTH) {
                if (v.getX() > v2.getX()) {
                    WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                } else {
                    WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                }
            } else if (side == BlockFace.EAST) {
                if (v.getZ() < v2.getZ()) {
                    WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                } else {
                	WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                }
            } else if (side == BlockFace.WEST) {
                if (v.getZ() > v2.getZ()) {
                    WorldThreadit.rloc.put(p.getName(), rl.getBlock().getRelative(side, amt).getLocation());
                } else {
                    WorldThreadit.lloc.put(p.getName(), ll.getBlock().getRelative(side, amt).getLocation());
                }
            } else {
                Util.sendMessage(p, ChatColor.RED + "Direction Not Allowed.");
                return;
            }

            Util.sendMessage(p, ChatColor.GREEN + "Expanded " + amt + " " + side.toString().toLowerCase() + ".");

        } else if (args.length == 2) {
        	//TODO direction expand
        }
	}
	
}
