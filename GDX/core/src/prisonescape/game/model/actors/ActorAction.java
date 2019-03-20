package prisonescape.game.model.actors;

/**
 * This class specifies the methods required for a NPC within the game, which allow
 * for it to behave in its own way.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public abstract class ActorAction {
	
	/**
	 * The actor object of the NPC.
	 */
	private Actor actor;
	
	/**
	 * Create a new action for a given NPC.
	 * 
	 * @param a The NPC.
	 */
	public ActorAction(Actor a) {
		this.actor = a;
	}
	
	/**
	 * This method controls the execution of an NPC.
	 * 
	 * @param delta The time taken since last frame was rendered.
	 */
	public abstract void update(float delta);
	
	/**
	 * Retrieves the actor of the NPC.
	 * 
	 * @return The NPC actor.
	 */
	public Actor getActor() {
		return this.actor;
	}	
	
	/**
	 * The map which the actor appears in.
	 * 
	 * @return File path of map appearing in.
	 */
	public abstract String getAppearsIn();
	
	/**
	 * Name of the NPC.
	 * 
	 * @return NPC's name.
	 */
	public abstract String getActionFor();
}
