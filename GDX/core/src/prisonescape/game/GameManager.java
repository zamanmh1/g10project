package prisonescape.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.ObjectMap;

import prisonescape.game.model.actors.Item;
import prisonescape.game.screens.Loading;
import prisonescape.game.screens.MainMenu;
import prisonescape.game.util.Time;

/**
 * 
 * @author Hamza Zaman, Shibu George
 * @version 1.0
 * @since 1.0
 */
public class GameManager {
	private GameController controller;

	/**
	 * Represent objects in the game, values are typically obtained from
	 * 
	 * @see #controller
	 */
	private ObjectMap<String, Object> values = new ObjectMap<String, Object>();

	/**
	 * Holds the game state properties once saved
	 */
	private FileHandle fileHandle;

	/**
	 * Holds a list of items player keeps before game save
	 * 
	 * @see #controller
	 */
	private ArrayList<String> items;

	/**
	 * Creates a <code>GameManager</code> object, initialises a controller a list of
	 * items and a file based on system time
	 * 
	 * @param GameController
	 */
	public GameManager(GameController controller) {
		this.controller = controller;
		items = new ArrayList<String>();
		fileHandle = Gdx.files.local("data/bin/" + System.currentTimeMillis());
	}

	/**
	 * Takes items/data to store from {@link #controller} and inserts into map
	 * 
	 * @param game - instance of PrisionBreakout
	 */
	public void saveData(PrisonBreakout game) {
		setProperty("map", controller.getMapScreen().getMapName());
		setProperty("state", controller.getGameState());
		setProperty("currentObjective", controller.getCurrentObjective());
		setProperty("playerX", controller.getPlayer().getX());
		setProperty("playerY", controller.getPlayer().getY());
		setProperty("time-hour", controller.getTime().getHour());
		setProperty("time-min", controller.getTime().getMin());

		for (int i = 0; i < controller.getItemHandler().getFoundItems().size(); i++) {

			items.add(controller.getItemHandler().getFoundItems().get(i).getName());

		}

		if (controller != null) {

			/*
			 * String encodedMap = Base64Coder.encodeString(values.get("map").toString());
			 * String encodedState =
			 * Base64Coder.encodeString(values.get("state").toString()); String
			 * encodedObjective =
			 * Base64Coder.encodeString(values.get("currentObjective").toString()); String
			 * encodedPlayerX = Base64Coder.encodeString(values.get("playerX").toString());
			 * String encodedPlayerY =
			 * Base64Coder.encodeString(values.get("playerY").toString()); String
			 * encodedTimeHour =
			 * Base64Coder.encodeString(values.get("time-hour").toString()); String
			 * encodedTimeMin = Base64Coder.encodeString(values.get("time-min").toString());
			 * 
			 * String encodedItems = Base64Coder.encodeString(listToString(items));
			 * 
			 * replace below lines with encoded strings
			 */

			fileHandle.writeString("Map," + values.get("map") + "," + "\n", false);

			fileHandle.writeString("State," + values.get("state") + "," + "\n", true);
			fileHandle.writeString("currentObjective," + values.get("currentObjective") + "," + "\n", true);

			fileHandle.writeString("X," + values.get("playerX") + "," + "\n", true);

			fileHandle.writeString("Y," + values.get("playerY") + "," + "\n", true);
			fileHandle.writeString("Hour," + values.get("time-hour") + "," + "\n", true);
			fileHandle.writeString("Minute," + values.get("time-min") + "," + "\n", true);

			fileHandle.writeString("Items," + listToString(items) + "," + "\n", true);

			try {
				Runtime.getRuntime().exec("attrib +H data/bin");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads in data from a game save
	 * 
	 * @param file - file to be loaded from a list of saves
	 */
	public void loadData(FileHandle file) {
		// To decode the saved file
		// Base64Coder.decode(file.readString());
		try {
			Runtime.getRuntime().exec("attrib -H data/bin");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// file.readString();
		String[] data = file.readString().split(",");
		String map = data[1];
		int x = Integer.parseInt(data[7]);
		int y = Integer.parseInt(data[9]);
		String state = data[3];
		String currentObjective = data[5];
		int hour = Integer.parseInt(data[11]);
		int minute = Integer.parseInt(data[13]);

		((Game) Gdx.app.getApplicationListener()).setScreen(new Loading(controller.getGame()));
		controller.setMap(map, x, y);
		controller.setGameState(state);
		controller.setCurrentObjective(currentObjective);
		String[] foundItems = data[15].split("\\+");

		if (!foundItems[0].isEmpty()) {
			for (String i : foundItems) {

				if (!controller.getItemHandler().itemExists(i)) {

					if(controller.getGameState().equals("3.1"))
					{
						controller.setCurrentObjective("Speak to the Boss");
					}
						Item item = new Item(new Sprite(new Texture(Gdx.files.internal("data/itemSprites/" + i + ".png"))), i,
								"data/maps/cell.tmx", "KEY", 1, 1);
						
						controller.getItemHandler().addItem(i, item);
						controller.getItemHandler().foundItem(item);
						controller.getMapScreen().h.setItem(item);
						controller.getMapScreen().h.checkItems(item);

				}else {
//					controller.getMapScreen();
//					ActiveGame.h.setItem(controller.getItemHandler().getAllItems().get(i));
					Item item = controller.getItemHandler().getAllItems().get(i);
					controller.getItemHandler().foundItem(item);
					controller.getMapScreen().h.setItem(item);
				}
			}
		}
		
		Calendar cal = controller.getTime().getCalendar();
		Time.setTime(cal, hour, minute);
		Boolean muted = MainMenu.getInstance(controller.getGame()).checkSoundMuted();
		if (muted == true) {
			controller.stopMusic();

		} else {

			if (controller.getAlarm().alarmTriggered()) {
				controller.playAlarmSound();
			}

		}
		try {
			Runtime.getRuntime().exec("attrib +H data/bin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Assigns game objects for easy storage and retrieval
	 * 
	 * @param key    - reference to the game component
	 * @param object - object corresponding to game component
	 */
	public void setProperty(String key, Object object) {
		values.put(key, object);
	}

	/**
	 * Lists a sequence of items
	 * 
	 * @param list - list of items
	 * @return result - number of items within a list
	 */
	public static String listToString(ArrayList<String> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i) + "+";
		}
		return result;
	}

	/**
	 * Retrieves elements from an object map
	 * 
	 * @param      <T> - class type identifier of stored object
	 * @param key  - identifier of object
	 * @param type - type of object
	 * @return composite property of an object
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getProperty(String key, Class<T> type) {
		T property = null;
		if (!values.containsKey(key)) {
			return property;
		}
		property = (T) values.get(key);
		return property;
	}

	/**
	 * Returns a <code>FileHandle</code>
	 * 
	 * @return fileHandle
	 */
	public FileHandle getFileHandle() {
		return this.fileHandle;
	}

}
