package com.mygdx.game.model;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Represents a model of an individual map within the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class TiledModel {

	private int width, height; // The width and height of the map in tiles.
	private Tile[][] tiles; // A 2d array of tiles.
	private TiledMap map; // The map that the model represents.

	/**
	 * Constructs a new TiledModel based upon a given TiledMap.
	 * 
	 * @param tilemap The TiledMap to model.
	 */
	public TiledModel(TiledMap tilemap) {
		this.map = tilemap;
		
		//TiledMapTileLayer levelLayer = getLayer("Tile Layer 1"); // Main layer of map.
		TiledMapTileLayer alarmLayer = getLayer("Tile Layer 2"); // Alarm layer of map.
		//TiledMapTileLayer tableLayer = getLayer("Tile Layer 3"); // Label representing tables within map.
		//TiledMapTileLayer detailLayer = getLayer("Tile Layer 4"); // Layer containing small details.
		
		ArrayList<TiledMapTileLayer> collisionLayers = new ArrayList<TiledMapTileLayer>();
		collisionLayers.add(getLayer("Tile Layer 1"));
		collisionLayers.add(getLayer("Tile Layer 3"));
		collisionLayers.add(getLayer("Tile Layer 4"));
		
		width = collisionLayers.get(0).getWidth(); // Finds the width of the map.
		height = collisionLayers.get(0).getHeight(); // Finds the height of the map.
		
		tiles = new Tile[width][height]; // Constructs the array of tiles based on the maps width and height.

		// Creates model of given TiledMap by iterating over TiledMap.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Tile();

				for (TiledMapTileLayer layer : collisionLayers) {
					if (layer != null) {
						if(layer.getCell(x,y) != null) {
							if(layer.getCell(x,y).getTile().getProperties().containsKey("blocked")) {
								tiles[x][y].setWalkable(false); // Create collision for any cells in TiledMap with property "blocked".
							}			
						}

					}
				}
				if (alarmLayer != null) {
					if (alarmLayer.getCell(x, y) != null) {
						tiles[x][y].setAlarm(true); // If tile is in the alarm layer, set alarm property to true.
					}
				}
			}
		}
	}

	/**
	 * Provides the Tile at a given location.
	 * 
	 * @param x X-coordinate of tile.
	 * @param y Y-coordinate of tile.
	 * 
	 * @return The Tile at the given coordinates.
	 */
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * Sets the tile at the given coordinates.
	 * 
	 * @param tile The Tile to set to.
	 * @param x    The x-coordinate to set the tile at.
	 * @param y    The y-coordinate to set the tile at.
	 */
	public void setTile(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	/**
	 * Sets the given tile to become a teleporter tile.
	 * 
	 * @param x The x-coordinate to set the tile at.
	 * @param y The y-coordinate to set the tile at.
	 */
	public void setTeleporterTile(int x, int y) {
		tiles[x][y].setTeleporter();
	}

	/**
	 * Provides the width of the model.
	 * 
	 * @return The model's width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Provides the height of the model.
	 * 
	 * @return The model's height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Provides a TiledMapTileLayer when given the name of a tile layer.
	 * 
	 * @param name The tile layers name to find.
	 * 
	 * @return The TiledMapTileLayer with the name given.
	 */
	public TiledMapTileLayer getLayer(String name) {
		return (TiledMapTileLayer) map.getLayers().get(name);
	}
}
