package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720; //528
		config.height = 520; //768
		config.title = "Team 10: Hack";
		config.foregroundFPS = 60;
		new LwjglApplication(new PrisonEscapeGame(), config);
	}
}
