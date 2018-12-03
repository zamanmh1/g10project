package com.mygdx.game.desktop;

<<<<<<< HEAD
=======
import java.awt.Dimension;
import java.awt.Toolkit;

>>>>>>> 9bfc1867090dcb01f3a9096d47bb4831a8a80f41
import com.badlogic.gdx.Gdx;
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
<<<<<<< HEAD
		config.width = 1920; //528
		config.height = 1080; //768
=======
		config.width = (int) screenSize.getWidth(); //528
		config.height = (int) screenSize.getHeight(); //768
>>>>>>> 9bfc1867090dcb01f3a9096d47bb4831a8a80f41
		config.fullscreen = true;
		config.title = "Team 10: Hack";
		config.foregroundFPS = 60;
		new LwjglApplication(new PrisonEscapeGame(), config);
	}
}
