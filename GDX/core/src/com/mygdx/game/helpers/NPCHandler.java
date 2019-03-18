package com.mygdx.game.helpers;

import java.util.HashMap;

import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.io.NPCReader;
import com.mygdx.prisonescapegame.GameController;

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
