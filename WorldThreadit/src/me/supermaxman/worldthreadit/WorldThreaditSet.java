package me.supermaxman.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldThreaditSet extends Thread {

private final List<Block> blocks;

private final Material ItemID;

private final Player player;

public WorldThreaditSet(List<Block> b, Material i, Player p){ blocks = b;ItemID=i; player = p;}
    public void run() {
        if(blocks != null){
        World world = null;
            if(world == null){
                world = blocks.get(0).getWorld();
            }
            world.setAutoSave(false);
            for (Block b : blocks) {
             if(b != null){
                    final Chunk c = b.getChunk();
                    if(!c.isLoaded()){
                       c.load();
                    }
                b.setType(ItemID);
             }
            }
            world.setAutoSave(true);
			player.sendMessage(ChatColor.AQUA+"[WorldThredit] "+ChatColor.GREEN + blocks.size()+" Block Edit.");
			System.out.println("[WorldThredit]  "+ blocks.size()+" Block Edit By "+player.getName());
        }
    }
    
}