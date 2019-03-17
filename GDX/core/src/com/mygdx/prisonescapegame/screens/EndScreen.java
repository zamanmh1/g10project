package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.BitmapAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Represents the End Screen
 * 
 * @author Shibu George
 *
 */
public class EndScreen implements Screen{
	
	
	private static final int RETURN_BUTTON_WIDTH = 643;
	private static final int RETURN_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 50;
	private static final int RETURN_BUTTON_HEIGHT = 46;
	private static final int EXIT_BUTTON_WIDTH = 301;
	private static final int EXIT_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 120;
	private static final int EXIT_BUTTON_HEIGHT = 46;
	private BitmapFont fontYellow;
	private TweenManager tween;
	private PrisonEscapeGame game;
	private Sprite backgroundSprite;
	private static String[] credits_strs={
			"Created by:", 
			"Samuel Ward", 
			"Sean Corcoran",
			"Shibu George",
			"Mohammed Zaman",
			"Adam Collins",
			"Kevinjeet Singh",
			"Maxwell Trimnell",
			"Jai Kumar",
			"Hasan Ahmed"};
	
	
	private float credit_y = 0;
	private Sprite theEndSprite;
	private Sprite returnButtonActive;
	private Sprite returnButtonInActive;
	private boolean checkReturnButtonMouseOver;
	private boolean buttonActive;
	private Sprite exitButtonActive;
	private Sprite exitButtonInActive;
	private boolean checkExitButtonMouseOver;
	private Sprite theEndSprite2;

	/**
	 * Constructs a new EndScreen with sprites and font for credits
	 * 
	 * @param game
	 */
	public EndScreen(PrisonEscapeGame game) {

		this.game = game;
		tween = new TweenManager();
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/background.png")));
		theEndSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/the_end.png")));
		theEndSprite2 = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/the_end.png")));
		returnButtonActive = new Sprite(new Texture("data/menuSprites/return_to_main_menu_active.png"));
		returnButtonInActive = new Sprite(new Texture("data/menuSprites/return_to_main_menu_inactive.png"));
		exitButtonActive = new Sprite(new Texture("data/menuSprites/exitgame_active.png"));
		exitButtonInActive = new Sprite(new Texture("data/menuSprites/exitgame_inactive.png"));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		buttonActive = false;
		

	}

	/**
	 * Setting up the transitions 
	 * 
	 */
	@Override
	public void show() {
		Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(theEndSprite, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(theEndSprite, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(theEndSprite2, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(returnButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(returnButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(fontYellow, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(fontYellow, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		

	}

	/**
	 * Rendering the EndScreen
	 * 
	 */

	public void render(float delta) {
		tween.update(delta);
		this.game.getGameController().getSpriteBatch().begin();
	
		backgroundSprite.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		backgroundSprite.draw(game.getGameController().getSpriteBatch());
		
		credit_y = credit_y + 40*delta;
		theEndSprite.setScale(0.5f);
		theEndSprite.setPosition(PrisonEscapeGame.WIDTH/2 - 160, credit_y + 40);
		theEndSprite.draw(game.getGameController().getSpriteBatch());
		
		theEndSprite2.setPosition(PrisonEscapeGame.WIDTH/2 - 160, PrisonEscapeGame.HEIGHT / 2 + 30);
		theEndSprite2.draw(game.getGameController().getSpriteBatch());
		
		for (int i = 0; i< credits_strs.length; i++) {
			GlyphLayout layout = new GlyphLayout();
			layout.setText(fontYellow,credits_strs[i]);
			float w = layout.width;
			fontYellow.draw(game.getGameController().getSpriteBatch(), layout,
					(PrisonEscapeGame.WIDTH - w)/2 + 200, credit_y - i*40);
		}
		if (credit_y > PrisonEscapeGame.HEIGHT + 380) {
			buttonActive = true;
			Tween.to(theEndSprite2, SpriteAccessor.ALPHA,0.2f).target(1).start(tween);
			Tween.to(returnButtonActive, SpriteAccessor.ALPHA,0.2f).target(1).start(tween);
			Tween.to(returnButtonInActive, SpriteAccessor.ALPHA,0.2f).target(1).start(tween);
			Tween.to(exitButtonActive, SpriteAccessor.ALPHA,0.2f).target(1).start(tween);
			Tween.to(exitButtonInActive, SpriteAccessor.ALPHA,0.2f).target(1).start(tween);
		}
		
		int xReturn = PrisonEscapeGame.WIDTH / 2 - RETURN_BUTTON_WIDTH / 2 + 200;
		returnButton(xReturn);
		
		int xExit = PrisonEscapeGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 200;
		exitButton(xExit);
		
		this.game.getGameController().getSpriteBatch().end();
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			((Game) Gdx.app.getApplicationListener())
			.setScreen(game.getGameController().getMapScreen());
		}
	}

	
	private void exitButton(int x) {
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}

					checkExitButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
					Gdx.app.exit();

				}
			}

		} else {
			checkExitButtonMouseOver = false;
			exitButtonInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonInActive.draw(game.getGameController().getSpriteBatch());

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

			if (buttonActive) {

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
					MapScreen.setBlackScreen();
					game.setScreen(game.getGameController().getMapScreen());
					game.getGameController().restartGame();
					game.getGameController().stopMusic();
					
				}

			}
		} else {
			checkReturnButtonMouseOver = false;
			returnButtonInActive.setPosition(x, RETURN_BUTTON_Y);
			returnButtonInActive.setSize(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
			returnButtonInActive.draw(game.getGameController().getSpriteBatch());

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
