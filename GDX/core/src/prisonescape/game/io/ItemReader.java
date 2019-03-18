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
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.2
 * 
 */

public class ItemReader {
	private String csvFile = "data/loader/items.csv";
	private BufferedReader bReader;
	
	public ItemReader() {
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public HashMap<String, Item> readItems(){
		HashMap<String, Item> items = new HashMap<String, Item>();
		String line = "";
		String splitBy = ",";
		
		try {
			while((line = bReader.readLine()) != null) {
				String[] readItem = line.split(splitBy);
				
				Item i = new Item(new Sprite(new Texture(Gdx.files.internal(readItem[1]))), readItem[0], readItem[2], readItem[3], Integer.parseInt(readItem[4]), Integer.parseInt(readItem[5]));
				items.put(i.getName(), i);
			}
			return items;
		} catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
