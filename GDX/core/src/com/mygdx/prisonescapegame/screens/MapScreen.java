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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import com.mygdx.prisonescape.scenes.Hud;
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

public class MapScreen implements Screen {
	private TiledMap tilemap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Viewport gamePort; // Hud
	private Hud hud;
	private Actor player;

	private ArrayList<Item> items; // Items to be drawn on each rendered frame

	private InputMultiplexer inputHandler; // Allows for multiple user inputs to be used
	private PlayerMovementController movementHandler;
	private InteractionController interactionHandler;

	private TiledModel model;

	private Sprite optionBackground;
	private Sprite remumeButtonMenuInActive;
	private Sprite exitButtonMenuInActive;
	private TweenManager tween;
	private boolean menuPressed;
	private static final int RESUME_BUTTON_WIDTH = 305;
	private static final int RESUME_BUTTON_Y = 400;
	private static final int RESUME_BUTTON_HEIGHT = 53;
	private Sprite resumeButtonMenuActive;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = 200;
	private static final int HELP_BUTTON_WIDTH = 174;
	private static final int HELP_BUTTON_Y = 300;
	private static final int HELP_BUTTON_HEIGHT = 52;
	private Sprite exitButtonMenuActive;
	private PrisonEscapeGame game;
	private boolean checkResumeButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;
	private Sprite volumeButtonMuted;
	private Sprite volumeButtonFull;
	private Sprite helpButtonMenuActive;
	private Sprite helpButtonMenuInActive;
	private boolean checkHelpButtonMouseOver;
	private Sprite backButtonActive;
	private Sprite backButtonInActive;
	private static final int BACK_BUTTON_WIDTH = 40;
	private static final int BACK_BUTTON_Y = 700;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private boolean checkBackButtonMouseOver;
	private boolean helpPressed;
	private boolean buttonActive;
	private Sprite wasdKeyboard;
	private Sprite eKeyboard;
	private BitmapFont font;
	private String movementText;
	private static final int WASD_WIDTH = 108;
	private static final int WASD_HEIGHT = 75;
	private static final int WASD_Y = 350;
	private static final int E_Y = 150;
	private static final int E_WIDTH = 39;
	private static final int E_HEIGHT = 39;
	private String objectPickingText;
	private Sprite logo;
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
		inputHandler.addProcessor(movementHandler);
		inputHandler.addProcessor(interactionHandler);
		inputHandler.addProcessor(stage);
		optionBackground = new Sprite(new Texture(Gdx.files.internal("data/OptionMenuBackGround.jpg")));
		remumeButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/resume_unactive.png")));
		resumeButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/resume_active.png")));
		exitButtonMenuActive = new Sprite(new Texture(Gdx.files.internal("data/exit_active.png")));
		exitButtonMenuInActive = new Sprite(new Texture(Gdx.files.internal("data/exit_inactive.png")));
		volumeButtonMuted = MainMenuScreen.getInstance(game).volumeButtonMuted();
		volumeButtonFull = MainMenuScreen.getInstance(game).volumeButtonFull();
		helpButtonMenuActive = new Sprite(new Texture("data/help_active.png"));
		helpButtonMenuInActive = new Sprite(new Texture("data/help_inactive.png"));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/back.png")));
		wasdKeyboard = new Sprite(new Texture(Gdx.files.internal("data/wasd.png")));
		eKeyboard = new Sprite(new Texture(Gdx.files.internal("data/e.png")));
		logo = new Sprite(new Texture(Gdx.files.internal("data/logo.png")));
		font = new BitmapFont(Gdx.files.internal("data/vision-bold-font.fnt"));
		objectPickingText = "Press E for picking up objects \n and going through doors";
		movementText = "Press W,S,A,D for movement";
		menuPressed = false;
		helpPressed = false;
		checkResumeButtonMouseOver = false;
		checkHelpButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		checkBackButtonMouseOver = false;
		buttonActive = true;

	}

	public void setMap(String map, GameHandler gameHandler, int newX, int newY) {

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
		getTiledModel().getTile(newX, newY).setActor(player);

		interactionHandler.setItemHandler(gameHandler); // high coupling (bad) provides way for interaction controller
														// to handle finding items

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
		Tween.set(volumeButtonMuted, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(volumeButtonMuted, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(volumeButtonFull, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(volumeButtonFull, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(resumeButtonMenuActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(resumeButtonMenuActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(helpButtonMenuInActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(helpButtonMenuInActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(helpButtonMenuActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(helpButtonMenuActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(remumeButtonMenuInActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(remumeButtonMenuInActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(exitButtonMenuActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(0f).start(tween);
		Tween.to(exitButtonMenuInActive, SpriteAccessor.ALPHA, 1.0f).target(1f).start(tween);
		Tween.set(backButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(backButtonInActive, SpriteAccessor.ALPHA, 1f).target(1).start(tween);
		Tween.set(backButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(backButtonActive, SpriteAccessor.ALPHA, 1f).target(1).start(tween);

	}

	@Override
	public void render(float delta) {
		// float oldDelta = delta;
		// if (menuPressed) {
		// delta = 0;
		// }

		// updates using time since last render call
		movementHandler.update(delta);
		player.update(delta);
		tween.update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Smooth camera based on updated player position
		oCamera.position.set(player.getWorldX() + 0.5f, player.getWorldY() + 0.5f, 0);

		oCamera.update();
		
		mapRenderer.setView(oCamera);
		mapRenderer.render();
		// renders the map and sets the view of the camera to display the map
		
		// set HUD camera
		game.getGameController().getSpriteBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

		mapRenderer.getBatch().begin();
		// player.draw(mapRenderer.getBatch());

		mapRenderer.getBatch().draw(player.getSprite(), player.getWorldX(), player.getWorldY(), GameSettings.TILE_SIZE,
				GameSettings.TILE_SIZE); // Render player

		// Rendering the items in the given map
		for (Item i : items) {
			i.getSprite().setPosition(i.getWorldX(), i.getWorldY()); // Testing if restartItems() was being called.
			i.getSprite().draw(mapRenderer.getBatch());
		}

		mapRenderer.getBatch().end();
		stage.act();
		stage.draw();
		game.getGameController().getSpriteBatch().begin();

		if (menuKeyCheck() == true) {

			optionBackground.setPosition(PrisonEscapeGame.WIDTH / 2 - optionBackground.getWidth() / 2,
					PrisonEscapeGame.HEIGHT / 2 - optionBackground.getHeight() / 2 + 200);
			logo.setPosition(PrisonEscapeGame.WIDTH/2 - logo.getWidth()/2, 
					PrisonEscapeGame.HEIGHT/2 - logo.getHeight()/2 + 250);
			
		
			optionBackground.draw(game.getGameController().getSpriteBatch());
			logo.draw(game.getGameController().getSpriteBatch());
			resumeButtonMenu();
			helpButtonMenu();
			exitButtonMenu();
			volumeButton();
			if (helpPressed) {
				helpScreenUI();

			}

		}
		game.getGameController().getSpriteBatch().end();

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

	private void resumeButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - remumeButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + RESUME_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > RESUME_BUTTON_Y) {

			resumeButtonMenuActive.setPosition(x, RESUME_BUTTON_Y);
			resumeButtonMenuActive.setSize(RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
			resumeButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkResumeButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkResumeButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {

					menuPressed = false;

				}
			}
		} else {
			checkResumeButtonMouseOver = false;
			remumeButtonMenuInActive.setPosition(x, RESUME_BUTTON_Y);
			remumeButtonMenuInActive.setSize(RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
			remumeButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void helpButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - helpButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + HELP_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y) {

			helpButtonMenuActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonMenuActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkHelpButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkHelpButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					helpPressed = true;
					buttonActive = false;
					Tween.set(resumeButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(remumeButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(helpButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(helpButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
					Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(0).start(tween);
					helpScreenUI();

				}
			}
		} else {
			checkHelpButtonMouseOver = false;
			helpButtonMenuInActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonMenuInActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}
	}

	private void exitButtonMenu() {
		int x = (int) (Gdx.graphics.getWidth() / 2 - exitButtonMenuInActive.getWidth() / 2);
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonMenuActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkExitButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
				Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(0).start(tween);
				Tween.to(exitButtonMenuActive, SpriteAccessor.ALPHA, 0.5f).target(1).repeatYoyo(0, 0f).setCallback(new TweenCallback() {

					@Override
					public void onEvent(int type, BaseTween<?> source) {
						
						((Game) Gdx.app.getApplicationListener()).setScreen(MainMenuScreen.getInstance(game));
					}
				}).start(tween);
				}
			}

		} else {
			checkExitButtonMouseOver = false;
			exitButtonMenuInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonMenuInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonMenuInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	private void volumeButton() {
		int x = PrisonEscapeGame.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 650;
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
			volumeButtonMuted.draw(game.getGameController().getSpriteBatch());
			music.pause();
			getMouseOverSound.stop();
		} else {

			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(game.getGameController().getSpriteBatch());
			music.play();
		}

	}

	private void helpScreenUI() {
		int x = PrisonEscapeGame.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 500;

		backButton(x);
		font.draw(game.getGameController().getSpriteBatch(), movementText, Gdx.graphics.getWidth() / 2 -200,
				Gdx.graphics.getHeight() / 2 + 100);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 ;

		wasdKeyboard.setPosition(x, WASD_Y);
		wasdKeyboard.setSize(WASD_WIDTH, WASD_HEIGHT);
		wasdKeyboard.draw(game.getGameController().getSpriteBatch());

		font.draw(game.getGameController().getSpriteBatch(), objectPickingText, Gdx.graphics.getWidth() / 2-200,
				Gdx.graphics.getHeight() / 2 - 100);

		x = PrisonEscapeGame.WIDTH / 2 - WASD_WIDTH / 2 + 30;

		eKeyboard.setPosition(x, E_Y);
		eKeyboard.setSize(E_WIDTH, E_HEIGHT);
		eKeyboard.draw(game.getGameController().getSpriteBatch());

	}

	private void backButton(int x) {

		if (Gdx.input.getX() < x + BACK_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y) {

			backButtonActive.setPosition(x, BACK_BUTTON_Y);
			backButtonActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkBackButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkBackButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Tween.set(resumeButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(remumeButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(helpButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(helpButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(exitButtonMenuActive, SpriteAccessor.ALPHA).target(1).start(tween);
				Tween.set(exitButtonMenuInActive, SpriteAccessor.ALPHA).target(1).start(tween);
				helpPressed = false;
				buttonActive = true;

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
		dispose();
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
