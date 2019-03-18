package prisonescape.game.model;

import prisonescape.game.model.actors.DIRECTION;

/**
 * Stores the information required to be able to move between rooms within the map.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class Teleporter {	
	
	private TeleporterSource source; // The teleporter source.
	private TeleporterDestination destination; // The teleporter destination.
	
	/**
	 * Constructs a new Teleporter object.
	 */
	public Teleporter(String srcFile, int srcX, int srcY, int playSrcX, int playSrcY,
			String destFile, int playDestX, int playDestY, String dir) {
		source = new TeleporterSource(srcFile, srcX, srcY, playSrcX, playSrcY);
		destination = new TeleporterDestination(destFile, playDestX, playDestY, DIRECTION.valueOf(dir));
	}
	
	/**
	 * Provides the teleporter source object.
	 * 
	 * @return Teleporter source.
	 */
	public TeleporterSource getSource() {
		return this.source;
	}
	
	/**
	 * Provides the teleporter destination object.
	 * 
	 * @return Teleporter destination.
	 */
	public TeleporterDestination getDestination() {
		return this.destination;
	}

}
