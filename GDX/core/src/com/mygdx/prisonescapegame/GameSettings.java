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
	public static final int TIME_SCALE = 20;
	public static final int TILE_SIZE = 32;
	public static final int HOUR_DAY_BEGINS = 7;
	public static final int HOUR_NIGHT_BEGINS = 22;
	
	public static String currentObjective = "";
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
