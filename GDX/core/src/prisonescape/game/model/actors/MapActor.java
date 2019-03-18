package prisonescape.game.model.actors;

/**
 * A simplified representation of an Actor in the assumed TiledMap.
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public interface MapActor {
		
	/**
	 * The methods which MapActor objects must implement.
	 */
	
	// Any item which wants to occupy a tile within map must implement.
	int getX();
	int getY();	
	float getWorldX();
	float getWorldY();
}
