package com.mygdx.game.model;

import com.mygdx.game.entities.MapActor;

/**
 * Represents an individual tile of a map for the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class Tile {
	
	private MapActor actor; // The actor currently occupying the tile.
	private boolean walkable; // Whether the tile is walkable by actors.
	private boolean teleporter; // Whether the tile acts as a teleporter.
	private boolean alarm; // Whether the tile is part of the alarm system.
	
	/**
	 * Constructs an empty tile, containing no actors and is walkable by actors.
	 */
	public Tile() {
		actor = null;
		walkable = true;
		teleporter = false;
		alarm = false;
	}
	
	/**
	 * Provides information of the current actor in the tile.
	 * 
	 * @return The actor in the tile or null if empty.
	 */
	public MapActor getActor() {
		return actor;
	}
	
	/**
	 * Sets the tile with the actor provided.
	 * 
	 * @param actor The actor to set in the tile.
	 */
	public void setActor(MapActor actor) {
		this.actor = actor;
	}
	
	/**
	 * Provides information of whether or not actors can walk within the tile.
	 * 
	 * @return True if tile is walkable, false if not.
	 */
	public boolean getWalkable() {
		return walkable;
	}
	
	/**
	 * Provides a way to change whether or not the tile is walkable.
	 * 
	 * @param walkable True to be able to walk in the tile, false if not.
	 */
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	/**
	 * Provides a way to find if the tile is used as a teleporter.
	 * 
	 * @return Whether the tile is a teleporter or not.
	 */
	public boolean getTeleporter() {
		return teleporter;
	}
	
	/**
	 * Sets the tile to become a teleporter tile.
	 */
	public void setTeleporter() {			
		this.teleporter = true;
	}
	
	/**
	 * Sets the alarm property of the tile.
	 * 
	 * @param alarm True means that the tile contains an alarm.
	 */
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	
	/**
	 * Provides information of whether or not the tile is an alarm tile.
	 * @return True if an alarm tile, otherwise false.
	 */
	public boolean getAlarm() {
		return alarm;
	}
}
