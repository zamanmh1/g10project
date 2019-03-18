package prisonescape.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Sprite;

import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.Item.ITEM_TYPE;

public class TestItem {
	private Item item;
	
	@Before
	public void setUp() {
		item = new Item(new Sprite(), "pillow", "data/maps/cell.tmx", "SLEEP", 2, 1);
	}
	
	@Test
	public void testItem_ItemType() {
		assertEquals(item.getType(), ITEM_TYPE.SLEEP);
	}
	
	@Test
	public void testItem_ItemFound() {
		assertFalse(item.getFound());
		item.setFound(true);
		assertTrue(item.getFound());
	}
}
