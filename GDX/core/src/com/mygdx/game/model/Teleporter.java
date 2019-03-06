package com.mygdx.game.model;

import com.mygdx.game.entities.DIRECTION;

/**
 * Stores the information required to be able to move between rooms within the map.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class Teleporter {	
	
	private String srcFile; // The filename of the current map.
	private int telSrcX; // The X value of the teleporter.
	private int telSrcY; // The Y value of the teleporter.
	private int playSrcX; // The X value required by the player.
	private int playSrcY; // The Y value required by the player.
	private String destFile; // The teleporter destination map's filename.
	private int playDestX; // The destination X value of the teleporter.
	private int playDestY; // The destination Y value of the teleporter.
	private DIRECTION facing; // The direction facing after teleporting.
	
	/**
	 * Constructs a new Teleporter object.
	 */
	public Teleporter(String srcFile, int srcX, int srcY, int playSrcX, int playSrcY,
			String destFile, int playDestX, int playDestY, String dir) {
		this.srcFile = srcFile;
		this.telSrcX = srcX;
		this.telSrcY = srcY;
		this.playSrcX = playSrcX;
		this.playSrcY = playSrcY;
		this.destFile = destFile;
		this.playDestX = playDestX;
		this.playDestY = playDestY;
		this.facing = DIRECTION.valueOf(dir);
	}

	/**
	 * This provides the X value in the source of the teleporter itself.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getTelSourceX() {
		return telSrcX;
	}
	
	/**
	 * This provides the Y value in the source of the teleporter itself.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getTelSourceY() {
		return telSrcY;
	}

	/**
	 * Gives the filename of the map for the source of the teleporter.
	 * 
	 * @return The source map filename.
	 */
	public String getSourceFile() {
		return srcFile;
	}
	
	/**
	 * This provides the X value in the source required by the player in order to use the teleporter.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getPlaySourceX() {
		return playSrcX;
	}
	
	/**
	 * This provides the Y value in the source required by the player in order to use the teleporter.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getPlaySourceY() {
		return playSrcY;
	}

	/**
	 * This provides the X value that the player will be teleported to in the destination.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getPlayerDestinationX() {
		return playDestX;
	}
	
	/**
	 * This provides the Y value that the player will be teleported to in the destination.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getPlayerDestinationY() {
		return playDestY;
	}

	/**
	 * This provides the filename of the destination map of the teleporter.
	 * 
	 * @return The filename of the destination map.
	 */
	public String getDestinationFile() {
		return destFile;
	}
	
	/**
	 * This provides the direction that the player will be facing after travelling through the teleporter.
	 * 
	 * @return The {@link DIRECTION} for the player to face after teleportation.
	 */
	public DIRECTION getPlayerDestinationDirection() {
		return this.facing;
	}
}
