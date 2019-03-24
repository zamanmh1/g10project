package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.GameController;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.helpers.ItemHandler;
import prisonescape.game.io.player.PlayerMovementController;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.DIRECTION;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

/**
 * A class which tests a PlayerMovementController object under controlled circumstances.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestPlayerMovementController {

	/**
	 * The actor that the PlayerMovementController will operate on.
	 */
	private Actor actor;
	
	/**
	 * The PlayerMovementController to test with.
	 */
	private PlayerMovementController move;

	/**
	 * Sets up a PlayerMovementController and and Actor to test on.
	 * 
	 * @throws Exception Catches any exception caused by setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Actor initialised with position and non-operational GameController.
		// Null parameter as no animations are required.
		actor = new Actor(1, 1, null, new TestGC());
		
		// PlayerMovementController created using Actor.
		move = new PlayerMovementController(actor);
	}

	/**
	 * This test ensures that pressing a key will change the state of
	 * the associated actor.
	 */
	@Test
	public void testMovementController_ChangeDirection() {
		// Assert that actor's initially facing south.
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		// Register W key press (forward).
		move.keyUp(Keys.W);
		// Assert that should now be facing north (forward).
		assertEquals(actor.getFacing(), DIRECTION.NORTH);

	}

	/**
	 * This test checks that no movement is allowed when the actor is frozen.
	 */
	@Test
	public void testMovementController_ChangeDirection_Frozen() {
		// Sets the actor to become frozen.
		actor.setFrozen(true);
		// Assert that actor is initially facing south.
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		// Register W key press (forward).
		move.keyUp(Keys.W);
		// Assert that actor still facing south.
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);

	}

	/**
	 * This test ensures that another movement cannot be registered while
	 * still performing the previous movement.
	 */
	@Test
	public void testMovementController_ChangeDirection_NotStanding() {
		// While changing facing direction, state equals refacing.
		actor.changeFacing(DIRECTION.EAST);
		// Attempt to register next movement.
		move.keyUp(Keys.W);
		// Actor still facing east, as previous move not yet complete.
		// To move actor state must equal standing.
		assertEquals(actor.getFacing(), DIRECTION.EAST);
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
