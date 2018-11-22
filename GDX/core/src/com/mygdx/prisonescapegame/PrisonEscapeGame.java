package com.mygdx.prisonescapegame;

import com.mygdx.game.entities.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class PrisonEscapeGame extends Game {

	public static final int WIDTH = 720;
	public static final int HEIGHT = 520;
	
	private GameController game;
	public Player player;
	
	@Override
	public void create() {
		this.player = new Player(new Sprite(new Texture(Gdx.files.internal("data/sprites/playerspriteR.png"))));
		this.game = new GameHandler(this);
	}

	@Override
	public void render() {
		super.render();		
	}
	
	public GameController getGameController() {
		return game;
	}

}
