package com.xegaming.worldthreadit.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WandCommand {

	public static void wand(Player p, String[] args){
        p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
	}
}
