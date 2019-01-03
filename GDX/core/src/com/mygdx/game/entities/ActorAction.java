package com.mygdx.game.entities;

public abstract class ActorAction {
	
	private Actor actor;
	
	public ActorAction(Actor a) {
		this.actor = a;
	}
	
	public abstract void update(float delta);
	
	public Actor getActor() {
		return this.actor;
	}	
	
	public abstract String getAppearsIn();
	
	public abstract String getActionFor();
}
