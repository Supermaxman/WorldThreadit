package com.xegaming.worldthreadit;

import java.util.LinkedList;

public class UndoThread extends Thread {
    final LinkedList<QueuedBlock> undoList = new LinkedList<QueuedBlock>();
    private final WorldThreadit threadit;
    final boolean canrun = true;
    public UndoThread(WorldThreadit threadit) {
        this.threadit = threadit;
        this.setName("threadit-UndoThread");
    }

    @Override
    public void run() {
    	while(canrun){
    		
    		try {
    			sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }
}