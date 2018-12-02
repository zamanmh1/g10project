package com.mygdx.game.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.model.Tile;
import com.mygdx.prisonescapegame.Dialogue;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.screens.Map;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward, Sean Corcoran
 * 
 * @version 0.3
 * @since 0.2
 * 
 */

public class InteractionController extends InputAdapter {
	private Actor actor;
	private GameHandler gameHandler;
	private Dialogue d = new Dialogue();
	private DialogueUI dBox = new DialogueUI();
	
	public InteractionController(Actor actor) {
		this.actor = actor;
	}
	
	public void setItemHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// E is use key for now.
		// Triggered upon key release (only called once).
		if (keycode == Keys.E) {			
			Tile target = actor.getCurrentMap().getTiledModel().getTile(actor.getX() + actor.getFacing().getMoveX(), actor.getY() + actor.getFacing().getMoveY()); // If player facing actor to interact with.
			// If actor in tile facing.
			if(target.getActor() != null) {
				MapActor interactingActor = target.getActor();
				
				// If interacting with an Actor.
				if(interactingActor instanceof Actor) {
					Actor a = (Actor) interactingActor;
					// Interact with Actor
					
					return true;
				
				// If interacting with an item.
				} else if (interactingActor instanceof Item) {
					Item i = (Item) interactingActor;
					// Interact with Item
					gameHandler.getItemHandler().foundItem(i); // Set item as found.
					actor.getCurrentMap().getTiledModel().getTile(i.getX(), i.getY()).setActor(null); // Remove from tile in model.
					gameHandler.removeActor(i); // Remove from list of actors.
					if(d.hasDialogue(i.getName())) //Null pointer safety in case dialogue doesn't exist
					{
						dBox.showDialogue(Map.getStage(), i.getName());
						//System.out.println(d.getDialogue(i.getName()));
					}
					
					return true;
				}
			}
			return false;
		}			
		return false;
	}
}
