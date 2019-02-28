package com.mygdx.prisonescapegame.screens;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * 
 * @author Shibu George
 *
 */
public class SlidingTile {
	Texture puzzleImage;
	int finishRow;
	int finishCol;
	int currentRow;
	int currentColumn;
	String blackTileLoc = "data/puzzles/black.png";
	String tileLoc = "data/puzzles/prisoner/";
	

	/**
	 * Creates row of x and y and assigns texture to each position, both puzzleImage
	 * and puzzleImageHover when the mouse is over the image. 
	 * 
	 * @param x position
	 * @param y position
	 */
	public SlidingTile(int x, int y) {
		this.finishRow = x;
		this.finishCol = y;
		
		this.puzzleImage = new Texture(tileLoc + x + y + ".jpg");
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
	public Texture getPuzzleImage() {
		return this.puzzleImage;
	}

	/**
	 * Texture with the black background
	 * 
	 */
	public void clearTexture() {
		this.puzzleImage = new Texture(this.blackTileLoc);
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
