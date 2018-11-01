package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor
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
	private TiledMapTileLayer collisionLayer;
	private float tempCX;
	private float tempCY;
	
	
	public Player(Sprite pSprite)
	{
		super(pSprite);
		//this.collisionLayer = collisionLayer;
		
	}
	
	@Override
	public void draw(Batch spriteBatch)
	{
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
		
	}
	
	public void update(float delta)
	{
		setY(getY() + velocity.y * delta);
		setX(getX() + velocity.x * delta);
		
		oldX = getX();
		oldY = getY();
		collisionX = false;
		collisionY = false;
		
		if(oldX < getX())
		{
			//moved to the right
		}
		else if(oldX > getX())
		{
			//moved to the left
		}
		
		if(oldY < getY())
		{
			//moved down
		}
		else if(oldY > getY())
		{
			//moved up
		}
		
	}
	
	private float getCurrentCellX() //gets current cell by taking x value / tile width to get the cell value
	{
		return getX() / 16;
	}
	
	private float getCurrentCellY()
	{
		return getY() / 16;
	}
	
	private boolean cellBlocked(float x, float y)
	{
		Cell cell = collisionLayer.getCell((int) x, (int) y);
		return cell.getTile().getProperties().containsKey(colProp);
	}

	public boolean collisionUp()
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
	}

	
	@Override
	public boolean keyDown(int keycode) //If key is held down
	{
		if(keycode == Keys.W)
		{
			//System.out.println("" + collisionUp());
			//velocity.y = speed;
			setY(getY() + 16);
		}
		if(keycode == Keys.S)
		{
			//velocity.y = -speed;
			setY(getY() - 16);
		}
		if(keycode == Keys.A)
		{
			//velocity.x = -speed;
			setX(getX() - 16);
		}
		if(keycode == Keys.D)
		{
			//velocity.x = speed;
			setX(getX() + 16);
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
