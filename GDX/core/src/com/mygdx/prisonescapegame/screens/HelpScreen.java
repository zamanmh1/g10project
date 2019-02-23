package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.BitmapAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * CLASS DESCRIPTION
 * 
 * @author Shibu George
 * 
 * @version 0.1
 * @since 0.1
 * 
 */
public class HelpScreen implements Screen {

	private PrisonEscapeGame game;
	private Sprite backgroundSprite;
	private TweenManager tween;
	private static final int BACK_BUTTON_WIDTH = 40;
	private static final int BACK_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 + 300;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private Sprite backButtonActive;
	private boolean checkBackButtonMouseOver;
	private Sprite backButtonInActive;
	private BitmapFont font;
	private String movementText;
	private String objectPickingText;
	private Sprite volumeButtonMuted;
	private Sprite volumeButtonFull;
	private Sprite wasdKeyboard;
	private Sprite eKeyboard;
	private Sprite iKeyboard;
	private Sprite escKeyboard;
	private String inventoryText;
	private String escText;
	private static final int E_Y = PrisonEscapeGame.HEIGHT / 2 - 160;
	private static final int E_WIDTH = 39;
	private static final int E_HEIGHT = 39;
	private static final int I_Y = PrisonEscapeGame.HEIGHT / 2 - 160;
	private static final int I_WIDTH = 39;
	private static final int I_HEIGHT = 39;
	private static final int ESC_Y = PrisonEscapeGame.HEIGHT / 2 + 80;
	private static final int ESC_WIDTH = 56;
	private static final int ESC_HEIGHT = 39;
	private static final int WASD_Y = PrisonEscapeGame.HEIGHT / 2 + 50;
	private static final int WASD_WIDTH = 108;
	private static final int WASD_HEIGHT = 75;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;

	public HelpScreen(PrisonEscapeGame game) {
		this.game = game;
		tween = new TweenManager();
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/background.png")));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back.png")));
		volumeButtonMuted = MainMenuScreen.getInstance(game).volumeButtonMuted();
		volumeButtonFull = MainMenuScreen.getInstance(game).volumeButtonFull();
		wasdKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/wasd.png")));
		eKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/e.png")));
		iKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/i.png")));
		escKeyboard = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/esc.png")));
		font = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		movementText = "Press W,S,A,D for movement";
		objectPickingText = "Press E for picking up objects \n and going through doors";
		inventoryText = "Press I for inventory";
		escText = "Press ESC for pause menu";
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
		Timeline.createSequence().beginSequence().push(Tween.set(font, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(backButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(backButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(font, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(wasdKeyboard, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(eKeyboard, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(iKeyboard, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(escKeyboard, BitmapAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonFull, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonMuted, SpriteAccessor.ALPHA).target(0))
				.push(Tween.from(backgroundSprite, SpriteAccessor.ALPHA, 0).target(0))
				.push(Tween.to(font, BitmapAccessor.ALPHA, 0.5f).target(1))
				.push(Tween.to(wasdKeyboard, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(eKeyboard, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(iKeyboard, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(escKeyboard, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(escKeyboard, BitmapAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(backButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(backButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(volumeButtonFull, SpriteAccessor.ALPHA, 0.2f).target(1))
				.push(Tween.to(volumeButtonMuted, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tween.update(delta);

		game.getGameController().getSpriteBatch().begin();

		backgroundSprite.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		backgroundSprite.draw(game.getGameController().getSpriteBatch());

		// Drawing wasd keyboard sprite with text
		font.draw(game.getGameController().getSpriteBatch(), movementText, Gdx.graphics.getWidth() / 2 - 110,
				Gdx.graphics.getHeight() / 2 + 200);

		int x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 80;

		wasdKeyboard.setPosition(x, WASD_Y);
		wasdKeyboard.setSize(WASD_WIDTH, WASD_HEIGHT);
		wasdKeyboard.draw(game.getGameController().getSpriteBatch());

		// Drawing e keyboard sprite with text
		font.draw(game.getGameController().getSpriteBatch(), objectPickingText, Gdx.graphics.getWidth() / 2 - 110,
				Gdx.graphics.getHeight() / 2 - 50);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 110;

		eKeyboard.setPosition(x, E_Y);
		eKeyboard.setSize(E_WIDTH, E_HEIGHT);
		eKeyboard.draw(game.getGameController().getSpriteBatch());

		// Drawing i keyboard sprite with text
		font.draw(game.getGameController().getSpriteBatch(), inventoryText, Gdx.graphics.getWidth() / 2 + 340,
				Gdx.graphics.getHeight() / 2 - 50);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 500;

		iKeyboard.setPosition(x, I_Y);
		iKeyboard.setSize(I_WIDTH, I_HEIGHT);
		iKeyboard.draw(game.getGameController().getSpriteBatch());

		// Drawing esc keyboard sprite with text
		font.draw(game.getGameController().getSpriteBatch(), escText, Gdx.graphics.getWidth() / 2 + 340,
				Gdx.graphics.getHeight() / 2 + 200);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 500;

		escKeyboard.setPosition(x, ESC_Y);
		escKeyboard.setSize(ESC_WIDTH, ESC_HEIGHT);
		escKeyboard.draw(game.getGameController().getSpriteBatch());

		x = PrisonEscapeGame.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 100;

		backButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 650;
		volumeButton(x);

		game.getGameController().getSpriteBatch().end();

	}

	private void backButton(int x) {
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

				game.setScreen(MainMenuScreen.getInstance(game));

			}
		} else {
			checkBackButtonMouseOver = false;
			backButtonInActive.setPosition(x, BACK_BUTTON_Y);
			backButtonInActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void volumeButton(int x) {
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

			volumeButtonMuted.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonMuted.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonMuted.draw(game.getGameController().getSpriteBatch());
			music.pause();
			getMouseOverSound.stop();
		} else {

			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(game.getGameController().getSpriteBatch());
			music.play();
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		backgroundSprite.getTexture().dispose();

	}

}
