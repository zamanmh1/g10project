package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward, Shibu George
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class MainMenuScreen implements Screen {

	private static final int PLAY_BUTTON_WIDTH = 174;
	private static final int PLAY_BUTTON_HEIGHT = 52;
	private static final int PLAY_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 200;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 200;
	private static final int HELP_BUTTON_WIDTH = 174;
	private static final int HELP_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2;
	private static final int HELP_BUTTON_HEIGHT = 52;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;
	private static final int BACK_BUTTON_WIDTH = 40;
	private static final int BACK_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 300;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private static final int NEWGAME_BUTTON_WIDTH = 427;
	private static final int NEWGAME_BUTTON_HEIGHT = 54;
	private static final int NEWGAME_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 100;
	private static final int LOADGAME_BUTTON_WIDTH = 427;
	private static final int LOADGAME_BUTTON_HEIGHT = 54;
	private static final int LOADGAME_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 100;

	private PrisonEscapeGame game;
	private TweenManager tween;
	private static MainMenuScreen mainInstance;
	private Sprite playButtonActive;
	private Sprite playButtonInActive;
	private Sprite exitButtonActive;
	private Sprite exitButtonInActive;
	private Sprite backgroundSprite;

	private Sprite helpButtonActive;
	private Sprite helpButtonInActive;

	private Sound mouseOverSound;
	private boolean checkPlayButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private boolean checkHelpButtonMouseOver;
	private Sprite volumeButtonFull;
	private Sprite volumeButtonMute;
	private boolean volumeMuted;
	private Sprite backButtonActive;
	private Sprite backButtonInActive;
	private boolean checkBackButtonMouseOver;
	private boolean buttonActive;
	private boolean playPressed;
	private Sprite loadButtonActive;
	private Sprite newButtonActive;
	private Sprite loadButtonInActive;
	private Sprite newButtonInActive;
	private boolean checkLoadButtonMouseOver;
	private boolean checkNewButtonMouseOver;
	private long time;

	private MainMenuScreen(PrisonEscapeGame game) {
		this.game = game;

		tween = new TweenManager();
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_active.png")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_inactive.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_inactive.png")));
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/background.png")));
		helpButtonActive = new Sprite(new Texture("data/menuSprites/help_active.png"));
		helpButtonInActive = new Sprite(new Texture("data/menuSprites/help_inactive.png"));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back.png")));
		loadButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/load_active.png")));
		loadButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/load_inactive.png")));
		newButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/new_active.png")));
		newButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/new_inactive.png")));
		volumeButtonFull = new Sprite(new Texture("data/menuSprites/Volume-full.png"));
		volumeButtonMute = new Sprite(new Texture("data/menuSprites/Volume-off.png"));
		mouseOverSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/MouseOver.ogg"));
		checkPlayButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		checkHelpButtonMouseOver = false;
		checkBackButtonMouseOver = false;
		checkLoadButtonMouseOver = false;
		checkNewButtonMouseOver = false;
		buttonActive = true;
		playPressed = false;
		volumeMuted = false;
		

	}

	@Override
	public void show() {
		time = System.currentTimeMillis();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Timeline.createSequence().beginSequence()

				.push(Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(helpButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(helpButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(loadButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(loadButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(newButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(newButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonFull, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonMute, SpriteAccessor.ALPHA).target(0))
				.push(Tween.from(backgroundSprite, SpriteAccessor.ALPHA, 0).target(0))
				.push(Tween.to(playButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(playButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(helpButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(helpButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(exitButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(exitButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(volumeButtonFull, SpriteAccessor.ALPHA, 0).target(1))
				.push(Tween.to(volumeButtonMute, SpriteAccessor.ALPHA, 0).target(1)).end().start(tween);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tween.update(delta);

		game.getGameController().getSpriteBatch().begin();

		backgroundSprite.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		backgroundSprite.draw(game.getGameController().getSpriteBatch());

		int x = PrisonEscapeGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 + 200;

		playButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 200;

		exitButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - HELP_BUTTON_WIDTH / 2 + 200;

		helpButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 650;

		volumeButton(x);

		if (playPressed) {
			playOptions();

		}
		game.getGameController().getSpriteBatch().end();

	}

	private void playButton(int x) {

		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			playButtonActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkPlayButtonMouseOver == false) {
					mouseOverSound.play(1f);
					checkPlayButtonMouseOver = true;

				}

				if (System.currentTimeMillis() > time + 1000) {
					if (Gdx.input.isTouched()) {
						Timeline.createSequence().beginSequence()
								.push(Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(helpButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(helpButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.to(newButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(newButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(loadButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(loadButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1)).end()
								.start(tween);
						playPressed = true;
						buttonActive = false;

					}
				}
			}
		} else {
			checkPlayButtonMouseOver = false;
			playButtonInActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void playOptions() {
		int backX = PrisonEscapeGame.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 100;

		backButton(backX);

		int newGameX = PrisonEscapeGame.WIDTH / 2 - NEWGAME_BUTTON_WIDTH / 2 + 200;

		newGameButton(newGameX);

		int loadGameX = PrisonEscapeGame.WIDTH / 2 - LOADGAME_BUTTON_WIDTH / 2 + 200;

		loadGameButton(loadGameX);

	}

	private void newGameButton(int newGameX) {
		if (Gdx.input.getX() < newGameX + NEWGAME_BUTTON_WIDTH && Gdx.input.getX() > newGameX
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < NEWGAME_BUTTON_Y + NEWGAME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > NEWGAME_BUTTON_Y) {

			newButtonActive.setPosition(newGameX, NEWGAME_BUTTON_Y);
			newButtonActive.setSize(NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
			newButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkNewButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkNewButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				playPressed = false;
				buttonActive = true;
				game.setScreen(new MainGameScreen(game));
				game.getGameController().stopMusic();
				game.getGameController().setMusic("data/sounds/MainGameMusic.mp3");
				game.getGameController().playMusic();
				if (volumeMuted == true) {
					Music music = game.getGameController().getMusic();
					music.pause();
				}

			}
		} else {
			checkNewButtonMouseOver = false;
			newButtonInActive.setPosition(newGameX, NEWGAME_BUTTON_Y);
			newButtonInActive.setSize(NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
			newButtonInActive.draw(game.getGameController().getSpriteBatch());
		}
	}

	private void loadGameButton(int loadGameX) {
		if (Gdx.input.getX() < loadGameX + LOADGAME_BUTTON_WIDTH && Gdx.input.getX() > loadGameX
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < LOADGAME_BUTTON_Y + LOADGAME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > LOADGAME_BUTTON_Y) {

			loadButtonActive.setPosition(loadGameX, LOADGAME_BUTTON_Y);
			loadButtonActive.setSize(LOADGAME_BUTTON_WIDTH, LOADGAME_BUTTON_HEIGHT);
			loadButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkLoadButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkLoadButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {

			}
		} else {
			checkLoadButtonMouseOver = false;
			loadButtonInActive.setPosition(loadGameX, LOADGAME_BUTTON_Y);
			loadButtonInActive.setSize(LOADGAME_BUTTON_WIDTH, LOADGAME_BUTTON_HEIGHT);
			loadButtonInActive.draw(game.getGameController().getSpriteBatch());

		}
	}

	private void backButton(int backX) {
		if (Gdx.input.getX() < backX + BACK_BUTTON_WIDTH && Gdx.input.getX() > backX
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y) {

			backButtonActive.setPosition(backX, BACK_BUTTON_Y);
			backButtonActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkBackButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkBackButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				show();
				playPressed = false;
				buttonActive = true;

			}
		} else {
			checkBackButtonMouseOver = false;
			backButtonInActive.setPosition(backX, BACK_BUTTON_Y);
			backButtonInActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void exitButton(int x) {
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					mouseOverSound.play(1f);
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

	private void helpButton(int x) {
		if (Gdx.input.getX() < x + HELP_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y) {

			helpButtonActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkHelpButtonMouseOver == false) {
					mouseOverSound.play(1f);
					checkHelpButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
					game.setScreen(new HelpScreen(game));
				}
			}
		} else {
			checkHelpButtonMouseOver = false;
			helpButtonInActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonInActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void volumeButton(int x) {
		if (Gdx.input.getX() < x + VOLUME_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > VOLUME_BUTTON_Y && Gdx.input.justTouched()) {
			if (volumeMuted == false) {
				volumeMuted = true;

			} else if (volumeMuted == true) {
				volumeMuted = false;
			}
		}

		Music music = game.getGameController().getMusic();
		if (volumeMuted == false) {

			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(game.getGameController().getSpriteBatch());
			music.play();

		} else if (volumeMuted == true) {

			volumeButtonMute.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonMute.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonMute.draw(game.getGameController().getSpriteBatch());
			music.pause();
			mouseOverSound.stop();

		}
	}

	public Sound mouseOverSound() {

		return mouseOverSound;
	}

	public boolean checkSoundMuted() {
		return volumeMuted;

	}

	public void setVolumeMute(boolean mute) {
		volumeMuted = mute;

	}

	public Sprite volumeButtonMuted() {
		return volumeButtonMute;

	}

	public Sprite volumeButtonFull() {
		return volumeButtonFull;

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

		playButtonActive.getTexture().dispose();
		playButtonInActive.getTexture().dispose();
		exitButtonActive.getTexture().dispose();
		exitButtonInActive.getTexture().dispose();
		helpButtonActive.getTexture().dispose();
		helpButtonInActive.getTexture().dispose();
		volumeButtonFull.getTexture().dispose();
		volumeButtonMute.getTexture().dispose();
		mouseOverSound.dispose();
	}

	public static MainMenuScreen getInstance(PrisonEscapeGame game) {
		if (mainInstance == null) {
			mainInstance = new MainMenuScreen(game);
		}
		return mainInstance;
	}

}
