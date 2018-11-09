package com.mygdx.game.entities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sam Ward
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class PlayerMover implements InputProcessor {

	private Player player;
	private boolean hasKey;
	
	public PlayerMover(Player player) {
		this.player = player;
		this.hasKey = false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		float oldX = player.getPlayerSprite().getX();
		float oldY = player.getPlayerSprite().getY();

		switch(keycode) {
		case Keys.W:
			player.getPlayerSprite().setY(oldY + 16);
			if (player.getCurrentMap().checkTouching("Collision")) {
				player.getPlayerSprite().setY(oldY);
			}
			break; 
		case Keys.S:
			player.getPlayerSprite().setY(oldY - 16);
			if (player.getCurrentMap().checkTouching("Collision")) {
				player.getPlayerSprite().setY(oldY);
			}
			break;
		case Keys.A:
			player.getPlayerSprite().setX(oldX - 16);
			if (player.getCurrentMap().checkTouching("Collision")) {
				player.getPlayerSprite().setX(oldX);
			}
			break;
		case Keys.D:
			player.getPlayerSprite().setX(oldX + 16);
			if (player.getCurrentMap().checkTouching("Collision")) {
				player.getPlayerSprite().setX(oldX);
			}
			break;
		case Keys.E:
			//Check if on use layer, if there pick up/use
			if(player.getCurrentMap().checkTouching("Use") == true && player.getCurrentMap().getLayerVisibility("Key") == true)
			{
				hasKey = true;
				player.getCurrentMap().setLayerVisibility("Key", false);
			}
			if(player.getCurrentMap().checkTouching("Door") == true && hasKey == true)
			{
				player.getPlayerSprite().setY(oldY + 32);
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {return false;}

	@Override
	public boolean keyTyped(char character) {return false;}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}

	@Override
	public boolean scrolled(int amount) {return false;}

}