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
import com.mygdx.game.map.Map;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class MainGameScreen implements Screen {

	PrisonEscapeGame game;
	private Sprite loading;
	private TweenManager tweenManager;
	
	public MainGameScreen (PrisonEscapeGame game) {
		this.game = game;
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getSpriteBatch().begin();
		
		loading.setPosition(Gdx.graphics.getWidth()/2 - loading.getWidth()/2, 
				Gdx.graphics.getHeight()/2 - loading.getHeight()/2);
		
		loading.draw(game.getSpriteBatch());
		game.getSpriteBatch().end();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		loading = new Sprite(new Texture("assets/loading.png"));

		Tween.set(loading, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(loading, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				
//				config.width = 720; //528
//				config.height = 520; //768
				Gdx.graphics.setWindowedMode(528, 768); //Resizes window for map
				((Game) Gdx.app.getApplicationListener()).setScreen(new Map());
			}
		}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
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