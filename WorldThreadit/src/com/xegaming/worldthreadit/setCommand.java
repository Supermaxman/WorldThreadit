package com.xegaming.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * User: Benjamin
 * Date: 19/07/12
 * Time: 15:43
 */

class setCommand extends Thread {
    private final WorldThreadit threadit;
    private final Location ll;
    private final Location rl;
    private final World world;
    private final Material mat;
    private final Player sender;

    public setCommand(WorldThreadit threadit, Player sender, Location ll, Location rl, World world, Material mat) {
        this.threadit = threadit;
        this.ll = ll;
        this.rl = rl;
        this.world = world;
        this.mat = mat;
        this.sender = sender;
        this.start();
    }

    public void run() {
        final int matid = mat.getId();
        final String worldName = world.getName();
        ArrayList<QueuedBlock> blocks = new ArrayList<QueuedBlock>();
        final Vector min = Vector.getMinimum(ll.toVector(), rl.toVector());
        final Vector max = Vector.getMaximum(ll.toVector(), rl.toVector());
        int total = 0;
        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
                    if (!(y <= 0 && y >= 256)) {
                        total++;
                    }
                }
            }
        }
        Util.sendMessage(sender, String.format(ChatColor.GREEN + "%d Block edit queued.", total));
        WorldThreadit.log.info(total + " Block Edit queued By " + sender.getName());

        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
                    if (!(y <= 0 && y >= 256)) {
                        blocks.add(new QueuedBlock(x, y, z, worldName, matid));
                        if (blocks.size() >= 10000) {
                            threadit.bq.addToBlockQueue(blocks);
                            blocks.clear();
                        }
                    }
                }
            }
        }

        threadit.bq.addToBlockQueue(blocks);

    }
}
