package prisonescape.game;

import prisonescape.game.model.actors.Actor;
import prisonescape.game.util.ActorAnimation;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * This class holds the logic for Prison Breakout.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public class PrisonBreakout extends Game {
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH =(int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();
	
	/**
	 * An asset manager to store the sprites associated with actor models.
	 */
	private AssetManager assetManager;	
	
	/**
	 * A controller to manage the execution of the game.
	 */
	private GameController controller;
	
	/**
	 * The actor of the player playing the game.
	 */
	protected Actor player;
	
	/**
	 * Creates and runs the application.
	 */
	@Override
	public void create() {
		// Store texture atlas containing player textures and animations in an AssetManager.
		assetManager = new AssetManager();
		assetManager.load("data/packed/textures.atlas", TextureAtlas.class);
		assetManager.finishLoading();
		
		// Perform initial setup of game.
		setupGame();
	}

	/**
	 * Used to render the application.
	 */
	@Override
	public void render() {
		super.render();		
	}
	
	/**
	 * Creates the starting state for a new Prison Breakout game.
	 */
	private void setupGame() {	
		// Finds the texture atlas containing the player's actor textures.
		TextureAtlas atlas = getAssetManager().get("data/packed/textures.atlas", TextureAtlas.class);	
		
		// Stores player's actor animations in new AnimationSet.
		ActorAnimation animations = new ActorAnimation(
				new Animation<Object>(0.3f/2f, atlas.findRegions("player01_walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions("player01_walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions("player01_walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions("player01_walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("player01_stand_north"),
				atlas.findRegion("player01_stand_south"),
				atlas.findRegion("player01_stand_east"),
				atlas.findRegion("player01_stand_west")
		);
		
		// Creates a new controller for this game.
		this.controller = new GameHandler(this);
		// Creates a new actor for the player to play as in this game.
		this.player = new Actor(1, 2, animations, controller);
		// Sets the map to the starting map and sets player location within it. 
		controller.setMap("data/maps/cell.tmx", player.getX(), player.getY());
		// Sets music to the game background music and plays it.
		controller.setMusic("data/sounds/BackgroundSound.mp3");
		controller.playMusic();		
	}
	
	/**
	 * Discards the current game and its associated state.
	 */
	private void discardGame() {
		controller = null;
		player = null;
	}
	
	/**
	 * Discards the current game and restarts with a fresh, new game.
	 */
	public void restartGame() {
		discardGame();
		setupGame();
	}
	
	/**
	 * Retrieve the current controller of the game.
	 * 
	 * @return GameController
	 */
	public GameController getGameController() {
		return controller;
	}
	
	/**
	 * Retrieve the AssetManager of the game.
	 * 
	 * @return AssetManager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
