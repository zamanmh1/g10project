package prisonescape.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import prisonescape.game.model.Teleporter;

/**
 * This class allows us to read all of the connection between maps
 * from a file into the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class MapReader {
	
	/**
	 * The object that allows us to read the file.
	 */
	private BufferedReader bReader;
	
	/**
	 * Creates a new MapReader object.
	 * 
	 * @param csvFile The path to the .csv file.
	 */
	public MapReader(String csvFile) {
		// Attempts to create a new reader using the path of the file given.
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Reads the connections between maps and processes them.
	 * 
	 * @return An ArrayList containing all of the teleporters between maps.
	 */
	public ArrayList<Teleporter> readTeleporters() {
		// Creates a new empty list of teleporters.
		ArrayList<Teleporter> teleporters = new ArrayList<Teleporter>();
		// Current line is empty.
		String line = "";
		// Character to split by is comma (as .csv file).
		String splitBy = ",";
				
		// Attempts to read in from the file.
		try {
			// Discards first line, as contains column headings.
			bReader.readLine();
			
			// While there are still Teleporters to be read, read the next one.
			while((line = bReader.readLine()) != null) {
				// Splits the line into an array of strings separating by commas.
				String[] readItem = line.split(splitBy);
				
				// Creates a new teleporter object using the read data.
				Teleporter tele = new Teleporter(readItem[0], Integer.parseInt(readItem[1]), Integer.parseInt(readItem[2]), Integer.parseInt(readItem[3]), 
						Integer.parseInt(readItem[4]), readItem[5], Integer.parseInt(readItem[6]), Integer.parseInt(readItem[7]), readItem[8]);
				
				// Adds the teleporter to the ArrayList.
				teleporters.add(tele);
			}
			// Returns all teleporters after all have been read.
			return teleporters;
		} 
		// Catches any error which may occur, preventing a crash of the game.
		catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
