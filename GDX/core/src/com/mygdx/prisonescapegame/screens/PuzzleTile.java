package com.mygdx.prisonescapegame.screens;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * 
 * 
 * @author Shibu George
 *
 */
public class PuzzleTile {
	private Sprite puzzleImage;
	private int finishRow;
	private int finishCol;
	private int currentRow;
	private int currentColumn;
	private String blackTileLoc = "data/puzzles/black.png";
//	private String [] arr = {"data/puzzles/police/", "data/puzzles/logo/"};
//	
//	private Random random = new Random();
//	private String loc = arr[random.nextInt(arr.length)];
	/**
	 * Creates row of x and y and assigns texture to each position, puzzleImage is
	 * the Sprite that is used.
	 * 
	 * @param x position
	 * @param y position
	 */
	public PuzzleTile(int x, int y) {
		this.finishRow = x;
		this.finishCol = y;
		this.puzzleImage = new Sprite(new Texture("data/puzzles/police/" + x + y + ".png"));
	}

	/**
	 * Returns the current row
	 * 
	 * @return currentRow
	 */
	public int getRow() {
		return this.currentRow;
	}

	/**
	 * Returns the current column
	 * 
	 * @return currentColum
	 */
	public int getColumn() {
		return this.currentColumn;
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
	 * Checks if its in the right position
	 * 
	 * @param x
	 * @param y
	 * @return boolean true or false
	 */
	public boolean inCorrectPosition(int x, int y) {
		return (x == this.finishRow) && (y == this.finishCol);
	}

}
