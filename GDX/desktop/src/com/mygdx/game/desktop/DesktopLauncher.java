package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PrisonEscapeGame.WIDTH;
		config.height = PrisonEscapeGame.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new PrisonEscapeGame(), config);
	}
}
