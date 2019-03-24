package prisonescape.game.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import prisonescape.game.GameHandler;
import prisonescape.game.GameSettings;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.io.player.InteractionController;
import prisonescape.game.io.player.PlayerMovementController;
import prisonescape.game.model.TiledModel;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.ActorAction;
import prisonescape.game.model.actors.Item;
import prisonescape.game.screens.components.HUD;
import prisonescape.game.tween.SpriteAccessor;
import prisonescape.game.util.Time;

/**
 * This class represents the current active game play which includes the map,
 * HUD and pause menu
 * 
 * @author Sam Ward, Shibu George, Sean Corcoran
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class ActiveGame implements Screen {
	private TiledMap tilemap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Actor player;

	private ArrayList<Item> items; // Items to be drawn on each rendered frame
	private ArrayList<ActorAction> npcs;

	private InputMultiplexer inputHandler; // Allows for multiple user inputs to be used
	private PlayerMovementController movementHandler;
	private InteractionController interactionHandler;

	private TiledModel model;
	private static TweenManager tween;
	private PrisonBreakout game;
	private static Sprite roomTransition;
	private boolean inventoryPressed;
	private String mapName;
	private Item foundItem;
	public static HUD h;
	private Pause pauseMenu;

	private static Stage stage;

	/**
	 * Constructs the stage, sprites, pause menu, actors, tiledMap and input
	 * handlers
	 * 
	 * @param player
	 * @param game
	 */
	public ActiveGame(Actor player, PrisonBreakout game) {
		pauseMenu = new Pause();
		this.player = player;
		this.game = game;
		tween = new TweenManager();
		mapRenderer = new OrthogonalTiledMapRenderer(null);
		tilemap = null;
		stage = new Stage();
		movementHandler = new PlayerMovementController(player);
		interactionHandler = new InteractionController(player);
		inputHandler = new InputMultiplexer();
		roomTransition = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/black_background.jpg")));
		inventoryPressed = false;

	}

	/**
	 * Sets the map on the screen from the location to TiledMap and handles Items
	 * using <code>GameHandler</code>
	 * 
	 * @param String      map which contains the location of the map
	 * @param gameHandler which allows item handlers
	 */
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

	/**
	 * Adds an <code>Item</code> to items ArrayList
	 * 
	 * @param i is the <code>Item</code>
	 */
	// Needed for rendering items in map
	public void addItemToMap(Item i) {
		items.add(i);
	}

	/**
	 * Removes <code>Item</code> from items ArrayList
	 * 
	 * @param i is the <code>Item</code>
	 */
	// Stop given item being rendered in map
	public void removeItemFromMap(Item i) {
		items.remove(i);
		foundItem = i;
	}

	/**
	 * Adds the NPC's to the map given the action they take and how they behave.
	 * 
	 * @param behaviour/action of the NPC
	 */
	public void addNPCToMap(ActorAction action) {
		npcs.add(action);
	}

	/**
	 * 
	 * Removes the NPC's to the map given the action they take and how they behave.
	 * 
	 * @param behaviour/action of the NPC
	 */
	public void removeNPCFromMap(ActorAction action) {
		npcs.remove(action);
	}

	/**
	 * 
	 * Represents what is shown when you view this class. Transitions, camera
	 * position, HUD and setting up input handler.
	 * 
	 */
	@Override
	public void show() {
		// Gdx.graphics.setWindowedMode(528, 768);
		// mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the
		// Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); // creates a camera to display the map on screen
		// oCamera.setToOrtho(false, 11,16);
		// hud = new Hud(game.getGameController().getSpriteBatch());
		h = new HUD(game.getGameController());

		oCamera.setToOrtho(false, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
		// Sets the camera and renders the scene from the bottom left. /3 to zoom in to
		// match the size of the window.

		Gdx.input.setInputProcessor(inputHandler);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(pauseMenu.logo, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(pauseMenu.logo, SpriteAccessor.ALPHA, 1.0f).target(1).start(tween);
		Tween.set(pauseMenu.optionBackground, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(pauseMenu.optionBackground, SpriteAccessor.ALPHA, 1.0f).target(0.9f).start(tween);

	}

	/**
	 * Renders the sprite, maps, transitions, handlers and stage.
	 * 
	 */
	@Override
	public void render(float delta) {
		// updates using time since last render call
		if (!pauseMenu.menuPressed) {
			movementHandler.update(delta);
			game.getGameController().update(delta);
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

		mapRenderer.getBatch().begin();

		mapRenderer.getBatch().setColor(game.getGameController().getTime().getTint());
		mapRenderer.setView(oCamera);
		// mapRenderer.render();

		// Array containing all map layers other than the alarm layer.
		TiledMapTileLayer[] mapLayers = new TiledMapTileLayer[3];
		mapLayers[0] = model.getLayer("Tile Layer 1"); // Main layer of map.
		mapLayers[1] = model.getLayer("Tile Layer 3"); // Layer representing tables within map.
		mapLayers[2] = model.getLayer("Tile Layer 4"); // Layer containing small details.

		for (TiledMapTileLayer layer : mapLayers) {
			if (layer != null) {
				mapRenderer.renderTileLayer(layer);
			}
		}

		TiledMapTileLayer alarmLayer = model.getLayer("Tile Layer 2");
		if (alarmLayer != null) {
			if (game.getGameController().getTime().isDay() == true) {
				mapRenderer.renderTileLayer(alarmLayer);
			} else {

				mapRenderer.getBatch().setColor(0.9f, 0.0f, 0.0f, 1.0f);
				mapRenderer.renderTileLayer(alarmLayer);
			}
		}

		// set HUD camera
		// game.getGameController().getSpriteBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		// hud.stage.draw();

		/**
		 * !!! Placed here means that the tint doesn't effect players and items.
		 */
		mapRenderer.getBatch().setColor(1, 1, 1, 1);

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
		Time time = game.getGameController().getTime();
		h.update(mapName.substring(10, mapName.lastIndexOf('.')), foundItem, time.toString());
		h.setTimeImage(time.getHour());

		game.getGameController().getSpriteBatch().begin();

		roomTransition.setPosition(PrisonBreakout.WIDTH / 2 - roomTransition.getWidth() / 2,
				PrisonBreakout.HEIGHT / 2 - roomTransition.getHeight() / 2);

		roomTransition.draw(game.getGameController().getSpriteBatch());

		if (menuKeyCheck() == true) {
			inputHandler.clear();
			pauseMenu.drawPauseMenu(game, tween);

		}

		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			Credits credits = new Credits(game);
			credits.ending2();
					
			((Game) Gdx.app.getApplicationListener()).setScreen(credits);
		}

		if (inventoryKeyCheck() == true) {
		}

		if (Gdx.input.isKeyJustPressed(Keys.M)) {

			game.setScreen(Minimap.getInstance(game));

		}

		game.getGameController().getSpriteBatch().end();

	}

	/**
	 * Returns true if the "I" Key is pressed which opens up the Inventory
	 * 
	 * @return boolean true if "I" is pressed
	 */
	private boolean inventoryKeyCheck() {
		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			if (h.isVisible() == false) {
				h.setVisible(true);

			} else if (h.isVisible() == true) {
				h.setVisible(false);

			}

		}
		return inventoryPressed;

	}

	/**
	 * Returns true if the "ESC" Key is pressed which opens up pause menu
	 * 
	 * @return boolean true if "ESC" is pressed
	 */
	private boolean menuKeyCheck() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			if (pauseMenu.menuPressed == false) {
				pauseMenu.menuPressed = true;
			} else if (pauseMenu.menuPressed == true) {
				Time.updateLastTime();
				pauseMenu.menuPressed = false;
			}
		}
		return pauseMenu.menuPressed;
	}

	/**
	 * Setting Map Screen to black for smoother transition to exit and come back
	 * 
	 */
	public static void setBlackScreen() {
		Tween.set(roomTransition, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(roomTransition, SpriteAccessor.ALPHA, 0f).target(1).start(tween);

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
		// dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getSprite().getTexture().dispose();
		pauseMenu.exitButtonMenuInActive.getTexture().dispose();
		pauseMenu.exitButtonMenuActive.getTexture().dispose();
		pauseMenu.resumeButtonMenuActive.getTexture().dispose();
		pauseMenu.optionBackground.getTexture().dispose();
		pauseMenu.remumeButtonMenuInActive.getTexture().dispose();

	}

	/**
	 * Returns the tiledMap rendered.
	 * 
	 * @return tiledMap
	 */
	public TiledMap getTileMap() {
		return tilemap;
	}

	/**
	 * 
	 * Returns the TiledModel which represents a model of an individual map within the game.
	 * 
	 * @return TiledModel
	 */
	public TiledModel getTiledModel() {
		return model;
	}

	/**
	 * 
	 * Returns the stage of the game.
	 * 
	 * @return Stage
	 */
	public static Stage getStage() {
		return stage;
	}

	/**
	 * 
	 * Returns the current map name the player is at.
	 * 
	 * @return String map name
	 */
	public String getMapName() {
		return mapName;
	}

}
