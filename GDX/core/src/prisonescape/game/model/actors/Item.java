package prisonescape.game.model.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;

import prisonescape.game.GameSettings;

/**
 * This class encapsulates the logic of an Item within the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class Item implements MapActor {
	
	/**
	 * The sprite of the item.
	 */
	private Sprite sprite;
	
	/**
	 * The name of the item.
	 */
	private String name;
	
	/**
	 * The type of the item.
	 */
	private ITEM_TYPE type;
	
	/**
	 * The map which the item appears in.
	 */
	private String appearsIn;
	
	/**
	 * The coordinates of the item within the map.
	 */
	private int x, y;
	
	/**
	 * Whether can walk over item or not.
	 */
	private boolean walkable;
	
	/**
	 * Whether the item has been found.
	 */
	private boolean found;
	
	/**
	 * Creates a new Item within the game.
	 * 
	 * @param sprite The item's image.
	 * @param name Item's name.
	 * @param appearsIn File path of map appearing in.
	 * @param type Item's type.
	 * @param x X-Coordinate.
	 * @param y Y-Coordinate.
	 */
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
	
	/**
	 * An enum to store the different types of item which are available within the game.
	 * 
	 * @author Sam Ward
	 * 
	 * @version 1.0
	 * @since 0.2
	 *
	 */
	public enum ITEM_TYPE {
		KEY,
		WEAPON,
		SLEEP,
		INTERACT,
		;
	}
	
	/**
	 * Retrieves whether the item can be walked over.
	 * 
	 * @return Whether the tile the item is in can be walked over.
	 */
	public boolean getWalkable() {
		return this.walkable;
	}
	
	/**
	 * The image associated with the item.
	 * 
	 * @return The way the item looks.
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	
	/**
	 * Retrieves the name of the item.
	 * 
	 * @return Item's name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Retrieves the type of the item.
	 * 
	 * @return Item's type.
	 */
	public ITEM_TYPE getType() {
		return this.type;
	}
	
	/**
	 * Retrieves the map the item appears in.
	 * 
	 * @return File path of map appearing in.
	 */
	public String getAppearsIn() {
		return this.appearsIn;
	}
	
	/**
	 * Retrieve actor's current X-Coordinate.
	 * 
	 * @return Current X-Coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Retrieve actor's current Y-Coordinate.
	 * 
	 * @return Current Y-Coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Retrieve actor's current X-Coordinate in the world, scaled to tile size.
	 * 
	 * @return Current X-Coordinate in the world.
	 */
	public float getWorldX() {
		return getX() * GameSettings.TILE_SIZE;
	}
	
	/**
	 * Retrieve actor's current Y-Coordinate in the world, scaled to tile size.
	 * 
	 * @return Current Y-Coordinate in the world.
	 */
	public float getWorldY() {
		return getY() * GameSettings.TILE_SIZE;
	}

	/**
	 * Retrieves whether the item has been found yet.
	 * 
	 * @return True if item found, otherwise false.
	 */
	public boolean getFound()
	{
		return found;
	}
	
	/**
	 * Updates the found status of the item.
	 * 
	 * @param b True if item has been found.
	 */
	public void setFound(boolean b)
	{
		found = b;
	}

}
