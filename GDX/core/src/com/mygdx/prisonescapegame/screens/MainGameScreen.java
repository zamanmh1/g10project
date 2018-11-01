package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.map.Map;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class MainGameScreen implements Screen {

	public static final float SPEED = 120;

	Texture img;
	float x;
	float y;

	PrisonEscapeGame game;

	public MainGameScreen(PrisonEscapeGame game) {
		this.game = game;

	}

	@Override
	public void show() {
	
		Map mapScreen = new Map(); //Creates the screen to render and display the map
		game.setScreen(mapScreen); //Sets the scene which displays map
	}

	public void render()
	{
		
	}
	
	@Override
	public void pause()
	{
		
	}
	
	@Override
	public void resume()
	{
		
	}
	
	@Override
	public void resize(int width, int height)
	{
		resize(width, height);
	}
	
	@Override
	public void dispose()
	{
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
