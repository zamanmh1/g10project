package com.mygdx.game.entities;

import com.mygdx.game.util.ActorAnimation;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.prisonescapegame.screens.Map;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class Actor implements MapActor {
	
	private Map currentMap;
	private int x, y; // Coordinates in model.
	private DIRECTION facing;
	
	private float worldX, worldY; // Coordinates in map.
	
	private int origX, origY; // Coordinates of movement origin.
	private int destX, destY; // Coordinates of movement destination.
	private float animTimer;
	private float WALK_TIME_PER_TILE = 0.4f; // Time to move between two tiles.
	private float REFACE_TIME = 0.1f; // Time taken to change direction.
	
	private float walkTimer;
	private boolean movementRequest; // Signals that player wants to move.
	
	private ACTOR_STATE currentState; // What the player is currently doing.
	
	private ActorAnimation animations;
	
	public Actor(int x, int y, ActorAnimation animations, PrisonEscapeGame game)
	{
		this.currentMap = new Map(this, game);
		this.x = x;
		this.y = y;
		this.worldX = x * GameSettings.TILE_SIZE; // World coordinates adjusted for tile size.
		this.worldY = y * GameSettings.TILE_SIZE; // World coordinates adjusted for tile size.
		this.animations = animations;
		this.currentState = ACTOR_STATE.STANDING;
		this.facing = DIRECTION.SOUTH;
	}
	
	public enum ACTOR_STATE {
		WALKING,
		STANDING,
		REFACING,
		;
	}
	
	
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
	
	public boolean move(DIRECTION dir) {
		// If already moving, submit movement request.
		if(currentState == ACTOR_STATE.WALKING) {
			if (facing == dir) {
				movementRequest = true;
			}
			return false;
		}
		// If actor already in tile.
		if(currentMap.getTiledModel().getTile(x + (dir.getMoveX()) , y + dir.getMoveY()).getActor() != null) {
			changeFacing(dir);
			return false;
		}
		// If tile not walkable.
		if(currentMap.getTiledModel().getTile(x + dir.getMoveX(), y + dir.getMoveY()).getWalkable() == false) {
			changeFacing(dir);
			return false;
		}		
		// Prepare for incoming move.
		prepareMove(dir);
		currentMap.getTiledModel().getTile(getX(), getY()).setActor(null);	// Moving out of tile in model.
		// Get detected movement.
		x = x + dir.getMoveX();
		y = y + dir.getMoveY();
		currentMap.getTiledModel().getTile(getX(), getY()).setActor(this); // Move into tile in model.
		
		return true;
	}	
	
	public boolean changeFacing(DIRECTION dir) {
		if (currentState != ACTOR_STATE.STANDING) {
			return false;
		}
		if (facing == dir) {
			return false;
		}
		facing = dir; // Face direction provided.
		currentState = ACTOR_STATE.REFACING; // State changed to refacing.
		animTimer = 0f; // Animation completed.
		return true;
	}
	
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
		this.currentState = ACTOR_STATE.WALKING;
	}
	
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
	
	public void setMap(String maps, GameHandler gameHandler) {
		currentMap.setMap(maps, gameHandler);
	}

	public Map getCurrentMap() {
		return currentMap;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getWorldX() {
		return worldX;
	}
	
	public float getWorldY() {
		return worldY;
	}
	
	public TextureRegion getSprite() {
		// Which texture to display at which time depending on state, direction facing and animation timer.
		if(currentState == ACTOR_STATE.WALKING) {
			return (TextureRegion) animations.getWalking(facing).getKeyFrame(walkTimer);
		} else if (currentState == ACTOR_STATE.STANDING) {
			return animations.getStanding(facing);
		} else if (currentState == ACTOR_STATE.REFACING) {
			return (TextureRegion) animations.getWalking(facing).getKeyFrames()[0];
		}
		return null;		
	}
	
	public DIRECTION getFacing() {
		return this.facing;
	}
}