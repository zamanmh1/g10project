package prisonescape.game.model;

import prisonescape.game.model.actors.DIRECTION;

/**
 * Stores the information about the teleporter source.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TeleporterDestination {
	
	private String destFile; // The teleporter destination map's filename.
	private int playDestX; // The destination X value of the teleporter.
	private int playDestY; // The destination Y value of the teleporter.
	private DIRECTION facing; // The direction facing after teleporting.
	
	public TeleporterDestination(String destFile, int playerDestX, int playerDestY, DIRECTION dir) {
		this.destFile = destFile;
		this.playDestX = playerDestX;
		this.playDestY = playerDestY;
		this.facing = dir;
	}
	
	/**
	 * This provides the X value that the player will be teleported to in the destination.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getPlayerX() {
		return playDestX;
	}
	
	/**
	 * This provides the Y value that the player will be teleported to in the destination.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getPlayerY() {
		return playDestY;
	}

	/**
	 * This provides the filename of the destination map of the teleporter.
	 * 
	 * @return The filename of the destination map.
	 */
	public String getFile() {
		return destFile;
	}
	
	/**
	 * This provides the direction that the player will be facing after travelling through the teleporter.
	 * 
	 * @return The {@link DIRECTION} for the player to face after teleportation.
	 */
	public DIRECTION getPlayerDirection() {
		return this.facing;
	}
}
