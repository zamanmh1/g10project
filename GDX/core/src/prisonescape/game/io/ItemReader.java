package prisonescape.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import prisonescape.game.model.actors.Item;

/**
 * This class allows all of the in game interactive items to be read
 * from a .csv file into the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class ItemReader {
	
	/**
	 * The object that allows us to read the file.
	 */
	private BufferedReader bReader;
	
	/**
	 * Creates a new ItemReader object.
	 */
	public ItemReader() {
		// The path to the file containing the game's NPCs.
		String csvFile = "data/loader/items.csv";
		
		// Attempts to create a new reader using the path of the file given.
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Reads the Items from the file and processes them.
	 * 
	 * @return A HashMap with the Item name as the Key and the Item as the value.
	 */
	public HashMap<String, Item> readItems(){
		// Creates a new, empty HashMap.
		HashMap<String, Item> items = new HashMap<String, Item>();
		// Current line is empty.
		String line = "";
		// Character to split by is comma (as .csv file).
		String splitBy = ",";
		
		// Attempts to read in from the file.
		try {
			// Discards the first line, as has column headings.
			bReader.readLine();
			
			// While there are still Items to be read, read the next one.
			while((line = bReader.readLine()) != null) {
				// Splits the line into an array of strings separating by commas.
				String[] readItem = line.split(splitBy);
				
				// Creates new Item using the read data.
				Item i = new Item(new Sprite(new Texture(Gdx.files.internal(readItem[1]))), readItem[0], readItem[2], readItem[3], Integer.parseInt(readItem[4]), Integer.parseInt(readItem[5]));
				
				// Adds the item to the HashMap.
				items.put(i.getName(), i);
			}
			// Returns the HashMap once all items have been read.
			return items;
		} 
		// Catches any error which may occur, preventing a crash of the game.
		catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
