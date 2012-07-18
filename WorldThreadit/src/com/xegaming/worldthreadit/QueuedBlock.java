package com.xegaming.worldthreadit;

/**
 * User: Benjamin
 * Date: 18/07/12
 * Time: 13:55
 */
public class QueuedBlock {
    int X;
    int Y;
    int Z;
    String worldName;
    int newID;

    public QueuedBlock(int X, int Y, int Z, String worldName, int newID) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.worldName = worldName;
        this.newID = newID;
    }

}
