package com.mygdx.prisonescapegame;

import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.game.entities.Player;
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
	private SpriteBatch batch;
	private Music music;
	public GameHandler(PrisonEscapeGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.setScreen(new Splash(game));
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
	public Player getPlayer() {
		return game.player;
	}

	@Override
	public void setMap(String map) {
		getPlayer().setMap(map, 80, 64);
	}
	

	
	

}
