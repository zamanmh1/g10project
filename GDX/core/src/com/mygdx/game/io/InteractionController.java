package com.mygdx.game.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.entities.DIRECTION;
import com.mygdx.game.entities.Item;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Tile;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.Dialogue;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.screens.MapScreen;
import com.mygdx.prisonescapegame.screens.MainMenuScreen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

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
		if (actor.getFrozen()) {
			return false;
		}
		if (keycode == Keys.E) {			
			Tile target = gameHandler.getMapScreen().getTiledModel().getTile(actor.getX() + actor.getFacing().getMoveX(), actor.getY() + actor.getFacing().getMoveY()); // If player facing actor to interact with.

			// If tile facing is a teleporter tile.
			if (target.getTeleporter() == true) {
				// Get whether moving forwards or backwards in teleporter.
				try {
					Item i = gameHandler.getItemHandler().getFoundItem("Book"); 

					if (i != null) {
						String telType = target.getTeleporterType();

						// Get current map.
						Map currentMap = gameHandler.getMapHandler().getMap(gameHandler.getMapHandler().getCurrentMap(), telType);
						// Get map moving to.
						Map movingTo = gameHandler.getMapHandler().getMap(currentMap.getLeadsTo(), telType);

						gameHandler.setMap(movingTo.getFileLocation(), movingTo.getSpawnX(), movingTo.getSpawnY());
						actor.changeFacing(movingTo.getDirection());
						gameHandler.getMapHandler().setCurrentMap(movingTo.getName());

					} 
				}catch (NullPointerException e) {

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
					if(a.getFrozen() == false) {	
						if(d.hasDialogue(action.getActionFor()))
						{
							a.changeFacing(DIRECTION.getBehind(actor.getFacing()));		
							a.setFrozen(true);
							dBox.showDialogue(MapScreen.getStage(), action.getActionFor());
						}
					} else {
						a.setFrozen(false);
					}
					return true;
				
				// If interacting with an item.
				} else if (interactingActor instanceof Item) {
					Item i = (Item) interactingActor;
					// Interact with Item
					gameHandler.getItemHandler().foundItem(i); // Set item as found.
					gameHandler.getMapScreen().getTiledModel().getTile(i.getX(), i.getY()).setActor(null); // Remove from tile in model.
					gameHandler.removeActor(i); // Remove from list of actors.
					if(d.hasDialogue(i.getName())) //Null pointer safety in case dialogue doesn't exist
					{
						dBox.showDialogue(MapScreen.getStage(), i.getName());
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
