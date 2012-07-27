package com.xegaming.worldthreadit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {

    /**
     * Send a message to a player
     *
     * @param player
     * @param message
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.AQUA + "[WorldThredit] " + message);
    }
}
