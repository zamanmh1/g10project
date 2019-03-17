package com.mygdx.prisonescapegame.screens;

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
	private int currentRow;
	private int currentColumn;
	private String blackTileLoc = "data/puzzles/black.png";
	private String puzzleLoc;

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
		this.puzzleLoc = "data/puzzles/" + PuzzleScreen.getPuzzleTheme() + "/" + x + y + ".png";
		this.puzzleImage = new Sprite(new Texture(puzzleLoc));
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
	 * Texture with the black background
	 * 
	 */
	public void clearTexture() {
		this.puzzleImage = new Sprite(new Texture(this.blackTileLoc));
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
