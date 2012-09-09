package com.xegaming.worldthreadit;

import java.util.UUID;

/**
 * User: Rainbow
 * Date: 18/07/12
 * Time: 13:55
 */
class QueuedBlock {
    //X Coordinate of block
	final int X;
	
    //Y Coordinate of block
    final int Y;
    
    //Z Coordinate of block
    final int Z;
    
    //World that contains the block
    final String worldName;
    
    //What to change the block to
    final int newID;
    
    //What the block was
    final int oldID;
    
    //Edit ID
    final UUID uuid;
    
    final byte oldData;
    
    public QueuedBlock(int X, int Y, int Z, String worldName, int newID, int oldID, UUID uuid, byte oldData) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.worldName = worldName;
        this.newID = newID;
        this.oldID = oldID;
        this.uuid = uuid;
        this.oldData = oldData;
        		
    }

}
