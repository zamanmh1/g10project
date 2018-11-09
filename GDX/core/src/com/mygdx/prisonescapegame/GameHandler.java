package com.mygdx.prisonescapegame;

import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.game.entities.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.prisonescapegame.screens.Splash;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class GameHandler implements GameController {
	
	private final PrisonEscapeGame game;
	private SpriteBatch batch;

	public GameHandler(PrisonEscapeGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.setScreen(new Splash(game));
	}
	
	@Override
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public Player getPlayer() {
		return game.player;
	}

	@Override
	public void setMap(String map) {
		getPlayer().setMap(map, 80, 64);
	}
	
	

}
