package com.mygdx.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.game.io.MapReader;
import com.mygdx.game.model.Teleporter;
import com.mygdx.game.model.TiledModel;

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
	
	private ArrayList<Teleporter> teleporters;
	private String currentMap;
	
	public MapHandler() {
		teleporters = new ArrayList<Teleporter>();
		currentMap = null;
		loadMaps();
	}
	
	private void loadMaps() {
		teleporters = new MapReader("data/loader/teleporters.csv").readTeleporters();
	}
	
	public Teleporter getTeleporter(String srcFile, int srcX, int srcY) {
		for(Teleporter m: teleporters) {
			if(srcFile.equals(m.getSourceFile())
					&& srcX == m.getPlaySourceX()
					&& srcY == m.getPlaySourceY()) {				
				return m;
			}
		}
		return null;
	}
	
	public void initialiseTeleporters(TiledModel model) {
		for(Teleporter m: teleporters) {
			if(m.getSourceFile().equals(currentMap)) {
				model.setTeleporterTile(m.getTelSourceX(), m.getTelSourceY());
			}
		}
	}
	
	public void setCurrentMap(String map) {
		this.currentMap = map;
	}
	
	public String getCurrentMap() {
		return currentMap;
	}
}
