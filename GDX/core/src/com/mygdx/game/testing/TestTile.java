package com.mygdx.game.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entities.Item;
import com.mygdx.game.model.Tile;

public class TestTile {

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
		Item actor = new Item(new Sprite(), "sleepItem", "assets/data/maps/cell.tmx", "SLEEP", 2, 1);
		tile.setActor(actor);		
		assertNotNull(tile.getActor());
		
		tile.setActor(null);
		assertNull(tile.getActor());
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
