package prisonescape.game.model.actors;

/**
 * A simplified representation of an Actor in the assumed TiledMap.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public interface MapActor {
		
	/**
	 * The methods which MapActor objects must implement, so that it can
	 * be handled accordingly.
	 */
	
	/**
	 * The MapActor's X-Coordinate within the map.
	 * 
	 * @return X-Coordinate.
	 */
	int getX();
	
	/**
	 * The MapActor's Y-Coordinate within the map.
	 * 
	 * @return Y-Coordinate.
	 */
	int getY();	
	
	/**
	 * The MapActor's X-Coordinate within the world (scaled by the game's tile size).
	 * 
	 * @return X-Coordinate scaled by tile size.
	 */
	float getWorldX();

	/**
	 * The MapActor's Y-Coordinate within the world (scaled by the game's tile size).
	 * 
	 * @return Y-Coordinate scaled by tile size.
	 */
	float getWorldY();
}
