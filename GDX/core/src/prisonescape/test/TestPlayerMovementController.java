package prisonescape.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.GameController;
import prisonescape.game.PrisonEscapeGame;
import prisonescape.game.io.player.PlayerMovementController;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.DIRECTION;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

public class TestPlayerMovementController {
	
	private GameController controller;
	private Actor actor;
	private PlayerMovementController move;
	
	@Before
	public void setUp() {
		controller = new TestGC();
		actor = new Actor(1, 1, null, controller);
		move = new PlayerMovementController(actor);
	}

	@Test
	public void testMovementController_ChangeDirection() {	
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		
		move.keyUp(Keys.W);
		
		assertEquals(actor.getFacing(), DIRECTION.NORTH);
		
	}
	
	@Test
	public void testMovementController_ChangeDirection_Frozen() {
		actor.setFrozen(true);
		
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		
		move.keyUp(Keys.W);
		
		assertEquals(actor.getFacing(), DIRECTION.SOUTH);
		
	}
	
	@Test
	public void testMovementController_ChangeDirection_NotStanding() {
		// Actor state must be standing before next move.
		// While changing facing direction, state equals refacing.
		actor.changeFacing(DIRECTION.EAST);		
		move.keyUp(Keys.W);
		
		assertEquals(actor.getFacing(), DIRECTION.EAST);
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
		public ActiveGame getMapScreen() {
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
