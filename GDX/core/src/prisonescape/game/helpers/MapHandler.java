package prisonescape.game.helpers;

import java.util.ArrayList;

import prisonescape.game.io.MapReader;
import prisonescape.game.model.Teleporter;
import prisonescape.game.model.TiledModel;

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
			if(srcFile.equals(m.getSource().getFile())
					&& srcX == m.getSource().getPlayX()
					&& srcY == m.getSource().getPlayY()) {				
				return m;
			}
		}
		return null;
	}
	
	public void initialiseTeleporters(TiledModel model) {
		for(Teleporter m: teleporters) {
			if(m.getSource().getFile().equals(currentMap)) {
				model.setTeleporterTile(m.getSource().getTelX(), m.getSource().getTelY());
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
