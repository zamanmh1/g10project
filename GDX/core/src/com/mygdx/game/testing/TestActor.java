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
	private Actor actor;
	
	@Before
	public void setUp() {
		controller = new TestGC();
		actor = new Actor(1, 1, null, controller);
	}

	@Test
	public void testActor_ChangeFacing_FaceEast() {		
		actor.changeFacing(DIRECTION.EAST);
		
		assertEquals(actor.getFacing(), DIRECTION.EAST);
	}
	
	@Test
	public void testActor_ChangeFacing_ActorState() {		
		actor.changeFacing(DIRECTION.EAST);
		
		assertEquals(actor.getState(), ACTOR_STATE.REFACING);
	}
	
	@Test
	public void testActor_ChangeFacing_Frozen() {		
		actor.setFrozen(true);
		actor.changeFacing(DIRECTION.EAST);
		
		assertTrue(actor.getFrozen());
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		assertEquals(actor.getState(), ACTOR_STATE.STANDING);
	}
	
	@Test
	public void testActor_InitialisedState () {
		assertEquals(actor.getX(), 1);
		assertEquals(actor.getY(), 1);
		assertEquals(actor.getState(), ACTOR_STATE.STANDING);
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		assertFalse(actor.getFrozen());
	}
	
	@Test
	public void testActor_TeleportToPosition() {
		actor.teleport(3, 6);
		assertEquals(actor.getX(), 3);
		assertEquals(actor.getY(), 6);
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
