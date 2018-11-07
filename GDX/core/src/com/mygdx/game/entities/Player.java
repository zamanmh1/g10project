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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements InputProcessor
{
	
	private Vector2 velocity = new Vector2();
	//private float speed = 8 * 2f;
	private TiledMap mapUse;
	private MapObjects objects;
	private MapObjects useObjects;
	private MapObjects doorObj;
	private MapLayer collisionObjectLayer;
	private MapLayer useObjectLayer;
	private float tempCX;
	private float tempCY;
	private Sprite playerSprite;
	private boolean hasKey;
	private MapLayer doorLayer;
	
	
	public Player(Sprite sprite)
	{
		this.playerSprite = sprite;
		hasKey = false;
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
		
	}
	
	private float getCurrentCellX() //gets current cell by taking x value / tile width to get the cell value
	{
		return playerSprite.getX() / 16;
	}
	
	private float getCurrentCellY()
	{
		return playerSprite.getY() / 16;
	}

	
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
			break;
		case Keys.E:
			//Check if on use layer, if there pick up/use
			if(useObject() == true && getLayerVisibility("Key") == true)
			{
				hasKey = true;
				setLayerVisibility("Key", false);
			}
			if(useDoor() == true && hasKey == true)
			{
				playerSprite.setY(playerSprite.getY() + 32);
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
	
	//These set ups can probably be all pushed into one method looping and setting up the individual
	//MapObjects for each layer.
	
	public void collisionSetUp(TiledMap tilemap) {
			int objectLayerID = 1;
			//collisionObjectLayer = tilemap.getLayers().get(objectLayerID);
			int layerID = tilemap.getLayers().getIndex("Collision");
			collisionObjectLayer = tilemap.getLayers().get(layerID);
			
			objects = collisionObjectLayer.getObjects();
	}
	
	public void useLayerSetUp(TiledMap tilemap)
	{
		int layerID = tilemap.getLayers().getIndex("Use");
		useObjectLayer = tilemap.getLayers().get(layerID);
		
		useObjects = useObjectLayer.getObjects();
		
		mapUse = tilemap;
	}
	
	public void doorLayerSetUp(TiledMap tilemap)
	{
		int layerID = tilemap.getLayers().getIndex("Door");
		useObjectLayer = tilemap.getLayers().get(layerID); //This should be doorLayer but there's a bug when it changes
		
		doorObj = useObjectLayer.getObjects();
	}
	
	
	//This felt like the easiest method of interacting with objects for now being as they're all literally rectangles
	//However we end up with A LOT of repeated code, but it works. Defo need to think about how to re-factor this.
	public boolean useObject()
	{
		for(RectangleMapObject rectangleObject : useObjects.getByType(RectangleMapObject.class)) 
		{
			Rectangle rectangle = rectangleObject.getRectangle();
			if(Intersector.overlaps(rectangle, playerSprite.getBoundingRectangle())) 
			{
				System.out.println("Used");	
				
				return true;
			}
		}
		return false;
	}
	
	public boolean useDoor()
	{
		for(RectangleMapObject rectangleObject : doorObj.getByType(RectangleMapObject.class)) 
		{
			Rectangle rectangle = rectangleObject.getRectangle();
			if(Intersector.overlaps(rectangle, playerSprite.getBoundingRectangle())) 
			{
				System.out.println("Used Door");	
				
				return true;
			}
		}
		return false;
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
	
	//These methods should probably be put into the Map class
	public boolean getLayerVisibility(String layerID)
	{
		int layer = mapUse.getLayers().getIndex(layerID);
		return mapUse.getLayers().get(layer).isVisible();
	}
	
	public void setLayerVisibility(String layerID, boolean visibility)
	{
		int layer = mapUse.getLayers().getIndex(layerID);
		mapUse.getLayers().get(layer).setVisible(visibility);
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
