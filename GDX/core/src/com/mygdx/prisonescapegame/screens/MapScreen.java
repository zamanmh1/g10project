package com.mygdx.prisonescapegame.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.Item;
import com.mygdx.game.io.InteractionController;
import com.mygdx.game.io.PlayerMovementController;
import com.mygdx.game.model.TiledModel;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
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

public class MapScreen implements Screen
{	
	private TiledMap tilemap; 
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Actor player;
	
	private ArrayList<Item> items; // Items to be drawn on each rendered frame
	
	private InputMultiplexer inputHandler; // Allows for multiple user inputs to be used
	private PlayerMovementController movementHandler;
	private InteractionController interactionHandler;
	
	private boolean isPaused;
	
	private TiledModel model;	

	private Sprite optionBackground;
	private Sprite playButtonMenuInActive;
	private Sprite exitButtonMenuInActive;
	private TweenManager tween;
	private boolean menuPressed;
	private static final int PLAY_BUTTON_WIDTH = 174;
	private static final int PLAY_BUTTON_Y = 300;
	private static final int PLAY_BUTTON_HEIGHT = 52;
	private Sprite playButtonMenuActive;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = 100;
	private Sprite exitButtonMenuActive;
	private PrisonEscapeGame game;
	private SpriteBatch batch;
	private boolean checkPlayButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;
	private Sprite volumeButtonMuted;
	private Sprite volumeButtonFull;

	

	public MapScreen(Actor player, PrisonEscapeGame game) {

		this.player = player;
		this.game = game;
		tween = new TweenManager();
		mapRenderer = new OrthogonalTiledMapRenderer(null);
		tilemap = null;
		
		batch = new SpriteBatch();	

		movementHandler = new PlayerMovementController(player);
		interactionHandler = new InteractionController(player);
		
		inputHandler = new InputMultiplexer();
		inputHandler.addProcessor(movementHandler);
		inputHandler.addProcessor(interactionHandler);
		optionBackground = new Sprite(new Texture(Gdx.files.internal("data/OptionMenuBackGround.jpg")));
		playButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/play_inactive.png")));
		playButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/play_active.png")));
		exitButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		volumeButtonMuted = MainMenuScreen.getInstance(game).volumeButtonMuted();
		volumeButtonFull = MainMenuScreen.getInstance(game).volumeButtonFull();
		menuPressed = false;
		checkPlayButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		
	}
	
	public void setMap(String map, GameHandler gameHandler, int newX, int newY) {	
		
		tilemap = new TmxMapLoader().load(map);
		/** 
		 * If already a map being shown, try to dispose of it.
		 * However, if it is the first map to be shown there is nothing to dispose of. 
		 */
		try {
			
			mapRenderer.getMap().dispose();
			
			
			
		} catch  (NullPointerException e) {	
			// Initial call to method to setup first map.
			// Maybe output a message to welcome player to game?
		}

		mapRenderer.setMap(tilemap);
		
		model = new TiledModel(tilemap);
		getTiledModel().getTile(newX, newY).setActor(player);
		
		interactionHandler.setItemHandler(gameHandler); // high coupling (bad) provides way for interaction controller to handle finding items
		
		items = new ArrayList<Item>(); // Resets items in map
		
		
	}
	
	

	// Needed for rendering items in map
	public void addItemToMap(Item i) {
		items.add(i);
	}
	
	// Stop given item being rendered in map
	public void removeItemFromMap(Item i) {
		items.remove(i);
	}
	
	@Override
	public void show() 
	{		
		//Gdx.graphics.setWindowedMode(528, 768);
		//mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); //creates a camera to display the map on screen
		//oCamera.setToOrtho(false, 11,16);
	
		oCamera.setToOrtho(false, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		//Sets the camera and renders the scene from the bottom left. /3 to zoom in to match the size of the window.
		
		Gdx.input.setInputProcessor(inputHandler);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Tween.set(optionBackground, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(optionBackground, SpriteAccessor.ALPHA, 1.0f).target(0.9f).start(tween);
		Tween.set(volumeButtonMuted, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(volumeButtonMuted, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(volumeButtonFull, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(volumeButtonFull, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(playButtonMenuActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(playButtonMenuActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(playButtonMenuInActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(playButtonMenuInActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(exitButtonMenuActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(exitButtonMenuInActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		
	}

	@Override
	public void render(float delta) 
	{	
		//float oldDelta = delta;
		//if (menuPressed) {
		//	delta = 0;
		//}
		
		// updates using time since last render call
		movementHandler.update(delta);
		player.update(delta);
		tween.update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Smooth camera based on updated player position
		oCamera.position.set(player.getWorldX()+0.5f, player.getWorldY()+0.5f, 0);
		
		oCamera.update();
		
		mapRenderer.setView(oCamera);
		mapRenderer.render();
		//renders the map and sets the view of the camera to display the map
		
		mapRenderer.getBatch().begin();
		//player.draw(mapRenderer.getBatch());
		
		
		mapRenderer.getBatch().draw(player.getSprite(),
				player.getWorldX(), 
				player.getWorldY(),
				GameSettings.TILE_SIZE,
				GameSettings.TILE_SIZE); // Render player
		
		//Rendering the items in the given map
		for(Item i : items)
		{
			i.getSprite().setPosition(i.getWorldX(), i.getWorldY()); // Testing if restartItems() was being called.
			i.getSprite().draw(mapRenderer.getBatch());
		}		
		
		//delta = oldDelta;
		
		mapRenderer.getBatch().end();
		batch.begin();
		
		if (menuKeyCheck() == true) {
			
			
			
			optionBackground.setPosition(Gdx.graphics.getWidth() / 2 - optionBackground.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - optionBackground.getHeight() / 2);
			
			
			
			optionBackground.draw(batch);
			playButtonMenu();
			exitButtonMenu();
			volumeButton();
			
		}
		batch.end();
		
		}
		

	private boolean menuKeyCheck() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (menuPressed == false) {
				menuPressed = true;

			} else if (menuPressed == true) {
				menuPressed = false;
			}

		}
		return menuPressed;
		
	}
	private void playButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - playButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			playButtonMenuActive.setPosition(x,PLAY_BUTTON_Y);
			playButtonMenuActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonMenuActive.draw(batch);

			if (checkPlayButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkPlayButtonMouseOver = true;

			}
			

			if (Gdx.input.isTouched()) {

				menuPressed = false;
				

			}
		} else {
			checkPlayButtonMouseOver = false;
			playButtonMenuInActive.setPosition(x,PLAY_BUTTON_Y);
			playButtonMenuInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonMenuInActive.draw(batch);

		}
		
	}
	
	private void exitButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - exitButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonMenuActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuActive.draw(batch);

			if (checkExitButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkExitButtonMouseOver = true;

			}
			

			if (Gdx.input.justTouched()) {
				Tween.registerAccessor(Sprite.class, new SpriteAccessor());

				Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
				Tween.to(exitButtonMenuActive, SpriteAccessor.ALPHA, 0.5f).target(1).repeatYoyo(0, 0).setCallback(new TweenCallback() {

					@Override
					public void onEvent(int type, BaseTween<?> source) {
						game.setScreen(MainMenuScreen.getInstance(game));
					}
				}).start(tween);
				//game.setScreen(MainMenuScreen.getInstance(null));

			}
		} else {
			checkExitButtonMouseOver = false;
			exitButtonMenuInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuInActive.draw(batch);

		}
		
	}

	private void volumeButton() {
		int x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 300;
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
			volumeButtonMuted.draw(batch);
			music.pause();
			getMouseOverSound.stop();
		} else {

			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(batch);
			music.play();
		}

	}
	
	@Override
	public void resize(int width, int height) {
		//oCamera.viewportHeight = height;
		//oCamera.viewportWidth = width;
		//oCamera.update();
		//oCamera.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
		//Test stuff; setToOrtho method above achieves the effect much better and cleaner.
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {	}

	@Override
	public void hide() 
	{
		dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getSprite().getTexture().dispose();
		exitButtonMenuInActive.getTexture().dispose();
		exitButtonMenuActive.getTexture().dispose();
		playButtonMenuActive.getTexture().dispose();
		optionBackground.getTexture().dispose();
		playButtonMenuInActive.getTexture().dispose();

	}
	
	public TiledMap getTileMap()
	{
		return tilemap;
	}
	
	public TiledModel getTiledModel() {
		return model;
	}
	
}

