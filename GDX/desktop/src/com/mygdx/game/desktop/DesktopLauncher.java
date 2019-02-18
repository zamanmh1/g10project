package com.mygdx.game.desktop;


import java.awt.Dimension;
import java.awt.Toolkit;

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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = (int) screenSize.getWidth(); //528
		config.height = (int) screenSize.getHeight(); //768

		config.fullscreen = true;
		config.title = "Prison Breakout Team 10: Final";
		config.foregroundFPS = 60;
		new LwjglApplication(new PrisonEscapeGame(), config);
	}
}
