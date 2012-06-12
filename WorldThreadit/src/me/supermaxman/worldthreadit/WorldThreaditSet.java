package me.supermaxman.worldthreadit;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WorldThreaditSet extends Thread {

    private final Vector min;
    private final Vector max;
    private final Material mat;

    private final Player player;

    public WorldThreaditSet(Vector mn, Vector mx, Material i, Player p) {
        setName("WT-Thread-" + getId());
        mat = i;
        player = p;
        min = mn;
        max = mx;
    }

    public void run() {
        World world = player.getWorld();
        int i = 0;
        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {

                    //counting run
                    i++;
                }
            }
        }
        world.setAutoSave(false);
        player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + i + " Block Edit.");
        System.out.println("[WorldThredit]  " + i + " Block Edit By " + player.getName());
        for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
            for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {

                    //Get block and null check
                    Block b = player.getWorld().getBlockAt(x, y, z);
                    if (b != null) {
                        //Get location
                        Location l = b.getLocation();
                        //Check height
                        if (!(l.getBlockY() < 0 || l.getBlockY() > 256)) {
                            final Chunk c = b.getChunk();
                            //Load chunk, fixes npe
                            if (!c.isLoaded()) {
                                c.load();
                            }
                            //Set block!
                            b.setType(mat);
                        }
                    }
                }
            }
        }
        world.setAutoSave(true);

        player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Done!");

    }

}