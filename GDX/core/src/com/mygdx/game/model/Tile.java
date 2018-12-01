package com.mygdx.game.model;

import com.mygdx.game.entities.MapActor;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class Tile {
	
	private MapActor actor;
	private boolean walkable;
	private boolean teleporter;
	private String teleporterType;
	
	public Tile() {
		actor = null;
		walkable = true;
		teleporter = false;
	}
	
	public MapActor getActor() {
		return actor;
	}
	
	public void setActor(MapActor actor) {
		this.actor = actor;
	}
	
	public boolean getWalkable() {
		return walkable;
	}
	
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	public boolean getTeleporter() {
		return teleporter;
	}
	
	public String getTeleporterType() {
		return teleporterType;
	}
	
	public void setTeleporter(boolean teleporter, String str) {			
		if(str.equals("fwd")) {
			this.teleporter = teleporter;
			this.teleporterType = str;
		} else if(str.equals("bwd")) {
			this.teleporter = teleporter;
			this.teleporterType = str;
		}
	}
}