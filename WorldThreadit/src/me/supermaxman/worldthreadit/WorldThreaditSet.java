package me.supermaxman.worldthreadit;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

public class WorldThreaditSet extends Thread {

private final List<Block> blocks;

private final Material ItemID;

public WorldThreaditSet(List<Block> b, Material i){ blocks = b;ItemID=i;}
    public void run() {
        if(blocks != null){
        World world = null;
            if(world == null){
                world = blocks.get(0).getWorld();
            }
            world.setAutoSave(false);
            for (Block b : blocks) {
            	if(b != null){
                b.setType(ItemID);
            	}
            }
            world.setAutoSave(true);
        }
    }
    
}