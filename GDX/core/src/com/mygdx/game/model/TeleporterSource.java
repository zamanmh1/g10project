package com.mygdx.game.model;

/**
 * Stores the information about the teleporter destination.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TeleporterSource {
	
	private String srcFile; // The filename of the current map.
	private int telSrcX; // The X value of the teleporter.
	private int telSrcY; // The Y value of the teleporter.
	private int playSrcX; // The X value required by the player.
	private int playSrcY; // The Y value required by the player.
	
	public TeleporterSource(String srcFile, int telSrcX, int telSrcY, int playerSrcX, int playerSrcY) {
		this.srcFile = srcFile;
		this.telSrcX = telSrcX;
		this.telSrcY = telSrcY;
		this.playSrcX = playerSrcX;
		this.playSrcY = playerSrcY;
	}
	
	/**
	 * This provides the X value in the source of the teleporter itself.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getTelX() {
		return telSrcX;
	}
	
	/**
	 * This provides the Y value in the source of the teleporter itself.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getTelY() {
		return telSrcY;
	}

	/**
	 * Gives the filename of the map for the source of the teleporter.
	 * 
	 * @return The source map filename.
	 */
	public String getFile() {
		return srcFile;
	}
	
	/**
	 * This provides the X value in the source required by the player in order to use the teleporter.
	 * 
	 * @return The X value to use the teleporter.
	 */
	public int getPlayX() {
		return playSrcX;
	}
	
	/**
	 * This provides the Y value in the source required by the player in order to use the teleporter.
	 * 
	 * @return The Y value to use the teleporter.
	 */
	public int getPlayY() {
		return playSrcY;
	}

}
