package com.xegaming.worldthreadit.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.xegaming.worldthreadit.SetCommandThread;
import com.xegaming.worldthreadit.Util;
import com.xegaming.worldthreadit.WorldThreadit;

public class SetCommand {

	
	public static void set(Player p, String[] args){
        final Location rl = WorldThreadit.rloc.get(p.getName());
        final Location ll = WorldThreadit.lloc.get(p.getName());

        if ((rl == null) || (ll == null)) {
            Util.sendMessage(p, ChatColor.RED + "Locations not set.");
            return;
        }
        if (args.length != 2) {
            Util.sendMessage(p, ChatColor.RED + "Arguement Error.");
            return;
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
            return;
        }
        if (!m.isBlock()) {
            Util.sendMessage(p, ChatColor.RED + "Cannot Set This Material.");
            return;
        }
        if ((m == Material.LAVA) || (m == Material.WATER) || (m == Material.STATIONARY_WATER || (m == Material.STATIONARY_LAVA))) {
            Util.sendMessage(p, ChatColor.RED + "Water and Lava Not Allowed.");
            return;
        }
        if ((m == Material.GLASS)) {
            Util.sendMessage(p, ChatColor.RED + "Glass Not Allowed.");
            return;
        }
        Util.sendMessage(p, ChatColor.GREEN + "Edit queued.");

        new SetCommandThread(WorldThreadit.plugin, p, ll, rl, p.getWorld(), m);
        
        return;
	}
}
