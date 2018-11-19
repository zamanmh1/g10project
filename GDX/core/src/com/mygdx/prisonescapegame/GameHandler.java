package com.mygdx.prisonescapegame;

import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.game.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
	private SpriteBatch batch;
	private Sound sound;
	public GameHandler(PrisonEscapeGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.setScreen(new Splash(game));
		sound = Gdx.audio.newSound(Gdx.files.internal("data/BackgroundSound.mp3"));
		sound.play(1.0f);
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
