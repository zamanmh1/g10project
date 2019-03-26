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
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class GameHandler implements GameController {

	private final PrisonBreakout game;
	private ActiveGame currentMap;

	private MapHandler mapHandler;
	private ItemHandler itemHandler;
	private NPCHandler NPCsHandler;

	private SpriteBatch batch;
	private Music music;
	private List<MapActor> actors;
	private HashMap<Actor, ActorAction> actions;
	
	private AlarmSystem alarm;
	private boolean restarting;
	
	private String currentObjective;
	private String gameState = "2";
	private Sound alarmSound;
	private Time time;
	private Sound alarm_beep;

	public GameHandler(PrisonBreakout game) {
		this.game = game;

		batch = new SpriteBatch();
		game.setScreen(new Splash(game));

		mapHandler = new MapHandler();
		itemHandler = new ItemHandler();
		NPCsHandler = new NPCHandler(this);
		alarm_beep = Gdx.audio.newSound(Gdx.files.internal("data/sounds/Alarm_beep.ogg"));
		alarm = new AlarmSystem(this);
		restarting = false;
		alarmSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/Alarm.ogg"));
		currentObjective = "";
		
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		this.time = Time.getTime(cal, GameSettings.TIME_SCALE);
		
		
	}
	
	public String getGameState() {
		return gameState;
	}
	
	public void setGameState(String newState) {
		this.gameState = newState;
	}
	
	public String getCurrentObjective() {
		return this.currentObjective;
	}
	
	public void setCurrentObjective(String newObjective) {
		this.currentObjective = newObjective;
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public Actor getPlayer() {
		return game.player;
	}

	@Override
	public Time getTime() {
		return this.time;
	}

	@Override
	public void setTime(Time updatedTime) {
		this.time = updatedTime;
	}

	@Override
	public Music getMusic() {
		return this.music;

	}

	@Override
	public Music setMusic(String musicLoc) {
		return this.music = Gdx.audio.newMusic(Gdx.files.internal(musicLoc));
	}
	
	@Override
	public void playMusic() {

		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
	}

	@Override
	public void stopMusic() {
		music.stop();
		
	}
	
	@Override
	public void playAlarmSound() {
		//alarmSound.play(1f);
		alarmSound.loop(1f);
	}
	@Override
	public void stopAlarmSound() {
		alarmSound.stop();
	}

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

	public void addActor(MapActor a) {
		currentMap.getTiledModel().getTile(a.getX(), a.getY()).setActor(a); // Add actor to tile in model (tile becomes
																			// unwalkable).
		actors.add(a); // Update list of actors in action.
	}

	public void addActor(Actor a, ActorAction action) {
		addActor(a);
		actions.put(a, action);
	}

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

	@Override
	public ItemHandler getItemHandler() {
		return this.itemHandler;
	}

	public MapHandler getMapHandler() {
		return this.mapHandler;
	}

	public ActiveGame getMapScreen() {
		return this.currentMap;
	}

	public PrisonBreakout getGame() {
		return this.game;
	}

	public HashMap<Actor, ActorAction> getActions() {
		return this.actions;
	}
	
	public AlarmSystem getAlarm() {
		return this.alarm;
	}
	
	public void restartGame() {
		restarting = true;
		
	}
	
	public boolean checkRestart() {
		return restarting;
	}

	@Override
	public void playAlarmBeep() {

		alarm_beep.play();
		

	}
	
	@Override
	public void stopAlarmBeep() {

		alarm_beep.stop();

	}

	
}
