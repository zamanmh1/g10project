package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.GameController;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.helpers.ItemHandler;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.DIRECTION;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.model.actors.Actor.ACTOR_STATE;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

/**
 * A class which tests an Actor object under controlled circumstances.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestActor {

	/**
	 * The actor that tests will be performed on.
	 */
	private Actor actor;

	/**
	 * Sets up an actor to be tested.
	 * 
	 * @throws Exception Catches any exception caused by setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Actor initialised with position and non-operational GameController.
		// Null parameter as no animations are required.
		actor = new Actor(1, 1, null, new TestGC());
	}

	/**
	 * This test checks that an actor can change to face the required correctly.
	 */
	@Test
	public void testActor_ChangeFacing_FaceEast() {
		// Assert that initial direction facing is south.
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		
		// Change direction facing to east.
		actor.changeFacing(DIRECTION.EAST);

		// Assert that direction now facing is east.
		assertEquals(actor.getFacing(), DIRECTION.EAST);
	}

	/**
	 * This test checks that when an actor is changing direction that its actor
	 * state is updated to reflect this.
	 */
	@Test
	public void testActor_ChangeFacing_ActorState() {
		// Change direction facing to east.
		actor.changeFacing(DIRECTION.EAST);

		// Assert that actor state is refacing.
		assertEquals(actor.getState(), ACTOR_STATE.REFACING);
	}

	/**
	 * This test checks that an actor cannot change direction facing while 
	 * in being frozen.
	 */
	@Test
	public void testActor_ChangeFacing_Frozen() {
		// Sets actor to frozen and attempts to change direction facing.
		actor.setFrozen(true);
		actor.changeFacing(DIRECTION.EAST);

		// Assert that actor is frozen.
		assertTrue(actor.getFrozen());
		// Assert that actor still facing direction from when initialised.
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		// Assert that actor state is still standing.
		assertEquals(actor.getState(), ACTOR_STATE.STANDING);
	}

	/**
	 * This test checks the initialised state of an actor.
	 */
	@Test
	public void testActor_InitialisedState() {
		// Asserts that the state of an actor is as expected after initialisation.
		assertEquals(actor.getX(), 1);
		assertEquals(actor.getY(), 1);
		assertEquals(actor.getState(), ACTOR_STATE.STANDING);
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		assertFalse(actor.getFrozen());
	}

	/**
	 * This test checks that an actor can teleport to a new position.
	 */
	@Test
	public void testActor_TeleportToPosition() {
		// Teleports the actor.
		actor.teleport(3, 6);
		
		// Assert that the actor's location matches the location teleported to.
		assertEquals(actor.getX(), 3);
		assertEquals(actor.getY(), 6);
	}

	/**
	 * A non-operational version of a GameController that allows for tests to run which require a game
	 * controller but don't use any of its methods.
	 * 
	 * @author Sam Ward
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
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
		public ActiveGame getMapScreen() {
			return null;
		}

		@Override
		public PrisonBreakout getGame() {
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

		@Override
		public void playAlarmSound() {

		}

		@Override
		public void stopAlarmSound() {

		}

		@Override
		public void playAlarmBeep() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stopAlarmBeep() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ItemHandler getItemHandler() {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
