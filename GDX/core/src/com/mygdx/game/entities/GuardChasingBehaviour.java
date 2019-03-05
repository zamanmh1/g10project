package com.mygdx.game.entities;

import java.util.ArrayList;
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
		ArrayList<GridPoint2> positions = new ArrayList<GridPoint2>();
		TiledModel model = getActor().gameHandler.getMapScreen().getTiledModel();	
		
		int playerX = chasing.getX();
		int playerY = chasing.getY();
		
		for (int diffX = -1; diffX < 2; diffX++) {
			for (int diffY = -1; diffY < 2; diffY++) {
				int spawnX = playerX + diffX;
				int spawnY = playerY + diffY;
				Tile position = model.getTile(spawnX, spawnY);
				if (isValidLocation(position) ) {
					positions.add(new GridPoint2(spawnX, spawnY));
				}
			}
		}		
		
		int randomPosition = random.nextInt(positions.size());
		GridPoint2 selected = positions.get(randomPosition);
		
		getActor().teleport(selected.x, selected.y);
	}
	
	private boolean isValidLocation(Tile target) {		
		if (target.getWalkable() == true && target.getActor() == null) {
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
						    	plyerCaught(); // !!! Need to freeze player controls.
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
    	chasing.changeFacing(DIRECTION.NORTH);
    	controller.setMap("data/maps/cell.tmx", 3, 1);    	
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
