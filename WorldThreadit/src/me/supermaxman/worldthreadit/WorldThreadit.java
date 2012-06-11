package me.supermaxman.worldthreadit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class WorldThreadit extends JavaPlugin implements Listener{
	
	//Required
	public static Map<String, Location> rloc = new HashMap<String, Location>();
	public static Map<String, Location> lloc = new HashMap<String, Location>();
	
	public static WorldThreadit plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	@Override
	public void onDisable() {this.logger.info("WorldThreadit Disabled.");}
	@Override
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new WorldThreadit(), this);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		
	}
	
	private List<Block> blocks = new ArrayList<Block>();
	
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
        if (sender instanceof Player && command.getName().equalsIgnoreCase("wt")) {
            if (sender.isOp()) {
            	
            	final Player p = (Player) sender;
            	if(args[0].equalsIgnoreCase("set")){        
					final Location rl = rloc.get(p.getName());
					final Location ll = lloc.get(p.getName());
					
					if ((rl==null)||(ll==null)){
						p.sendMessage(ChatColor.BOLD+""+ChatColor.AQUA+"WorldThreadit Locations not set.");
						return true;
					}
					if (args.length!=2){
						p.sendMessage(ChatColor.BOLD+""+ChatColor.AQUA+"Arguement Error.");
						return true;
					}
					
					
					final int ItemID = Integer.parseInt(args[1]);
					
			        Vector min = Vector.getMinimum(ll.toVector(), rl.toVector());
			        Vector max = Vector.getMaximum(ll.toVector(), rl.toVector());
			        for (int y = (int) min.getY(); y <= (int) max.getY(); y++) {
			        	for (int x = (int) min.getX(); x <= (int) max.getX(); x++) {
			                for (int z = (int) min.getZ(); z <= (int) max.getZ(); z++) {
			                    blocks.add(p.getWorld().getBlockAt(x, y, z));
			                }
			            }
			        }
			        
					Thread thread =new WorldThreaditSet(blocks, ItemID);
					thread.start();
					
            		return true;
            			
            	}else if(args[0].equalsIgnoreCase("wand")){
            		p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
            	}
            }
        }
        return true;
	}
	
	
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK){
			if ((player.getItemInHand().getType()==Material.GOLD_AXE)&&(player.isOp())){
				Location loc = event.getClickedBlock().getLocation();
				rloc.put(player.getName(), loc);
				player.sendMessage(ChatColor.BOLD+""+ChatColor.AQUA+"WorldThreadit Location 2 Placed");
				event.setCancelled(true);
			}
			
		}else if (action == Action.LEFT_CLICK_BLOCK){
			if ((player.getItemInHand().getType()==Material.GOLD_AXE)&&(player.isOp())){
				Location loc = event.getClickedBlock().getLocation();
				lloc.put(player.getName(), loc);
				player.sendMessage(ChatColor.BOLD+""+ChatColor.AQUA+"WorldThreadit Location 1 Placed");
				event.setCancelled(true);
			}
		
		}
	}
	
	
	
	
	
	
	
	
	
	
}