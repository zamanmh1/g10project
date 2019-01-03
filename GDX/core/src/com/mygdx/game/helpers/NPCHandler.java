package com.mygdx.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.entities.Item;
import com.mygdx.game.io.ItemReader;
import com.mygdx.game.io.NPCReader;
import com.mygdx.prisonescapegame.GameController;

public class NPCHandler {

	private HashMap<String, ActorAction> npcs; 

	public NPCHandler(GameController game) {
		npcs = new HashMap<String, ActorAction>();

		loadItems(game);
	}

	private void loadItems(GameController game) {
		NPCReader reader = new NPCReader(game);
		npcs = reader.readNPCs();
	}

	public HashMap<String, ActorAction> getAllNPCs() {
		return npcs;
	}
}
