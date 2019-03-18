package prisonescape.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

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
	
	ActiveGame getMapScreen();
	
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