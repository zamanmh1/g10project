package com.mygdx.game.entities;

import com.mygdx.game.map.Map;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class Player extends MapEntity{
	
	private Map currentMap;
	private PlayerMover mover;

	public Player(Sprite sprite)
	{
		super(sprite);
		this.mover = new PlayerMover(this);
		this.currentMap = new Map(this);
	}

	public void setMap(String map, int x, int y) {
		currentMap.setMap(map);
		super.getSprite().setPosition(x, y);
	}

	public Map getCurrentMap() {
		return currentMap;
	}
	
	public PlayerMover getPlayerMover() {
		return mover;
	}

}