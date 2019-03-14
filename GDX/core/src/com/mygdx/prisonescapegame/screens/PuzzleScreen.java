package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.tween.BitmapAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.Timeline;
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
	private PuzzleTile[][] tiles;
	private PuzzleTile swapTile;
	private boolean isPuzzleFinished;
	private Sprite puzzleBackground;
	private Sprite actualImage;
	private static final int QUIT_BUTTON_WIDTH = 96;
	private static final int QUIT_BUTTON_HEIGHT = 33;
	private static final int QUIT_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 300;
	private static final int RETURN_BUTTON_WIDTH = 174;
	private static final int RETURN_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 300;
	private static final int RETURN_BUTTON_HEIGHT = 33;
	private static final int TRYAGAIN_BUTTON_WIDTH = 230;
	private static final int TRYAGAIN_BUTTON_HEIGHT = 33;
	private static final int TRYAGAIN_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 300;
	private Sprite quitButtonActive;
	private Sprite quitButtonInActive;
	private Sprite returnButtonActive;
	private Sprite returnButtonInActive;
	private boolean checkQuitButtonMouseOver;
	private BitmapFont fontYellow;
	private PuzzleTile currentSelected;
	private TweenManager tween;
	private boolean buttonQuitActive;
	private BitmapFont fontBigYellow;
	private int moveCounter;
	private BitmapFont fontBigBlack;
	private BitmapFont fontPuzzleCompleteYellow;
	private boolean checkReturnButtonMouseOver;
	private boolean buttonReturnActive;
	private Sprite tryAgainButtonActive;
	private Sprite tryAgainButtonInActive;
	private boolean buttonTryAgainActive;
	private BitmapFont fontPuzzleCompleteBlack;
	private boolean checkTryAgainButtonMouseOver;
	
	private static String[] arr = { "puzzle1", "puzzle2", "puzzle3", "puzzle4" };
	private static Random randomTheme;
	private static String puzzleTheme;

	/**
	 * Creates a 4 x 4 puzzle tile with one tile black. Creates PuzzleTile to get
	 * the textures at each position. Random at each time PuzzleScreen is called so
	 * the tiles are jumbled up.
	 * 
	 * @param game
	 */
	public PuzzleScreen(PrisonEscapeGame game) {

		this.game = game;
		randomTheme = new Random();
		tween = new TweenManager();
		buttonQuitActive = true;
		buttonReturnActive = false;
		buttonTryAgainActive = false;
		moveCounter = 0;
		puzzleTheme = arr[randomTheme.nextInt(arr.length)];
		puzzleBackground = new Sprite(new Texture(Gdx.files.internal("data/puzzles/puzzleBackground.png")));
		actualImage = new Sprite(new Texture(Gdx.files.internal("data/puzzles/" + getPuzzleTheme() + "/actual.png")));
		quitButtonActive = new Sprite(new Texture("data/menuSprites/quit_active.png"));
		quitButtonInActive = new Sprite(new Texture("data/menuSprites/quit_inactive.png"));
		returnButtonActive = new Sprite(new Texture("data/menuSprites/return_active.png"));
		returnButtonInActive = new Sprite(new Texture("data/menuSprites/return_inactive.png"));
		tryAgainButtonActive = new Sprite(new Texture("data/menuSprites/tryagain_active.png"));
		tryAgainButtonInActive = new Sprite(new Texture("data/menuSprites/tryagain_inactive.png"));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		fontPuzzleCompleteYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-big.fnt"));
		fontPuzzleCompleteBlack = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-big-black.fnt"));
		fontBigYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-big.fnt"));
		fontBigBlack = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-big-black.fnt"));
		

		this.tiles = new PuzzleTile[4][4];
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {

				this.tiles[x][y] = new PuzzleTile(x, y);

			}
		}

		this.swapTile = this.tiles[3][3];
		this.swapTile.clearTexture();

		Random ran = new Random();
		for (int i = 0; i < 51; i++) {
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
		currentSelected = this.tiles[x][y];
		this.tiles[x][y] = this.swapTile;
		this.swapTile = temp;
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
		Timeline.createSequence().beginSequence()

				.push(Tween.set(fontBigYellow, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(fontBigBlack, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(fontYellow, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(actualImage, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(fontPuzzleCompleteYellow, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(fontPuzzleCompleteBlack, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(currentSelected.getPuzzleImage(), SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(quitButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(quitButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(returnButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(returnButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(tryAgainButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(tryAgainButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.from(puzzleBackground, SpriteAccessor.ALPHA, 0.2f).target(0))
				.push(Tween.to(fontBigYellow, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(fontBigBlack, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(fontYellow, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(actualImage, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(currentSelected.getPuzzleImage(), SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(quitButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(quitButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);
	}

	public void render(float delta) {

		tween.update(delta);

		this.game.getGameController().getSpriteBatch().begin();
		puzzleBackground.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		puzzleBackground.setPosition(Gdx.graphics.getWidth() / 2 - puzzleBackground.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - puzzleBackground.getHeight() / 2);
		puzzleBackground.draw(game.getGameController().getSpriteBatch());

		actualImage.setSize(351, 351);
		actualImage.setPosition(Gdx.graphics.getWidth() / 2 - actualImage.getWidth() / 2 - 433,
				Gdx.graphics.getHeight() / 2 - actualImage.getHeight() / 2 - 145);

		actualImage.draw(game.getGameController().getSpriteBatch());

		fontBigBlack.draw(game.getGameController().getSpriteBatch(), "Total Moves: " + moveCounter,
				Gdx.graphics.getWidth() / 2 - 550, Gdx.graphics.getHeight() / 2 + 300);
		
		fontBigBlack.draw(game.getGameController().getSpriteBatch(), "Goal: 15 moves",
				Gdx.graphics.getWidth() / 2 - 550, Gdx.graphics.getHeight() / 2 + 180);

		fontBigYellow.draw(game.getGameController().getSpriteBatch(), "Solve puzzle to continue...",
				Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() / 2 + 320);

		fontBigBlack.draw(game.getGameController().getSpriteBatch(), "Actual Image", Gdx.graphics.getWidth() / 2 - 550,
				Gdx.graphics.getHeight() / 2 + 75);

		fontYellow.draw(game.getGameController().getSpriteBatch(), "Click on any tile to select and drop",
				Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() / 2 + 200);

		fontYellow.draw(game.getGameController().getSpriteBatch(), "Selected tile", Gdx.graphics.getWidth() / 2 + 475,
				Gdx.graphics.getHeight() / 2 + 80);

		int xQuit = PrisonEscapeGame.WIDTH / 2 - QUIT_BUTTON_WIDTH / 2 + 590;

		quitButton(xQuit);

		this.isPuzzleFinished = true;
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {

				float size = 128.0F;
				float xLoc = (x - 1) * size + 700;
				float yLoc = 4.0F * size - y * size;

				float mouseX = Gdx.input.getX();
				float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

				this.tiles[x][y].getPuzzleImage().setPosition(xLoc, yLoc);
				this.tiles[x][y].getPuzzleImage().setSize(size, size);
				this.tiles[x][y].getPuzzleImage().draw(this.game.getGameController().getSpriteBatch());

				if ((mouseX > xLoc) && (mouseX < xLoc + size) && (mouseY > yLoc) && (mouseY < yLoc + size)) {

					if (!buttonReturnActive) {
						if (Gdx.input.justTouched()) {

							moveCounter += 1;

							swapWithEmpty(x, y);

						}
					}
				}

				if (!this.tiles[x][y].inCorrectPosition(x, y)) {
					this.isPuzzleFinished = false;
				}

			}
		}

		if (this.isPuzzleFinished) {

//			Tween.set(returnButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
//			Tween.set(returnButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
//			Tween.set(quitButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
//			Tween.set(quitButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
			
			for (int x = 1; x < 4; x++) {
				for (int y = 1; y < 4; y++) {
					Tween.to(this.tiles[x][y].getPuzzleImage(), SpriteAccessor.ALPHA, 0.2f).target(0).start(tween);
				}
			}
			Timeline.createSequence().beginSequence().push(Tween.to(fontYellow, BitmapAccessor.ALPHA, 0.2f).target(0))
			.push(Tween.to(fontBigYellow, BitmapAccessor.ALPHA, 0.2f).target(0))
			.push(Tween.to(fontBigBlack, BitmapAccessor.ALPHA, 0.2f).target(0))
			.push(Tween.to(quitButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(0))
			.push(Tween.to(quitButtonActive, SpriteAccessor.ALPHA, 0.2f).target(0))
			.push(Tween.to(fontPuzzleCompleteBlack, BitmapAccessor.ALPHA, 0.2f).target(1))
			.push(Tween.to(fontPuzzleCompleteYellow, BitmapAccessor.ALPHA, 0.2f).target(1))
			.end().start(tween);
			fontPuzzleCompleteBlack.draw(game.getGameController().getSpriteBatch(), "Total Moves: " + moveCounter,
					Gdx.graphics.getWidth() / 2 - 550, Gdx.graphics.getHeight() / 2 + 200);
			if (moveCounter < 16) {
				
				fontPuzzleCompleteYellow
				.draw(game.getGameController().getSpriteBatch(),
						"          Congratulations! \n \nYou have completed the puzzle",
						Gdx.graphics.getWidth() / 2 - 40, Gdx.graphics.getHeight() / 2 + 200);
				buttonQuitActive = false;
				buttonReturnActive = true;
				Tween.to(returnButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
				Tween.to(returnButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
				
				
			}else {
				fontPuzzleCompleteYellow
				.draw(game.getGameController().getSpriteBatch(),
						"                      Oh no! \n \nYou have taken more than 15 moves",
						Gdx.graphics.getWidth() / 2 - 40, Gdx.graphics.getHeight() / 2 + 200);
				buttonReturnActive = false;
				buttonQuitActive = true;
				buttonTryAgainActive = true;
				Tween.to(tryAgainButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
				Tween.to(tryAgainButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
				Tween.to(quitButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
				Tween.to(quitButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
			}
			

			

		}

		int xReturn = PrisonEscapeGame.WIDTH / 2 - RETURN_BUTTON_WIDTH / 2 + 200;
		returnButton(xReturn);
		
		int xTryAgain = PrisonEscapeGame.WIDTH / 2 - TRYAGAIN_BUTTON_WIDTH / 2 + 100;
		tryAgainButton(xTryAgain);

		currentSelected.getPuzzleImage().setSize(162, 162);
		currentSelected.getPuzzleImage().setPosition(Gdx.graphics.getWidth() / 2 + 480,
				Gdx.graphics.getHeight() / 2 - 130);
		currentSelected.getPuzzleImage().draw(game.getGameController().getSpriteBatch());
		this.game.getGameController().getSpriteBatch().end();

	}

	private void tryAgainButton(int xTryAgain) {
		if (Gdx.input.getX() < xTryAgain + TRYAGAIN_BUTTON_WIDTH && Gdx.input.getX() > xTryAgain
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < TRYAGAIN_BUTTON_Y + TRYAGAIN_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > TRYAGAIN_BUTTON_Y) {
			tryAgainButtonActive.setPosition(xTryAgain, TRYAGAIN_BUTTON_Y);
			tryAgainButtonActive.setSize(TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);
			tryAgainButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();

			if (buttonTryAgainActive) {

				if (checkTryAgainButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}
					checkTryAgainButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					this.game.setScreen(new PuzzleScreen(game));

				}

			}
		} else {
			checkTryAgainButtonMouseOver = false;
			tryAgainButtonInActive.setPosition(xTryAgain, TRYAGAIN_BUTTON_Y);
			tryAgainButtonInActive.setSize(TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);
			tryAgainButtonInActive.draw(game.getGameController().getSpriteBatch());

		}
		
	}

	private void returnButton(int x) {
		if (Gdx.input.getX() < x + RETURN_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < RETURN_BUTTON_Y + RETURN_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > RETURN_BUTTON_Y) {
			returnButtonActive.setPosition(x, RETURN_BUTTON_Y);
			returnButtonActive.setSize(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
			returnButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();

			if (buttonReturnActive) {

				if (checkReturnButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}
					checkReturnButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					Stage stage = MapScreen.getStage();
					stage.clear();
					this.game.setScreen(game.getGameController().getMapScreen());

				}

			}
		} else {
			checkReturnButtonMouseOver = false;
			returnButtonInActive.setPosition(x, RETURN_BUTTON_Y);
			returnButtonInActive.setSize(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
			returnButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void quitButton(int x) {
		if (Gdx.input.getX() < x + QUIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < QUIT_BUTTON_Y + QUIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > QUIT_BUTTON_Y) {

			quitButtonActive.setPosition(x, QUIT_BUTTON_Y);
			quitButtonActive.setSize(QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
			quitButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
			if (buttonQuitActive) {
				if (checkQuitButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}
					checkQuitButtonMouseOver = true;

				}

				if (Gdx.input.justTouched()) {
					Stage stage = MapScreen.getStage();
					stage.clear();
					this.game.setScreen(game.getGameController().getMapScreen());

				}
			}
		} else {
			checkQuitButtonMouseOver = false;
			quitButtonInActive.setPosition(x, QUIT_BUTTON_Y);
			quitButtonInActive.setSize(QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
			quitButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	public static String getPuzzleTheme() {

		return puzzleTheme;

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
