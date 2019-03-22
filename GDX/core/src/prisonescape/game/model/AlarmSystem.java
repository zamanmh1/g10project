package prisonescape.game.model;

import java.util.Calendar;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import prisonescape.game.GameHandler;
import prisonescape.game.GameSettings;
import prisonescape.game.io.DialogueUI;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.DIRECTION;
import prisonescape.game.model.actors.GuardChasingBehaviour;
import prisonescape.game.screens.MainMenu;
import prisonescape.game.util.ActorAnimation;
import prisonescape.game.util.Time;

/**
 * This class holds the logic of the game's alarm system.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class AlarmSystem {

	/**
	 * The time after triggering the alarm that a guard will spawn.
	 */
	private static long guardSpawnTime = 240000 / GameSettings.TIME_SCALE; // 4 Minutes.

	/**
	 * The time after triggering the alarm that the guard will search until.
	 */
	private static long guardLookTime = guardSpawnTime + (900000 / GameSettings.TIME_SCALE); // 15 Minutes.

	/**
	 * Tracks how long the alarm has been triggered for.
	 */
	private long alarmStarted;

	/**
	 * The current state of the game.
	 */
	private GameHandler controller;

	/**
	 * The guard who will be searching for the player.
	 */
	private GuardChasingBehaviour guard;

	/**
	 * The location in which the player was spotted triggering the alarm.
	 */
	private GridPoint2 playerSpottedLoc;

	/**
	 * Whether or not the guard has caught the player.
	 */
	private boolean playerCaught;

	/**
	 * Creation of the alarm system.
	 * 
	 * @param controller The current game state.
	 */
	public AlarmSystem(GameHandler controller) {
		this.alarmStarted = 0L;
		this.controller = controller;
		this.guard = null;

	}

	/**
	 * Set whether the alarm has been triggered.
	 * 
	 * @param alarm True (on) or false (off).
	 */
	public void setAlarm(boolean alarm) {
		if (alarm == true) {
			startAlarm();

		} else {
			resetAlarm();
		}
	}

	/**
	 * The alarm has been triggered.
	 */
	private void startAlarm() {
		alarmStarted = System.currentTimeMillis();
		controller.stopAlarmBeep();
		controller.playAlarmSound();
	}

	/*
	 * The triggered alarm has finished it's execution.
	 */
	private void resetAlarm() {
		// Remove guard from being present.
		controller.removeActor(guard.getActor());
		controller.getMapScreen().removeNPCFromMap(guard);
		guard = null;

		// Reset alarm state.
		alarmStarted = 0L;
		playerSpottedLoc = null;
		controller.stopAlarmSound();

		// If ended through the player being caught by the guard.
		if (playerCaught == true) {
			// Set time to next morning.
			Calendar cal = controller.getTime().getCalendar();
			Time time = Time.getTime(cal);
			time = Time.setTime(cal, 7, 15);
			controller.setTime(time);

			// Move player to cell and unfreeze.

			controller.setMap("data/maps/cell.tmx", 3, 1); 		    	 	   
	    	controller.getPlayer().setFrozen(false);
	    	controller.getPlayer().changeFacing(DIRECTION.NORTH);  
	    	
			// Displays message to player through dialogue box when wakes up in cell.
	    	DialogueUI dUI = new DialogueUI(controller);
	    	dUI.showDialogue(controller.getMapScreen().getStage(), "guard");
		} 


	}

	/**
	 * Spawn a guard who will chase the player.
	 */
	private void spawnGuard() {
		// Find the atlas containing the textures for the guard.
		TextureAtlas atlas = controller.getGame().getAssetManager().get("data/packed/textures.atlas",
				TextureAtlas.class);
		// The prefeix for the guard's textures.
		String guardSprite = "guard01_";
		// The animations of the guard.
		ActorAnimation animations = new ActorAnimation(
				new Animation<Object>(0.3f / 2f, atlas.findRegions(guardSprite + "walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f / 2f, atlas.findRegions(guardSprite + "walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f / 2f, atlas.findRegions(guardSprite + "walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f / 2f, atlas.findRegions(guardSprite + "walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion(guardSprite + "stand_north"), atlas.findRegion(guardSprite + "stand_south"),
				atlas.findRegion(guardSprite + "stand_east"), atlas.findRegion(guardSprite + "stand_west"));

		// Initialise a new guard, using the locations in which the player was spotted
		// triggering the alarm.
		guard = new GuardChasingBehaviour("alarmGuard", controller.getMapHandler().getCurrentMap(),
				new Actor(playerSpottedLoc.x, playerSpottedLoc.y, animations, controller), controller.getPlayer(),
				controller);

		// Add the guard to the controller, allowing its state to be updated
		// automatically.
		controller.addActor(guard.getActor(), guard);
		// Add the guard to be rendered on the screen.
		controller.getMapScreen().addNPCToMap(guard);

		// Player not yet caught.
		playerCaught = false;
	}

	/**
	 * This method controls the execution of the alarm system.
	 */
	public void update() {
		// If the alarm has been triggered.
		if (alarmTriggered()) {
			// Time passed since alarm has been triggered.
			long timePassed = System.currentTimeMillis() - alarmStarted;

			// If it is time for a guard to spawn and has not yet.
			if (timePassed > guardSpawnTime && guard == null) {
				// Spawn a guard to chase the player.
				spawnGuard();
			}
			// If the guard has searched for the whole duration of the search time.
			// Or if the player has been caught.
			else if (timePassed > guardLookTime || (guard != null && playerCaught == true)) {
				// Deactivate the alarm.
				setAlarm(false);
			}
		}
		// If the alarm hasn't yet been triggered.
		else if (alarmTriggered() == false) {
			// Find player coordinates.
			int playerX = controller.getPlayer().getX();
			int playerY = controller.getPlayer().getY();
			
			Time t = controller.getTime();
			
			
			if ((t.getHour() == GameSettings.HOUR_DAY_BEGINS) || (t.getHour() == GameSettings.HOUR_NIGHT_BEGINS)) {
				if (t.getMin() == 0) {
					controller.playAlarmBeep();
				}
			}
			
			// If it is night time and player moved into an alarm tile then trigger alarm.
			if (!controller.getTime().isDay()
					&& controller.getMapScreen().getTiledModel().getTile(playerX, playerY).getAlarm()) {
				// Player spotted location is player's current location.
				playerSpottedLoc = new GridPoint2(playerX, playerY);
				// Trigger the alarm system.
				setAlarm(true);
			}
		}
	}

	/**
	 * Set that the player has been caught.
	 */
	public void playerCaught() {
		playerCaught = true;
	}

	/**
	 * Finds whether the alarm has been triggered.
	 * 
	 * @return True if alarm triggered, else false.
	 */
	public boolean alarmTriggered() {
		return alarmStarted != 0L; // Checks if the alarm has started or if it is yet to be started.
	}
}
