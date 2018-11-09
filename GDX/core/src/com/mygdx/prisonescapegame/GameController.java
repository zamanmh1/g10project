package com.mygdx.prisonescapegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Player;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public interface GameController {

	SpriteBatch getSpriteBatch();
	
	Player getPlayer();
	
	void setMap(String map);
	
}