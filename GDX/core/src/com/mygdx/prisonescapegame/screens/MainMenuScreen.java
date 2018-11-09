package com.mygdx.prisonescapegame.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class MainMenuScreen implements Screen {

	private static final int PLAY_BUTTON_WIDTH = 200;
	private static final int PLAY_BUTTON_HEIGHT = 110;
	private static final int PLAY_BUTTON_Y = 300;
	private static final int EXIT_BUTTON_WIDTH = 200;
	private static final int EXIT_BUTTON_HEIGHT = 110;
	private static final int EXIT_BUTTON_Y = 150;
	
	private PrisonEscapeGame game;
	private TweenManager tween;

	private Sprite logo;
	private Sprite playButtonActive;
	private Sprite playButtonInActive;
	private Sprite exitButtonActive;
	private Sprite exitButtonInActive;
	private Tween active;
	private Sprite backgroundSprite;
	
	

	public MainMenuScreen(PrisonEscapeGame game) {
		this.game = game;
		tween = new TweenManager();
		logo = new Sprite(new Texture(Gdx.files.internal("data/logo.png")));
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/play_active.png")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/play_inactive.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/background.png")));

	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(backgroundSprite,SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(backgroundSprite,SpriteAccessor.ALPHA, 2).target(1).start(tween);
		Tween.set(logo,SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(logo,SpriteAccessor.ALPHA, 2).target(1).start(tween);
		Tween.set(playButtonInActive,SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(playButtonInActive,SpriteAccessor.ALPHA, 2).target(1).start(tween);
		Tween.set(exitButtonInActive,SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(exitButtonInActive,SpriteAccessor.ALPHA, 2).target(1).start(tween);
		active = Tween.to(playButtonActive,SpriteAccessor.ALPHA, 2).target(1).start(tween);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tween.update(delta);
		
		game.getGameController().getSpriteBatch().begin();
	
		backgroundSprite.draw(game.getGameController().getSpriteBatch());
		int x = PrisonEscapeGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 + 100;
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			
			playButtonActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonActive.draw(game.getGameController().getSpriteBatch());
			if (Gdx.input.isTouched()) {
				game.setScreen(new MainGameScreen(game));
				
			}
		} else {
			
			playButtonInActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonInActive.draw(game.getGameController().getSpriteBatch());
		}
		x = PrisonEscapeGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 100;
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		} else {
			exitButtonInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonInActive.draw(game.getGameController().getSpriteBatch());

		}		
	
		game.getGameController().getSpriteBatch().end();

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
		logo.getTexture().dispose();
		playButtonActive.getTexture().dispose();
		playButtonInActive.getTexture().dispose(); 
		exitButtonActive.getTexture().dispose(); 
		exitButtonInActive.getTexture().dispose();	
	}

}
