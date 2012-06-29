package me.supermaxman.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class WorldThreadit extends JavaPlugin implements Listener {

    //Required
    public static Map<String, Location> rloc = new HashMap<String, Location>();
    public static Map<String, Location> lloc = new HashMap<String, Location>();

    public static WorldThreadit plugin;

    @Override
    public void onDisable() {
        getLogger().info("WorldThreadit Disabled.");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new WorldThreadit(), this);
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
        plugin = this;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && command.getName().equalsIgnoreCase("wt")) {
            if (sender.isOp()) {

                final Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("set")) {
                    final Location rl = rloc.get(p.getName());
                    final Location ll = lloc.get(p.getName());

                    if ((rl == null) || (ll == null)) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Locations not set.");
                        return true;
                    }
                    if (args.length != 2) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
                        return true;
                    }

                    Material m = null;
                    try {
                        m = Material.getMaterial(Integer.parseInt(args[1]));
                    } catch (Exception e) {
                        String s = args[1].toUpperCase();
                        m = Material.getMaterial(s);
                    }


                    if (m == null) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Arguement Error.");
                        return true;
                    }
                    if ((m == Material.LAVA) || (m == Material.WATER)) {
                        p.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.RED + "Water and Lava Not Allowed.");
                        return true;
                    }
                    Thread thread = new WorldThreaditSet(Vector.getMinimum(ll.toVector(), rl.toVector()), Vector.getMaximum(ll.toVector(), rl.toVector()), m, p);
                    thread.start();

                    return true;

                } else if (args[0].equalsIgnoreCase("wand")) {
                    p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
                }
            }
        }
        return true;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            if ((player.getItemInHand().getType() == Material.GOLD_AXE) && (player.isOp())) {
                Location loc = event.getClickedBlock().getLocation();
                rloc.put(player.getName(), loc);
                player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Location 2 Placed.");
                event.setCancelled(true);
            }

        } else if (action == Action.LEFT_CLICK_BLOCK) {
            if ((player.getItemInHand().getType() == Material.GOLD_AXE) && (player.isOp())) {
                Location loc = event.getClickedBlock().getLocation();
                lloc.put(player.getName(), loc);
                player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + ChatColor.GREEN + "Location 1 Placed.");
                event.setCancelled(true);
            }

        }
    }


}