package com.xegaming.worldthreadit.commands;


import org.bukkit.entity.Player;

import com.xegaming.worldthreadit.WorldThreadit;

public class BaseCommandExecutor extends BaseExecutor {
    @Override
    protected void run(Player player, String[] args) {
        if(player.isOp()){
        	
        }
    }

    public BaseCommandExecutor(WorldThreadit XE) {
        super(XE);
    }
}
