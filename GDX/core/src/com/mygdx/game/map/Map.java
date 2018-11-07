package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.Player;

public class Map implements Screen
{
	
	private TiledMap tilemap; 
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera oCamera;
	private Player player;

	@Override
	public void show() 
	{
		TmxMapLoader loader = new TmxMapLoader();
		tilemap = loader.load("assets/sprites/hack.tmx");
		//above lines initialises the map loader and loads the .tmx map file.
		
		
		//Gdx.graphics.setWindowedMode(528, 768);
		mapRenderer = new OrthogonalTiledMapRenderer(tilemap); //initialises the Orthogonal (top-down) renderer for the map
		oCamera = new OrthographicCamera(); //creates a camera to display the map on screen
		//oCamera.setToOrtho(false, 11,16);
		oCamera.setToOrtho(false, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		//Sets the camera and renders the scene from the bottom left. /3 to zoom in to match the size of the window.
		
		//for now, unless we have a better way, loading the player directly onto map for hack.
		player = new Player(new Sprite(new Texture("assets/sprites/playerspriteR.png")));
		player.getPlayerSprite().setPosition(80, 64);
		Gdx.input.setInputProcessor(player);
		player.collisionSetUp(this.getTileMap());
		player.useLayerSetUp(tilemap);
		player.doorLayerSetUp(tilemap);
//		player.layerSetUps(tilemap);
		
		//test();
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
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void hide() 
	{
		dispose();
	}

	@Override
	public void dispose() {
		tilemap.dispose();
		mapRenderer.dispose();
		player.getPlayerSprite().getTexture().dispose();
	}
	
	public TiledMap getTileMap()
	{
		return tilemap;
	}
	
	public boolean getLayerVisibility(String layerID)
	{
		int layer = tilemap.getLayers().getIndex(layerID);
		return tilemap.getLayers().get(layer).isVisible();
	}
	
	public void setLayerVisibility(String layerID, boolean visibility)
	{
		int layer = tilemap.getLayers().getIndex(layerID);
		tilemap.getLayers().get(layer).setVisible(visibility);
	}

//	public void test()
//	{
//		System.out.println(tilemap.getLayers().get(1).getName());
//		MapLayer collisionlayer = tilemap.getLayers().get(1);
//		TiledMapTileLayer tilelayer = (TiledMapTileLayer)tilemap.getLayers().get(0);
//		System.out.println(collisionlayer.getName());
//		collisionlayer.getObjects().toString();
//		System.out.println(collisionlayer.getObjects().toString());
//		Cell cell = tilelayer.getCell(0,0);
//		System.out.println(cell);
//		cell.getTile().getId();
//		System.out.println(cell.getTile().getId());
//		cell.getTile().getProperties().containsKey("blocked");
//		System.out.println(cell.getTile().getProperties().containsKey("blocked"));
//		System.out.println(cell.getTile().getObjects().getIndex(cell.getTile().getObjects().get(0)));
//	}

}

/** For collision, in the hack map, I have a number of different collision methods we can try. There's the object collision
 * which is handled in the **.tsx** file and looks like is controlled with **objectID=1**.
 * 
 * I've added a collision layer with a number of rectangles defining that boundary. Could try using that.
 * 
 * Finally I have all the walls, door, floors that should be blocked with the custom property **blocked** 
 * We can use that property, check if the tile has that property, if so, disallow movement.
 */

