package me.supermaxman.worldthreadit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

public class WorldThreaditSet extends Thread {
	
	private List<Block> blocks = new ArrayList<Block>();
	
	private int ItemID;
	
	public WorldThreaditSet(List<Block> b, int i){ blocks = b;ItemID=i;}
    public void run() {
        Thread t = Thread.currentThread();
		while (!t.isInterrupted()) {
		    for (Block b : blocks) {
		        b.setTypeId(ItemID);
		        try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		    this.interrupt();
		}
    }
    
}
