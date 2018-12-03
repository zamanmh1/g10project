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
	private static final int PLAY_BUTTON_Y = 500;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = 300;
	private static final int HELP_BUTTON_WIDTH = 174;
	private static final int HELP_BUTTON_Y = 400;
	private static final int HELP_BUTTON_HEIGHT = 52;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;

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
	private  Sound mouseOverSound;
	private boolean checkPlayButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private boolean checkHelpButtonMouseOver;
	private Sprite volumeButtonFull;
	private Sprite volumeButtonMute;
	private boolean volumeMuted;

	private MainMenuScreen(PrisonEscapeGame game) {
		this.game = game;

		tween = new TweenManager();
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/play_active.png")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/play_inactive.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/background.png")));
		helpButtonActive = new Sprite(new Texture("data/help_active.png"));
		helpButtonInActive = new Sprite(new Texture("data/help_inactive.png"));
		volumeButtonFull = new Sprite(new Texture("data/Volume-full.png"));
		volumeButtonMute = new Sprite(new Texture("data/Volume-off.png"));
		mouseOverSound = Gdx.audio.newSound(Gdx.files.internal("data/MouseOver.ogg"));
		checkPlayButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		checkHelpButtonMouseOver = false;
		volumeMuted = false;

	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Timeline.createSequence().beginSequence()
		
		.push(Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0))
		.push(Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0))
		.push(Tween.set(helpButtonInActive, SpriteAccessor.ALPHA).target(0))
		.push(Tween.set(helpButtonActive, SpriteAccessor.ALPHA).target(0))
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
		.push(Tween.to(volumeButtonMute, SpriteAccessor.ALPHA, 0).target(1))
		.end().start(tween);
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tween.update(delta);

		game.getGameController().getSpriteBatch().begin();

		backgroundSprite.draw(game.getGameController().getSpriteBatch());
		
		int x = PrisonEscapeGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 + 100;
		
		playButton(x);
		
		x = PrisonEscapeGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 100;
		
		exitButton(x);
		
		x = PrisonEscapeGame.WIDTH / 2 - HELP_BUTTON_WIDTH / 2 + 100;
		
		helpButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 300;
		
		volumeButton(x);

		game.getGameController().getSpriteBatch().end();

	}
	
	

	private void playButton(int x) {
		
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			playButtonActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkPlayButtonMouseOver == false) {
				mouseOverSound.play(1f);
				checkPlayButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {

				game.setScreen(new MainGameScreen(game));

			}
		} else {
			checkPlayButtonMouseOver = false;
			playButtonInActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonInActive.draw(game.getGameController().getSpriteBatch());

		}
	}
	
	private void exitButton(int x) {
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			if (checkExitButtonMouseOver == false) {
				mouseOverSound.play(1f);
				checkExitButtonMouseOver = true;

			}
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();

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
			if (checkHelpButtonMouseOver == false) {
				mouseOverSound.play(1f);
				checkHelpButtonMouseOver = true;

			}
			if (Gdx.input.isTouched()) {
				game.setScreen(new HelpScreen(game));
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
