package com.mygdx.game.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Actor;
import com.mygdx.game.entities.AlarmSystem;
import com.mygdx.game.entities.DIRECTION;
import com.mygdx.game.entities.MapActor;
import com.mygdx.game.entities.Actor.ACTOR_STATE;
import com.mygdx.game.util.Time;
import com.mygdx.prisonescapegame.GameController;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import com.mygdx.prisonescapegame.screens.MapScreen;

public class TestActor {
	
	private GameController controller;
	private Actor a;
	
	@Before
	public void setUp() {
		controller = new TestGC();
		a = new Actor(1, 1, null, controller);
	}

	@Test
	public void testActor_ChangeFacing_FaceEast() {		
		a.changeFacing(DIRECTION.EAST);
		
		assertEquals(a.getFacing(), DIRECTION.EAST);
	}
	
	@Test
	public void testActor_ChangeFacing_ActorState() {		
		a.changeFacing(DIRECTION.EAST);
		
		assertEquals(a.getState(), ACTOR_STATE.REFACING);
	}
	
	@Test
	public void testActor_ChangeFacing_Frozen() {		
		a.setFrozen(true);
		a.changeFacing(DIRECTION.EAST);
		
		assertTrue(a.getFrozen());
		assertEquals(a.getFacing(), DIRECTION.SOUTH);
		assertEquals(a.getState(), ACTOR_STATE.STANDING);
	}
	
	@Test
	public void testActor_InitialisedState () {
		assertEquals(a.getX(), 1);
		assertEquals(a.getY(), 1);
		assertEquals(a.getState(), ACTOR_STATE.STANDING);
		assertEquals(a.getFacing(), DIRECTION.SOUTH);
		assertFalse(a.getFrozen());
	}
	
	@Test
	public void testActor_TeleportToPosition() {
		a.teleport(3, 6);
		assertEquals(a.getX(), 3);
		assertEquals(a.getY(), 6);
	}
	
	private class TestGC implements GameController {
		/**
		 * Auto-generated methods.
		 */
		@Override
		public SpriteBatch getSpriteBatch() {
			return null;
		}
		@Override
		public Actor getPlayer() {
			return null;
		}
		@Override
		public Music getMusic() {
			return null;
		}
		@Override
		public Music setMusic(String musicLoc) {
			return null;
		}
		@Override
		public void playMusic() {	
		}
		@Override
		public void stopMusic() {		
		}
		@Override
		public void addActor(MapActor a) {			
		}
		@Override
		public void removeActor(MapActor a) {			
		}
		@Override
		public void setMap(String map, int x, int y) {			
		}
		@Override
		public MapScreen getMapScreen() {
			return null;
		}
		@Override
		public PrisonEscapeGame getGame() {
			return null;
		}
		@Override
		public void update(float delta) {			
		}
		@Override
		public Time getTime() {
			return null;
		}
		@Override
		public void setTime(Time updatedTime) {
		}
		@Override
		public AlarmSystem getAlarm() {
			return null;
		}
		@Override
		public void restartGame() {
		}
		@Override
		public String getGameState() {
			return null;
		}
		@Override
		public String getCurrentObjective() {
			return null;
		}
		@Override
		public void setGameState(String newState) {
		}
		@Override
		public void setCurrentObjective(String newObjective) {			
		}		
	}

}
