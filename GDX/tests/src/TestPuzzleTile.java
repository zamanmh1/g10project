

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import prisonescape.game.screens.components.PuzzleTile;

@RunWith(GdxTestRunner.class)
public class TestPuzzleTile{

	private PuzzleTile puzzleTile1;

	@Before
	public void setUp() throws Exception {
		puzzleTile1 = new PuzzleTile(1,1);
		puzzleTile1.setPuzzleImage("data/puzzles/puzzle1/11.png");
	}

	@Test
	public void testInCorrectPosition() {
		assertTrue(puzzleTile1.inCorrectPosition(1, 1));
	}

}
