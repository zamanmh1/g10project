package prisonescape.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.tween.BitmapAccessor;
import prisonescape.game.tween.SpriteAccessor;

/**
 * Represents the Credits Screen
 * 
 * @author Shibu George
 *
 * @version 1.0
 * @since 1.0
 */
public class Credits implements Screen {

	private static final int RETURN_BUTTON_WIDTH = 643;
	private static final int RETURN_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 50;
	private static final int RETURN_BUTTON_HEIGHT = 46;
	private static final int EXIT_BUTTON_WIDTH = 301;
	private static final int EXIT_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 120;
	private static final int EXIT_BUTTON_HEIGHT = 46;
	private BitmapFont fontYellow;
	private TweenManager tween;
	private PrisonBreakout game;
	private Sprite backgroundSprite;
	private static String[] credits_strs = { "Created by:", "Samuel Ward", "Sean Corcoran", "Shibu George",
			"(Mohammed) Hamza Zaman", "Adam Collins", "Kevinjeet Singh", "Maxwell Trimnell", "Jai Kumar",
			"Hasan Ahmed" };

	private float credit_y = 0;
	private Sprite theEndSprite;
	private Sprite returnButtonActive;
	private Sprite returnButtonInActive;
	private boolean checkReturnButtonMouseOver;
	private boolean buttonActive;
	private Sprite exitButtonActive;
	private Sprite exitButtonInActive;
	private boolean checkExitButtonMouseOver;
	private Sprite theEndSprite2;
	private BitmapFont fontBlack;
	private Sprite logo;
	private String endingText;

	/**
	 * Constructs a new EndScreen with sprites and font for credits
	 * 
	 * @param game
	 */
	public Credits(PrisonBreakout game) {
		this.game = game;
		tween = new TweenManager();
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/plainbackground.png")));
		theEndSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/the_end.png")));
		theEndSprite2 = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/the_end.png")));
		logo = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/logo.png")));
		returnButtonActive = new Sprite(new Texture("data/menuSprites/return_to_main_menu_active.png"));
		returnButtonInActive = new Sprite(new Texture("data/menuSprites/return_to_main_menu_inactive.png"));
		exitButtonActive = new Sprite(new Texture("data/menuSprites/exitgame_active.png"));
		exitButtonInActive = new Sprite(new Texture("data/menuSprites/exitgame_inactive.png"));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		fontBlack = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font-black.fnt"));
		buttonActive = false;
	}

	/**
	 * Setting up the transitions
	 * 
	 */
	@Override
	public void show() {
		Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.set(theEndSprite, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(theEndSprite, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(logo, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.to(logo, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(theEndSprite2, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(returnButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(returnButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0).start(tween);
		Tween.set(fontYellow, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(fontYellow, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);
		Tween.set(fontBlack, BitmapAccessor.ALPHA).target(0).start(tween);
		Tween.to(fontBlack, BitmapAccessor.ALPHA, 0.2f).target(1).start(tween);

	}

	/**
	 * Rendering the Credit Screen
	 * 
	 */

	public void render(float delta) {
		tween.update(delta);
		this.game.getGameController().getSpriteBatch().begin();

		backgroundSprite.setSize(PrisonBreakout.WIDTH, PrisonBreakout.HEIGHT);
		backgroundSprite.draw(game.getGameController().getSpriteBatch());

		logo.setPosition(PrisonBreakout.WIDTH / 2 - 630, PrisonBreakout.HEIGHT / 2 + 200);
		logo.draw(game.getGameController().getSpriteBatch());

		fontBlack.draw(game.getGameController().getSpriteBatch(), endingText, PrisonBreakout.WIDTH / 2 - 680,
				PrisonBreakout.HEIGHT / 2 + 50);

		credit_y = credit_y + 40 * delta;
		theEndSprite.setScale(0.5f);
		theEndSprite.setPosition(PrisonBreakout.WIDTH / 2 - 160, credit_y + 40);
		theEndSprite.draw(game.getGameController().getSpriteBatch());

		theEndSprite2.setPosition(PrisonBreakout.WIDTH / 2 - 160, PrisonBreakout.HEIGHT / 2 + 30);
		theEndSprite2.draw(game.getGameController().getSpriteBatch());

		for (int i = 0; i < credits_strs.length; i++) {
			GlyphLayout layout = new GlyphLayout();
			layout.setText(fontYellow, credits_strs[i]);
			float w = layout.width;
			fontYellow.draw(game.getGameController().getSpriteBatch(), layout, (PrisonBreakout.WIDTH - w) / 2 + 200,
					credit_y - i * 40);
		}
		if (credit_y > PrisonBreakout.HEIGHT + 380) {
			buttonActive = true;
			Tween.to(theEndSprite2, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
			Tween.to(returnButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
			Tween.to(returnButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
			Tween.to(exitButtonActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
			Tween.to(exitButtonInActive, SpriteAccessor.ALPHA, 0.2f).target(1).start(tween);
		}

		int xReturn = PrisonBreakout.WIDTH / 2 - RETURN_BUTTON_WIDTH / 2 + 200;
		returnButton(xReturn);

		int xExit = PrisonBreakout.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 200;
		exitButton(xExit);

		this.game.getGameController().getSpriteBatch().end();
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			((Game) Gdx.app.getApplicationListener()).setScreen(game.getGameController().getMapScreen());
		}
	}

	/**
	 * Method which renders the exit button's positioning, size and active and
	 * inactive versions of the sprite
	 * 
	 * @param x coordinate of the exit button
	 */
	private void exitButton(int x) {
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenu.getInstance(game).checkSoundMuted();
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}

					checkExitButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
					Gdx.app.exit();

				}
			}

		} else {
			checkExitButtonMouseOver = false;
			exitButtonInActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonInActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Method which renders the return button's positioning, size and active and
	 * inactive versions of the sprite
	 * 
	 * @param x coordinate of the return button
	 */
	private void returnButton(int x) {
		if (Gdx.input.getX() < x + RETURN_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < RETURN_BUTTON_Y + RETURN_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > RETURN_BUTTON_Y) {
			returnButtonActive.setPosition(x, RETURN_BUTTON_Y);
			returnButtonActive.setSize(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
			returnButtonActive.draw(game.getGameController().getSpriteBatch());
			Boolean muted = MainMenu.getInstance(game).checkSoundMuted();

			if (buttonActive) {

				if (checkReturnButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();
					if (muted == true) {
						getMouseOverSound.stop();
					} else {
						getMouseOverSound.play(1f);
					}
					checkReturnButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					ActiveGame.setBlackScreen();
					game.setScreen(game.getGameController().getMapScreen());
					game.getGameController().restartGame();
					game.getGameController().stopMusic();

				}

			}
		} else {
			checkReturnButtonMouseOver = false;
			returnButtonInActive.setPosition(x, RETURN_BUTTON_Y);
			returnButtonInActive.setSize(RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
			returnButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	public String ending1() {
		return endingText = "It took a few hours for the riots to\n"
				+ "settle down and for them to eventually \n"
				+ "find out that 2 prisoners had escaped, \n"
				+ "however you had already made it to the\n"
				+ "safehouse before that. The mob stayed\n"
				+ "true to their word and your family is safe. \n"
				+ "There was not much time left but before a\n"
				+ "widespread manhunt started to take place. \n"
				+ "Being the mob they had connections. \n"
				+ "They offered you to ride with them across \n"
				+ "the border and have them drop you and \n"
				+ "your family somewhere. \n\nSomewhere you can start again.";
	}

	public String ending2() {
		return endingText = "You don't know why, \n"
				+ "but this felt so right to you. \n"
				+ "Mixed feelings of guilt, rage \n"
				+ "and confusion overwhelmed you. \n"
				+ "But revenge felt so right, after all, \n"
				+ "they threatened your family. \n"
				+ "This is no time to gawk. You're free.\n\n" + 
				"I just hope they're safe.";
	}

	public String ending3() {
		return endingText = "The Warden managed to commute your \n"
				+ "sentence down to just a couple more \n"
				+ "months. It wasn't an easy ride. \n"
				+ "Snitches get stitches after all. \n"
				+ "Even the Gambler had to cut contact \n"
				+ "just to save face. \n"
				+ "Those gruelling few months are over now, \n"
				+ "and just beyond those gates leading to \n"
				+ "freedom your family awaits. \n"
				+ "Witness protection was granted. \n\n"
				+ "The mob will never put your family \n"
				+ "in harms way again.";
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
