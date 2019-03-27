package prisonescape.game.tween;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * CLASS DESCRIPTION
 * 
 * @author Shibu George
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

import aurelienribon.tweenengine.TweenAccessor;

/**
 * 
 * Represents the <code>BitmapFont</code> accessor so tween (transitions) of
 * fading can work with <code>BitmapFont</code>.
 * 
 * @author Shibu George
 *
 * @version 1.0
 * @since 0.2
 */
public class BitmapAccessor implements TweenAccessor<BitmapFont> {

	public static final int ALPHA = 0;

	/**
	 * Gets the BitmapFont colour
	 * 
	 */
	@Override
	public int getValues(BitmapFont target, int tweenType, float[] returnValues) {
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
	 * Sets the BitmapFont colour
	 * 
	 */
	@Override
	public void setValues(BitmapFont target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}

	}

}
