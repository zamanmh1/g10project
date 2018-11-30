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
	// Directions that actor can move in.
	NORTH(0,1),
	EAST(1,0),
	SOUTH(0,-1),
	WEST(-1,0),
	;
	
	private int moveX, moveY;
	
	private DIRECTION(int moveX, int moveY) {
		this.moveX = moveX;
		this.moveY = moveY;
	}
	
	public int getMoveX() {
		return moveX;
	}
	
	public int getMoveY() {
		return moveY;
	}
}
