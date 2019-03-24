package prisonescape.game.desktop;


import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import prisonescape.game.PrisonBreakout;

/**
 * This class launches the desktop version of Prison Breakout.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public class DesktopLauncher 
{
	public static void main (String[] args) 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) screenSize.getWidth();
		config.height = (int) screenSize.getHeight();
		config.fullscreen = true;
		config.title = "Prison Breakout!";
		config.foregroundFPS = 60;
		config.addIcon("data/icon.png", Files.FileType.Internal);
		
		new LwjglApplication(new PrisonBreakout(), config);
	}
}
