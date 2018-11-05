package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements InputProcessor
{
	
	private Vector2 velocity = new Vector2();
	private float speed = 8 * 2f;
	private float oldX;
	private float oldY;
	private float tileWidth;
	private float tileHeight;
	private boolean collisionX;
	private boolean collisionY;
	private String colProp = "blocked";
	private MapObjects objects;
	private MapLayer collisionObjectLayer;
	private float tempCX;
	private float tempCY;
	private Sprite playerSprite;
	
	
	public Player(Sprite sprite)
	{
		this.playerSprite = sprite;
		//this.collisionLayer = collisionLayer;
	
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
		
		oldX = playerSprite.getX();
		oldY = playerSprite.getY();
		collisionX = false;
		collisionY = false;
		
		if(oldX < playerSprite.getX())
		{
			//moved to the right
		}
		else if(oldX > playerSprite.getX())
		{
			//moved to the left
		}
		
		if(oldY < playerSprite.getY())
		{
			//moved down
		}
		else if(oldY > playerSprite.getY())
		{
			//moved up
		}
		
	}
	
	private float getCurrentCellX() //gets current cell by taking x value / tile width to get the cell value
	{
		return playerSprite.getX() / 16;
	}
	
	private float getCurrentCellY()
	{
		return playerSprite.getY() / 16;
	}
	
	/*private boolean cellBlocked(float x, float y)
	{
		Cell cell = collisionLayer.getCell((int) x, (int) y);
		return cell.getTile().getProperties().containsKey(colProp);
	} */

	/*public boolean collisionUp()
	{
		float i = getCurrentCellY() - 1;
		for(i = i; i <= i + 2; i++)
		{
			tempCY = i;
			tempCX = getCurrentCellX();
			if(cellBlocked(tempCX, tempCY))
			{
				System.out.println("" + tempCY + " " + tempCX);
				return true;
			}
		}
		return false;
	}*/

	
	@Override
	public boolean keyDown(int keycode) //If key is held down
	{			
		float oldX1 = playerSprite.getX();
		float oldY1 = playerSprite.getY();
		
		switch(keycode) {
		case Keys.W:
			//velocity.y = speed;
			playerSprite.setY(playerSprite.getY() + 16);
			if (checkCollision(keycode)) {
				playerSprite.setY(oldY1);
			}
			break;
		case Keys.S:
			//velocity.y = -speed;
			playerSprite.setY(playerSprite.getY() - 16);
			if (checkCollision(keycode)) {
				playerSprite.setY(oldY1);
			}
			break;
		case Keys.A:
			//velocity.x = -speed;
			playerSprite.setX(playerSprite.getX() - 16);
			if (checkCollision(keycode)) {
				playerSprite.setX(oldX1);
			}
			break;
		case Keys.D:
			//velocity.x = speed;
			playerSprite.setX(playerSprite.getX() + 16);
			if (checkCollision(keycode)) {
				playerSprite.setX(oldX1);
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) 
	{
		if(keycode == Keys.W || keycode == Keys.S)
		{
			velocity.y = 0;
		}
		if(keycode == Keys.A || keycode == Keys.D)
		{
			velocity.x = 0;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Sprite getPlayerSprite() {
		return playerSprite;
	}
	
	public void collisionSetUp(TiledMap tilemap) {
			int objectLayerID = 1;
			//collisionObjectLayer = tilemap.getLayers().get(objectLayerID);
			int layerID = tilemap.getLayers().getIndex("Collision");
			collisionObjectLayer = tilemap.getLayers().get(layerID);

			objects = collisionObjectLayer.getObjects();
	}
	
	public boolean checkCollision(int keycode) {
		
		/*switch(keycode) {
		case Keys.W:
			playerSprite.setY(playerSprite.getY() + 16);
			break;
		case Keys.S:
			playerSprite.setY(playerSprite.getY() - 16);
			break;
		case Keys.A:
			playerSprite.setX(playerSprite.getX() - 16);
			break;
		case Keys.D:
			playerSprite.setX(playerSprite.getX() + 16);
		}*/
		
		for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(Intersector.overlaps(rectangle, playerSprite.getBoundingRectangle())) {
				System.out.println("Collision occured");					
				return true;
			}
		}	
		return false;
	}
	
	/*
	int objectLayerId = 5;
	TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get(objectLayerId);
	MapObjects objects = collisionObjectLayer.getObjects();

	// there are several other types, Rectangle is probably the most common one
	for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

	    Rectangle rectangle = rectangleObject.getRectangle();
	    if (Intersector.overlaps(rectangle, player.getRectangle()) {
	        // collision happened
	    }
	}	
	*/
}


/** For collision, in the hack map, I have a number of different collision methods we can try. There's the object collision
 * which is handled in the **.tsx** file and looks like is controlled with **objectID=1**.
 * 
 * I've added a collision layer with a number of rectangles defining that boundary. Could try using that.
 * 
 * There's also the possibility of using Box2D (part of libGDX)
 * 
 * Finally I have all the walls, door, floors that should be blocked with the custom property **blocked** 
 * We can use that property, check if the tile has that property, if so, disallow movement.
 */
