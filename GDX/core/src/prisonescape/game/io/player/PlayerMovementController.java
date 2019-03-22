package prisonescape.game.io.player;

import com.badlogic.gdx.Input.Keys;

import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.DIRECTION;

import com.badlogic.gdx.InputAdapter;

/**
 * This class enables a player to register movements for a given Actor.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class PlayerMovementController extends InputAdapter {
	
	/**
	 * The actor for the player to control.
	 */
	private Actor player;
	
	/**
	 * Tracks which key has been pressed.
	 */
	private boolean[] keyPress;
	
	/**
	 * Tracks the length of each key press.
	 */
	private float[] pressLength;
	
	/**
	 * The key press length required to move to a new tile.
	 */
	private final static float PRESS_LENGTH_FOR_MOVEMENT = 0.07f;
	
	/**
	 * Constructs a new PlayerMovementController, allowing an Actor to be controlled
	 * by a player.
	 * 
	 * @param player The Actor being controlled.
	 */
	public PlayerMovementController(Actor player) {
		this.player = player;
		
		// Creates arrays large enough to hold values for each direction.
		keyPress = new boolean[DIRECTION.values().length];
		pressLength = new float[DIRECTION.values().length];
		
		// Not attempting to move in any direction when created.
		keyPress[DIRECTION.NORTH.ordinal()] = false;
		keyPress[DIRECTION.SOUTH.ordinal()] = false;
		keyPress[DIRECTION.EAST.ordinal()] = false;
		keyPress[DIRECTION.WEST.ordinal()] = false;
		
		// Therefore, no press length to begin with.
		pressLength[DIRECTION.NORTH.ordinal()] = 0f;
		pressLength[DIRECTION.SOUTH.ordinal()] = 0f;
		pressLength[DIRECTION.EAST.ordinal()] = 0f;
		pressLength[DIRECTION.WEST.ordinal()] = 0f;
	}
	
	/**
	 * Registers that the player has pressed a key and is 
	 * attempting to move in a given direction.
	 * 
	 * @param keycode The direction wanting to move in.
	 */
	@Override
	public boolean keyDown(int keycode) {
		// As keypress stored in boolean variable.
		// Allows us to hold down key for movement rather than have to press key each time.
		// Moves while ever boolean is set to true.
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
	
	/**
	 * Registers that a player has released a key and is signalling
	 * that they would like to stop moving in that direction.
	 * 
	 * @param keycode The direction to stop movement in.
	 */
	@Override
	public boolean keyUp(int keycode) {
		// Calls method releaseDirection to finish movement in certain direction.
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
	
	/**
	 * This method handles the update of the state of the Actor.
	 * 
	 * @param delta The time taken since last frame was rendered.
	 */
	public void update(float delta) {
		// Checks whether the player has attempted to move in any direction since the last frame was rendered.
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
	
	/**
	 * Evaluates the move that the player is attempting to make.
	 * 
	 * @param dir The direction to move in.
	 * @param delta The time taken since last frame was rendered.
	 */
	private void changeDirection(DIRECTION dir, float delta) {
		 // Increments the length of the press while a key is being held down,
		 // and evaluates whether this move is valid or not.
		pressLength[dir.ordinal()] += delta;
		evaluateMove(dir);
	}
	
	/**
	 * Updates that the player no longer wants to move in the given direction,
	 * and handles the finishing of this move.
	 * 
	 * @param dir The direction moving in.
	 */
	private void releaseDirection(DIRECTION dir) {
		// Sets boolean to false for moving in given direction.
		// Resets key press length ready for next movement in same direction.
		keyPress[dir.ordinal()] = false;
		evaluateReface(dir);
		pressLength[dir.ordinal()] = 0f;
	}
	
	/**
	 * Evaluates whether the player would like to move, given
	 * how long they pressed the given direction key for.
	 * 
	 * @param dir The direction to move in.
	 */
	private void evaluateMove(DIRECTION dir) {
		// If keypress greater than length required for movement to occur.
		if(pressLength[dir.ordinal()] > PRESS_LENGTH_FOR_MOVEMENT) {
			player.move(dir);
		}
	}
	
	/**
	 * Evaluates whether the player would like to reface, given
	 * how long they pressed the given direction key for.
	 * 
	 * @param dir The direction to move in.
	 */
	private void evaluateReface(DIRECTION dir) {
		// If keypress less than length required for movement to occur.
		if(pressLength[dir.ordinal()] < PRESS_LENGTH_FOR_MOVEMENT) {
			player.changeFacing(dir);
		}
	}
}
