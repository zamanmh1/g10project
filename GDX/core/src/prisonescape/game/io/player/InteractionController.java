package prisonescape.game.io.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import prisonescape.game.GameHandler;
import prisonescape.game.io.DialogueUI;
import prisonescape.game.model.Dialogue;
import prisonescape.game.model.Teleporter;
import prisonescape.game.model.Tile;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.ActorAction;
import prisonescape.game.model.actors.DIRECTION;
import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;

/**
 * Handles what will happen upon pressing the Interact key [E]
 * 
 * @author Sam Ward, Sean Corcoran
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class InteractionController extends InputAdapter {
	private Actor actor;
	private GameHandler gameHandler;
	private Dialogue d;
	private DialogueUI dBox;


	public InteractionController(Actor actor) {
		this.actor = actor;				
	}

	/**
	 * Sets up the Item Handler used for when interacting with items.
	 * <p>
	 * Also sets the Dialogue and DialogueUI objects used in creating any dialogue when in an interaction
	 * @param gameHandler
	 */
	public void setItemHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
		dBox = new DialogueUI(gameHandler);
		d = new Dialogue(gameHandler);
	}


	@Override
	public boolean keyUp(int keycode) {
		// E is use key for now.
		// Triggered upon key release (only called once).
		if (keycode == Keys.E) {			
			Tile target = gameHandler.getMapScreen().getTiledModel().getTile(actor.getX() + actor.getFacing().getMoveX(), actor.getY() + actor.getFacing().getMoveY()); // If player facing actor to interact with.

			// If tile facing is a teleporter tile.

			if (target.getTeleporter() == true) {
				if (gameHandler.getAlarm().alarmTriggered() == false) {
					if (gameHandler.getItemHandler().getFoundItem("Book") != null) {
						// Find the teleporter.
						Teleporter teleporter = gameHandler.getMapHandler().getTeleporter(gameHandler.getMapHandler().getCurrentMap(), actor.getX(), actor.getY());

						// Change showing map and player's location within it.
						gameHandler.setMap(teleporter.getDestination().getFile(), teleporter.getDestination().getPlayerX(), teleporter.getDestination().getPlayerY());
						// Change the direction that the player is facing.
						actor.changeFacing(teleporter.getDestination().getPlayerDirection());
					}
					else if(!dBox.beenCalled() && gameHandler.getItemHandler().getFoundItem("Book") == null)
					{
						dBox.showDialogue(ActiveGame.getStage(), "door");
					}
				} else if (!dBox.beenCalled() && gameHandler.getAlarm().alarmTriggered() == true) {
					dBox.showDialogue(ActiveGame.getStage(), "lockdown");
				}
			}			

			// If actor in tile facing.
			else if(target.getActor() != null) {
				MapActor interactingActor = target.getActor();

				// If interacting with an Actor.
				if(interactingActor instanceof Actor) {
					Actor a = (Actor) interactingActor;
					ActorAction action = gameHandler.getActions().get(a);
					// Interact with Actor
					if(a.getFrozen() != true) {	
						if(d.hasDialogue(action.getActionFor()))
						{
							// Force NPC to face actor, freeze both actors and display dialogue.
							a.changeFacing(DIRECTION.getBehind(actor.getFacing()));		
							a.setFrozen(true);
							dBox.showDialogue(ActiveGame.getStage(), action.getActionFor());
							actor.setFrozen(true);
						}
					} else {
						// Unfreeze both actors when seen dialogue.
						a.setFrozen(false);
						actor.setFrozen(false);
					}
					return true;

					// If interacting with an item.
				} else if (interactingActor instanceof Item) {
					Item i = (Item) interactingActor;
					// Interact with Item
					if (i.getType() == Item.ITEM_TYPE.SLEEP) {		
						if(ActiveGame.h.sleepIsVisible() != true)
						{
							ActiveGame.h.sleepUI();
							ActiveGame.h.showSleepUI();							
						}
					} else if (i.getType() == Item.ITEM_TYPE.INTERACT)
					{
						if(actor.getFrozen() != true)
						{
							if(d.hasDialogue(i.getName()) && !dBox.beenCalled())
							{
								dBox.showDialogue(ActiveGame.getStage(), i.getName());
								actor.setFrozen(true);
							}
						}
						else {
							actor.setFrozen(false);
						}
					} else {
						gameHandler.getItemHandler().foundItem(i); // Set item as found.
						gameHandler.getMapScreen().getTiledModel().getTile(i.getX(), i.getY()).setActor(null); // Remove from tile in model.
						gameHandler.removeActor(i); // Remove from list of actors.
						if(d.hasDialogue(i.getName()) && dBox.beenCalled() == false) //Null pointer safety in case dialogue doesn't exist
						{
							dBox.showDialogue(ActiveGame.getStage(), i.getName());
						}

						return true;
					}
				}
			}
			return false;
		}			
		return false;
	}
}
