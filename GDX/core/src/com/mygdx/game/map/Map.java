package com.mygdx.game.map;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.MapLayer;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.Player;
import com.mygdx.game.helpers.ItemHandler;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class Map implements Screen
{
	
	private TiledMap tilemap; 
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Player player;
	private TmxMapLoader loader;
	private HashMap<String, Item> items;
	
	
	private ItemHandler iHandler;
	
	private HashMap<String, MapObjects> mapObjects;

	public Map(Player player) {
		this.player = player;
		tilemap = null;
		loader = new TmxMapLoader();	
		mapObjects = new HashMap<String, MapObjects>();
		
		//new item handler
		iHandler = new ItemHandler();
	}
	
	public void setMap(String map) {
		tilemap = loader.load(map);
	}
	
	@Override
	public void show() 
	{		
		//Gdx.graphics.setWindowedMode(528, 768);
		mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); //creates a camera to display the map on screen
		//oCamera.setToOrtho(false, 11,16);
		oCamera.setToOrtho(false, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		//Sets the camera and renders the scene from the bottom left. /3 to zoom in to match the size of the window.
		
		//for now, unless we have a better way, loading the player directly onto map for hack.
		//player.getPlayerSprite().setPosition(80, 64);
		Gdx.input.setInputProcessor(player.getPlayerMover());
		
		setupLayer("Collision");
		setupLayer("Door");
		setupLayer("Use");
		
		//gets all the items for the map
		items = iHandler.getItems();
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapRenderer.setView(oCamera);
		mapRenderer.render();
		//renders the map and sets the view of the camera to display the map
		
		mapRenderer.getBatch().begin();
		//player.setPosition(80, 64);
		player.draw(mapRenderer.getBatch());
		
		//Rendering the items
		for(Item i : items.values())
		{
			i.getSprite().setPosition(i.getX(), i.getY()); // Testing if restartItems() was being called.
			i.draw(mapRenderer.getBatch());
		}
		
		
		mapRenderer.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		//oCamera.viewportHeight = height;
		//oCamera.viewportWidth = width;
		//oCamera.update();
		//Test stuff; setToOrtho method above achieves the effect much better and cleaner.
	}

	@Override
	public void pause() {	}

	@Override
	public void resume() {	}

	@Override
	public void hide() 
	{
		dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getSprite().getTexture().dispose();
	}
	
	public TiledMap getTileMap()
	{
		return tilemap;
	}

	public void setupLayer(String layerID) {
			int layer = tilemap.getLayers().getIndex(layerID);
			MapLayer objectLayer = tilemap.getLayers().get(layer);
			MapObjects object = objectLayer.getObjects();

			mapObjects.put(layerID, object);
	}
	
	public boolean checkTouching(String layerID) {	
		MapObjects objectTouching = mapObjects.get(layerID);
		
		for(RectangleMapObject rectangleObject : objectTouching.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(Intersector.overlaps(rectangle, player.getSprite().getBoundingRectangle())) {
				return true;
			}
		}	
		return false;
	}
	
	public boolean getLayerVisibility(String layerID)
	{
		return tilemap.getLayers().get(tilemap.getLayers().getIndex(layerID)).isVisible();
	}
	
	public void setLayerVisibility(String layerID, boolean visibility)
	{
		tilemap.getLayers().get(tilemap.getLayers().getIndex(layerID)).setVisible(visibility);
	}
	
}

