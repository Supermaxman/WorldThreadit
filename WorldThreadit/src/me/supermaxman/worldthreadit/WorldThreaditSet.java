package me.supermaxman.worldthreadit;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

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
        final World world = player.getWorld();
        int i = 0;
        final ArrayList<Chunk> chunks = new ArrayList<Chunk>();
        final ArrayList<Block> blocks = new ArrayList<Block>();
        world.setAutoSave(false);
            for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
                for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
                    for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
                    final Block b = world.getBlockAt(x, y, z);
                    if (!(y <= 0 && y >= 256)) {
                      blocks.add(b);
                    }
                    i++;
                }
            }
        }
        for (Block b : blocks) {
            final Chunk c = b.getChunk();
            if(!chunks.contains(c)){
                if (!c.isLoaded()) {
                    c.load();
                }
                chunks.add(c);
            }
        }
        int i2 = 0;
        final int id = mat.getId();
        for (Block block : blocks) {
            i++;
            //Set block!
            block.setTypeId(id);
            if(i2 >= 10){
                i2 = 0;
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }

        world.setAutoSave(true);

        player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + i + " Block Edit.");
        WorldThreadit.plugin.getLogger().info(i + " Block Edit By " + player.getName());


    }

}