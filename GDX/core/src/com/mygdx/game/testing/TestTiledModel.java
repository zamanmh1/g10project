package com.mygdx.game.testing;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.model.TiledModel;

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
