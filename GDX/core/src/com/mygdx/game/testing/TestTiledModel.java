package com.mygdx.game.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.model.TiledModel;
import com.mygdx.prisonescapegame.GameController;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.prisonescapegame.screens.MapScreen;

public class TestTiledModel {

	private TiledModel model;
	private TiledMap tilemap;

	@Before
	public void setUp() {	
		//tilemap = new TmxMapLoader().load("data/maps/cell.tmx");
		//model = new TiledModel(tilemap);
	}
	
	@Test
	public void testModel_NotNull() {
		//assertNotNull(model);
	}
}
