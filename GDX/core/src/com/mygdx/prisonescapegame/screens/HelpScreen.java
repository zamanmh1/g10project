package com.mygdx.prisonescapegame.screens;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.BitmapAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

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
	private static final int BACK_BUTTON_Y = 460;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private Sprite backButtonActive;
	private boolean checkBackButtonMouseOver;
	private Sprite backButtonInActive;
	private BitmapFont font;
	private String movementText;
	private String objectPickingText;

	public HelpScreen(PrisonEscapeGame game) {
		this.game = game;
		tween = new TweenManager();
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/background.png")));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/back.png")));

		font = new BitmapFont(Gdx.files.internal("data/vision-bold-font.fnt"));
		movementText = "Press W,S,A,D for movement";
		objectPickingText = "Press E for picking up objects";
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
		Tween.set(backgroundSprite, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(backgroundSprite, SpriteAccessor.ALPHA, 2).target(1).start(tween);
		Tween.set(backButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(backButtonInActive, SpriteAccessor.ALPHA, 2).target(1).start(tween);
		Tween.set(font, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(font, BitmapAccessor.ALPHA, 2).target(1).start(tween);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tween.update(delta);

		game.getGameController().getSpriteBatch().begin();

		backgroundSprite.draw(game.getGameController().getSpriteBatch());
		font.draw(game.getGameController().getSpriteBatch(), movementText, Gdx.graphics.getWidth() / 2 ,
				Gdx.graphics.getHeight() / 2 + 100);
		font.draw(game.getGameController().getSpriteBatch(), objectPickingText, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - 50);

		int x = 300;

		backButton(x);

		
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
				Sound mouse = MainMenuScreen.getInstance(game).mouseOverSound();
				Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
				if (muted) {
					mouse.stop();
				} else {
					mouse.play(1f);
				}
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
