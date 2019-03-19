package prisonescape.game.helpers;

import java.util.HashMap;

import prisonescape.game.GameController;
import prisonescape.game.io.NPCReader;
import prisonescape.game.model.actors.ActorAction;

/**
 * A class to handle the storage of all of the NPCs within the game whilst
 * the game is running.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class NPCHandler {
	
	/**
	 * A HashMap to contain all of the NPCs within the game.
	 */
	private HashMap<String, ActorAction> npcs; 

	/**
	 * Creates a new NPCHandler.
	 * 
	 * @param controller The current game state.
	 */
	public NPCHandler(GameController controller) {
		npcs = new HashMap<String, ActorAction>();

		// Loads the NPCs when created.
		loadNPCs(controller);
	}

	/**
	 * Loads all of the NPCs into the NPCHandler using the NPCReader.
	 * 
	 * @param controller The current game state.
	 */
	private void loadNPCs(GameController controller) {
		NPCReader reader = new NPCReader(controller);
		npcs = reader.readNPCs();
	}

	/**
	 * Retrieves all of the NPCs within the game.
	 * 
	 * @return HashMap containing all NPCs.
	 */
	public HashMap<String, ActorAction> getAllNPCs() {
		return npcs;
	}
}
