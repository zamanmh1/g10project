package com.mygdx.game.helpers;

import java.util.HashMap;

import com.mygdx.game.io.MapReader;
import com.mygdx.game.model.Map;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class MapHandler {
	
	private HashMap<String, Map> fwdMaps;
	private HashMap<String, Map> bwdMaps;
	private String currentMap;
	
	public MapHandler() {
		fwdMaps = new HashMap<String, Map>();
		bwdMaps = new HashMap<String, Map>();
		
		loadMaps();
		
		currentMap = "cell";
	}
	
	private void loadMaps() {
		fwdMaps = new MapReader("data/loader/fwdMaps.csv").readMaps();
		bwdMaps = new MapReader("data/loader/bwdMaps.csv").readMaps();
	}
	
	public Map getMap(String mapName, String str) {
		if(str.equals("fwd")) {
			return fwdMaps.get(mapName);
		} else if(str.equals("bwd")) {
			return bwdMaps.get(mapName);
		}		
		return null;
	}
	
	public String getCurrentMap() {
		return this.currentMap;
	}
	
	public void setCurrentMap(String map) {
		this.currentMap = map;
	}
}
