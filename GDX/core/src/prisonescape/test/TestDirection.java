package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Test;

import prisonescape.game.model.actors.DIRECTION;

/**
 * A class which tests the DIRECTION class under controlled circumstances.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestDirection {

	/**
	 * This test ensures that the north direction provides the correct information.
	 */
	@Test
	public void testDIRECTION_North() {
		// Creates a north direction variable.
		DIRECTION dir = DIRECTION.NORTH;
		
		// Assert that doesn't move in the x-direction.
		assertEquals(dir.getMoveX(), 0);
		// Assert that moves 1 in the positive y-direction.
		assertEquals(dir.getMoveY(), 1);
		// Assert that opposite direction is south.
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.SOUTH);
	}
	
	/**
	 * This test ensures that the south direction provides the correct information.
	 */
	@Test
	public void testDIRECTION_South() {
		// Creates a south direction variable.
		DIRECTION dir = DIRECTION.SOUTH;
		
		// Assert that doesn't move in the x-direction.
		assertEquals(dir.getMoveX(), 0);
		// Assert that moves 1 in the negative y-direction.
		assertEquals(dir.getMoveY(), -1);
		// Assert that opposite direction is south.
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.NORTH);
	}
	
	/**
	 * This test ensures that the east direction provides the correct information.
	 */
	@Test
	public void testDIRECTION_East() {
		DIRECTION dir = DIRECTION.EAST;
		
		// Assert that moves 1 in the positive x-direction.
		assertEquals(dir.getMoveX(), 1);
		// Assert that doesn't move in the y-direction.
		assertEquals(dir.getMoveY(), 0);
		// Assert that opposite direction is west.
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.WEST);
	}
	
	/**
	 * This test ensures that the west direction provides the correct information.
	 */
	@Test
	public void testDIRECTION_West() {
		DIRECTION dir = DIRECTION.WEST;
		
		// Assert that moves 1 in the negative y-direction.
		assertEquals(dir.getMoveX(), -1);
		// Assert that doesn't move in the y-direction.
		assertEquals(dir.getMoveY(), 0);
		// Assert that opposite direction is east.
		assertEquals(DIRECTION.getBehind(dir), DIRECTION.EAST);
	}

}
