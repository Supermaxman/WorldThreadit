package com.xegaming.worldthreadit.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.xegaming.worldthreadit.Util;

public class ReplaceCommand {

	public static void replace(Player p, String[] args){
		Util.sendMessage(p, ChatColor.RED + "Removed until replace is stable.");
	}
}
