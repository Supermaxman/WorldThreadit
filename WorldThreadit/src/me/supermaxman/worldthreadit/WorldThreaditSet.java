package me.supermaxman.worldthreadit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;

public class WorldThreaditSet extends Thread {
	
	
		private List<Block> blocks = new ArrayList<Block>();

		private int ItemID;

		public WorldThreaditSet(List<Block> b, int i){ blocks = b;ItemID=i;}
		    public void run() {
		        Thread t = Thread.currentThread();
		        World world = null;
		        if(world == null){
		            	world = blocks.get(0).getWorld();
		            }
		            world.setAutoSave(false);
		            for (Block b : blocks) {
		            	if (b==null){
		            		break;
		            	}
		            	b.setTypeId(ItemID);
		            }
		            world.setAutoSave(true);
		    }
}