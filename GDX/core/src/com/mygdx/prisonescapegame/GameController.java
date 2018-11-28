package com.mygdx.prisonescapegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.MapActor;

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
	
	void setMap(String map);
	
	void addActor(MapActor a);
	
	void removeActor(MapActor a);
	
}