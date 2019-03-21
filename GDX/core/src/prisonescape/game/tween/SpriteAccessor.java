package prisonescape.game.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * 
 * Represents the <code>Sprite</code> accessor so tween (transitions) of
 * fading can work with <code>Sprite</code>.
 * 
 * @author Shibu George
 *
 */

public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int ALPHA = 0;
	
	/**
	 * Gets the Sprite colour
	 * 
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	/**
	 * Sets the Sprite colour
	 * 
	 */
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r,target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}

	}

}
