package com.mygdx.prisonescapegame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.prisonescapegame.screens.Splash;

public class PrisonEscapeGame extends Game {

	public static final int WIDTH = 528;
	public static final int HEIGHT = 768;
	
	private SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new Splash(this));
	}

	@Override
	public void render() {
		super.render();		
	}
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

}
