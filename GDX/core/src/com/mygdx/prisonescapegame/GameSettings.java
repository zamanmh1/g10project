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
	// !!! UPDATE TIME SCALE
	public static int TIME_SCALE = 20;
	public static int TILE_SIZE = 32;
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
