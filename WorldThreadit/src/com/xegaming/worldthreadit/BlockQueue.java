package com.xegaming.worldthreadit;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * User: Benjamin
 * Date: 18/07/12
 * Time: 13:53
 */
class BlockQueue extends Thread {
    final LinkedList<QueuedBlock> list = new LinkedList<QueuedBlock>();
    private final WorldThreadit threadit;

    public BlockQueue(WorldThreadit threadit) {
        this.threadit = threadit;
        this.setName("threadit-BlockQueue");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (list) {
                while (!list.isEmpty()) {
                    try {
                        final QueuedBlock queuedBlock = list.pop();
                        threadit.getServer().getScheduler().scheduleSyncDelayedTask(threadit, new Runnable() {
                            public void run() {
                                World w = threadit.getServer().getWorld(queuedBlock.worldName);
                                final Block b = w.getBlockAt(queuedBlock.X, queuedBlock.Y, queuedBlock.Z);
                                if (!b.getChunk().isLoaded()) {
                                    b.getChunk().load();
                                }
                                b.setTypeId(queuedBlock.newID);
                            }
                        });
                    } catch (NoSuchElementException e) {
                        break;
                    } catch (NullPointerException e) {
                        //Swallowing due to lazy
                    }
                }

            }
        }
    }

    public void addToBlockQueue(Block b, Material m) {
        QueuedBlock qb = new QueuedBlock(b.getX(), b.getY(), b.getZ(), b.getWorld().getName(), m.getId());

        list.push(qb);

    }
}
