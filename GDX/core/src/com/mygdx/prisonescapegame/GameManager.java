package com.mygdx.prisonescapegame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;


public class GameManager {
	private static GameManager instance = new GameManager();
	//public GameHandler gameHandler;
	//private PrisonEscapeGame gameInstance;
	private Json json = new Json();
	private ObjectMap<String, Object> values = new ObjectMap<String, Object>();
	private FileHandle fileHandle = Gdx.files.local("data/bin/GameData.json");
	private boolean save;
	
	private GameManager() {
		save = false;
	};
	
	/*
	public void initialiseGameData() {
		if(!fileHandle.exists()) {
			//gameHandler = new GameHandler(game);object
	
			
			saveData();
			
		} else {
			loadData();
		}object
	}
	*/
	
	public void savePlayerData(PrisonEscapeGame game, GameHandler gh) {
		
		setProperty("controller", gh);
		//setProperty("y", gh.getPlayer().getWorldY());
		//setProperty("player", gh.getPlayer());
		
		//System.out.println(values.get("player"));
		
		fileHandle.writeString(json.prettyPrint(values.get("controller")), false);
		//fileHandle.writeString(json.prettyPrint(gh.getPlayer().getWorldY()), true);
		
		
		
		if (gh != null) {
			//fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(currentMap)), false);
			//System.out.println(fileHandle.toString());
			//fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(actors)), false);
			//fileHandle.writeString(json.prettyPrint(time), false);
			//fileHandle.writeString(json.prettyPrint(values), false);

		}
	}
	
//	public void loadData() {
//		gameHandler = json.fromJson(GameHandler.class, Base64Coder.decodeString(fileHandle.readString()));
//	}
	
	public static GameManager getInstance() {
		return instance;
	}
	
//	public PrisonEscapeGame getGame()
//	{
//		if(gameInstance != null)
//		{
//			return gameHandler.getGame();
//		}
//		return null;
//	}
	
	
	public void setProperty(String key, Object object){
	        values.put(key, object);
	    }

	    @SuppressWarnings("unchecked")
		public <T extends Object> T getProperty(String key, Class<T> type){
	        T property = null;
	        if( !values.containsKey(key) ){
	            return property;
	        }
	        property = (T)values.get(key);
	        return property;
	    }

		public void prepareSave(GameHandler gh) {
			save = true;		
			initSave(gh);
		}
		
		public void initSave(GameHandler gh) {
			if (save) {
				savePlayerData(gh.getGame(), gh);
				save = false;
				System.out.println("Here");
			}
		}


}
