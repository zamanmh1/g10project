package prisonescape.game.helpers;

import java.util.ArrayList;

import prisonescape.game.io.MapReader;
import prisonescape.game.model.Teleporter;
import prisonescape.game.model.TiledModel;

/**
 * A class to handle the teleportation between maps throughout the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class MapHandler {
	
	/**
	 * A collection of all of the teleporters within the map.
	 */
	private ArrayList<Teleporter> teleporters;
	
	/**
	 * The current map file path.
	 */
	private String currentMap;
	
	/**
	 * Creates a new MapHandler object.
	 */
	public MapHandler() {
		teleporters = new ArrayList<Teleporter>();
		currentMap = null;
		
		// Loads all of the maps when created.
		loadMaps();
	}
	
	/**
	 * Reads all of the teleporters using the MapReader.
	 */
	private void loadMaps() {
		teleporters = new MapReader("data/loader/teleporters.csv").readTeleporters();
	}
	
	/**
	 * Retrieves a teleporter, should there be one found from the given parameters.
	 * 
	 * @param srcFile The map to find a teleporter in.
	 * @param srcX The players x location within the map.
	 * @param srcY The players y location within the map.
	 * 
	 * @return Return a teleporter matching the parameters, else return null.
	 */
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
	
	/**
	 * Updates the TiledModel to contain the teleporter tiles.
	 * 
	 * @param model The TiledModel.
	 */
	public void initialiseTeleporters(TiledModel model) {
		for(Teleporter m: teleporters) {
			if(m.getSource().getFile().equals(currentMap)) {
				model.setTeleporterTile(m.getSource().getTelX(), m.getSource().getTelY());
			}
		}
	}
	
	/**
	 * Set the current map within the game.
	 * 
	 * @param map The new map's file path.
	 */
	public void setCurrentMap(String map) {
		this.currentMap = map;
	}
	
	/**
	 * Retrieves the current map's file path.
	 * 
	 * @return File path of current map.
	 */
	public String getCurrentMap() {
		return currentMap;
	}
}
