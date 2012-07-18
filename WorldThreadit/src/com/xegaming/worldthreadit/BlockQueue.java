package com.xegaming.worldthreadit;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * User: Benjamin
 * Date: 18/07/12
 * Time: 13:53
 */
public class BlockQueue extends Thread {
    LinkedList<QueuedBlock> list = new LinkedList<QueuedBlock>();
    WorldThreadit threadit;
    Random rand = new Random(6783628548L);

    public BlockQueue(WorldThreadit threadit) {
        this.threadit = threadit;
        this.setName("threadit-BlockQueue");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (list) {
                while (!list.isEmpty()) {
                    try {
                        QueuedBlock queuedBlock = list.pop();
                        World w = threadit.getServer().getWorld(queuedBlock.worldName);
                        Block b = w.getBlockAt(queuedBlock.X, queuedBlock.Y, queuedBlock.Z);
                        b.setTypeId(queuedBlock.newID);
                        sleep(1);
                    } catch (NoSuchElementException e) {
                        break;
                    } catch (NullPointerException e) {
                        //Swallowing due to lazy
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
