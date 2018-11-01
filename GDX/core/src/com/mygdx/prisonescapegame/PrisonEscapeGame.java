package com.mygdx.prisonescapegame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.prisonescapegame.screens.MainMenuScreen;

public class PrisonEscapeGame extends Game {

	public static final int WIDTH = 528;
	public static final int HEIGHT = 768;
	
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render() {
		super.render();

	}

}
