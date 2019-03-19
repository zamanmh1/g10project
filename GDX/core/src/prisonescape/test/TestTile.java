package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;

import prisonescape.game.model.Tile;
import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.MapActor;

/**
 * A class which tests an individual Tile object under controlled circumstances.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestTile {

	/**
	 * The tile that the tests will be perforemd on.
	 */
	private Tile tile;
	
	/**
	 * Sets up a tile to be tested.
	 * 
	 * @throws Exception Catches any exception caused by setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Creates a new, empty tile.
		tile = new Tile();
	}
	
	/**
	 * This test checks the initial state of the tile is as expected.
	 */
	@Test
	public void testTile_InitialState() {
		// Assert that the initial state of the tile is correct.
		assertEquals(tile.getActor(), null);
		assertTrue(tile.getWalkable());
		assertFalse(tile.getTeleporter());
		assertFalse(tile.getAlarm());
	}
	
	/**
	 * This test adds an actor to the tile and checks its existence.
	 */
	@Test
	public void testTile_ContainsActor() {
		// Create a new MapActor to occupy the tile.
		MapActor actor = new Item(new Sprite(), "sleepItem", "assets/data/maps/cell.tmx", "SLEEP", 2, 1);
		// Adds the MapActor to the tile.
		tile.setActor(actor);		
		
		// Asserts that the actor in the tile is not null.
		assertNotNull(tile.getActor());
		
		// Remove the actor from the tile.
		tile.setActor(null);
		
		// Assert that the actor in the tile is now null.
		assertNull(tile.getActor());
	}
	
	/**
	 * This test checks making a tile part of the alarm system.
	 */
	@Test
	public void testTile_changeAlarm() {
		// Set the tile to be part of the alarm system.
		tile.setAlarm(true);		
		// Assert that the tile now contains an alarm.
		assertTrue(tile.getAlarm());
		
		// Remove the tile from the alarm system.
		tile.setAlarm(false);
		// Assert that the tile doesn't contain an alarm.
		assertFalse(tile.getAlarm());
	}

	/*
	 * This test checks making the tile a teleporter tile.
	 */
	@Test
	public void testTile_setTeleporter() {
		// Transform the tile into a teleporter tile.
		tile.setTeleporter();
		// Assert that the tile is a teleporter.
		assertTrue(tile.getTeleporter());
	}
}
