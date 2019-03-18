package com.mygdx.game.io;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.entities.Item;
import com.mygdx.game.util.Time;
import com.mygdx.prisonescapegame.GameController;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.screens.MapScreen;

/**
 * HUD creates the Heads Up Display which is displayed on all playable screens.
 * The current version displays the name of the current area the player is in, the time, current objectives and the inventory.
 * HUD also facilitates the Sleeping UI where a player can interact with a "SLEEP" object such as a bed, which brings up the
 * Sleeping window allowing the player to select a time for them to sleep until.
 * 
 * @author Sean Corcoran
 * 
 * @version 1.0
 * @since 0.1
 */

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
	private Window sleepWin;
	private Slider sleepSlide;
	private Label sliderValueLabel;
	private GameController controller;
	private int rowCounter = 0;
	
	/**
	 * Constructor for the <code>HUD</code> class.
	 * <p>
	 * Creates the table in which other HUD elements are placed into, and then added onto the Scene2d stage for it to be
	 * rendered onto the screen.
	 * <p>
	 * Also calls the methods which creates any other HUD elements such as the quest tracker and inventory.
	 * 
	 * @param g	of type <code>PrisonEscapeGame</code> is taken as a parameter to help in setting the time for the game in the 
	 * Sleeping UI
	 */
	
	public HUD(GameController controller)

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
		sleepUI();

		
		this.controller = controller;
		

	}
	
	private void inventory()
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
	
	/**
	 * The update method is used to actively update any HUD elements that need to be changed.
	 * <p>
	 * Updates the area name, time, items in the inventory and the value on the Sleeping UI.
	 * 
	 * @param mapName	of type <code>String</code> used to change the mapName
	 * @param item	of type <code>Item</code> for setting the item into the inventory
	 * @param time	of type <code>String</code> for updating the time as it changes
	 */
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
		

		//Sets the value of the current objective HUD element
		currObjective.setText(controller.getCurrentObjective());
		
		//Sets and formats the value of the time selected from the slider in the Sleeping HUD window
		String sliderValue = "";
		if(sleepSlide.getValue() == 0)
		{
			sliderValue = "00:00";
		}
		else
		{
			sliderValue = ((int) sleepSlide.getValue() + ":00");
		}
		sliderValueLabel.setText(sliderValue);

		currObjective.setText(controller.getCurrentObjective());

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
	
	/**
	 * Sets the image to be displayed next to the time in the HUD.
	 * <p>
	 * Image is either a sun or a moon depending on the time of day in relation to the constants <code>HOUR_(DAY/NIGHT)_BEGINS</code>
	 * in <code>GameSettings</code>
	 * 
	 * @param hour
	 */
	public void setTimeImage(int hour)
	{
		//When day begins, set image next to time to the sun
		if(hour >= GameSettings.HOUR_DAY_BEGINS && hour < GameSettings.HOUR_NIGHT_BEGINS) //If 8 >= 7 & X < 22
		{
			table.removeActor(timeImage);
			timeImage = new Image(sun);
			table.add(timeImage);
		}
		//When night begins, set image next to time to the moon
		else if(hour >= GameSettings.HOUR_NIGHT_BEGINS || hour < GameSettings.HOUR_DAY_BEGINS) //IF 8 >= 22 OR < 7
		{
			table.removeActor(timeImage);
			timeImage = new Image(moon);
			table.add(timeImage);
		}
	}
	
	private void questTracker()
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
	
	public void sleepUI()
	{
		sleepWin = new Window("Sleep until?", skin);
		sleepSlide = new Slider(0, 23, 1f, false, skin);
		
		sleepWin.setVisible(false);
		sleepWin.setSize(600, 200);
		sleepWin.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
		
		sleepSlide.getStyle().background.setMinHeight(15);
		sleepSlide.setSize(500, 20);
		sleepSlide.setPosition(40, 120);
		
		sliderValueLabel = new Label("00:00", skin);
		sliderValueLabel.setPosition(300, 200);
		
		final Button setTime = new Button(skin);
		final Button cancel = new Button(skin);
		Label setLabel = new Label("Sleep", skin);
		Label cancelLabel = new Label("Cancel", skin);
		
		setTime.add(setLabel);
		cancel.add(cancelLabel);
		
		setTime.setSize(setTime.getPrefWidth(), setTime.getPrefHeight());
		setTime.setPosition(150, 40);
		cancel.setSize(cancel.getPrefWidth(), cancel.getPrefHeight());
		cancel.setPosition(350, 40);
		
		setTime.addListener(new InputListener()
		{
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
			{
				sleepClicked(sleepSlide.getValue());
				setTimeImage((int) sleepSlide.getValue());
				sleepSlide.setValue(0);
				sleepWin.setVisible(false);
				sleepWin.remove();
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				return true;
			}
		});
		cancel.addListener(new InputListener()
		{
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
			{
				sleepSlide.setValue(0);
				sleepWin.setVisible(false);
				sleepWin.remove();
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				return true;
			}
		});
		sleepWin.addActor(sleepSlide);
		sleepWin.addActor(setTime);
		sleepWin.addActor(cancel);
		sleepWin.add(sliderValueLabel);

		stage.addActor(sleepWin);
	}
	
	private void sleepClicked(float slept)
	{
		Calendar cal = controller.getTime().getCalendar();
		Time.setTime(cal, (int) slept, 0);
	}
	
	/**
	 * Sets the Sleeping UI window to be visible.
	 */
	public void showSleepUI()
	{
		sleepWin.setVisible(true);
	}
	
	/**
	 * 
	 * @return	the visibility of whether the Sleeping UI is visible or not.
	 */
	public boolean sleepIsVisible()
	{
		return sleepWin.isVisible();
	}	
}
