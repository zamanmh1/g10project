package com.mygdx.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.game.entities.Item;
import com.mygdx.game.io.ItemReader;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class ItemHandler {
	
	private HashMap<String, Item> items; 
	private ArrayList<Item> foundItems;
	
	public ItemHandler() {
		items = new HashMap<String, Item>();
		foundItems = new ArrayList<Item>();
		
		loadItems(); // Read items in from file.
	}
	
	private void loadItems() {
		ItemReader reader = new ItemReader();
		items = reader.readItems();
	}
	
	public HashMap<String, Item> getAllItems() {
		return items;
	}
	
	public void foundItem(Item i) {
		if (!foundItems.contains(i)) {
			foundItems.add(i); // Add item to found list.
			items.remove(i.getName()); // Remove item from items list.
		}
	}
	
	public ArrayList<Item> getFoundItems() {
		return foundItems;
	}
	
	public Item getFoundItem(String name) {
		for(Item i : foundItems) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
}
