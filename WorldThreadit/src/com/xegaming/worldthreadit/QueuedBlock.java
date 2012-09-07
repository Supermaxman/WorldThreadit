package com.xegaming.worldthreadit;

/**
 * User: Rainbow
 * Date: 18/07/12
 * Time: 13:55
 */
class QueuedBlock {
    final int X;
    final int Y;
    final int Z;
    final String worldName;
    final int newID;
    final int oldID;
    final String playerName;

    public QueuedBlock(int X, int Y, int Z, String worldName, int newID, int oldID, String playerName) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.worldName = worldName;
        this.newID = newID;
        this.oldID = oldID;
        this.playerName = playerName;
        		
    }

}
