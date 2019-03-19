package prisonescape.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import prisonescape.game.io.ItemReader;
import prisonescape.game.model.actors.Item;

/**
 * A class to handle all of the items while the game is running.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class ItemHandler {
	
	/**
	 * A collection of all yet to be found items within the game.
	 */
	private HashMap<String, Item> items; 
	
	/**
	 * A collection of all found items within the game.
	 */
	private ArrayList<Item> foundItems;
	
	/**
	 * Creates a new ItemHandler object.
	 */
	public ItemHandler() {
		items = new HashMap<String, Item>();
		foundItems = new ArrayList<Item>();
		
		// Read items in from file.
		loadItems();
	}
	
	/**
	 * Loads the items into the ItemHandler using an ItemReader.
	 */
	private void loadItems() {
		ItemReader reader = new ItemReader();
		items = reader.readItems();
	}
	
	/**
	 * Retrieves all of the yet to be found items.
	 * 
	 * @return HashMap of all yet to be found items.
	 */
	public HashMap<String, Item> getAllItems() {
		return items;
	}
	
	/**
	 * Finds item and processes this action.
	 * 
	 * @param i The found item.
	 */
	public void foundItem(Item i) {
		if (!foundItems.contains(i)) {
			// Add item to found list.
			foundItems.add(i);
			// Remove item from items list.
			items.remove(i.getName());
		}
	}
	
	/**
	 * Retrieves a list of all found items.
	 * 
	 * @return ArrayList of found items.
	 */
	public ArrayList<Item> getFoundItems() {
		return foundItems;
	}
	
	/**
	 * Retrieves an item given a name, if it has already been found.
	 * 
	 * @param name The item's name.
	 * 
	 * @return The item.
	 */
	public Item getFoundItem(String name) {
		for(Item i : foundItems) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Adds an item to the list of yet to be found items.
	 * 
	 * @param name The item's name.
	 * @param item The item.
	 */
	public void addItem(String name, Item item)
	{
		items.put(name, item);
	}
	
	/**
	 * Removes an item from the yet to be found items.
	 * 
	 * @param name The item's name.
	 */
	public void removeItem(String name)
	{
		items.remove(name);
	}
	
	/**
	 * Finds whether a yet to be found item exists.
	 * 
	 * @param name The item's name.
	 * 
	 * @return The item.
	 */
	public boolean itemExists(String name)
	{
		return items.containsKey(name);
	}
}
