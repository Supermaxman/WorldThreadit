package com.xegaming.worldthreadit.commands;

import org.bukkit.entity.Player;

import com.xegaming.worldthreadit.UndoThread;

public class UndoCommand {
	
	public static void undo(Player p, String[] args){
		UndoThread.undo(p);
	}
}
