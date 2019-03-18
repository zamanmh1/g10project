package com.mygdx.prisonescapegame.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class MainGameScreen implements Screen {

	private PrisonEscapeGame game;
	private Sprite loading;
	private TweenManager tween;
	
	public MainGameScreen (PrisonEscapeGame game) {
		this.game = game;
		tween = new TweenManager();

		loading = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/loading.png")));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		game.getGameController().getSpriteBatch().begin();
		
		loading.setPosition(Gdx.graphics.getWidth()/2 - loading.getWidth()/2, 
				Gdx.graphics.getHeight()/2 - loading.getHeight()/2);
		loading.draw(game.getGameController().getSpriteBatch());
		
		game.getGameController().getSpriteBatch().end();

		tween.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Tween.set(loading, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(loading, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				
				((Game) Gdx.app.getApplicationListener()).setScreen(game.getGameController().getMapScreen());
			}
		}).start(tween);

		tween.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {		
		loading.getTexture().dispose();
	}

}