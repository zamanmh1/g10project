package prisonescape.game.model.actors;

import java.util.Random;

import com.badlogic.gdx.math.GridPoint2;

import prisonescape.game.model.actors.Actor.ACTOR_STATE;

/**
 * This class encapsulates the logic of an NPC who is appearing to move around
 * in the environment.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class LimitedWalkingAction extends ActorAction {

	/**
	 * The limit that the actor is able to move in each direction.
	 */
	private int limitNorth;
	private int limitSouth;
	private int limitEast;
	private int limitWest;
	
	/**
	 * The minimum and maximum time possible between moves.
	 */
	private float minMoveInterval;
	private float maxMoveInterval;
	
	/**
	 * The calculated wait time between moves.
	 */
	private float currentWaitTime;
	
	/**
	 * The current wait time between moves.
	 */
	private float timer;
	
	/**
	 * The current move for the NPC to make.
	 */
	private GridPoint2 thisMove;
	
	/**
	 * A random object.
	 */
	private Random random;
	
	/**
	 * The NPC's name.
	 */
	private String actionFor;
	
	/**
	 * The map which the NPC appears in.
	 */
	private String appearsIn;
	
	/**
	 * Creates a new LimitedWalkingAction.
	 * 
	 * @param a NPC actor.
	 * @param appearsIn File path of map appearing in.
	 * @param limitNorth Limit to move in north direction.
	 * @param limitSouth Limit to move in south direction.
	 * @param limitEast Limit to move in east direction.
	 * @param limitWest Limit to move in west direction.
	 * @param minMoveInterval Minimum time before move can occur.
	 * @param maxMoveInterval Maximum time before a move must occur.
	 * @param random Random object.
	 * @param actionFor NPC name.
	 */
	public LimitedWalkingAction(Actor a, String appearsIn, int limitNorth, int limitSouth, int limitEast, int limitWest, float minMoveInterval, float maxMoveInterval, Random random, String actionFor) {
		super(a);
		this.actionFor = actionFor;
		this.appearsIn = appearsIn;
		this.limitNorth = limitNorth;
		this.limitSouth = limitSouth;
		this.limitEast = limitEast;
		this.limitWest = limitWest;
		this.minMoveInterval = minMoveInterval;
		this.maxMoveInterval = maxMoveInterval;
		this.random = random;
		this.currentWaitTime = calculateWaitTime();
		this.timer = 0f;
		this.thisMove = new GridPoint2();
	}

	/**
	 * This method controls the execution of the guard.
	 * Overrides the regular execution of an actor.
	 * 
	 * @param delta The time taken since last frame was rendered.
	 */
	@Override
	public void update(float delta) {
		// If actor isn't standing, can't proceed with move.
		if(getActor().getState() != ACTOR_STATE.STANDING) {
			return;
		}
		// Update for given time.
		timer += delta;
		if(timer >= currentWaitTime) {
			// Randomly decide direction to move in.
			int direction = random.nextInt(DIRECTION.values().length);
			DIRECTION moveDir = DIRECTION.values()[direction];
			
			// If move greater than limit in given direction, then simply reface to look that way.
			// Then prepare for another move.
			if(thisMove.x + moveDir.getMoveX() > limitEast ||
					-(thisMove.x + moveDir.getMoveX()) > limitWest ||
					thisMove.y + moveDir.getMoveY() > limitNorth ||
					-(thisMove.y + moveDir.getMoveY()) > limitSouth) {
				getActor().changeFacing(moveDir);
				currentWaitTime = calculateWaitTime();
				timer = 0.0f;
				return;
			}
			
			// Attempt to move in given direction. 
			boolean moved = getActor().move(moveDir);
			// If move successful, update NPC's state.
			if(moved) {
				thisMove.x += moveDir.getMoveX();
				thisMove.y += moveDir.getMoveY();
			}
			// Calculate time to next move and reset time since last move.
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		}
	}
	
	/**
	 * Compute time between previous and next move.
	 * 
	 * @return Time between moves.
	 */
	private float calculateWaitTime() {
		float waitTime = random.nextFloat() * (maxMoveInterval - minMoveInterval) + minMoveInterval;
		return waitTime;
	}
	
	/**
	 * The map which the actor appears in.
	 * 
	 * @return File path of map appearing in.
	 */
	@Override
	public String getAppearsIn() {
		return this.appearsIn;
	}

	/**
	 * Name of the NPC.
	 * 
	 * @return NPC's name.
	 */
	@Override
	public String getActionFor() {
		return this.actionFor;
	}
}
