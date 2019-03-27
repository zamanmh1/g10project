package prisonescape.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import prisonescape.game.GameController;
import prisonescape.game.GameManager;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.helpers.ItemHandler;
import prisonescape.game.model.AlarmSystem;
import prisonescape.game.model.actors.Actor;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.ActiveGame;
import prisonescape.game.util.Time;

/**
 * This class tests the GameManager class
 * @author Hamza Zaman, Shibu George
 * 
 * @version 1.0
 * @since 1.0
 */
public class TestGameManager {
	private GameManager gm;

	/**
	 * Sets up an instance of GameManager throughout tests
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gm = new GameManager(new TestGC());
	}

	/**
	 * Releases resources after tests
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		gm = null;
	}

	/**
	 * Tests if GameManager populates a string full of items
	 * @see GameManager
	 */
	@Test
	public void testListToString() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Book"); list.add("Key"); list.add("Jigsaw");
		assertEquals(list.toString(),GameManager.listToString(list));
		
	}

	private class TestGC implements GameController{

		@Override
		public SpriteBatch getSpriteBatch() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Actor getPlayer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Music getMusic() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Music setMusic(String musicLoc) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void playMusic() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stopMusic() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void playAlarmSound() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stopAlarmSound() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addActor(MapActor a) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeActor(MapActor a) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setMap(String map, int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ActiveGame getMapScreen() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PrisonBreakout getGame() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(float delta) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Time getTime() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setTime(Time updatedTime) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public AlarmSystem getAlarm() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void restartGame() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getGameState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCurrentObjective() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setGameState(String newState) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setCurrentObjective(String newObjective) {
			// TODO Auto-generated method stub
			
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
