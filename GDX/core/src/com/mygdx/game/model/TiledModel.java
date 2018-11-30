package com.mygdx.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class TiledModel {
	
	private int width, height;
	private Tile[][] tiles;
	private TiledMap map;
	
	public TiledModel(TiledMap tilemap) {
		this.map = tilemap;
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Tile Layer 1"); // Layer name/id needs to be the same for each TiledMap file.

		width = layer.getWidth();
		height = layer.getHeight();
		
		tiles = new Tile[width][height];		

		// Creates model of given TiledMap.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Tile();
				if(layer.getCell(x,y).getTile().getProperties().containsKey("blocked")) {
					tiles[x][y].setWalkable(false); // Create collision for any cells in TiledMap with property "blocked".
				}
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}		
}
