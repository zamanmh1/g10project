package com.mygdx.game.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.model.TiledModel;

public class TestTiledModel extends GameTest{

	private TiledModel model;
	private TiledMap tilemap;
	
	@Before
	public void setUp() {
		String map = "data/maps/cell.tmx";
		//String path = Gdx.files.internal(map).path();
		tilemap = new TmxMapLoader().load(map);
		model = new TiledModel(tilemap);
	}
	
	@Test
	public void testModel_NotNull() {
		
	}

}
