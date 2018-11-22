package com.mygdx.game.entities;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector2;

/**
 * A simplified model of an Actor in the assumed TiledMap.
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public abstract class MapEntity implements MapActor {
	
	private Sprite sprite;
	//private Vector2 velocity = new Vector2();
	
	public MapEntity(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public float getXCell() {
		return getSprite().getX() / 16;
	}
	
	public float getYCell() {
		return getSprite().getY() / 16;
	}
	
	public void draw(Batch spriteBatch) {
		//update(Gdx.graphics.getDeltaTime());
		getSprite().draw(spriteBatch);
	}
	
	/*
	public void update(float delta) {
		getSprite().setY(getSprite().getY() + velocity.y * delta);
		getSprite().setX(getSprite().getX() + velocity.x * delta);
	}
	*/
}
