package prisonescape.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.PrisonBreakout;
import prisonescape.game.helpers.ItemHandler;
import prisonescape.game.helpers.MapHandler;
import prisonescape.game.helpers.NPCHandler;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.ActorAction;
import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.screens.Splash;
import prisonescape.game.util.Time;

/**
 * This class provides a concrete handler for the execution of a PrisonBreakout game.
 * 
 * @author Sam Ward, Shibu George, Sean Corcoran
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public class GameHandler implements GameController {

	/**
	 * The current game instance.
	 */
	private final PrisonBreakout game;
	
	/**
	 * The ActiveGame screen for the game.
	 */
	private ActiveGame currentMap;

	/**
	 * The MapHandler for this game instance.
	 */
	private MapHandler mapHandler;
	
	/**
	 * The ItemHandler for this game instance.
	 */
	private ItemHandler itemHandler;
	
	/**
	 * The NPCHandler for this game instance.
	 */
	private NPCHandler NPCsHandler;

	/**
	 * The SpriteBatch for drawing the game.
	 */
	private SpriteBatch batch;
	
	/**
	 * The current music of the game.
	 */
	private Music music;
	
	/**
	 * The collection of current actors in the game.
	 */
	private List<MapActor> actors;
	
	/**
	 * The collection of current actors with associated actions in the game.
	 */
	private HashMap<Actor, ActorAction> actions;
	
	/**
	 * The AlarmSystem of this game.
	 */
	private AlarmSystem alarm;
	
	/**
	 * Holds whether the game is waiting to restart.
	 */
	private boolean restarting;
	
	/**
	 * The current objective of the game.
	 */
	private String currentObjective;
	
	/**
	 * The current state of the game.
	 */
	private String gameState = "1";
	
	/**
	 * The sound to be played when the alarm is set.
	 */
	private Sound alarmSound;
	
	/**
	 * The current time in the game.
	 */
	private Time time;
	
	/**
	 * The sound to be played when the alarm is triggered.
	 */
	private Sound alarmBeep;

	/**
	 * Creates a new instance of the GameHandler class to control the executions of the given game.
	 * 
	 * @param game The game to handle the execution of.
	 */
	public GameHandler(PrisonBreakout game) {
		this.game = game;

		batch = new SpriteBatch();
		game.setScreen(new Splash(game));

		mapHandler = new MapHandler();
		itemHandler = new ItemHandler();
		NPCsHandler = new NPCHandler(this);
		alarmBeep = Gdx.audio.newSound(Gdx.files.internal("data/sounds/Alarm_beep.ogg"));
		alarm = new AlarmSystem(this);
		restarting = false;
		alarmSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/Alarm.ogg"));
		currentObjective = "";
		
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 5);
		this.time = Time.getTime(cal, GameSettings.TIME_SCALE);		
	}
	
	/**
	 * @return The current game state, in a string representation.
	 */
	public String getGameState() {
		return gameState;
	}
	
	/**
	 * Update the current game state.
	 * 
	 * @param newState The new state of the game.
	 */
	public void setGameState(String newState) {
		this.gameState = newState;
	}
	
	/**
	 * @return The current objective, in a string representation.
	 */
	public String getCurrentObjective() {
		return this.currentObjective;
	}
	
	/**
	 * Update the current objective.
	 * 
	 * @param newObjective The new objective.
	 */
	public void setCurrentObjective(String newObjective) {
		this.currentObjective = newObjective;
	}

	/**
	 * @return The game's sprite batch for drawing.
	 */
	@Override
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	/**
	 * @return The actor of the player.
	 */
	@Override
	public Actor getPlayer() {
		return game.player;
	}

	/**
	 * @return The current Time object for the game.
	 */
	@Override
	public Time getTime() {
		return this.time;
	}

	/**
	 * Change the current time within the game.
	 * 
	 * @param updatedTime The new time to be set to.
	 */
	@Override
	public void setTime(Time updatedTime) {
		this.time = updatedTime;
	}

	/**
	 * @return The current music playing.
	 */
	@Override
	public Music getMusic() {
		return this.music;

	}

	/**
	 * Set the music to be played within the game.
	 *  
	 * @param musicLoc The file path of the music to be played.
	 * @return The new music to be played.
	 */
	@Override
	public Music setMusic(String musicLoc) {
		return this.music = Gdx.audio.newMusic(Gdx.files.internal(musicLoc));
	}
	
	/**
	 * Begin playing music within the game.
	 */
	@Override
	public void playMusic() {

		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
	}

	/**
	 * Stop playing music within the game.
	 */
	@Override
	public void stopMusic() {
		music.stop();
		
	}
	
	/**
	 * Play the alarm sound.
	 */
	@Override
	public void playAlarmSound() {
		alarmSound.loop(1f);
	}
	
	/**
	 * Stop playing the alarm sound.
	 */
	@Override
	public void stopAlarmSound() {
		alarmSound.stop();
	}

	/**
	 * Set the current map being viewed and played on.
	 * 
	 * @param map The file path of the new map to be displayed.
	 * @param x The x coordinate to occupy in the new map.
	 * @param y The y coordinate to occupy in the new map.
	 */
	@Override
	public void setMap(String map, int x, int y) {
		// In initial call, player actor won't be in the map.
		// Catches null pointer without throwing run time error.
		// Creates new map screen on null pointer caught.
		try {
			actors.remove(getPlayer());
			getMapScreen().getTiledModel().getTile(getPlayer().getX(), getPlayer().getY()).setActor(null);
		} catch (NullPointerException e) {
			currentMap = new ActiveGame(getPlayer(), this.game);
		}

		actors = new ArrayList<MapActor>();
		actions = new HashMap<Actor, ActorAction>();

		actors.add(getPlayer());

		currentMap.setMap(map, this);
		getMapScreen().getTiledModel().getTile(x, y).setActor(getPlayer());

		mapHandler.setCurrentMap(map);
		mapHandler.initialiseTeleporters(getMapScreen().getTiledModel());

		getPlayer().teleport(x, y);

		for (Item i : itemHandler.getAllItems().values()) {
			if (i.getAppearsIn().equals(map)) {
				currentMap.addItemToMap(i);
				addActor(i);
			}
		}
		for (ActorAction action : NPCsHandler.getAllNPCs().values()) {
			if (action.getAppearsIn().equals(map)) {
				currentMap.addNPCToMap(action);
				addActor(action.getActor(), action);
			}
		}
	}

	/**
	 * Adds an actor to the game, allowing it to be handled appropriately.
	 * 
	 * @param a The actor to add.
	 */
	public void addActor(MapActor a) {
		currentMap.getTiledModel().getTile(a.getX(), a.getY()).setActor(a); // Add actor to tile in model (tile becomes
																			// unwalkable).
		actors.add(a); // Update list of actors in action.
	}

	/**
	 * Adds an actor to the game which has a behaviour associated with it,
	 * allowing it to be handled appropriately.
	 * 
	 * @param a The actor to add.
	 * @param action The behaviour associated with the actor.
	 */
	public void addActor(Actor a, ActorAction action) {
		addActor(a);
		actions.put(a, action);
	}

	/**
	 * Remove an actor from being handled within the game.
	 * 
	 * @param a The actor to remove.
	 */
	public void removeActor(MapActor a) {
		currentMap.getTiledModel().getTile(a.getX(), a.getY()).setActor(null); // Set tile in model to null (empty, can
																				// be walked in).
		actors.remove(a); // Remove actor from list of current actors.

		if (a instanceof Item) {
			currentMap.removeItemFromMap((Item) a); // Remove from being rendered in map if item object.
		}
		if (actions.containsKey(a)) {
			actions.remove(a);
		}
	}

	/**
	 * This method controls the execution of the game.
	 * 
	 * @param delta The time since the last frame was rendered.
	 */
	public void update(float delta) {
		if (restarting) {
			game.restartGame();
			restarting = false;
		}
		for (MapActor a : actors) {
			if (a instanceof Actor) {
				Actor actor = (Actor) a;
				if (actions.containsKey(actor)) {
					actions.get(actor).update(delta);
				}
				actor.update(delta);
			}
		}
		alarm.update();		
		setTime(Time.getTime(getTime().getCalendar(), GameSettings.TIME_SCALE));
	}

	/**
	 * @return The item handler associated with this game instance.
	 */
	@Override
	public ItemHandler getItemHandler() {
		return this.itemHandler;
	}

	/**
	 * @return The map handler associated with this game instance.
	 */
	public MapHandler getMapHandler() {
		return this.mapHandler;
	}

	/**
	 * @return The current ActiveGame screen in the game.
	 */
	public ActiveGame getMapScreen() {
		return this.currentMap;
	}

	/**
	 * @return The instance of the current game being played.
	 */
	public PrisonBreakout getGame() {
		return this.game;
	}

	/**
	 * @return The collection of actor's with their associated actions.
	 */
	public HashMap<Actor, ActorAction> getActions() {
		return this.actions;
	}
	
	/**
	 * @return The instance of the AlarmSystem associated with this game.
	 */
	public AlarmSystem getAlarm() {
		return this.alarm;
	}
	
	/**
	 * Restart the game.
	 */
	public void restartGame() {
		restarting = true;
		
	}
	
	/**
	 * Check's whether the game is waiting to restart.
	 * 
	 * @return True if wanting to restart, false otherwise.
	 */
	public boolean checkRestart() {
		return restarting;
	}

	/**
	 * Play an alarm beep.
	 */
	@Override
	public void playAlarmBeep() {
		alarmBeep.play();
	}
	
	/**
	 * Stop the alarm from beeping.
	 */
	@Override
	public void stopAlarmBeep() {
		alarmBeep.stop();
	}	
}
