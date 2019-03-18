package prisonescape.game.helpers;

import java.util.HashMap;

import prisonescape.game.GameController;
import prisonescape.game.io.NPCReader;
import prisonescape.game.model.actors.ActorAction;

public class NPCHandler {

	private HashMap<String, ActorAction> npcs; 

	public NPCHandler(GameController game) {
		npcs = new HashMap<String, ActorAction>();

		loadNPCs(game);
	}

	private void loadNPCs(GameController game) {
		NPCReader reader = new NPCReader(game);
		npcs = reader.readNPCs();
	}

	public HashMap<String, ActorAction> getAllNPCs() {
		return npcs;
	}
}
