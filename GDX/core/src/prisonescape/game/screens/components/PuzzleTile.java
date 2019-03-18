package prisonescape.game.screens.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents the individual puzzle tile in the PuzzleScreen
 * 
 * @author Shibu George
 *
 */
public class PuzzleTile {
	private Sprite puzzleImage;
	private int finishRow;
	private int finishColumn;
	private String blackTileLoc = "data/puzzles/black.png";

	/**
	 * Creates row of x and y and assigns texture to each position, puzzleImage is
	 * the Sprite that is used.
	 * 
	 * @param x position
	 * @param y position
	 */

	public PuzzleTile(int x, int y) {
		this.finishRow = x;
		this.finishColumn = y;
	}

	/**
	 * Returns the puzzle image
	 * 
	 * @return puzzleImage
	 */
	public Sprite getPuzzleImage() {
		return this.puzzleImage;
	}
	
	/**
	 * Returns the new puzzle image texture set to each position 
	 * 
	 * @param Location of the texture
	 * @return puzzleImage
	 */
	public Sprite setPuzzleImage(String puzzleLoc) {
		return puzzleImage = new Sprite(new Texture(Gdx.files.internal(puzzleLoc)));
		
	}

	/**
	 * Texture with the black background
	 * 
	 */
	public void clearTexture() {
		this.puzzleImage = new Sprite(new Texture(Gdx.files.internal(this.blackTileLoc)));
	}

	/**
	 * Checks if each puzzle tile are in the right position
	 * 
	 * @param x coordinate 
	 * @param y coordinate
	 * @return boolean true or false
	 */
	public boolean inCorrectPosition(int x, int y) {
		return ((x == finishRow) && (y == finishColumn));

	}

}
