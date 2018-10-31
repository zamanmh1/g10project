package com.mygdx.prisonescapegame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.prisonescapegame.screens.MainGameScreen;
import com.mygdx.prisonescapegame.screens.MainMenuScreen;

public class PrisonEscapeGame extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	
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
