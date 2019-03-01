package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import java.util.Random;

/**
 * 
 * 
 * @author Shibu George
 *
 */
public class PuzzleScreen implements Screen {

	private PrisonEscapeGame game;
	private SlidingTile[][] tiles;
	private SlidingTile empty;
	private boolean isPuzzleFinished = true;
	private BitmapFont font;
	private Sprite puzzleBackground;
	


	/**
	 * Creates a 4 x 4 puzzle tile with one tile black. Creates SlidingTile to get
	 * the textures at each position. Random at each time PuzzleScreen is called so
	 * the tiles are jumbled up.
	 * 
	 * @param game
	 */
	public PuzzleScreen(PrisonEscapeGame game) {

		this.game = game;
		font = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		puzzleBackground = new Sprite(new Texture(Gdx.files.internal("data/puzzles/puzzleBackground.png")));
		
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

	/**
	 * When user clicks on the tile, it will swap from the empty tile.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void swapTiles(int x1, int y1, int x2, int y2) {
		SlidingTile temp = this.tiles[x1][y1];
		this.tiles[x1][y1] = this.tiles[x2][y2];
		this.tiles[x2][y2] = temp;
	}

	@Override
	public void show() {
		
	}

	public void render(float delta) {

		this.game.getGameController().getSpriteBatch().begin();
		puzzleBackground.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		puzzleBackground.setPosition(Gdx.graphics.getWidth()/2 - puzzleBackground.getWidth()/2, 
				Gdx.graphics.getHeight()/2 - puzzleBackground.getHeight()/2);
		puzzleBackground.draw(game.getGameController().getSpriteBatch());
		
//		font.draw(game.getGameController().getSpriteBatch(), "Complete puzzle to continue...",
//				PrisonEscapeGame.WIDTH / 2 - 200, PrisonEscapeGame.HEIGHT / 2 + 300);
		
		
		this.isPuzzleFinished = true;
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {

				float size = 128.0F;
				float xLoc = (x - 1) * size + 700;
				float yLoc = 4.0F * size - y * size;
				int hover = 0;

				float mouseX = Gdx.input.getX();
				float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
				if ((mouseX > xLoc) && (mouseX < xLoc + size) && (mouseY > yLoc) && (mouseY < yLoc + size)) {
					if (Gdx.input.isTouched()) {
						moveTile(x, y);
					}
				} 
					this.game.getGameController().getSpriteBatch().draw(this.tiles[x][y].getPuzzleImage(), xLoc, yLoc,
							size, size);
				
				if (!this.tiles[x][y].inCorrectPosition(x, y)) {
					this.isPuzzleFinished = false;
				}

			}
		}
		if (this.isPuzzleFinished) {
			dispose();
			this.game.setScreen(game.getGameController().getMapScreen());
		}

		this.game.getGameController().getSpriteBatch().end();
	}

	/**
	 * Checks whether tile has been moved
	 * 
	 * @param r
	 * @param c
	 * @return true or false
	 */
	public boolean moveTile(int r, int c) {
		return (checkEmpty(r, c, -1, 0)) || (checkEmpty(r, c, 1, 0)) || (checkEmpty(r, c, 0, -1))
				|| (checkEmpty(r, c, 0, 1));
	}

	/**
	 * Checks to see if its empty
	 * 
	 * @param x
	 * @param y
	 * @param xChange
	 * @param yChange
	 * @return true or false
	 */
	private boolean checkEmpty(int x, int y, int xChange, int yChange) {
		int xNext = x + xChange;
		int yNext = y + yChange;
		if ((isLegalSquare(xNext, yNext)) && (this.tiles[xNext][yNext] == this.empty)) {
			swapTiles(x, y, xNext, yNext);
			return true;
		}
		return false;
	}

	/**
	 * Check whether the square is legal and can be used
	 * 
	 * @param r
	 * @param c
	 * @return true or false
	 */
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
