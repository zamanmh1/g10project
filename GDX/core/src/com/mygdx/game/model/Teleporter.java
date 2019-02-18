package com.mygdx.game.model;

import com.mygdx.game.entities.DIRECTION;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class Teleporter {
	
	private String srcFile;
	private int telSrcX;
	private int telSrcY;
	private int playSrcX;
	private int playSrcY;
	private String destFile;
	private int playDestX;
	private int playDestY;
	private DIRECTION facing;
	
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

	public int getTelSourceX() {
		return telSrcX;
	}
	
	public int getTelSourceY() {
		return telSrcY;
	}

	public String getSourceFile() {
		return srcFile;
	}
	
	public int getPlaySourceX() {
		return playSrcX;
	}
	
	public int getPlaySourceY() {
		return playSrcY;
	}

	public int getPlayerDestinationX() {
		return playDestX;
	}
	
	public int getPlayerDestinationY() {
		return playDestY;
	}

	public String getDestinationFile() {
		return destFile;
	}
	
	public DIRECTION getPlayerDestinationDirection() {
		return this.facing;
	}
}
