package prisonescape.game.model.actors;

import prisonescape.game.GameController;
import prisonescape.game.GameSettings;
import prisonescape.game.util.ActorAnimation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

/**
 * This class encapsulates the logic of an Actor within the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.1
 * 
 */

public class Actor implements MapActor {
	
	/**
	 * The current game state.
	 */
	protected GameController gameHandler;
	
	/**
	 * The current coordinates within the map.
	 */
	private int x, y;
	
	/**
	 * The direction currently facing.
	 */
	private DIRECTION facing;
	
	/**
	 * The current coordinates in the map (scaled according to tile size).
	 * @see GameSettings.TILE_SIZE
	 */
	private float worldX, worldY;
	
	/**
	 * Coordinates of movement origin.
	 */
	private int origX, origY;
	
	/**
	 * Coordinates of movement destination.
	 */
	private int destX, destY;
	
	/**
	 * The current animation time.
	 */
	private float animTimer;
	
	/**
	 * The time taken to walk between two tiles.
	 */
	private static float WALK_TIME_PER_TILE = 0.4f;
	
	/**
	 * The time taken to change direction facing.
	 */
	private float REFACE_TIME = 0.1f;
	
	/**
	 * The current time spent walking.	
	 */
	private float walkTimer;
	
	/**
	 * Whether the actor is currently trying to move.
	 */
	private boolean movementRequest;
	
	/**
	 * What the actor is currently doing.
	 */
	private ACTOR_STATE currentState;
	
	/**
	 * The set of animations for this actor.
	 */
	private ActorAnimation animations;
	
	/**
	 * Whether the actor is currently frozen.
	 */
	private boolean isFrozen;
	
	/**
	 * Creates a new Actor.
	 * 
	 * @param x X-Coordinate.
	 * @param y Y-Coordinate.
	 * @param animations Actor's animations.
	 * @param gameHandler Current game state.
	 */
	public Actor(int x, int y, ActorAnimation animations, GameController gameHandler)
	{
		this.gameHandler = gameHandler;
		this.x = x;
		this.y = y;
		this.worldX = x * GameSettings.TILE_SIZE; // World coordinates adjusted for tile size.
		this.worldY = y * GameSettings.TILE_SIZE; // World coordinates adjusted for tile size.
		this.animations = animations;
		this.currentState = ACTOR_STATE.STANDING;
		this.facing = DIRECTION.SOUTH;
		this.isFrozen = false;
	}
	
	/**
	 * This enum holds each of the states an Actor can have.
	 * 
	 * @author Sam Ward
	 * 
	 * @version 1.0
	 * @since 0.2
	 */
	public enum ACTOR_STATE {
		WALKING,
		STANDING,
		REFACING,
		;
	}	
	
	/**
	 * This method controls the execution of an Actor.
	 * 
	 * @param delta The time taken since last frame was rendered.
	 */
	public void update(float delta) {		
		if (currentState == ACTOR_STATE.WALKING) {
			// Update for given time.
			animTimer += delta;		
			walkTimer += delta;

			// Move smoothly between origin and destination.
			// Can change Interpolation for different movement styles.
			worldX = Interpolation.linear.apply(origX, destX, animTimer/WALK_TIME_PER_TILE);
			worldY = Interpolation.linear.apply(origY, destY, animTimer/WALK_TIME_PER_TILE);				

			if(animTimer > WALK_TIME_PER_TILE) {
				// Amount of time animation takes which hasn't been completed.
				float leftOverTime = animTimer - WALK_TIME_PER_TILE;
				// Prepare for next move.
				finishMove();
				if(movementRequest) {
					if (move(facing)) {
						// If animation not fully completed.
						animTimer += leftOverTime;
						worldX = Interpolation.linear.apply(origX, destX, animTimer/WALK_TIME_PER_TILE);
						worldY = Interpolation.linear.apply(origY, destY, animTimer/WALK_TIME_PER_TILE);	
					}
				} else {
					// Finished walking.
					walkTimer = 0f;
				}
			}
		}
		if (currentState == ACTOR_STATE.REFACING) {
			animTimer += delta;
			if (animTimer > REFACE_TIME) {
				// Once finished refacing, actor state becomes standing.
				currentState = ACTOR_STATE.STANDING;
			}
		}

		// Movement request reset.
		movementRequest = false;	
	}
	
	/**
	 * Retrieves whether or not actor is currently frozen.
	 * 
	 * @return True if frozen, otherwise false.
	 */
	public boolean getFrozen() {
		return this.isFrozen;
	}
	
	/**
	 * Update the current frozen state of the actor.
	 * 
	 * @param freeze True if becoming frozen, false to unfreeze.
	 */
	public void setFrozen(boolean freeze) {
		this.isFrozen = freeze;
	}
	
	/**
	 * Attempt to move in a given direction.
	 * 
	 * @param dir The direction to move in.
	 * 
	 * @return Whether the move has been approved and performed.
	 */
	public boolean move(DIRECTION dir) {
		// If actor frozen, return don't allow to move.
		if (isFrozen) {
			return false;
		}
		// If already moving, submit movement request.
		if(currentState == ACTOR_STATE.WALKING) {
			if (facing == dir) {
				movementRequest = true;
			}
			// No move completed.
			return false;
		}
		// If actor already in tile.
		if(gameHandler.getMapScreen().getTiledModel().getTile(x + (dir.getMoveX()) , y + dir.getMoveY()).getActor() != null) {
			// Change to face given direction.
			changeFacing(dir);
			// No move completed.
			return false;
		}
		// If tile not walkable.
		if(gameHandler.getMapScreen().getTiledModel().getTile(x + dir.getMoveX(), y + dir.getMoveY()).getWalkable() == false) {
			// Change to face given direction.
			changeFacing(dir);
			// No move completed.
			return false;
		}		
		// Prepare for incoming move.
		prepareMove(dir);
		gameHandler.getMapScreen().getTiledModel().getTile(getX(), getY()).setActor(null);	// Moving out of tile in model.
		// Get detected movement.
		x = x + dir.getMoveX();
		y = y + dir.getMoveY();
		gameHandler.getMapScreen().getTiledModel().getTile(getX(), getY()).setActor(this); // Move into tile in model.
		
		// Movement has been approved and completed.
		return true;
	}	
	
	/**
	 * Change the direction that the actor is facing.
	 * 
	 * @param dir New direction to face.
	 * 
	 * @return Whether successfully changed direction facing.
	 */
	public boolean changeFacing(DIRECTION dir) {
		// If actor frozen, don't allow.
		if (isFrozen) {
			return false;
		}
		// If actor state not standing, don't allow.
		if (currentState != ACTOR_STATE.STANDING) {
			return false;
		}
		// If already facing given direction, don't allow.
		if (facing == dir) {
			return false;
		}
		facing = dir; // Face direction provided.
		currentState = ACTOR_STATE.REFACING; // State changed to refacing.
		animTimer = 0f; // Animation completed.
		
		// Changed direction facing successfully.
		return true;
	}
	
	
	/**
	 * Setup a move in a given direction.
	 * 
	 * @param dir The direction wanting to mvoe in.
	 */
	private void prepareMove(DIRECTION dir) {
		// Prepare for incoming move.
		// Global variable tile size used to determine distance to move in TiledMap.
		this.facing = dir;
		this.origX = getX() * GameSettings.TILE_SIZE;
		this.origY = getY() * GameSettings.TILE_SIZE;
		this.destX = (getX() * GameSettings.TILE_SIZE) + (dir.getMoveX() * GameSettings.TILE_SIZE);
		this.destY = (getY() * GameSettings.TILE_SIZE) + (dir.getMoveY() * GameSettings.TILE_SIZE);
		this.worldX = getX() * GameSettings.TILE_SIZE;
		this.worldY = getY() * GameSettings.TILE_SIZE;
		this.animTimer = 0f;
		this.currentState = ACTOR_STATE.WALKING; // Actor begins to walk.
	}
	
	/**
	 * Complete a move and reset ready for next move.
	 */
	private void finishMove() {
		// Position in world updated.
		// State, origin and destination reset for next move.
		this.currentState = ACTOR_STATE.STANDING;
		this.worldX = destX;
		this.worldY = destY;
		this.origX = 0;
		this.origY = 0;
		this.destX = 0;
		this.destY = 0;
		
	}
	
	/**
	 * Retrieve actor's current X-Coordinate.
	 * 
	 * @return Current X-Coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Retrieve actor's current Y-Coordinate.
	 * 
	 * @return Current Y-Coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Retrieve actor's current X-Coordinate in the world, scaled to tile size.
	 * 
	 * @return Current X-Coordinate in the world.
	 */
	public float getWorldX() {
		return worldX;
	}
	
	/**
	 * Retrieve actor's current Y-Coordinate in the world, scaled to tile size.
	 * 
	 * @return Current Y-Coordinate in the world.
	 */
	public float getWorldY() {
		return worldY;
	}
	
	/**
	 * Retrieves which texture to display at which time depending on state, direction facing and animation timer.
	 * 
	 * @return The current texture to display.
	 */
	public TextureRegion getSprite() {
		if(currentState == ACTOR_STATE.WALKING) {
			// Finds appropriate texture in walking sequence.
			return (TextureRegion) animations.getWalking(facing).getKeyFrame(walkTimer);
		} else if (currentState == ACTOR_STATE.STANDING) {
			return animations.getStanding(facing);
		} else if (currentState == ACTOR_STATE.REFACING) {
			return (TextureRegion) animations.getWalking(facing).getKeyFrames()[0];
		}
		return null;		
	}
	
	/**
	 * Retrieve the current direction that the actor is facing.
	 * 
	 * @return Direction currently facing.
	 */
	public DIRECTION getFacing() {
		return this.facing;
	}
	
	/**
	 * Teleport the actor to another position in the world.
	 * 
	 * @param x The new X-Coordinate.
	 * @param y The new Y-Coordinate.
	 */
	public void teleport(int x, int y) {
		this.worldX = x * GameSettings.TILE_SIZE;
		this.worldY = y * GameSettings.TILE_SIZE;		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieve the current state of the actor.
	 * 
	 * @return Actor's current state.
	 */
	public ACTOR_STATE getState() {
		return this.currentState;
	}
}