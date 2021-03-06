package prisonescape.game.screens.components;

import java.util.Calendar;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;

import prisonescape.game.GameController;
import prisonescape.game.GameSettings;
import prisonescape.game.model.actors.Item;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

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
	private Stage stage = ActiveGame.getStage();
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
	private int itemCounter = 0;
	
	private Label stateLabel;
	
	private HashMap<String, Image> shownItems;
	
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

		stateLabel = new Label("state", skin);
		timeImage = new Image(moon);
		
		table = new Table();
		table.left().top();
		table.setFillParent(true);
		table.padLeft(100);
		table.padTop(15);
		table.padRight(15);
		
		table.add(areaName);
		table.row();
		table.add(timeHud, timeImage);
		table.row();
		table.add(stateLabel);

		stage.addActor(table);
		
		inventory();
		questTracker();
		sleepUI();

		shownItems = new HashMap<String,Image>();
		
		this.controller = controller;
	}
	
	/**
	 * Sets up and formats the table for the Inventory after which it is added to the stage
	 */
	private void inventory()
	{		
		Label inv = new Label("Inventory", skin);
		invTable = new Table();
		invTable.left().top();
		invTable.setVisible(false);
		invTable.setFillParent(true);
		invTable.padTop(100);
		invTable.padLeft(15);
		invTable.setName("Inventory");
		
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
		stateLabel.setText("State: " + controller.getGameState());
		
		//If item exists and hasn't already been found yet then set the item into the inventory
		if(item != null && item.getFound() == false)
		{
			setItem(item);
			checkItems(item);
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

	}
	
	/**
	 * Sets the visibility of the inventory table
	 * @param isVisible boolean to set the visibility
	 */
	public void setVisible(boolean isVisible)
	{
		invTable.setVisible(isVisible);
	}
	
	/**
	 * 
	 * @return The visibility of the inventory table
	 */
	public boolean isVisible()
	{
		return invTable.isVisible();
	}
	
	//Sets the item sprite into the inventory
	/**
	 * Sets an item sprite to be displayed in the inventory
	 * <p>
	 * If two items are being displayed on one row then a new row is added to the inventory
	 * @param item of type <code>Item</code> is taken to get the sprite value
	 */
	public void setItem(Item item)
	{
		rowCounter++;
		Image img = new Image(item.getSprite());
		invTable.add(img);
		shownItems.put(item.getName(), img);
		if(rowCounter == 2) //When 2 items are on the same row in the inventory, next item starts a new row
		{
			invTable.row();
			rowCounter = 0;
		}
		item.setFound(true);
	}
	
	/**
	 * Removes an item from being displayed in the inventory
	 * <p>
	 * If items were displayed on a new row and no more items are being displayed on the row, then this also removes the
	 * unused row
	 * @param itemName Takes the String name of the item
	 */
	public void removeItem(String itemName)
	{
		invTable.removeActor(shownItems.get(itemName));
		shownItems.remove(itemName);
		if(rowCounter != 0)
		{
			rowCounter--;
		}
		else
		{
			//If the rowCounter is 0 then a new row is made, this removes the last row which is unused
			Array<Cell> cells = invTable.getCells();
			cells.ordered = false;
			cells.removeIndex(cells.size-1);
		}
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
	
	/**
	 * Sets up and formats the table required for the quest/objective tracker and adds it to the stage
	 */
	private void questTracker()
	{
		questTable = new Table(skin);
		questTable.right().top();
		questTable.setFillParent(true);
		questTable.padTop(300);
		questTable.padRight(20);
		
		Label trackTitle = new Label("Current Objective:", skin);
		currObjective = new Label("", skin);

		questTable.add(trackTitle);
		questTable.row();
		questTable.add(currObjective);
		
		stage.addActor(questTable);
		
	}
	
	/**
	 * Sets up and formats the Sleeping UI, used in changing the time.
	 * 
	 */
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
	
	/**
	 * Sets the time to the given value
	 * @param slept The value to set the time to
	 */
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
	
	/**
	 * Checks the given item against a list of story items required in the game. If all items are found, the items are combined
	 * into a new item, the parts are removed and the state and objective is updated.
	 * @param i Takes an <code>Item</code> in order to check if it's a required story item
	 */
	public void checkItems(Item i)
	{
		//Checks list of items for certain story items and updates the state
		if(controller.getGameState().equals("3.1"))
		{
			String[] clippers = {"LeftHandle", "RightHandle", "Cutters"};
			for(String s : clippers)
			{
				if(i.getName().equals(s))
				{
					itemCounter++;
				}
			}
			if(itemCounter == 3)
			{
				controller.setGameState("3.2");
				Item cutters = new Item(new Sprite(new Texture(Gdx.files.internal("data/itemSprites/wirecutters.png"))),"Wirecutters","data/maps/basement.tmx","KEY",5,2);
				removeItem("LeftHandle");
				removeItem("RightHandle");
				removeItem("Cutters");
				setItem(cutters);
				controller.getItemHandler().foundItem(cutters); //Adds the cutters to the list of found items.
				controller.setCurrentObjective("Return to the boss");
			}
		}
	}
}
