package prisonescape.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.ObjectMap;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.screens.Loading;
import prisonescape.game.screens.MainMenu;
import prisonescape.game.util.Time;

public class GameManager {
	private GameController controller;
	private ObjectMap<String, Object> values = new ObjectMap<String, Object>();

	private FileHandle fileHandle;
	private ArrayList<String> items;

	public GameManager(GameController controller) {
		this.controller = controller;
		items = new ArrayList<String>();
		fileHandle = Gdx.files.local("data/bin/" + System.currentTimeMillis());
	}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void loadData(FileHandle file) {
		// To decode the saved file
		// Base64Coder.decode(file.readString());
		try {
			Runtime.getRuntime().exec("attrib -H data/bin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

		for (String i : foundItems) {

			
			controller.getItemHandler().foundItem(controller.getItemHandler().getAllItems().get(i));
			
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

	public void setProperty(String key, Object object) {
		values.put(key, object);
	}

	public static String listToString(ArrayList<String> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i) + "+";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getProperty(String key, Class<T> type) {
		T property = null;
		if (!values.containsKey(key)) {
			return property;
		}
		property = (T) values.get(key);
		return property;
	}

	public FileHandle getFileHandle() {
		return this.fileHandle;
	}

}
