package com.mygdx.prisonescapegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class GameManager {
	private static GameManager instance = new GameManager();
	private GameHandler gameHandler;
	private Json json = new Json();
	private ObjectMap<String, Object> values = new ObjectMap<String, Object>();
	private FileHandle fileHandle = Gdx.files.local("data/bin/GameData.json");
	private GameManager() {
	};
	
	public void saveData() {

		setProperty("state", gameHandler.getGameState());
		setProperty("currentObjective", gameHandler.getGameState());

		if (gameHandler != null) {
			fileHandle.writeString(json.prettyPrint(values.get("state")), false);
			fileHandle.writeString(json.prettyPrint(values.get("currentObjective")), true);
		}
	}

	public void loadData() {
		gameHandler = json.fromJson(GameHandler.class, fileHandle.readString());
		gameHandler.setGameState(getProperty("state", GameHandler.class).toString());
		gameHandler.setGameState(getProperty("currentObjective", GameHandler.class).toString());
	}

	public static GameManager getInstance() {
		return instance;
	}

	public void setProperty(String key, Object object) {
		values.put(key, object);
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

}
