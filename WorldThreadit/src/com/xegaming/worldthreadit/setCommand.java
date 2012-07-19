package com.xegaming.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * User: Benjamin
 * Date: 19/07/12
 * Time: 15:43
 */
public class setCommand extends Thread {
    WorldThreadit threadit;
    Location ll;
    Location rl;
    World world;
    Material mat;
    Player sender;

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
        int i = 0;

        final Vector min = Vector.getMinimum(ll.toVector(), rl.toVector());
        final Vector max = Vector.getMaximum(ll.toVector(), rl.toVector());
        synchronized (threadit.bq.list) {
            for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
                for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                    for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
                        final Block b = world.getBlockAt(x, y, z);
                        if (!(y <= 0 && y >= 256)) {
                            if (!b.getType().equals(mat)) {
                                threadit.bq.addToBlockQueue(b, mat);
                                i++;
                            }
                        }
                    }
                }
            }
        }

        sender.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + i + " Block Edit.");
        WorldThreadit.log.info(i + " Block Edit By " + sender.getName());
    }
}
