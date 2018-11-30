package com.mygdx.prisonescapegame;

import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.helpers.ItemHandler;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	private ItemHandler iHandler;
	private SpriteBatch batch;
	private Music music;
	
	private List<MapActor> actors;
	
	public GameHandler(PrisonEscapeGame game) {
		this.game = game;	
		
		batch = new SpriteBatch();
		game.setScreen(new Splash(game));
		iHandler = new ItemHandler();
		setMap("data/playerSprites/hack.tmx");

		music = Gdx.audio.newMusic(Gdx.files.internal("data/BackgroundSound.mp3"));
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
	public void setMap(String map) {
		actors = new ArrayList<MapActor>();
		actors.add(getPlayer());
		
		getPlayer().setMap(map, this);
		
		for (Item i : iHandler.getAllItems().values()) {
			if (i.getAppearsIn().equals(map)) {
				getPlayer().getCurrentMap().addItemToMap(i);
				addActor(i);
			}
		}
	}	
	
	public void addActor(MapActor a) {
		getPlayer().getCurrentMap().getTiledModel().getTile(a.getX(), a.getY()).setActor(a); // Add actor to tile in model (tile becomes unwalkable).
		actors.add(a); // Update list of actors in action.
	}
	
	public void removeActor(MapActor a) {
		getPlayer().getCurrentMap().getTiledModel().getTile(a.getX(), a.getY()).setActor(null); // Set tile in model to null (empty, can be walked in).
		actors.remove(a); // Remove actor from list of current actors.
		
		if (a instanceof Item) {
			getPlayer().getCurrentMap().removeItemFromMap((Item) a); // Remove from being rendered in map if item object.
		}
	}
	
	public ItemHandler getItemHandler() {
		return this.iHandler;
	}
}
