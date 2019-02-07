package com.mygdx.game.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.ActorAction;
import com.mygdx.game.entities.LimitedWalkingAction;
import com.mygdx.game.util.ActorAnimation;
import com.mygdx.prisonescapegame.GameController;

public class NPCReader {

	private String csvFile = "data/loader/npcs.csv";
	private BufferedReader bReader;

	private GameController game;

	public NPCReader(GameController game) {
		try {
			bReader = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}

		this.game = game;
	}

	public HashMap<String, ActorAction> readNPCs(){
		HashMap<String, ActorAction> actions = new HashMap<String, ActorAction>();
		String line = "";
		String splitBy = ",";

		try {
			AssetManager assetManager = game.getGame().getAssetManager();
			TextureAtlas atlas = assetManager.get("data/packed/textures.atlas", TextureAtlas.class);

			while((line = bReader.readLine()) != null) {
				String[] readItem = line.split(splitBy);

				ActorAnimation animations = new ActorAnimation(
						new Animation(0.3f/2f, atlas.findRegions(readItem[1] + "walk_north"), PlayMode.LOOP_PINGPONG),
						new Animation(0.3f/2f, atlas.findRegions(readItem[1] + "walk_south"), PlayMode.LOOP_PINGPONG),
						new Animation(0.3f/2f, atlas.findRegions(readItem[1] + "walk_east"), PlayMode.LOOP_PINGPONG),
						new Animation(0.3f/2f, atlas.findRegions(readItem[1] + "walk_west"), PlayMode.LOOP_PINGPONG),
						atlas.findRegion(readItem[1] + "stand_north"),
						atlas.findRegion(readItem[1] + "stand_south"),
						atlas.findRegion(readItem[1] + "stand_east"),
						atlas.findRegion(readItem[1] + "stand_west")
						);

				Actor a = new Actor(Integer.parseInt(readItem[3]), Integer.parseInt(readItem[4]), animations, game);
				
				LimitedWalkingAction action = new LimitedWalkingAction(a, readItem[2], Integer.parseInt(readItem[5]), Integer.parseInt(readItem[6]), Integer.parseInt(readItem[7]), Integer.parseInt(readItem[8]), Float.parseFloat(readItem[9]), Float.parseFloat(readItem[10]), new Random(Integer.parseInt(readItem[11])), readItem[0]);
				
				actions.put(action.getActionFor(), action);
			}
			return actions;
		} catch (IOException e) {
			System.out.println(e.toString());
			return null;
		}		
	}
}
