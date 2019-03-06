package com.mygdx.game.entities;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public enum DIRECTION {
	/**
	 * Directions that actors can move in.
	 * Have associated values to move in each direction based on given DIRECTION.
	 */
	NORTH(0,1),
	EAST(1,0),
	SOUTH(0,-1),
	WEST(-1,0),
	;
	
	private int moveX, moveY; // The amount to move in each direction.
	
	/**
	 * Constructs a new DIRECTION object from a DIRECTION to move in.
	 * Move amount and direction determined by the DIRECTION to move.
	 * 
	 * @param moveX The amount to move in the x-direction.
	 * @param moveY The amount to move in the y-direction.
	 */
	private DIRECTION(int moveX, int moveY) {
		this.moveX = moveX;
		this.moveY = moveY;
	}
	
	/**
	 * Provides the direction opposite to the one provided.
	 * 
	 * @param dir Direction to find opposite of.
	 * 
	 * @return Opposite to direction provided.
	 */
	public static DIRECTION getBehind(DIRECTION dir) {
		switch(dir) {
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		}
		return null;

	}
	
	/**
	 * Provides the amount to move in the x-direction.
	 * 
	 * @return Amount to move in x-direction.
	 */
	public int getMoveX() {
		return moveX;
	}
	
	/**
	 * Provides the amount to move in the y-direction.
	 * 
	 * @return Amount to move in y-direction.
	 */
	public int getMoveY() {
		return moveY;
	}
}
