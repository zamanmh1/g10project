package com.mygdx.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entities.Item;
import com.mygdx.game.model.Map;

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
	
	public HashMap<String, Map> readMaps(){
		HashMap<String, Map> maps = new HashMap<String, Map>();
		String line = "";
		String splitBy = ",";
		
		try {
			while((line = bReader.readLine()) != null) {
				String[] readItem = line.split(splitBy);
				
				Map m = new Map(readItem[0], readItem[1], Integer.parseInt(readItem[2]), Integer.parseInt(readItem[3]), readItem[4], readItem[5]);
				maps.put(m.getName(), m);
			}
			return maps;
		} catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
