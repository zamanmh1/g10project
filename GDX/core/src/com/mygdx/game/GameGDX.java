package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.map.Map;

public class GameGDX extends Game
{
	
	@Override
	public void create()
	{
		//this.setScreen(new Map());
		Map mapScreen = new Map(); //Creates the screen to render and display the map
		this.setScreen(mapScreen); //Sets the scene which displays map
	}
	
	@Override
	public void render()
	{
		super.render();
	}
	
	@Override
	public void pause()
	{
		super.pause();
	}
	
	@Override
	public void resume()
	{
		super.resume();
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
}
