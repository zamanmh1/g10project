package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import java.util.Random;

/**
 * 
 * 
 * @author Shibu George
 *
 */
public class PuzzleScreen implements Screen {
	
	private PrisonEscapeGame game;
	String imageLoc = "data/puzzles/";
	private SlidingTile[][] tiles;
	private SlidingTile empty;
	boolean puzzleFinished = true;

	public PuzzleScreen(PrisonEscapeGame game) {
		

		this.game = game;

		this.tiles = new SlidingTile[4][4];
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				this.tiles[x][y] = new SlidingTile(x, y);
			}
		}
		this.empty = this.tiles[3][3];
		this.empty.clearTexture();

		Random ran = new Random();
		for (int i = 0; i < 40; i++) {
			moveTile(1 + ran.nextInt(3), 1 + ran.nextInt(3));
		}
	}

	private void swapTiles(int x1, int y1, int x2, int y2) {
		SlidingTile temp = this.tiles[x1][y1];
		this.tiles[x1][y1] = this.tiles[x2][y2];
		this.tiles[x2][y2] = temp;
	}

	public void show() {
	}

	public void render(float delta) {
		this.game.getGameController().getSpriteBatch().begin();

		this.puzzleFinished = true;
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				float size = 128.0F;
				float xLoc = (x - 1) * size;
				float yLoc = 4.0F * size - y * size;
				int hover = 0;

				float mouseX = Gdx.input.getX();
				float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
				if ((mouseX > xLoc) && (mouseX < xLoc + size) && (mouseY > yLoc) && (mouseY < yLoc + size)) {
					this.game.getGameController().getSpriteBatch().draw(this.tiles[x][y].getHoverTexture(), xLoc, yLoc, size, size);
					if (Gdx.input.isTouched()) {
						moveTile(x, y);
					}
				} else {
					this.game.getGameController().getSpriteBatch().draw(this.tiles[x][y].getTexture(), xLoc, yLoc, size, size);
				}
				if (!this.tiles[x][y].inCorrectPosition(x, y)) {
					this.puzzleFinished = false;
				}
			}
		}
		
		if (this.puzzleFinished) {
			dispose();
			this.game.setScreen(game.getGameController().getMapScreen());
		}
		this.game.getGameController().getSpriteBatch().end();
	}

	public boolean moveTile(int r, int c) {
		return (checkEmpty(r, c, -1, 0)) || (checkEmpty(r, c, 1, 0)) || (checkEmpty(r, c, 0, -1))
				|| (checkEmpty(r, c, 0, 1));
	}

	private boolean checkEmpty(int x, int y, int xChange, int yChange) {
		int xNext = x + xChange;
		int yNext = y + yChange;
		if ((isLegalSquare(xNext, yNext)) && (this.tiles[xNext][yNext] == this.empty)) {
			swapTiles(x, y, xNext, yNext);
			return true;
		}
		return false;
	}

	public boolean isLegalSquare(int r, int c) {
		return (r >= 0) && (r < 4) && (c >= 0) && (c < 4);
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void hide() {
	}

	public void dispose() {
	}
}
