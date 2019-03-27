package prisonescape.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.tween.SpriteAccessor;

/**
 * Represents the mini map view with 3 floor: Top floor, Bottom floor and
 * Basement floor.
 * 
 * @author Shibu George
 *  
 * @version 1.0
 * @since 1.0
 */
public class Minimap implements Screen {

	private static final int TOPFLOOR_BUTTON_WIDTH = 355;
	private static final int TOPFLOOR_BUTTON_HEIGHT = 46;
	private static final int TOPFLOOR_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 250;
	private static final int BOTTOMFLOOR_BUTTON_WIDTH = 497;
	private static final int BOTTOMFLOOR_BUTTON_HEIGHT = 46;
	private static final int BOTTOMFLOOR_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 300;
	private static final int BASEMENTFLOOR_BUTTON_WIDTH = 348;
	private static final int BASEMENTFLOOR_BUTTON_HEIGHT = 46;
	private static final int BASEMENTFLOOR_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 350;
	private static Minimap miniMapInstance;
	private PrisonBreakout game;
	private Sprite bottomFloor;
	private Sprite blackBackground;
	private BitmapFont fontYellow;
	private TweenManager tween;
	private Sprite topFloor;
	private Sprite basementFloor;
	private Sprite bottomFloorButtonActive;
	private Sprite bottomFloorButtonInActive;
	private Sprite topFloorButtonActive;
	private Sprite topFloorButtonInActive;
	private boolean bottomActive;
	private boolean checkTopButtonMouseOver;
	private boolean topActive;
	private boolean basementActive;
	private boolean checkBottomButtonMouseOver;
	private Sprite basementFloorButtonActive;
	private Sprite basementFloorButtonInActive;
	private boolean checkBasementButtonMouseOver;

	/**
	 * Constructs a new mini map based upon which floor is chosen.
	 * 
	 * @param game
	 */
	private Minimap(PrisonBreakout game) {

		this.game = game;
		tween = new TweenManager();
		topFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/fullMapTopFloor.png")));
		bottomFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/fullMapBottomFloor.png")));
		basementFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/basement.png")));
		blackBackground = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/black_background.jpg")));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		topFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/top_active.png")));
		topFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/top_inactive.png")));
		bottomFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/bottom_active.png")));
		bottomFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/bottom_inactive.png")));
		basementFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/basement_active.png")));
		basementFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/basement_inactive.png")));
		topActive = true;
		bottomActive = false;
		basementActive = false;

	}

	/**
	 * Setting up the transitions
	 * 
	 */
	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Tween.set(topFloor, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloor, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloor, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(bottomFloor, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(basementFloor, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(basementFloor, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(topFloorButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloorButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(topFloorButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloorButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloorButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(bottomFloorButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloorButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(bottomFloorButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(basementFloorButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(basementFloorButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(basementFloorButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(basementFloorButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);

	}

	/**
	 * Rendering the mini map view
	 * 
	 */

	public void render(float delta) {
		tween.update(delta);
		this.game.getGameController().getSpriteBatch().begin();

		// Back background in the back
		blackBackground.setSize(PrisonBreakout.WIDTH, PrisonBreakout.HEIGHT);
		blackBackground.setPosition(Gdx.graphics.getWidth() / 2 - blackBackground.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - blackBackground.getHeight() / 2);
		blackBackground.draw(game.getGameController().getSpriteBatch());

		// Used mouse x and y coordinates to move the sprite
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();

		// if the top floor is active then draw the top floor
		if (topActive) {
			topFloor.setPosition(PrisonBreakout.WIDTH - topFloor.getWidth() / 2 - mouseX,
					PrisonBreakout.HEIGHT / 2 - topFloor.getHeight() + mouseY);
			topFloor.draw(game.getGameController().getSpriteBatch());
		}
		// if the bottom floor is active then draw the bottom floor
		if (bottomActive) {
			bottomFloor.setSize(1400, 1200);
			bottomFloor.setPosition(PrisonBreakout.WIDTH - bottomFloor.getWidth() / 2 - mouseX,
					PrisonBreakout.HEIGHT / 2 - bottomFloor.getHeight() + mouseY);

			bottomFloor.draw(game.getGameController().getSpriteBatch());
		}

		// if the basement floor is active then draw the basement floor
		if (basementActive) {
			basementFloor.setPosition(PrisonBreakout.WIDTH - basementFloor.getWidth() / 2 - mouseX,
					PrisonBreakout.HEIGHT / 2 - basementFloor.getHeight() + mouseY);

			basementFloor.draw(game.getGameController().getSpriteBatch());
		}

		// Displaying some fonts for usability
		fontYellow.draw(game.getGameController().getSpriteBatch(), "Choose a floor to view the map",
				Gdx.graphics.getWidth() / 2 + 200, Gdx.graphics.getHeight() / 2 - 170);

		fontYellow.draw(game.getGameController().getSpriteBatch(), getFloor(), Gdx.graphics.getWidth() / 2 - 600,
				Gdx.graphics.getHeight() / 2 + 350);

		fontYellow.draw(game.getGameController().getSpriteBatch(), "Press M to return",
				Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2 + 350);

		// x is the position of the button
		int x = PrisonBreakout.WIDTH / 2 - TOPFLOOR_BUTTON_WIDTH / 2 + 400;

		topFloorButton(x);

		x = PrisonBreakout.WIDTH / 2 - BOTTOMFLOOR_BUTTON_WIDTH / 2 + 400;

		bottomFloorButton(x);

		x = PrisonBreakout.WIDTH / 2 - BASEMENTFLOOR_BUTTON_WIDTH / 2 + 400;

		basementFloorButton(x);

		// When M pressed, returns to the MapScreen
		if (Gdx.input.isKeyJustPressed(Keys.M)) {
			game.setScreen(game.getGameController().getMapScreen());
		}
		this.game.getGameController().getSpriteBatch().end();

	}

	/**
	 * Represents the position and area where the top floor button is drawn
	 * 
	 * @param x coordinate of the button
	 */
	private void topFloorButton(int x) {
		if (Gdx.input.getX() < x + TOPFLOOR_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < TOPFLOOR_BUTTON_Y + TOPFLOOR_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > TOPFLOOR_BUTTON_Y) {

			topFloorButtonActive.setPosition(x, TOPFLOOR_BUTTON_Y);
			topFloorButtonActive.setSize(TOPFLOOR_BUTTON_WIDTH, TOPFLOOR_BUTTON_HEIGHT);
			topFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenu.getInstance(game).checkSoundMuted();
			if (checkTopButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();
				if (muted == true) {
					getMouseOverSound.stop();
				} else {
					getMouseOverSound.play(1f);
				}
				checkTopButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Timeline.createSequence().beginSequence()
						.push(Tween.set(bottomFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.set(basementFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.to(topFloor, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);
				topActive = true;
				bottomActive = false;
				basementActive = false;

			}

		} else {
			checkTopButtonMouseOver = false;
			topFloorButtonInActive.setPosition(x, TOPFLOOR_BUTTON_Y);
			topFloorButtonInActive.setSize(TOPFLOOR_BUTTON_WIDTH, TOPFLOOR_BUTTON_HEIGHT);
			topFloorButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Represents the position and area where the bottom floor button is drawn
	 * 
	 * @param x coordinate of the button
	 */
	private void bottomFloorButton(int x) {
		if (Gdx.input.getX() < x + BOTTOMFLOOR_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < BOTTOMFLOOR_BUTTON_Y + BOTTOMFLOOR_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > BOTTOMFLOOR_BUTTON_Y) {

			bottomFloorButtonActive.setPosition(x, BOTTOMFLOOR_BUTTON_Y);
			bottomFloorButtonActive.setSize(BOTTOMFLOOR_BUTTON_WIDTH, BOTTOMFLOOR_BUTTON_HEIGHT);
			bottomFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenu.getInstance(game).checkSoundMuted();
			if (checkBottomButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();
				if (muted == true) {
					getMouseOverSound.stop();
				} else {
					getMouseOverSound.play(1f);
				}
				checkBottomButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Timeline.createSequence().beginSequence()
						.push(Tween.set(topFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.set(basementFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.to(bottomFloor, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);
				bottomActive = true;
				topActive = false;
				basementActive = false;

			}
		} else {
			checkBottomButtonMouseOver = false;
			bottomFloorButtonInActive.setPosition(x, BOTTOMFLOOR_BUTTON_Y);
			bottomFloorButtonInActive.setSize(BOTTOMFLOOR_BUTTON_WIDTH, BOTTOMFLOOR_BUTTON_HEIGHT);
			bottomFloorButtonInActive.draw(game.getGameController().getSpriteBatch());

		}
	}

	/**
	 * Represents the position and area where the basement floor button is drawn
	 * 
	 * @param x coordinate of the button
	 */
	private void basementFloorButton(int x) {
		if (Gdx.input.getX() < x + BASEMENTFLOOR_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < BASEMENTFLOOR_BUTTON_Y + BASEMENTFLOOR_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > BASEMENTFLOOR_BUTTON_Y) {

			basementFloorButtonActive.setPosition(x, BASEMENTFLOOR_BUTTON_Y);
			basementFloorButtonActive.setSize(BASEMENTFLOOR_BUTTON_WIDTH, BASEMENTFLOOR_BUTTON_HEIGHT);
			basementFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenu.getInstance(game).checkSoundMuted();
			if (checkBasementButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();
				if (muted == true) {
					getMouseOverSound.stop();
				} else {
					getMouseOverSound.play(1f);
				}
				checkBasementButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Timeline.createSequence().beginSequence().push(Tween.set(topFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.set(bottomFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.set(topFloor, SpriteAccessor.ALPHA).target(0))
						.push(Tween.to(basementFloor, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);
				bottomActive = false;
				topActive = false;
				basementActive = true;

			}
		} else {
			checkBasementButtonMouseOver = false;
			basementFloorButtonInActive.setPosition(x, BASEMENTFLOOR_BUTTON_Y);
			basementFloorButtonInActive.setSize(BASEMENTFLOOR_BUTTON_WIDTH, BASEMENTFLOOR_BUTTON_HEIGHT);
			basementFloorButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Returns what floor is active
	 * 
	 * @return String
	 */
	private String getFloor() {
		if (topActive) {
			return "Top Floor";
		} else if (bottomActive) {
			return "Bottom Floor";
		} else if (basementActive) {
			return "Basement Floor";
		}
		return "";

	}

	/**
	 * Returns the instance of MiniMap (Singleton)
	 * 
	 * @param game
	 * @return new MiniMap(game) if no instance or current instance. 
	 */
	public static Minimap getInstance(PrisonBreakout game) {
		if (miniMapInstance == null) {
			miniMapInstance = new Minimap(game);
		}
		return miniMapInstance;
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void hide() {
	}

	public void dispose() {

	}
}
