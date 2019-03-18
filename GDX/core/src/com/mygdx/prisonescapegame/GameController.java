package com.mygdx.prisonescapegame;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.AlarmSystem;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.util.Time;
import com.mygdx.prisonescapegame.screens.MapScreen;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public interface GameController {

	SpriteBatch getSpriteBatch();
	
	Actor getPlayer();
		
	Music getMusic();
	
	Music setMusic(String musicLoc);
	
	
	void playMusic();
	
	void stopMusic();
	
	void addActor(MapActor a);
	
	void removeActor(MapActor a);

	void setMap(String map, int x, int y);
	
	MapScreen getMapScreen();
	
	PrisonEscapeGame getGame();
	
	void update(float delta);
	
	Time getTime();
	
	void setTime(Time updatedTime);
	
	AlarmSystem getAlarm();
	
	void restartGame();
	
	String getGameState();
	
	String getCurrentObjective();
	
	void setGameState(String newState);
	
	void setCurrentObjective(String newObjective);

}