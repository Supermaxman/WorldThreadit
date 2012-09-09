package com.xegaming.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Util {

    
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + message);
    }
    public static int findVolume(Player p){
        final Location rl = WorldThreadit.rloc.get(p.getName());
        final Location ll = WorldThreadit.lloc.get(p.getName());
        int l = ll.getBlockX() - rl.getBlockX();
        int w = ll.getBlockZ() - rl.getBlockZ();
        int h = ll.getBlockY() - rl.getBlockY();
        l = Math.abs(l)+1;
        w = Math.abs(w)+1;
        h = Math.abs(h)+1;
        
        int size = l*w*h;
		return size;        
    }
}
