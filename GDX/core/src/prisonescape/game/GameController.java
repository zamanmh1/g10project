package prisonescape.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.helpers.ItemHandler;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

/**
 * This class provides an interface for the handling of a PrisonBreakout game.
 * 
 * @author Sam Ward, Shibu George
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public interface GameController {

	/**
	 * @return The game's sprite batch for drawing.
	 */
	SpriteBatch getSpriteBatch();
	
	/**
	 * @return The actor of the player.
	 */
	Actor getPlayer();
		
	/**
	 * @return The current music playing.
	 */
	Music getMusic();
	
	/**
	 * Set the music to be played within the game.
	 *  
	 * @param musicLoc The file path of the music to be played.
	 * @return The new music to be played.
	 */
	Music setMusic(String musicLoc);
	
	/**
	 * Begin playing music within the game.
	 */
	void playMusic();
	
	/**
	 * Stop playing music within the game.
	 */
	void stopMusic();
	
	/**
	 * Play the alarm sound.
	 */
	void playAlarmSound();
	
	/**
	 * Stop playing the alarm sound.
	 */
	void stopAlarmSound();
	
	/**
	 * Adds an actor to the game, allowing it to be handled appropriately.
	 * 
	 * @param a The actor to add.
	 */
	void addActor(MapActor a);
	
	/**
	 * Remove an actor from being handled within the game.
	 * 
	 * @param a The actor to remove.
	 */
	void removeActor(MapActor a);

	/**
	 * Set the current map being viewed and played on.
	 * 
	 * @param map The file path of the new map to be displayed.
	 * @param x The x coordinate to occupy in the new map.
	 * @param y The y coordinate to occupy in the new map.
	 */
	void setMap(String map, int x, int y);
	
	/**
	 * @return The current ActiveGame screen in the game.
	 */
	ActiveGame getMapScreen();
	
	/**
	 * @return The instance of the current game being played.
	 */
	PrisonBreakout getGame();
	
	/**
	 * This method controls the execution of the game.
	 * 
	 * @param delta The time since the last frame was rendered.
	 */
	void update(float delta);
	
	/**
	 * @return The current Time object for the game.
	 */
	Time getTime();
	
	/**
	 * Change the current time within the game.
	 * 
	 * @param updatedTime The new time to be set to.
	 */
	void setTime(Time updatedTime);
	
	/**
	 * @return The instance of the AlarmSystem associated with this game.
	 */
	AlarmSystem getAlarm();
	
	/**
	 * Restart the game.
	 */
	void restartGame();
	
	/**
	 * @return The current game state, in a string representation.
	 */
	String getGameState();
	
	/**
	 * @return The current objective, in a string representation.
	 */
	String getCurrentObjective();
	
	/**
	 * Update the current game state.
	 * 
	 * @param newState The new state of the game.
	 */
	void setGameState(String newState);
	
	/**
	 * Update the current objective.
	 * 
	 * @param newObjective The new objective.
	 */
	void setCurrentObjective(String newObjective);

	/**
	 * Play an alarm beep.
	 */
	void playAlarmBeep();

	/**
	 * Stop the alarm from beeping.
	 */
	void stopAlarmBeep();
	
	/**
	 * @return The item handler associated with this game instance.
	 */
	ItemHandler getItemHandler();

}