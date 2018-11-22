package com.mygdx.game.helpers;

import java.util.HashMap;

import com.mygdx.game.entities.Item;
import com.mygdx.game.io.ItemReader;

public class ItemHandler {
	
	private HashMap<String, Item> items; 
	
	public ItemHandler() {
		items = new HashMap<String, Item>();
		loadItems();
	}
	
	private void loadItems() {
		ItemReader reader = new ItemReader();
		items = reader.readItems();
	}
	
	public HashMap<String, Item> getItems() {
		return items;
	}
	
	public void refreshItems(String map) {
		for (Item i : items.values()) {
			if (i.getAppearsIn().equals(map)) {
				i.getSprite().setPosition(i.getX(), i.getY());
			}
		}
	}
}
