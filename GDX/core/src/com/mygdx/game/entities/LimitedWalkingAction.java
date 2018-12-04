package com.mygdx.game.entities;

import java.util.Random;

import com.badlogic.gdx.math.GridPoint2;
import com.mygdx.game.entities.Actor.ACTOR_STATE;

public class LimitedWalkingAction extends ActorAction {

	private int limitNorth;
	private int limitSouth;
	private int limitEast;
	private int limitWest;
	
	private float minMoveInterval;
	private float maxMoveInterval;
	
	private float currentWaitTime;
	private float timer;
	
	private GridPoint2 thisMove;
	
	private Random random;
	
	private String appearsIn;
	private String actionFor;
	
	public LimitedWalkingAction(Actor a, String appearsIn, int limitNorth, int limitSouth, int limitEast, int limitWest, float minMoveInterval, float maxMoveInterval, Random random, String actionFor) {
		super(a);
		this.actionFor = actionFor;
		this.appearsIn = appearsIn;
		this.limitNorth = limitNorth;
		this.limitSouth = limitSouth;
		this.limitEast = limitEast;
		this.limitSouth = limitSouth;
		this.minMoveInterval = minMoveInterval;
		this.maxMoveInterval = maxMoveInterval;
		this.random = random;
		this.currentWaitTime = calculateWaitTime();
		this.timer = 0f;
		this.thisMove = new GridPoint2();
	}

	public void update(float delta) {
		if(getActor().getState() != ACTOR_STATE.STANDING) {
			return;
		}
		
		timer += delta;
		if(timer >= currentWaitTime) {
			int direction = random.nextInt(DIRECTION.values().length);
			DIRECTION moveDir = DIRECTION.values()[direction];
			if(((thisMove.x + moveDir.getMoveX()) > limitEast) ||
					(-(thisMove.x + moveDir.getMoveX()) > limitWest) ||
						((thisMove.y + moveDir.getMoveY()) > limitNorth) ||
							(-(thisMove.y + moveDir.getMoveY()) > limitSouth)) {
				getActor().changeFacing(moveDir);
				currentWaitTime = calculateWaitTime();
				timer = 0.0f;
				return;
			}
			
			boolean moved = getActor().move(moveDir);
			if(moved) {
				thisMove.x += moveDir.getMoveX();
				thisMove.y += moveDir.getMoveY();
			}
			
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		}
	}
	
	private float calculateWaitTime() {
		float waitTime = random.nextFloat() * (maxMoveInterval - minMoveInterval) + minMoveInterval;
		return waitTime;
	}
	
	public String getAppearsIn() {
		return this.appearsIn;
	}
	
	public String getActionFor() {
		return this.actionFor;
	}
}
