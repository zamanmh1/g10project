package com.mygdx.game.entities;

import java.util.Random;

import com.badlogic.gdx.ai.btree.leaf.Wait;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.entities.Actor.ACTOR_STATE;
import com.mygdx.game.model.Tile;
import com.mygdx.game.model.TiledModel;
import com.mygdx.prisonescapegame.GameHandler;

public class GuardChasingBehaviour extends ActorAction {
	private Actor chasing;
	private GridPoint2 thisMove;
	private Random random;	
	private String name;
	private String appearsIn;
	private float currentWaitTime;
	private float timer;
	private GameHandler controller;
	
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
		
		setSpawnLocation();
	}
	
	private void setSpawnLocation() {
		int location = random.nextInt(7);		
		int X = chasing.getX();
		int Y = chasing.getY();
		
		if (location == 0) {
			Y += 1;
		} else if (location == 1) {
			X += 1;
			Y += 1;
		} else if (location == 2) {
			X +=1;
		} else if (location == 3) {
			X +=1;
			Y -=1;
		} else if (location == 4) {
			Y -=1;
		} else if (location == 5) {
			X -=1;
			Y -=1;
		} else if (location == 6) {
			X -=1;
		} else {
			X -=1;
			Y +=1;
		}
		
		if (isValidLocation(X, Y)) {
			getActor().teleport(X, Y);
		}
	}
	
	private boolean isValidLocation(int x, int y) {
		TiledModel model = super.getActor().gameHandler.getMapScreen().getTiledModel();
		
		Tile target = (model.getTile(x, y));
		
		if (target.getWalkable() == true &&
				target.getActor() == null) {
			return true;
		}
		return false;
	}

	@Override
	public void update(float delta) {
		if(getActor().getState() != ACTOR_STATE.STANDING) {
			return;
		}
		
		timer += delta;
		if(timer >= currentWaitTime) {
			int diffX = getActor().getX() - chasing.getX();
			int diffY = getActor().getY() - chasing.getY();
			
			//int axis = random.nextInt(2);
			DIRECTION moveDir = null;
			
			
			if (diffX == 0 || diffY == 0) {
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
			} else {
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
			
			if (moveDir != null) {
				getActor().changeFacing(moveDir);	

				boolean moved = getActor().move(moveDir);
				if(moved) {
					thisMove.x += moveDir.getMoveX();
					thisMove.y += moveDir.getMoveY();
				}
			}			
			
			Tile target = controller.getMapScreen().getTiledModel().getTile(getActor().getX() + getActor().getFacing().getMoveX(), getActor().getY() + getActor().getFacing().getMoveY());
			if (target.getActor() != null) {
				MapActor interactingActor = target.getActor();
				if(interactingActor instanceof Actor) {
					Actor a = (Actor) interactingActor;
					if (a.equals(chasing)) {	
						
						float delay = 2;

						Timer.schedule(new Task(){
						    @Override
						    public void run() {
						    	// !!! Need to freeze player controls.
						    	plyerCaught();
						    }
						}, delay);					
					}
				}
			}			
			
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		}
	}
	
	public void plyerCaught() {
    	controller.getAlarm().playerCaught();
		chasing.teleport(3,1);
	}

	@Override
	public String getAppearsIn() {
		return this.appearsIn;
	}

	@Override
	public String getActionFor() {
		return this.name;
	}
	
	private float calculateWaitTime() {
		float waitTime = random.nextFloat() * 1 + 1;
		return waitTime;
	}
}
