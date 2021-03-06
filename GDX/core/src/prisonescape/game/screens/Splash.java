package prisonescape.game.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.tween.SpriteAccessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents the splash screen with Prison Breakout logo when the user first
 * loads the game.
 * 
 * @author Sam Ward, Shibu George
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public class Splash implements Screen {

	private PrisonBreakout game;
	private Sprite splash;
	private TweenManager tween;

	/**
	 * Constructs the splash sprite
	 * 
	 * @param game
	 */
	public Splash(PrisonBreakout game) {
		this.game = game;
		tween = new TweenManager();
		splash = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/logo.png")));

	}

	/**
	 * 
	 * Renders the splash sprite to the screen
	 * 
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getGameController().getSpriteBatch().begin();

		splash.setPosition(Gdx.graphics.getWidth() / 2 - splash.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - splash.getHeight() / 2);

		splash.draw(game.getGameController().getSpriteBatch());
		game.getGameController().getSpriteBatch().end();

		tween.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	/**
	 * 
	 * Represents the transition of the splash sprite going from fading in to fading
	 * out which leads to calling the <code>MainMenu</code> Screen.
	 * 
	 */
	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(MainMenu.getInstance(game));
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
		splash.getTexture().dispose();
	}

}