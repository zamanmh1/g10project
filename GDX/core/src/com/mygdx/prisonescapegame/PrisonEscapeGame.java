package com.mygdx.prisonescapegame;

import com.mygdx.game.entities.Actor;
import com.mygdx.game.util.ActorAnimation;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.2
 * @since 0.1
 * 
 */

public class PrisonEscapeGame extends Game {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH =(int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();
	
	private AssetManager assetManager;
	
	private GameController game;
	public Actor player;
	
	@Override
	public void create() {
		// Store texture atlas containing player textures and animations in an AssetManager.
		assetManager = new AssetManager();
		assetManager.load("data/packed/textures.atlas", TextureAtlas.class);
		assetManager.finishLoading();
		
		TextureAtlas atlas = getAssetManager().get("data/packed/textures.atlas", TextureAtlas.class);
		
		// Stores player animations in new AnimationSet.
		ActorAnimation animations = new ActorAnimation(
				new Animation(0.3f/2f, atlas.findRegions("player_walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("player_walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("player_walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("player_walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("player_stand_north"),
				atlas.findRegion("player_stand_south"),
				atlas.findRegion("player_stand_east"),
				atlas.findRegion("player_stand_west")
		);
		
		this.game = new GameHandler(this);
		this.player = new Actor(5, 5, animations, game);
		game.setMap("data/maps/cell.tmx", player.getX(), player.getY());
	}

	@Override
	public void render() {
		super.render();		
	}
	
	public GameController getGameController() {
		return game;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
