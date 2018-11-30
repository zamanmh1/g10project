package com.mygdx.game.map;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.MapLayer;
import com.mygdx.game.entities.Player;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.prisonescapegame.screens.MainGameScreen;
import com.mygdx.prisonescapegame.screens.MainMenuScreen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class Map implements Screen {

	private TiledMap tilemap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Player player;
	private TmxMapLoader loader;
	private Sprite optionBackground;
	private Sprite playButtonInActive;
	private Sprite exitButtonInActive;

	private HashMap<String, MapObjects> mapObjects;
	private SpriteBatch batch;
	private TweenManager tween;
	private boolean menuPressed;
	private static final int PLAY_BUTTON_WIDTH = 174;
	private static final int PLAY_BUTTON_Y = 300;
	private static final int PLAY_BUTTON_HEIGHT = 52;
	private Sprite playButtonActive;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = 100;
	private Sprite exitButtonActive;
	


	public Map(Player player) {
		
		this.player = player;
		tween = new TweenManager();
		tilemap = null;
		loader = new TmxMapLoader();
		mapObjects = new HashMap<String, MapObjects>();
		optionBackground = new Sprite(new Texture(Gdx.files.internal("data/OptionMenuBackGround.jpg")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/play_inactive.png")));
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/play_active.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		batch = new SpriteBatch();
		menuPressed = false;
	}

	public void setMap(String map) {
		tilemap = loader.load(map);
	}

	@Override
	public void show() {
		// Gdx.graphics.setWindowedMode(528, 768);
		mapRenderer = new OrthogonalTiledMapRenderer(tilemap); // initialises the Orthogonal (top-down) renderer for the
																// map
		oCamera = new OrthographicCamera(); // creates a camera to display the map on screen
		// oCamera.setToOrtho(false, 11,16);
		oCamera.setToOrtho(false, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
		// Sets the camera and renders the scene from the bottom left. /3 to zoom in to
		// match the size of the window.

		// for now, unless we have a better way, loading the player directly onto map
		// for hack.
		// player.getPlayerSprite().setPosition(80, 64);
		Gdx.input.setInputProcessor(player.getPlayerMover());

		setupLayer("Collision");
		setupLayer("Door");
		setupLayer("Use");

		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(optionBackground, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(optionBackground, SpriteAccessor.ALPHA, 1.0f).target(0.9f).start(tween);
		Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(playButtonInActive, SpriteAccessor.ALPHA, 1.0f).target(0.5f).start(tween);
		Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(playButtonActive, SpriteAccessor.ALPHA, 1.0f).target(0.5f).start(tween);
		Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(exitButtonActive, SpriteAccessor.ALPHA, 1.0f).target(0.5f).start(tween);
		Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(exitButtonInActive, SpriteAccessor.ALPHA, 1.0f).target(0.5f).start(tween);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tween.update(delta);
		// Sets camera position to be where player is. Updated before camera is rendered
		// for smooth operation.
		oCamera.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
		oCamera.update();

		mapRenderer.setView(oCamera);
		mapRenderer.render();
		// renders the map and sets the view of the camera to display the map

		mapRenderer.getBatch().begin();
		// player.setPosition(80, 64);
		player.draw(mapRenderer.getBatch());

		mapRenderer.getBatch().end();

		batch.begin();
		menuKeyCheck();
		

		if (menuPressed) {
			optionBackground.setPosition(Gdx.graphics.getWidth() / 2 - optionBackground.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - optionBackground.getHeight() / 2);
			
			
			
			optionBackground.draw(batch);
			playButtonMenu();
			exitButtonMenu();
			
		}

		batch.end();
	}

	private void menuKeyCheck() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (menuPressed == false) {
				menuPressed = true;

			} else if (menuPressed == true) {
				menuPressed = false;
			}

		}
		
	}
	private void playButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - playButtonInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			playButtonActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonActive.draw(batch);

			

			if (Gdx.input.isTouched()) {

				menuPressed = false;

			}
		} else {
		
			playButtonInActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonInActive.draw(batch);

		}
		
	}
	
	private void exitButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - exitButtonInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(batch);

			

			if (Gdx.input.isTouched()) {

				

			}
		} else {
		
			exitButtonInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonInActive.draw(batch);

		}
		
	}
	
	

	@Override
	public void resize(int width, int height) {
		// oCamera.viewportHeight = height;
		// oCamera.viewportWidth = width;
		// oCamera.position.set(player.getSprite().getX(), player.getSprite().getY(),
		// 0);
		// oCamera.update();
		// Test stuff; setToOrtho method above achieves the effect much better and
		// cleaner.
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getSprite().getTexture().dispose();
	}

	public TiledMap getTileMap() {
		return tilemap;
	}

	public void setupLayer(String layerID) {
		int layer = tilemap.getLayers().getIndex(layerID);
		MapLayer objectLayer = tilemap.getLayers().get(layer);
		MapObjects object = objectLayer.getObjects();

		mapObjects.put(layerID, object);
	}

	public boolean checkTouching(String layerID) {
		MapObjects objectTouching = mapObjects.get(layerID);

		for (RectangleMapObject rectangleObject : objectTouching.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getSprite().getBoundingRectangle())) {
				return true;
			}
		}
		return false;
	}

	public boolean getLayerVisibility(String layerID) {
		return tilemap.getLayers().get(tilemap.getLayers().getIndex(layerID)).isVisible();
	}

	public void setLayerVisibility(String layerID, boolean visibility) {
		tilemap.getLayers().get(tilemap.getLayers().getIndex(layerID)).setVisible(visibility);
	}

}
