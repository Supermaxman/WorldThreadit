package com.xegaming.worldthreadit;

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
    boolean shouldrun = true;

    public BlockQueue(WorldThreadit threadit) {
        this.threadit = threadit;
        this.setName("threadit-BlockQueue");
    }

    @Override
    public void run() {
        while (shouldrun) {
            try {
                sleep(0);
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
                                if (w == null) {
                                    return;
                                }
                                final Block b = w.getBlockAt(queuedBlock.X, queuedBlock.Y, queuedBlock.Z);
                                if (b == null) {
                                    return;
                                }

                                if (!b.getChunk().isLoaded()) {
                                    b.getChunk().load();
                                }
                                b.setTypeId(queuedBlock.newID);
                            }
                        });
                    } catch (NoSuchElementException e) {
                        return;
                    }
                }

            }
        }
    }

    public void addToBlockQueue(int x, int y, int z, World world, int mat) {
        synchronized (list) {
            QueuedBlock qb = new QueuedBlock(x, y, z, world.getName(), mat);

            list.push(qb);
        }

    }
}
