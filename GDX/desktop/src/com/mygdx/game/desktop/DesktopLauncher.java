package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 528; //172
		config.height = 768; //256
		config.title = "Team 10: Hack";
		config.foregroundFPS = 60;
		new LwjglApplication(new PrisonEscapeGame(), config);
	}
}
