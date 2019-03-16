package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.tween.BitmapAccessor;
import com.mygdx.game.tween.SpriteAccessor;
import com.mygdx.prisonescapegame.PrisonEscapeGame;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Represents the full map view with 3 floor: Top floor, Bottom floor and
 * Basement floor.
 * 
 * @author Shibu George
 *
 */
public class FullMapScreen implements Screen {

	private static final int TOPFLOOR_BUTTON_WIDTH = 174;
	private static final int TOPFLOOR_BUTTON_HEIGHT = 52;
	private static final int TOPFLOOR_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 250;
	private static final int BOTTOMFLOOR_BUTTON_WIDTH = 174;
	private static final int BOTTOMFLOOR_BUTTON_HEIGHT = 52;
	private static final int BOTTOMFLOOR_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 300;
	private static final int BASEMENTFLOOR_BUTTON_WIDTH = 174;
	private static final int BASEMENTFLOOR_BUTTON_HEIGHT = 52;
	private static final int BASEMENTFLOOR_BUTTON_Y = PrisonEscapeGame.HEIGHT / 2 - 350;
	private PrisonEscapeGame game;
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
	 * Constructs a new FullMapScreen based upon which floor is chosen.
	 * 
	 * @param game
	 */
	public FullMapScreen(PrisonEscapeGame game) {

		this.game = game;
		tween = new TweenManager();
		topFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/fullMapTopFloor.png")));
		bottomFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/fullMapBottomFloor.png")));
		basementFloor = new Sprite(new Texture(Gdx.files.internal("data/maps/mapImage/basement.png")));
		blackBackground = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/black_background.jpg")));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		topFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_active.png")));
		topFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_inactive.png")));
		bottomFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/quit_active.png")));
		bottomFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/quit_inactive.png")));
		basementFloorButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_active.png")));
		basementFloorButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_inactive.png")));
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

		Tween.set(topFloor, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloor, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloor, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.set(basementFloor, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.set(topFloorButtonActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloorButtonActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(topFloorButtonInActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(topFloorButtonInActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloorButtonActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(bottomFloorButtonActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(bottomFloorButtonInActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(bottomFloorButtonInActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(basementFloorButtonActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(basementFloorButtonActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(basementFloorButtonInActive, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(basementFloorButtonInActive, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);

	}

	/**
	 * Rendering the Full Map View
	 * 
	 */

	public void render(float delta) {
		tween.update(delta);
		this.game.getGameController().getSpriteBatch().begin();
	
		//Back background in the back
		blackBackground.setSize(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT);
		blackBackground.setPosition(Gdx.graphics.getWidth() / 2 - blackBackground.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - blackBackground.getHeight() / 2);
		blackBackground.draw(game.getGameController().getSpriteBatch());

		// Used mouse x and y coordinates to move the sprite
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();

		// if the top floor is active then draw the top floor
		if (topActive) {
			topFloor.setPosition(PrisonEscapeGame.WIDTH - topFloor.getWidth() / 2 - mouseX,
					PrisonEscapeGame.HEIGHT / 2 - topFloor.getHeight() + mouseY);
			topFloor.draw(game.getGameController().getSpriteBatch());
		}
		// if the bottom floor is active then draw the bottom floor
		if (bottomActive) {
			bottomFloor.setSize(1400, 1200);
			bottomFloor.setPosition(PrisonEscapeGame.WIDTH - bottomFloor.getWidth() / 2 - mouseX,
					PrisonEscapeGame.HEIGHT / 2 - bottomFloor.getHeight() + mouseY);

			bottomFloor.draw(game.getGameController().getSpriteBatch());
		}
		
		// if the basement floor is active then draw the basement floor
		if (basementActive) {
			basementFloor.setPosition(PrisonEscapeGame.WIDTH - basementFloor.getWidth() / 2 - mouseX,
					PrisonEscapeGame.HEIGHT / 2 - basementFloor.getHeight() + mouseY);

			basementFloor.draw(game.getGameController().getSpriteBatch());
		}
		
		//Displaying some fonts for usability
		fontYellow.draw(game.getGameController().getSpriteBatch(), "Choose a floor to view the map",
				Gdx.graphics.getWidth() / 2 + 200, Gdx.graphics.getHeight() / 2 - 170);
		
		fontYellow.draw(game.getGameController().getSpriteBatch(), getFloor(),
				Gdx.graphics.getWidth() / 2 - 600, Gdx.graphics.getHeight() / 2 + 350);
		
		fontYellow.draw(game.getGameController().getSpriteBatch(), "Press M to return",
				Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2 + 350);
		
		//x is the position of the button
		int x = PrisonEscapeGame.WIDTH / 2 - TOPFLOOR_BUTTON_WIDTH / 2 + 400;

		topFloorButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - BOTTOMFLOOR_BUTTON_WIDTH / 2 + 400;

		bottomFloorButton(x);

		x = PrisonEscapeGame.WIDTH / 2 - BASEMENTFLOOR_BUTTON_WIDTH / 2 + 400;

		basementFloorButton(x);

		//When M pressed, returns to the MapScreen
		if (Gdx.input.isKeyJustPressed(Keys.M)) {
			Stage stage = MapScreen.getStage();
			stage.clear();
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
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < TOPFLOOR_BUTTON_Y + TOPFLOOR_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > TOPFLOOR_BUTTON_Y) {

			topFloorButtonActive.setPosition(x, TOPFLOOR_BUTTON_Y);
			topFloorButtonActive.setSize(TOPFLOOR_BUTTON_WIDTH, TOPFLOOR_BUTTON_HEIGHT);
			topFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
			if (checkTopButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
				if (muted == true) {
					getMouseOverSound.stop();
				} else {
					getMouseOverSound.play(1f);
				}
				checkTopButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Timeline.createSequence().beginSequence().push(Tween.set(bottomFloor, SpriteAccessor.ALPHA).target(0))
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
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < BOTTOMFLOOR_BUTTON_Y + BOTTOMFLOOR_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > BOTTOMFLOOR_BUTTON_Y) {

			bottomFloorButtonActive.setPosition(x, BOTTOMFLOOR_BUTTON_Y);
			bottomFloorButtonActive.setSize(BOTTOMFLOOR_BUTTON_WIDTH, BOTTOMFLOOR_BUTTON_HEIGHT);
			bottomFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
			if (checkBottomButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
				if (muted == true) {
					getMouseOverSound.stop();
				} else {
					getMouseOverSound.play(1f);
				}
				checkBottomButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				Timeline.createSequence().beginSequence().push(Tween.set(topFloor, SpriteAccessor.ALPHA).target(0))
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
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < BASEMENTFLOOR_BUTTON_Y + BASEMENTFLOOR_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > BASEMENTFLOOR_BUTTON_Y) {

			basementFloorButtonActive.setPosition(x, BASEMENTFLOOR_BUTTON_Y);
			basementFloorButtonActive.setSize(BASEMENTFLOOR_BUTTON_WIDTH, BASEMENTFLOOR_BUTTON_HEIGHT);
			basementFloorButtonActive.draw(game.getGameController().getSpriteBatch());

			Boolean muted = MainMenuScreen.getInstance(game).checkSoundMuted();
			if (checkBasementButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenuScreen.getInstance(game).mouseOverSound();
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
		}else if (bottomActive) {
			return "Bottom Floor";
		}else if (basementActive) {
			return "Basement Floor";
		}
		return "";
		
			
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
