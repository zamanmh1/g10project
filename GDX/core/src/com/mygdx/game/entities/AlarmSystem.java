package com.mygdx.game.entities;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.mygdx.game.util.ActorAnimation;
import com.mygdx.game.util.Time;
import com.mygdx.prisonescapegame.GameHandler;
import com.mygdx.prisonescapegame.GameSettings;

public class AlarmSystem {
	private static long guardSpawnTime = 240000 / GameSettings.TIME_SCALE; // 4 Minutes.
	private static long guardLookTime = guardSpawnTime + (900000 / GameSettings.TIME_SCALE); // 15 Minutes.
	
	private long alarmStarted;
	private GameHandler controller;
	private GuardChasingBehaviour guard;
	private GridPoint2 playerSpottedLoc;
	
	private boolean playerCaught;
	private Sound alarmSound;
	
	public AlarmSystem(GameHandler controller) {
		this.alarmStarted = 0L;
		this.controller = controller;
		this.guard = null;
		alarmSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/Alarm.ogg"));
	}
	
	public void setAlarm(boolean alarm) {
		if (alarm == true) {
			alarmStarted = System.currentTimeMillis();			
			// !!! Start alarm sound?
			alarmSound.play(1f);
			alarmSound.loop();
		} else {
			controller.removeActor(guard.getActor());
			controller.getMapScreen().removeNPCFromMap(guard);
			guard = null;
			playerSpottedLoc = null;
			alarmStarted = 0L;	
			
			if (playerCaught == true) {				
				// Set time to next morning.
				Calendar cal = controller.getTime().getCalendar();
				Time time = Time.getTime(cal);
				time = Time.setTime(cal, 7, 15);
				controller.setTime(time);		
				
				// Move player to cell and unfreeze.
				controller.setMap("data/maps/cell.tmx", 3, 1); 		    	 	   
		    	controller.getPlayer().setFrozen(false);
		    	controller.getPlayer().changeFacing(DIRECTION.NORTH);  
		    	
				// !!! Message to player saying caught?
			} 
			playerCaught = false;
			// !!! End alarm sound?
			alarmSound.stop();
		}
	}
	
	private void spawnGuard() {
		TextureAtlas atlas = controller.getGame().getAssetManager().get("data/packed/textures.atlas", TextureAtlas.class);
		String guardSprite = "guard01_";
		ActorAnimation animations = new ActorAnimation(
				new Animation<Object>(0.3f/2f, atlas.findRegions(guardSprite + "walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions(guardSprite + "walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions(guardSprite + "walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation<Object>(0.3f/2f, atlas.findRegions(guardSprite + "walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion(guardSprite + "stand_north"),
				atlas.findRegion(guardSprite + "stand_south"),
				atlas.findRegion(guardSprite + "stand_east"),
				atlas.findRegion(guardSprite + "stand_west")
				);
		
		guard = new GuardChasingBehaviour("alarmGuard", controller.getMapHandler().getCurrentMap(), new Actor(playerSpottedLoc.x, playerSpottedLoc.y,
				animations, controller), controller.getPlayer(), controller);
		
		controller.addActor(guard.getActor(), guard);
		controller.getMapScreen().addNPCToMap(guard);
		
		playerCaught = false;			
	}
	
	public void update() {
		if (getAlarm()) {			
			long timePassed = System.currentTimeMillis() - alarmStarted;
			
			if (timePassed > guardSpawnTime && guard == null) {
				spawnGuard();
			} else if (timePassed > guardLookTime || playerCaught == true) {
				setAlarm(false);
			}
		} else if (getAlarm() == false){
			int playerX = controller.getPlayer().getX();
			int playerY = controller.getPlayer().getY();
			
			// If it is night time and player moved into an alarm tile then trigger alarm.	
			if (!controller.getTime().isDay() && controller.getMapScreen().getTiledModel().getTile(playerX, playerY).getAlarm()) {
				playerSpottedLoc = new GridPoint2(playerX, playerY);
				setAlarm(true);
			}
		}			
	} 
	
	public void playerCaught() {
		playerCaught = true;
	}
	
	public boolean getAlarm() {
		return alarmStarted != 0L; // Checks if the alarm has started or if it is yet to be started.
	}
}
