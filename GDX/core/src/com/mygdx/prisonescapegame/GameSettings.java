package com.mygdx.prisonescapegame;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class GameSettings {
	public static int TILE_SIZE = 16;
	private static String gameState = "1";

	public static String getGameState()
	{
		return gameState;
	}
	
	public static void setGameState(String newState)
	{
		gameState = newState;
	}

}
