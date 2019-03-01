package com.mygdx.game.io;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.entities.Item;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.screens.MapScreen;

public class HUD
{
	private Stage stage = MapScreen.getStage();
	private Skin skin = new Skin(Gdx.files.internal("data/story/skin/uiskin.json"));
	private Texture sun = new Texture(Gdx.files.internal("data/sprites/sun.png"));
	private Texture moon = new Texture(Gdx.files.internal("data/sprites/moon.png"));
	private Label areaName;
	private Label timeHud;
	private Image timeImage;
	private Table invTable;
	private Table table;
	
	private Table questTable;
	private Label currObjective;
	
	
	private int rowCounter = 0;
	
	public HUD()
	{
		areaName = new Label("map", skin);
		timeHud = new Label("time", skin);
		timeImage = new Image(moon);
		
		table = new Table();
		table.left().top();
		table.setFillParent(true);
		table.padLeft(100);
		
		table.add(areaName);
		table.row();
		table.add(timeHud, timeImage);

		stage.addActor(table);
		
		inventory();
		questTracker();
		
	}
	
	public void inventory()
	{
		Label inv = new Label("Inventory", skin);
		invTable = new Table();
		invTable.left().top();
		invTable.setVisible(false);
		invTable.setFillParent(true);
		invTable.padTop(100);
		
		invTable.add(inv);
		invTable.row();
		
		stage.addActor(invTable);
	}
	
	public void update(String mapName, Item item, String time)
	{
		areaName.setText("Area: " + mapName);
		timeHud.setText("Time: " + time);
		
		//If item exists and hasn't already been found yet then set the item into the inventory
		if(item != null && item.getFound() == false)
		{
			rowCounter++;
			setItem(item);
		}
		
		currObjective.setText(GameSettings.currentObjective);
	}
	
	//Set the visibility of the inventory table
	public void setVisible(boolean isVisible)
	{
		invTable.setVisible(isVisible);
	}
	
	public boolean isVisible()
	{
		return invTable.isVisible();
	}
	
	//Sets the item sprite into the inventory
	public void setItem(Item item)
	{
		Image img = new Image(item.getSprite());
		invTable.add(img);
		if(rowCounter == 2) //When 2 items are on the same row in the inventory, next item starts a new row
		{
			invTable.row();
			rowCounter = 0;
		}
		item.setFound(true);
	}
	
	public void setTimeImage(int hour)
	{
		//When day begins, set image next to time to the sun
		if(hour == GameSettings.HOUR_DAY_BEGINS)
		{
			table.removeActor(timeImage);
			timeImage = new Image(sun);
			table.add(timeImage);
		}
		//When night begins, set image next to time to the moon
		else if(hour == GameSettings.HOUR_NIGHT_BEGINS)
		{
			table.removeActor(timeImage);
			timeImage = new Image(moon);
			table.add(timeImage);
		}
	}
	
	public void questTracker()
	{
		questTable = new Table(skin);
		questTable.right().top();
		questTable.setFillParent(true);
		questTable.padTop(300);
		questTable.padRight(10);
		
		Label trackTitle = new Label("Current Objective:", skin);
		currObjective = new Label("", skin);
//		currObjective.setWrap(false);
//		currObjective.setWidth(currObjective.getPrefWidth());;
		//Playing with the values here to try and have the label wrap without being so wide
		questTable.add(trackTitle);
		questTable.row();
		questTable.add(currObjective);
		
		stage.addActor(questTable);
		
	}
		
}
