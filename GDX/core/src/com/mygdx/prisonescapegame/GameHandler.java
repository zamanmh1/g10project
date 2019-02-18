package com.mygdx.prisonescapegame;

import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.helpers.ItemHandler;
import com.mygdx.game.helpers.MapHandler;
import com.mygdx.game.helpers.NPCHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.prisonescapegame.screens.MapScreen;
import com.mygdx.prisonescapegame.screens.Splash;

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
	
	private final PrisonEscapeGame game;
	private MapScreen currentMap;

	private MapHandler mapHandler;
	private ItemHandler itemHandler;
	private NPCHandler NPCsHandler;
	
	private SpriteBatch batch;
	private Music music;
	private List<MapActor> actors;
	private HashMap<Actor, ActorAction> actions;
	
	private String currentMapFile;
	
	public GameHandler(PrisonEscapeGame game) {
		this.game = game;	
		
		batch = new SpriteBatch();
		game.setScreen(new Splash(game));

		mapHandler = new MapHandler();
		itemHandler = new ItemHandler();
		NPCsHandler = new NPCHandler(this);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/BackgroundSound.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
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
	public Music getMusic() {
		return this.music;
	}
	
	@Override
	public void setMap(String map, int x, int y) {
		// In initial call, player actor won't be in the map.
		// Catches null pointer without throwing run time error.
		// Creates new map screen on null pointer caught.
		try {
			actors.remove(getPlayer());
			getMapScreen().getTiledModel().getTile(getPlayer().getX(), getPlayer().getY()).setActor(null);
		} catch  (NullPointerException e) {	
			currentMap = new MapScreen(getPlayer(), this.game);
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
		currentMap.getTiledModel().getTile(a.getX(), a.getY()).setActor(a); // Add actor to tile in model (tile becomes unwalkable).
		actors.add(a); // Update list of actors in action.
	}
	
	public void addActor(Actor a, ActorAction action) {
		addActor(a);
		actions.put(a, action);
	}
	
	public void removeActor(MapActor a) {
		currentMap.getTiledModel().getTile(a.getX(), a.getY()).setActor(null); // Set tile in model to null (empty, can be walked in).
		actors.remove(a); // Remove actor from list of current actors.
		
		if (a instanceof Item) {
			currentMap.removeItemFromMap((Item) a); // Remove from being rendered in map if item object.
		}
		if(actions.containsKey(a)) {
			actions.remove(a);
		}
	}
	
	public void update(float delta) {
		for (MapActor a: actors) {
			if(a instanceof Actor) {
				Actor actor = (Actor) a;
				if(actions.containsKey(actor)) {
					actions.get(actor).update(delta);
				}
				actor.update(delta);
			}
		}
	}
	
	public ItemHandler getItemHandler() {
		return this.itemHandler;
	}
	
	public MapHandler getMapHandler() {
		return this.mapHandler;
	}
	
	public MapScreen getMapScreen() {
		return this.currentMap;
	}
	
	public PrisonEscapeGame getGame() {
		return this.game;
	}
	
	public HashMap<Actor, ActorAction> getActions(){
		return this.actions;
	}
	
	public String getCurrentMapFile() {
		return this.currentMapFile;
	}
}
