package com.mygdx.game.io;

import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.DIRECTION;
import com.mygdx.game.entities.Actor.ACTOR_STATE;
import com.badlogic.gdx.InputAdapter;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class PlayerMovementController extends InputAdapter {
	private Actor player;
	
	private boolean[] keyPress;
	private float[] pressLength;
	
	private final static float PRESS_LENGTH_FOR_MOVEMENT = 0.07f;
	
	public PlayerMovementController(Actor player) {
		this.player = player;
		keyPress = new boolean[DIRECTION.values().length];
		pressLength = new float[DIRECTION.values().length];
		
		keyPress[DIRECTION.NORTH.ordinal()] = false;
		keyPress[DIRECTION.SOUTH.ordinal()] = false;
		keyPress[DIRECTION.EAST.ordinal()] = false;
		keyPress[DIRECTION.WEST.ordinal()] = false;
		
		pressLength[DIRECTION.NORTH.ordinal()] = 0f;
		pressLength[DIRECTION.SOUTH.ordinal()] = 0f;
		pressLength[DIRECTION.EAST.ordinal()] = 0f;
		pressLength[DIRECTION.WEST.ordinal()] = 0f;
	}
	
	// Store keypress in boolean variable.
	// Allows us to hold down key for movement rather than have to press key each time.
	// Moves while ever boolean is set to true.
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.W) {
			keyPress[DIRECTION.NORTH.ordinal()] = true;
		}
		if (keycode == Keys.A) {
			keyPress[DIRECTION.WEST.ordinal()] = true;
		}
		if (keycode == Keys.S) {
			keyPress[DIRECTION.SOUTH.ordinal()] = true;
		}
		if (keycode == Keys.D) {
			keyPress[DIRECTION.EAST.ordinal()] = true;
		}
		return false;
	}
	
	// Key release signals that player wants to stop moving in certain direction.
	// Calls method releaseDirection to finish movement in certain direction.
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W) {
			releaseDirection(DIRECTION.NORTH);
		}
		if (keycode == Keys.A) {
			releaseDirection(DIRECTION.WEST);
		}
		if (keycode == Keys.S) {
			releaseDirection(DIRECTION.SOUTH);
		}
		if (keycode == Keys.D) {
			releaseDirection(DIRECTION.EAST);
		}
		return false;
	}
	
	public void update(float delta) {
		if(keyPress[DIRECTION.NORTH.ordinal()]) {
			changeDirection(DIRECTION.NORTH, delta);
			return;
		}
		if(keyPress[DIRECTION.WEST.ordinal()]) {
			changeDirection(DIRECTION.WEST, delta);
			return;
		}
		if(keyPress[DIRECTION.SOUTH.ordinal()]) {
			changeDirection(DIRECTION.SOUTH, delta);
			return;
		}
		if(keyPress[DIRECTION.EAST.ordinal()]) {
			changeDirection(DIRECTION.EAST, delta);
			return;
		}
	}
	
	// Moves while ever key is pressed down.
	private void changeDirection(DIRECTION dir, float delta) {
		pressLength[dir.ordinal()] += delta;
		evaluateMove(dir);
	}
	
	// Sets boolean to false for moving in given direction.
	// Resets key press length ready for next movement in same direction..
	private void releaseDirection(DIRECTION dir) {
		keyPress[dir.ordinal()] = false;
		evaluateReface(dir);
		pressLength[dir.ordinal()] = 0f;
	}
	
	private void evaluateMove(DIRECTION dir) {
		// If keypress greater than length required for movement to occur.
		if(pressLength[dir.ordinal()] > PRESS_LENGTH_FOR_MOVEMENT) {
			player.move(dir);
		}
	}
	
	private void evaluateReface(DIRECTION dir) {
		// If keypress less than length required for movement to occur.
		if(pressLength[dir.ordinal()] < PRESS_LENGTH_FOR_MOVEMENT) {
			player.changeFacing(dir);
		}
	}
}
