package prisonescape.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import prisonescape.game.model.Teleporter;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class MapReader {
	private BufferedReader bReader;
	
	public MapReader(String csvFile) {
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public ArrayList<Teleporter> readTeleporters(){
		ArrayList<Teleporter> teleporters = new ArrayList<Teleporter>();
		String line = "";
		String splitBy = ",";
				
		try {
			bReader.readLine(); // Discard column titles
			
			while((line = bReader.readLine()) != null) {
				String[] readItem = line.split(splitBy);
				
				Teleporter m = new Teleporter(readItem[0], Integer.parseInt(readItem[1]), Integer.parseInt(readItem[2]), Integer.parseInt(readItem[3]), 
						Integer.parseInt(readItem[4]), readItem[5], Integer.parseInt(readItem[6]), Integer.parseInt(readItem[7]), readItem[8]);
				teleporters.add(m);
			}
			return teleporters;
		} catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
