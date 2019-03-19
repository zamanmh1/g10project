package prisonescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class GameManager {
	private GameController controller;
	private Json json = new Json();
	private ObjectMap<String, Object> values = new ObjectMap<String, Object>();
	private FileHandle fileHandle = Gdx.files.local("data/bin/GameData.json");
	
	public GameManager(GameController controller) {
		this.controller = controller;
	};
	
	public void saveData(PrisonEscapeGame game) {
		setProperty("state", controller.getGameState());
		setProperty("currentObjective", controller.getGameState());

		if (controller != null) {
			fileHandle.writeString(json.prettyPrint(values.get("state")), false);
			fileHandle.writeString(json.prettyPrint(" "), true);
			fileHandle.writeString(json.prettyPrint(values.get("currentObjective")), true);
		}
	}

	public void loadData() {
		//controller = json.fromJson(GameHandler.class, fileHandle.readString());
		controller.setGameState(getProperty("state", String.class));
		controller.setGameState(getProperty("currentObjective", String.class));
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
