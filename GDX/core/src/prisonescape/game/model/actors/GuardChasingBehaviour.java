package prisonescape.game.model.actors;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import prisonescape.game.GameHandler;
import prisonescape.game.model.Tile;
import prisonescape.game.model.TiledModel;
import prisonescape.game.model.actors.Actor.ACTOR_STATE;

/**
 * This class encapsulates the logic of a Guard who is chasing an Actor within the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class GuardChasingBehaviour extends ActorAction {
	
	/**
	 * The actor which the guard is chasing.
	 */
	private Actor chasing;
	
	/**
	 * The current move for the guard to make.
	 */
	private GridPoint2 thisMove;
	
	/**
	 * A random object.
	 */
	private Random random;
	
	/**
	 * The guard's name.
	 */
	private String name;
	
	/**
	 * The map which the guard appears in.
	 */
	private String appearsIn;
	
	/**
	 * The calculated wait time between moves.
	 */
	private float currentWaitTime;
	
	/**
	 * The current wait time between moves.
	 */
	private float timer;
	
	/**
	 * The current game state.
	 */
	private GameHandler controller;
	
	/**
	 * Creates a new guard chasing behaviour.
	 * 
	 * @param name Guard's name.
	 * @param appearsIn File path of map appearing in.
	 * @param a Guard actor.
	 * @param player Actor chasing.
	 * @param controller Current game state.
	 */
	public GuardChasingBehaviour(String name, String appearsIn, Actor a, Actor player, GameHandler controller) {
		super(a);
		this.thisMove = new GridPoint2();
		this.chasing = player;
		this.random = new Random();
		this.currentWaitTime = calculateWaitTime();
		this.timer = 0f;
		this.name = name;
		this.appearsIn = appearsIn;
		this.controller = controller;
		
		// Calculate spawn location and position the guard accordingly.
		setSpawnLocation();
	}
	
	/**
	 * Finds all of the possible spawn locations for the guard and selects one randomly.
	 */
	private void setSpawnLocation() {
		// All possible spawn location.
		ArrayList<GridPoint2> positions = new ArrayList<GridPoint2>();
		// Model of map spawning in.
		TiledModel model = getActor().gameHandler.getMapScreen().getTiledModel();	
		
		// Location where player was spotted.
		int playerSpottedX = getActor().getX();
		int playerSpottedY = getActor().getY();
		
		// Iterates over 3x3 grid surrounding actor spotted location.
		for (int diffX = -1; diffX < 2; diffX++) {
			for (int diffY = -1; diffY < 2; diffY++) {
				int spawnX = playerSpottedX + diffX;
				int spawnY = playerSpottedY + diffY;
				Tile position = model.getTile(spawnX, spawnY);
				// If the location is a valid spawn location, add to list of possible locations.
				if (isValidLocation(position) ) {
					positions.add(new GridPoint2(spawnX, spawnY));
				}
			}
		}		
		
		// Select random location from list of valid locations.
		int randomPosition = random.nextInt(positions.size());
		GridPoint2 selected = positions.get(randomPosition);
		
		// Teleport guard to selected spawn point.
		getActor().teleport(selected.x, selected.y);
	}
	
	/**
	 * Retrieves whether a Tile is a suitable spawn location.
	 * 
	 * @param target Target spawn tile.
	 * 
	 * @return True if suitable, otherwise false.
	 */
	private boolean isValidLocation(Tile target) {		
		if (target.getWalkable() == true && target.getActor() == null) {
			return true;
		}
		return false;
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
			// Compute difference in location between guard and actor chasing.
			int diffX = getActor().getX() - chasing.getX();
			int diffY = getActor().getY() - chasing.getY();
			
			DIRECTION moveDir = null;
			
			// If can only move one direction towards player.
			if (diffX == 0 || diffY == 0) {
				// Select direction which can move.
				if (diffX == 0) {
					if (diffY < 0) {
						moveDir = DIRECTION.NORTH;
					} else {
						moveDir = DIRECTION.SOUTH;
					}
				} else {
					if (diffX < 0) {
						moveDir = DIRECTION.EAST;
					} else {
						moveDir = DIRECTION.WEST;
					}
				}				
			} 
			// If player location different in both direction.
			else {
				// Choose random direction for guard to move.
				boolean axisToMove = random.nextBoolean(); 
				if (axisToMove == true) {
					if (diffY < 0) {
						moveDir = DIRECTION.NORTH;
					} else {
						moveDir = DIRECTION.SOUTH;
					}
				} else {
					if (diffX < 0) {
						moveDir = DIRECTION.EAST;
					} else {
						moveDir = DIRECTION.WEST;
					}
				}
			}			
			
			// If move direction found.
			if (moveDir != null) {
				// Update the direction the guard is facing.
				getActor().changeFacing(moveDir);	

				// Attempt to move in given direction.
				boolean moved = getActor().move(moveDir);
				// If move successful, update guard's state.
				if(moved) {
					thisMove.x += moveDir.getMoveX();
					thisMove.y += moveDir.getMoveY();
				}
			}			
			
			// Retrieves tile facing.
			Tile target = controller.getMapScreen().getTiledModel().getTile(getActor().getX() + getActor().getFacing().getMoveX(), getActor().getY() + getActor().getFacing().getMoveY());
			// If there is another actor in the tile.
			if (target.getActor() != null) {
				MapActor interactingActor = target.getActor();
				// If another actor.
				if(interactingActor instanceof Actor) {
					Actor a = (Actor) interactingActor;
					// And actor is the actor which the guard is chasing, then catch the player.
					if (a.equals(chasing)) {						
						// Stop player from being able to move, force them to face guard.
						// Multiple calls ensure that refaced correctly.						
						chasing.setFrozen(true);
						chasing.setFrozen(false);
						chasing.changeFacing(DIRECTION.getBehind(getActor().getFacing()));
						chasing.setFrozen(true);
						
						// Delay catching to make it clear that player has been caught.
						long delay = 5;	
						Timer.schedule(new Task(){
						    @Override
						    public void run() {
						    	controller.getAlarm().playerCaught();
						    }
						}, delay);
					}
				}
			}			
			// Calculate time to next move and reset time since last move.
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		}
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
		return this.name;
	}
	
	/**
	 * Compute time between previous and next move.
	 * 
	 * @return Time between moves.
	 */
	private float calculateWaitTime() {
		float waitTime = random.nextFloat() * 1 + 0.25f;
		return waitTime;
	}
}
