package prisonescape.game.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import prisonescape.game.model.actors.DIRECTION;

/**
 * This class holds provides the logic for the way that Actors are
 * viewed within the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class ActorAnimation {
	
	/**
	 * Holds the animation for walking in each direction.
	 */
	private Map<DIRECTION, Animation<?>> walking;
	
	/**
	 * Holds the texture for standing in each direction.
	 */
	private Map<DIRECTION, TextureRegion> standing;
	
	/**
	 * Creates a new collection of textures and animations for an
	 * individual actor.
	 * 
	 * @param walkNorth Animation for walking north.
	 * @param walkSouth Animation for walking south.
	 * @param walkEast Animation for walking east.
	 * @param walkWest Animation for walking west.
	 * @param standNorth Animation for standing facing north.
	 * @param standSouth Animation for standing facing south.
	 * @param standEast Animation for standing facing east.
	 * @param standWest Animation for standing facing west.
	 */
	public ActorAnimation(Animation<?> walkNorth,
			Animation<?> walkSouth, 
			Animation<?> walkEast, 
			Animation<?> walkWest, 
			TextureRegion standNorth, 
			TextureRegion standSouth, 
			TextureRegion standEast, 
			TextureRegion standWest) {

		walking = new HashMap<DIRECTION, Animation<?>>();
		walking.put(DIRECTION.NORTH, walkNorth);
		walking.put(DIRECTION.SOUTH, walkSouth);
		walking.put(DIRECTION.EAST, walkEast);
		walking.put(DIRECTION.WEST, walkWest);
		
		standing = new HashMap<DIRECTION, TextureRegion>();
		standing.put(DIRECTION.NORTH, standNorth);
		standing.put(DIRECTION.SOUTH, standSouth);
		standing.put(DIRECTION.EAST, standEast);
		standing.put(DIRECTION.WEST, standWest);
	}
	
	/**
	 * Retrieves an animation for walking in a given direction.
	 * 
	 * @param dir The direction walking in.
	 * 
	 * @return The animations for this direction.
	 */
	public Animation<?> getWalking(DIRECTION dir) {
		return walking.get(dir);
	}
	
	/**
	 * Retrieves a texture for when an actor is standing facing a given direction.
	 * 
	 * @param dir The direction facing.
	 * 
	 * @return The texture for facing this direction.
	 */
	public TextureRegion getStanding(DIRECTION dir) {
		return standing.get(dir);
	}
}
