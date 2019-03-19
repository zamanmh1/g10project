package prisonescape.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;

import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.Item.ITEM_TYPE;

/**
 * A class which tests an Item object under controlled circumstances.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestItem {
	
	/**
	 * The item that tests will be performed on.
	 */
	private Item item;
	
	/**
	 * Sets up an item to be tested.
	 * 
	 * @throws Exception Catches any exception caused by setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Item initialised with test values.
		// No actual sprite is required to test the item.
		item = new Item(new Sprite(), "pillow", "data/maps/cell.tmx", "SLEEP", 2, 1);
	}
	
	/**
	 * This test checks that the correct item type is assigned.
	 */
	@Test
	public void testItem_ItemType() {
		// Assert that item is of the correct type.
		assertEquals(item.getType(), ITEM_TYPE.SLEEP);
	}
	
	/** 
	 * This test checks that an items state changes when found.
	 */
	@Test
	public void testItem_ItemFound() {
		// Assert that item yet to be found.
		assertFalse(item.getFound());
		// Find item.
		item.setFound(true);
		// Assert that item has been found.
		assertTrue(item.getFound());
	}
}
