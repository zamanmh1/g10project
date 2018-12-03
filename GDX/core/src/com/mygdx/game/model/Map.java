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

public class Map {
	
	private String name;
	private String fileLocation;
	private int spawnX, spawnY;
	private String leadsTo;
	private DIRECTION facing;
	
	public Map(String name, String fileLocation, int spawnX, int spawnY, String leadsTo, String dir) {
		this.name = name;
		this.fileLocation = fileLocation;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.leadsTo = leadsTo;
		this.facing = DIRECTION.valueOf(dir);
	}

	public String getName() {
		return name;
	}
	
	public String getFileLocation() {
		return fileLocation;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public String getLeadsTo() {
		return leadsTo;
	}
	
	public DIRECTION getDirection() {
		return this.facing;
	}
}
