package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.mygdx.game.util.ActorAnimation;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;

public class AlarmSystem {
	private long alarmStarted;
	private GameHandler controller;
	private GuardChasingBehaviour guard;
	
	public AlarmSystem(GameHandler controller) {
		this.alarmStarted = 0L;
		this.controller = controller;
		this.guard = null;
	}
	
	public void setAlarm(boolean alarm) {
		if (alarm == true) {
			TextureAtlas atlas = controller.getGame().getAssetManager().get("data/packed/textures.atlas", TextureAtlas.class);
			String guardSprite = "guard01_";
			ActorAnimation animations = new ActorAnimation(
					new Animation(0.3f/2f, atlas.findRegions(guardSprite + "walk_north"), PlayMode.LOOP_PINGPONG),
					new Animation(0.3f/2f, atlas.findRegions(guardSprite + "walk_south"), PlayMode.LOOP_PINGPONG),
					new Animation(0.3f/2f, atlas.findRegions(guardSprite + "walk_east"), PlayMode.LOOP_PINGPONG),
					new Animation(0.3f/2f, atlas.findRegions(guardSprite + "walk_west"), PlayMode.LOOP_PINGPONG),
					atlas.findRegion(guardSprite + "stand_north"),
					atlas.findRegion(guardSprite + "stand_south"),
					atlas.findRegion(guardSprite + "stand_east"),
					atlas.findRegion(guardSprite + "stand_west")
					);

			guard = new GuardChasingBehaviour("alarmGuard", controller.getMapHandler().getCurrentMap(), new Actor(controller.getPlayer().getX(), controller.getPlayer().getY(), animations, controller), controller.getPlayer());
			
			controller.addActor(guard.getActor(), guard);
			controller.getMapScreen().addNPCToMap(guard);
			
			alarmStarted = System.currentTimeMillis();			
		} else {						
			controller.removeActor(guard.getActor());
			controller.getMapScreen().removeNPCFromMap(guard);
			guard = null;
			alarmStarted = 0L;		
		}
	}
	
	public void update() {
		if (getAlarm()) {
			long timePassed = System.currentTimeMillis() - alarmStarted;
			long fiveMinutesInMillis = 900000 / GameSettings.TIME_SCALE; // 15 in game minutes.
			
			if (timePassed > fiveMinutesInMillis) {
				setAlarm(false);
			}
		}
	}
	
	public boolean getAlarm() {
		return alarmStarted != 0L; // Checks if the alarm has started or if it is yet to be started.
	}
}
