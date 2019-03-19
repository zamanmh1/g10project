package prisonescape.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import prisonescape.game.GameController;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.ActorAction;
import prisonescape.game.model.actors.LimitedWalkingAction;
import prisonescape.game.util.ActorAnimation;

/**
 * This class allows us to read the NPCs from a file into the game.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 0.2
 * 
 */

public class NPCReader {

	/**
	 * The object that allows us to read the file.
	 */
	private BufferedReader bReader;

	/**
	 * The reference to the current game state.
	 */
	private GameController controller;

	/**
	 * Creates a new NPCReader object.
	 * 
	 * @param controller The current game state.
	 */
	public NPCReader(GameController controller) {
		this.controller = controller;
		
		// The path to the file containing the game's NPCs.
		String csvFile = "data/loader/npcs.csv";
		
		// Attempts to create a new reader using the path of the file required.
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Reads the NPCs from the file and processes them.
	 * 
	 * @return A HashMap with the NPC name as the Key and the Action as the value.
	 */
	public HashMap<String, ActorAction> readNPCs(){
		// Creates a new, empty HashMap.
		HashMap<String, ActorAction> actions = new HashMap<String, ActorAction>();
		// Current line is empty.
		String line = "";
		// Character to split by is comma (as .csv file).
		String splitBy = ",";

		// Attempts to read in from the file.
		try {
			// Find the texture atlas from the asset manager, so that textures can be loaded.
			AssetManager assetManager = controller.getGame().getAssetManager();
			TextureAtlas atlas = assetManager.get("data/packed/textures.atlas", TextureAtlas.class);
			
			// Discards the first line, as has column headings.
			bReader.readLine();
			
			// While there are still NPCs to be read, read the next one.
			while((line = bReader.readLine()) != null) {
				// Splits the line into an array of strings separating by commas.
				String[] readItem = line.split(splitBy);

				// Creates the animations for the NPC using the prefix given.
				ActorAnimation animations = new ActorAnimation(
						new Animation<Object>(0.3f/2f, atlas.findRegions(readItem[1] + "walk_north"), PlayMode.LOOP_PINGPONG),
						new Animation<Object>(0.3f/2f, atlas.findRegions(readItem[1] + "walk_south"), PlayMode.LOOP_PINGPONG),
						new Animation<Object>(0.3f/2f, atlas.findRegions(readItem[1] + "walk_east"), PlayMode.LOOP_PINGPONG),
						new Animation<Object>(0.3f/2f, atlas.findRegions(readItem[1] + "walk_west"), PlayMode.LOOP_PINGPONG),
						atlas.findRegion(readItem[1] + "stand_north"),
						atlas.findRegion(readItem[1] + "stand_south"),
						atlas.findRegion(readItem[1] + "stand_east"),
						atlas.findRegion(readItem[1] + "stand_west")
						);

				// Creates an Actor object from the read items.
				Actor a = new Actor(Integer.parseInt(readItem[3]), Integer.parseInt(readItem[4]), animations, controller);
				
				// Then creates a new LimitedWalkingAction using the Actor and morr read items.
				LimitedWalkingAction action = new LimitedWalkingAction(a, readItem[2], Integer.parseInt(readItem[5]), Integer.parseInt(readItem[6]), Integer.parseInt(readItem[7]), Integer.parseInt(readItem[8]), Float.parseFloat(readItem[9]), Float.parseFloat(readItem[10]), new Random(Integer.parseInt(readItem[11])), readItem[0]);
				
				// Finally adds to the HashMap.
				actions.put(action.getActionFor(), action);
			}
			// Returns after all NPCs have been read from the file.
			return actions;
		} 
		// Catches any error which may occur, preventing a crash of the game.
		catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
