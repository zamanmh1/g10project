package com.mygdx.prisonescapegame.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * 
 * 
 * @author Shibu George
 *
 */
public class PauseMenu {

	
	protected Sprite optionBackground;
	protected Sprite remumeButtonMenuInActive;
	protected Sprite exitButtonMenuInActive;
	protected boolean menuPressed;
	private static final int RESUME_BUTTON_WIDTH = 305;
	private static final int RESUME_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 50;
	private static final int RESUME_BUTTON_HEIGHT = 53;
	protected Sprite resumeButtonMenuActive;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 150;
	private static final int HELP_BUTTON_WIDTH = 174;
	private static final int HELP_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 50;
	private static final int HELP_BUTTON_HEIGHT = 52;
	protected Sprite exitButtonMenuActive;
	private boolean checkResumeButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;
	protected Sprite volumeButtonMuted;
	protected Sprite volumeButtonFull;
	protected Sprite helpButtonMenuActive;
	protected Sprite helpButtonMenuInActive;
	private boolean checkHelpButtonMouseOver;
	protected Sprite backButtonActive;
	protected Sprite backButtonInActive;
	private static final int BACK_BUTTON_WIDTH = 40;
	private static final int BACK_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 300;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private boolean checkBackButtonMouseOver;
	protected boolean helpPressed;
	private boolean buttonActive;
	private Sprite wasdKeyboard;
	private Sprite eKeyboard;
	protected BitmapFont fontSmall;
	private String movementText;
	private static final int WASD_WIDTH = 108;
	private static final int WASD_HEIGHT = 75;
	private static final int WASD_Y = PrisonEscapeGame.HEIGHT / 2 - 20;
	private static final int E_Y = PrisonEscapeGame.HEIGHT / 2 - 210;
	private static final int E_WIDTH = 39;
	private static final int E_HEIGHT = 39;
	private String objectPickingText;
	protected Sprite logo;

	
	public PauseMenu() {
		
		optionBackground = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/OptionMenuBackGround.jpg")));
		remumeButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/resume_unactive.png")));
		resumeButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/resume_active.png")));
		exitButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_active.png")));
		exitButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_inactive.png")));
		helpButtonMenuActive = new Sprite(new Texture("data/menuSprites/help_active.png"));
		helpButtonMenuInActive = new Sprite(new Texture("data/menuSprites/help_inactive.png"));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back.png")));
		wasdKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/wasd.png")));
		eKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/e.png")));
		logo = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/logo.png")));
		objectPickingText = "Press E for picking up objects \n and going through doors";
		movementText = "Press W,S,A,D for movement";
		helpPressed = false;
		fontSmall = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		checkResumeButtonMouseOver = false;
		checkHelpButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		checkBackButtonMouseOver = false;
		buttonActive = true;
		menuPressed = false;
	}
	
	protected void resumeButtonMenu(PrisonEscapeGame game) {
		int x = (int) (PrisonEscapeGame.WIDTH / 2 - remumeButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + RESUME_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > RESUME_BUTTON_Y) {

			resumeButtonMenuActive.setPosition(x, RESUME_BUTTON_Y);
			resumeButtonMenuActive.setSize(RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
			resumeButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkResumeButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkResumeButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {

					menuPressed = false;

				}
			}
		} else {
			checkResumeButtonMouseOver = false;
			remumeButtonMenuInActive.setPosition(x, RESUME_BUTTON_Y);
			remumeButtonMenuInActive.setSize(RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
			remumeButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	protected void helpButtonMenu(TweenManager tween, PrisonEscapeGame game) {
		int x = (int) (PrisonEscapeGame.WIDTH / 2 - helpButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + HELP_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y) {

			helpButtonMenuActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonMenuActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkHelpButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkHelpButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {

					Tween.set(resumeButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(remumeButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(helpButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(helpButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					helpScreenUI(game, tween);
					helpPressed = true;
					buttonActive = false;

				}
			}
		} else {
			checkHelpButtonMouseOver = false;
			helpButtonMenuInActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonMenuInActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}
	}

	protected void exitButtonMenu(TweenManager tween, final PrisonEscapeGame game) {
		int x = (int) (PrisonEscapeGame.WIDTH / 2 - exitButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonMenuActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkExitButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
					Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.to(exitButtonMenuActive, SpriteAccessor.ALPHA, 0.5f).target(1).repeatYoyo(0, 0f)
							.setCallback(new TweenCallback() {

								@Override
								public void onEvent(int type, BaseTween<?> source) {

									((Game) Gdx.app.getApplicationListener())
											.setScreen(MainMenuScreen.getInstance(game));
								}
							}).start(tween);
				}
			}

		} else {
			checkExitButtonMouseOver = false;
			exitButtonMenuInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	

	protected void helpScreenUI(PrisonEscapeGame game, TweenManager tween) {
		int x = PrisonEscapeGame.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 500;

		backButton(x, game, tween);
		fontSmall.draw(game.getGameController().getSpriteBatch(), movementText, PrisonEscapeGame.WIDTH / 2 - 200,
				PrisonEscapeGame.HEIGHT / 2 + 100);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 - 10;

		wasdKeyboard.setPosition(x, WASD_Y);
		wasdKeyboard.setSize(WASD_WIDTH, WASD_HEIGHT);
		wasdKeyboard.draw(game.getGameController().getSpriteBatch());

		fontSmall.draw(game.getGameController().getSpriteBatch(), objectPickingText, PrisonEscapeGame.WIDTH / 2 - 200,
				PrisonEscapeGame.HEIGHT / 2 - 100);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 20;

		eKeyboard.setPosition(x, E_Y);
		eKeyboard.setSize(E_WIDTH, E_HEIGHT);
		eKeyboard.draw(game.getGameController().getSpriteBatch());

	}

	private void backButton(int x, PrisonEscapeGame game, TweenManager tween) {

		if (Gdx.input.getX() < x + BACK_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y) {

			backButtonActive.setPosition(x, BACK_BUTTON_Y);
			backButtonActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkBackButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkBackButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Tween.set(resumeButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(remumeButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(helpButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(helpButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				helpPressed = false;
				buttonActive = true;

			}
		} else {
			checkBackButtonMouseOver = false;
			backButtonInActive.setPosition(x, BACK_BUTTON_Y);
			backButtonInActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}
	protected void volumeButton(PrisonEscapeGame game) {
		int x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 650;
		Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
		if (Gdx.input.getX() < x + VOLUME_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > VOLUME_BUTTON_Y && Gdx.input.justTouched()) {
			if (muted == false) {

				MainMenuScreen.getInstance(game).setVolumeMute(true);

			} else if (muted == true) {
				MainMenuScreen.getInstance(game).setVolumeMute(false);
			}
		}

		Music music = game.getGameController().getMusic();
		Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
		
		
		if (muted) {
			volumeButtonMuted = MainMenuScreen.getInstance(game).volumeButtonMuted();
			volumeButtonMuted.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonMuted.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonMuted.draw(game.getGameController().getSpriteBatch());
			music.pause();
			getMouseOverSound.stop();
		} else {
			volumeButtonFull = MainMenuScreen.getInstance(game).volumeButtonFull();
			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(game.getGameController().getSpriteBatch());
			music.play();
		}

	}

}
