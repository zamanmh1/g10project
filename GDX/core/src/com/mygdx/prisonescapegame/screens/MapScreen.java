package com.mygdx.prisonescapegame.screens;

import java.util.ArrayList;
import java.util.Calendar;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.entities.Item;
import com.mygdx.game.io.InteractionController;
import com.mygdx.game.io.PlayerMovementController;
import com.mygdx.game.model.TiledModel;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.game.util.Time;
import com.mygdx.prisonescape.scenes.Hud;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward, Shibu George
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class MapScreen extends PauseMenu implements Screen {
	private TiledMap tilemap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Viewport gamePort; // Hud
	private Hud hud;
	private Actor player;

	private ArrayList<Item> items; // Items to be drawn on each rendered frame
	private ArrayList<ActorAction> npcs;

	private InputMultiplexer inputHandler; // Allows for multiple user inputs to be used
	private PlayerMovementController movementHandler;
	private InteractionController interactionHandler;

	private TiledModel model;
	private Sprite inventoryBackground;
	private TweenManager tween;
	private PrisonEscapeGame game;
	private Sprite roomTransition;
	private boolean inventoryPressed;
	private String inventoryText;
	private BitmapFont fontBig;
	private String mapName;
	private Sprite inventoryBox;

	private static Stage stage;

	public MapScreen(Actor player, PrisonEscapeGame game) {

		this.player = player;
		this.game = game;
		tween = new TweenManager();
		mapRenderer = new OrthogonalTiledMapRenderer(null);
		tilemap = null;
		stage = new Stage();
		movementHandler = new PlayerMovementController(player);
		interactionHandler = new InteractionController(player);
		inputHandler = new InputMultiplexer();
		inventoryBackground = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/inventory_background.jpg")));
		inventoryBox = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/inventory_box.png")));
		fontBig = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-big.fnt"));
		inventoryText = "Inventory";
		roomTransition = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/black_background.jpg")));
		inventoryPressed = false;

	}

	public void setMap(String map, GameHandler gameHandler) {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(roomTransition, SpriteAccessor.ALPHA).target(1).start(tween);
		Tween.to(roomTransition, SpriteAccessor.ALPHA, 0.5f).target(0).start(tween);

		mapName = map;
		tilemap = new TmxMapLoader().load(map);

		/**
		 * If already a map being shown, try to dispose of it. However, if it is the
		 * first map to be shown there is nothing to dispose of.
		 */
		try {

			mapRenderer.getMap().dispose();

		} catch (NullPointerException e) {
			// Initial call to method to setup first map.
			// Maybe output a message to welcome player to game?
		}

		mapRenderer.setMap(tilemap);
		model = new TiledModel(tilemap);

		interactionHandler.setItemHandler(gameHandler); // high coupling (bad) provides way for interaction controller
														// to handle finding items

		items = new ArrayList<Item>(); // Resets items in map
		npcs = new ArrayList<ActorAction>();

	}

	// Needed for rendering items in map
	public void addItemToMap(Item i) {
		items.add(i);
	}

	// Stop given item being rendered in map
	public void removeItemFromMap(Item i) {
		items.remove(i);
	}

	public void addNPCToMap(ActorAction action) {
		npcs.add(action);
	}

	@Override
	public void show() {
		// Gdx.graphics.setWindowedMode(528, 768);
		// mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the
		// Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); // creates a camera to display the map on screen
		// oCamera.setToOrtho(false, 11,16);
		gamePort = new FitViewport(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT, oCamera);
		hud = new Hud(game.getGameController().getSpriteBatch());

		oCamera.setToOrtho(false, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
		// Sets the camera and renders the scene from the bottom left. /3 to zoom in to
		// match the size of the window.

		Gdx.input.setInputProcessor(inputHandler);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(logo, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(logo, SpriteAccessor.ALPHA, 1.0f).target(1).start(tween);
		Tween.set(optionBackground, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(optionBackground, SpriteAccessor.ALPHA, 1.0f).target(0.9f).start(tween);

	}

	@Override
	public void render(float delta) {
		// updates using time since last render call
		if (!menuPressed) {
			movementHandler.update(delta);
			game.getGameController().update(delta);
			player.setFrozen(false);
		}
		inputHandler.addProcessor(movementHandler);
		inputHandler.addProcessor(interactionHandler);
		inputHandler.addProcessor(stage);
		tween.update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Smooth camera based on updated player position
		oCamera.position.set(player.getWorldX() + 0.5f, player.getWorldY() + 0.5f, 0);

		oCamera.update();

		mapRenderer.getBatch().setColor(game.getGameController().getTime().getTint());
		mapRenderer.setView(oCamera);
		mapRenderer.render();
		// renders the map and sets the view of the camera to display the map

		// set HUD camera
		game.getGameController().getSpriteBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		// hud.stage.draw();

		mapRenderer.getBatch().begin();
		// player.draw(mapRenderer.getBatch());
		
		/**
		 * !!! Placed here means that the tint doesn't effect players and items.
		 */
		mapRenderer.getBatch().setColor(1,1,1,1);
		
		mapRenderer.getBatch().draw(player.getSprite(), player.getWorldX(), player.getWorldY(), GameSettings.TILE_SIZE,
				GameSettings.TILE_SIZE); // Render player

		for (ActorAction action : npcs) {
			mapRenderer.getBatch().draw(action.getActor().getSprite(), action.getActor().getWorldX(),
					action.getActor().getWorldY(), GameSettings.TILE_SIZE, GameSettings.TILE_SIZE);
		}

		// Rendering the items in the given map
		for (Item i : items) {
			i.getSprite().setPosition(i.getWorldX(), i.getWorldY()); // Testing if restartItems() was being called.
			i.getSprite().draw(mapRenderer.getBatch());
		}

		mapRenderer.getBatch().end();
		stage.act();
		stage.draw();
		game.getGameController().getSpriteBatch().begin();

		roomTransition.setPosition(PrisonEscapeGame.WIDTH / 2 - roomTransition.getWidth() / 2,
				PrisonEscapeGame.HEIGHT / 2 - roomTransition.getHeight() / 2);

		roomTransition.draw(game.getGameController().getSpriteBatch());

		if (menuKeyCheck() == true) {
			player.setFrozen(true);
			inputHandler.clear();
			drawPauseMenu(game, tween);

		}

		// Display of inventory
		if (inventoryKeyCheck() == true) {
			inventoryBackground.setSize(inventoryBackground.getWidth(), PrisonEscapeGame.HEIGHT);
			inventoryBackground.draw(game.getGameController().getSpriteBatch());

			// Row and column of inventory boxes
			for (int x = 0; x < 300; x += 170) {
				for (int y = 0; y < PrisonEscapeGame.HEIGHT; y += 125) {
					inventoryBox.setPosition(x, y);
					inventoryBox.draw(game.getGameController().getSpriteBatch());

				}
			}

			// Text of Inventory
			fontBig.draw(game.getGameController().getSpriteBatch(), inventoryText, 70, PrisonEscapeGame.HEIGHT - 10);
			// Text of room names shifted to the right
			fontBig.draw(game.getGameController().getSpriteBatch(),
					"Room: " + mapName.substring(10, mapName.lastIndexOf('.')), 400, PrisonEscapeGame.HEIGHT - 10);
		} else {
			// Text of room names
			fontBig.draw(game.getGameController().getSpriteBatch(),
					"Room: " + mapName.substring(10, mapName.lastIndexOf('.')), 40, PrisonEscapeGame.HEIGHT - 10);
			
			
			// Time HUD Element
			Time time = game.getGameController().getTime();
			time = Time.getTime(time.getCalendar(), GameSettings.TIME_SCALE);
			game.getGameController().setTime(time);
			
			String hour = String.format("%02d", time.getHour());
			
			String minutes = String.format("%02d", time.getMin());			
			
			fontBig.draw(game.getGameController().getSpriteBatch(),
					"Time: " + hour + ":" + minutes, 40, PrisonEscapeGame.HEIGHT - 50);
		}
		game.getGameController().getSpriteBatch().end();

	}

	private boolean inventoryKeyCheck() {
		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			if (inventoryPressed == false) {
				inventoryPressed = true;

			} else if (inventoryPressed == true) {
				inventoryPressed = false;

			}

		}
		return inventoryPressed;

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

	@Override
	public void resize(int width, int height) {
		// oCamera.viewportHeight = height;
		// oCamera.viewportWidth = width;
		// oCamera.update();
		// oCamera.position.set(player.getSprite().getX(), player.getSprite().getY(),
		// 0);
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
		//dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getSprite().getTexture().dispose();
		exitButtonMenuInActive.getTexture().dispose();
		exitButtonMenuActive.getTexture().dispose();
		resumeButtonMenuActive.getTexture().dispose();
		optionBackground.getTexture().dispose();
		remumeButtonMenuInActive.getTexture().dispose();

	}

	public TiledMap getTileMap() {
		return tilemap;
	}

	public TiledModel getTiledModel() {
		return model;
	}

	public static Stage getStage() {
		return stage;
	}

}
