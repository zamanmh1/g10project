package com.mygdx.game.testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.model.Tile;
import com.mygdx.game.model.TiledModel;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class TestTile extends GameTest{

	private Tile tile;
	
	@Before
	public void setUp() {
		tile = new Tile();
	}
	
	@Test
	public void testTile_InitialState() {
		assertEquals(tile.getActor(), null);
		assertTrue(tile.getWalkable());
		assertFalse(tile.getTeleporter());
		assertFalse(tile.getAlarm());
	}
	
	
	@Test
	public void testTile_ContainsActor() {
		Sprite s = new Sprite(new Texture(Gdx.files.internal("data/itemSprites/sleep.png")));
		Item actor = new Item(s, "sleepItem", "assets/data/maps/cell.tmx", "SLEEP", 2, 1);
		tile.setActor(actor);
		
		assertNotNull(tile.getActor());
	}
	
	@Test
	public void testTile_changeAlarm() {
		tile.setAlarm(true);		
		assertTrue(tile.getAlarm());
		
		tile.setAlarm(false);
		assertFalse(tile.getAlarm());
	}

	@Test
	public void testTile_setTeleporter() {
		tile.setTeleporter();
		assertTrue(tile.getTeleporter());
	}
}
