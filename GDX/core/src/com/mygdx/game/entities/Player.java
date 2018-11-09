package com.mygdx.game.entities;

import com.mygdx.game.map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class Player {
	private Map currentMap;
	private Sprite playerSprite;
	private PlayerMover mover;

	private Vector2 velocity = new Vector2();

	public Player(Sprite sprite)
	{
		this.playerSprite = sprite;
		this.mover = new PlayerMover(this);
		this.currentMap = new Map(this);
	}

	public void setMap(String map, int x, int y) {
		currentMap.setMap(map);
		playerSprite.setPosition(x, y);
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public float getXCell() //gets current cell by taking x value / tile width to get the cell value
	{
		return playerSprite.getX() / 16;
	}

	public float getYCell()
	{
		return playerSprite.getY() / 16;
	}
	
	public PlayerMover getPlayerMover() {
		return mover;
	}

	public Sprite getPlayerSprite() {
		return playerSprite;
	}

	public void draw(Batch spriteBatch)
	{
		update(Gdx.graphics.getDeltaTime());
		playerSprite.draw(spriteBatch);
	}

	public void update(float delta)
	{
		playerSprite.setY(playerSprite.getY() + velocity.y * delta);
		playerSprite.setX(playerSprite.getX() + velocity.x * delta);
	}

}