package com.mygdx.prisonescapegame.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.Item;
import com.mygdx.game.io.InteractionController;
import com.mygdx.game.io.PlayerMovementController;
import com.mygdx.game.model.TiledModel;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

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

public class Map implements Screen
{	
	private TiledMap tilemap; 
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Actor player;
	private TmxMapLoader loader;
	
	private ArrayList<Item> items; // Items to be drawn on each rendered frame
	
	private InputMultiplexer inputHandler; // Allows for multiple user inputs to be used
	private PlayerMovementController movementHandler;
	private InteractionController interactionHandler;
	
	private TiledModel model;
	
	private SpriteBatch batch;
	private Sprite optionBackground;
	private Sprite playButtonInActive;
	private Sprite exitButtonInActive;
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

	public Map(Actor player) {
		this.player = player;
		tween = new TweenManager();
		tilemap = null;
		loader = new TmxMapLoader();	
				
		movementHandler = new PlayerMovementController(player);
		interactionHandler = new InteractionController(player);
		
		inputHandler = new InputMultiplexer();
		
		inputHandler.addProcessor(movementHandler);
		inputHandler.addProcessor(interactionHandler);
		optionBackground = new Sprite(new Texture(Gdx.files.internal("data/OptionMenuBackGround.jpg")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/play_inactive.png")));
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/play_active.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		batch = new SpriteBatch();
		menuPressed = false;
	}
	
	public void setMap(String map, GameHandler gameHandler) {			
		tilemap = loader.load(map);
		model = new TiledModel(tilemap);
		getTiledModel().getTile(player.getX(), player.getY()).setActor(player);
		
		
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
		mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); //creates a camera to display the map on screen
		//oCamera.setToOrtho(false, 11,16);
		oCamera.setToOrtho(false, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		//Sets the camera and renders the scene from the bottom left. /3 to zoom in to match the size of the window.
		
		Gdx.input.setInputProcessor(inputHandler);
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
	public void render(float delta) 
	{	
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
		//oCamera.viewportHeight = height;
		//oCamera.viewportWidth = width;
		//oCamera.update();
		//oCamera.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
		//Test stuff; setToOrtho method above achieves the effect much better and cleaner.
	}

	@Override
	public void pause() {	}

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
	}
	
	public TiledMap getTileMap()
	{
		return tilemap;
	}
	
	public TiledModel getTiledModel() {
		return model;
	}
	
}

