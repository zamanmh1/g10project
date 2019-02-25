package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.prisonescapegame.GameSettings;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class Item implements MapActor {
	private Sprite sprite;
	private String name;
	private ITEM_TYPE type;
	private String appearsIn;
	private int x, y;
	private boolean walkable;
	
	public Item(Sprite sprite, String name, String appearsIn, String type, int x, int y) {
		this.sprite = sprite;
		sprite.setSize(GameSettings.TILE_SIZE, GameSettings.TILE_SIZE); // Sprite the size of a single tile.
		this.name = name;
		this.appearsIn = appearsIn;
		this.type = ITEM_TYPE.valueOf(type); // Convert string to enum value.
		this.x = x;
		this.y = y;
		this.walkable = false;		
	}
	
	// Safer than using strings.
	// Later can use state pattern to handle depending upon item type.
	public enum ITEM_TYPE {
		KEY,
		WEAPON,
		SLEEP,
		;
	}
	
	public boolean getWalkable() {
		return this.walkable;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ITEM_TYPE getType() {
		return this.type;
	}
	
	public String getAppearsIn() {
		return this.appearsIn;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public float getWorldX() {
		return getX() * GameSettings.TILE_SIZE; // Coordinates in map (current tile * tile size)
	}
	
	public float getWorldY() {
		return getY() * GameSettings.TILE_SIZE; // Coordinates in map (current tile * tile size)
	}
}
