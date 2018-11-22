package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A simplified representation of an Actor in the assumed TiledMap.
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public interface MapActor {

	
	/**
	 * The methods which MapActor objects must implement.
	 */
	abstract Sprite getSprite();
	abstract float getXCell();
	abstract float getYCell();
	abstract void draw(Batch spriteBatch);
	//public abstract void update(float delta);
	
}
