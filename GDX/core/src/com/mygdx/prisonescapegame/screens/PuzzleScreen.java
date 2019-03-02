package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	private PuzzleTile[][] tiles;
	private PuzzleTile empty;
	private boolean isPuzzleFinished;
	private Sprite puzzleBackground;
	private Sprite actualImage;
	private static final int QUIT_BUTTON_WIDTH = 174;
	private static final int QUIT_BUTTON_HEIGHT = 62;
	private static final int QUIT_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 300;
	private Sprite quitButtonActive;
	private Sprite quitButtonInActive;
	private boolean checkQuitButtonMouseOver;
	private BitmapFont font;

	/**
	 * Creates a 4 x 4 puzzle tile with one tile black. Creates PuzzleTile to get
	 * the textures at each position. Random at each time PuzzleScreen is called so
	 * the tiles are jumbled up.
	 * 
	 * @param game
	 */
	public PuzzleScreen(PrisonEscapeGame game) {

		this.game = game;
		puzzleBackground = new Sprite(new Texture(Gdx.files.internal("data/puzzles/puzzleBackground.png")));
		actualImage = new Sprite(
				new Texture(Gdx.files.internal("data/puzzles/" + PuzzleTile.getPuzzleTheme() + "/actual.png")));
		quitButtonActive = new Sprite(new Texture("data/menuSprites/quit_active.png"));
		quitButtonInActive = new Sprite(new Texture("data/menuSprites/quit_inactive.png"));
		font = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		this.tiles = new PuzzleTile[4][4];
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				this.tiles[x][y] = new PuzzleTile(x, y);
			}
		}

		this.empty = this.tiles[3][3];
		this.empty.clearTexture();

		Random ran = new Random();
		for (int i = 0; i < 41; i++) {
			swapWithEmpty(1 + ran.nextInt(3), 1 + ran.nextInt(3));
		}

	}

	/**
	 * Swaps the tile with the empty black tile
	 * 
	 * @param x
	 * @param y
	 */
	private void swapWithEmpty(int x, int y) {
		PuzzleTile temp = this.tiles[x][y];
		this.tiles[x][y] = this.empty;
		this.empty = temp;

	}

	@Override
	public void show() {

	}

	public void render(float delta) {

		this.game.getGameController().getSpriteBatch().begin();
		puzzleBackground.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		puzzleBackground.setPosition(Gdx.graphics.getWidth() / 2 - puzzleBackground.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - puzzleBackground.getHeight() / 2);
		puzzleBackground.draw(game.getGameController().getSpriteBatch());

		actualImage.setPosition(Gdx.graphics.getWidth() / 2 - actualImage.getWidth() / 2 - 433,
				Gdx.graphics.getHeight() / 2 - actualImage.getHeight() / 2 - 130);
		actualImage.draw(game.getGameController().getSpriteBatch());
		font.draw(game.getGameController().getSpriteBatch(), "Click on any tile to be swapped by the empty tile", Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() / 2 + 200);
		int xQuit = PrisonEscapeGame.WIDTH / 2 - QUIT_BUTTON_WIDTH / 2 + 600;

		quitButton(xQuit);

		this.isPuzzleFinished = true;
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {

				float size = 128.0F;
				float xLoc = (x - 1) * size + 700;
				float yLoc = 4.0F * size - y * size;

				float mouseX = Gdx.input.getX();
				float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

				this.game.getGameController().getSpriteBatch().draw(this.tiles[x][y].getPuzzleImage(), xLoc, yLoc, size,
						size);

				if ((mouseX > xLoc) && (mouseX < xLoc + size) && (mouseY > yLoc) && (mouseY < yLoc + size)) {
					if (Gdx.input.justTouched()) {

						swapWithEmpty(x, y);

					}
				}

				if (!this.tiles[x][y].inCorrectPosition(x, y)) {
					this.isPuzzleFinished = false;
				}

			}
		}
		if (this.isPuzzleFinished) {
			Stage stage = MapScreen.getStage();
			stage.clear();
			dispose();
			this.game.setScreen(game.getGameController().getMapScreen());

		}

		this.game.getGameController().getSpriteBatch().end();
		Stage stage = MapScreen.getStage();
		stage.act();
		stage.draw();
	}

	private void quitButton(int x) {
		if (Gdx.input.getX() < x + QUIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < QUIT_BUTTON_Y + QUIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > QUIT_BUTTON_Y) {

			quitButtonActive.setPosition(x, QUIT_BUTTON_Y);
			quitButtonActive.setSize(QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
			quitButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkQuitButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);
				checkQuitButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Stage stage = MapScreen.getStage();
				stage.clear();
				this.game.setScreen(game.getGameController().getMapScreen());

			}

		} else {
			checkQuitButtonMouseOver = false;
			quitButtonInActive.setPosition(x, QUIT_BUTTON_Y);
			quitButtonInActive.setSize(QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
			quitButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

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
