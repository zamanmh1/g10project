package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Test;

import prisonescape.game.model.actors.DIRECTION;

public class TestDirection {

	@Test
	public void testDIRECTION_North() {
		DIRECTION dir = DIRECTION.NORTH;
		
		assertEquals(dir.getMoveX(), 0);
		assertEquals(dir.getMoveY(), 1);
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.SOUTH);
	}
	
	@Test
	public void testDIRECTION_South() {
		DIRECTION dir = DIRECTION.SOUTH;
		
		assertEquals(dir.getMoveX(), 0);
		assertEquals(dir.getMoveY(), -1);
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.NORTH);
	}
	
	@Test
	public void testDIRECTION_East() {
		DIRECTION dir = DIRECTION.EAST;
		
		assertEquals(dir.getMoveX(), 1);
		assertEquals(dir.getMoveY(), 0);
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.WEST);
	}
	
	@Test
	public void testDIRECTION_West() {
		DIRECTION dir = DIRECTION.WEST;
		
		assertEquals(dir.getMoveX(), -1);
		assertEquals(dir.getMoveY(), 0);
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.EAST);
	}

}
