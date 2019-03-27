package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prisonescape.game.screens.components.PuzzleTile;

/**
 * A class which test if the puzzle tiles are in the correct position
 * 
 * @author Shibu George
 *
 */
public class TestPuzzleTile {

	/**
	 * 2D array of the puzzle tiles
	 * 
	 */
	private PuzzleTile[][] tiles;
	

	/**
	 * Creating 4 x 4 row and creating PuzzleTile
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.tiles = new PuzzleTile[4][4];
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {

				this.tiles[x][y] = new PuzzleTile(x, y);
				
				
			}
		}

	}

	/**
	 * Testing whether each tile is in the right position.
	 * 
	 */
	@Test
	public void testInCorrectPosition() {

		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				assertTrue(this.tiles[x][y].inCorrectPosition(x, y));

			}
		}
	}

}
