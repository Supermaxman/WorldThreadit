package com.xegaming.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
        int i = 0;

        final Vector min = Vector.getMinimum(ll.toVector(), rl.toVector());
        final Vector max = Vector.getMaximum(ll.toVector(), rl.toVector());
        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
                    if (!(y <= 0 && y >= 256)) {
                        threadit.bq.addToBlockQueue(x, y, z, world, mat.getId());
                        i++;
                    }
                }
            }
        }


        Util.sendMessage(sender, String.format(ChatColor.GREEN + "%d Block Edit.", i));
        WorldThreadit.log.info(i + " Block Edit By " + sender.getName());
    }
}
