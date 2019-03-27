package prisonescape.game.screens;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import prisonescape.game.GameManager;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import prisonescape.game.PrisonBreakout;
import prisonescape.game.tween.BitmapAccessor;
import prisonescape.game.tween.SpriteAccessor;

/**
 * Represents the Main Menu Screen once the user has loaded the screen.
 * 
 * @author Sam Ward, Shibu George, Hamza Zaman
 * 
 * @version 1.0
 * @since 0.1
 */

public class MainMenu implements Screen {

	private static final int PLAY_BUTTON_WIDTH = 174;
	private static final int PLAY_BUTTON_HEIGHT = 52;
	private static final int PLAY_BUTTON_Y = PrisonBreakout.HEIGHT / 2 + 100;
	private static final int EXIT_BUTTON_WIDTH = 174;
	private static final int EXIT_BUTTON_HEIGHT = 52;
	private static final int EXIT_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 100;
	private static final int HELP_BUTTON_WIDTH = 174;
	private static final int HELP_BUTTON_Y = PrisonBreakout.HEIGHT / 2;
	private static final int HELP_BUTTON_HEIGHT = 52;
	private static final int VOLUME_BUTTON_WIDTH = 50;
	private static final int VOLUME_BUTTON_Y = 50;
	private static final int VOLUME_BUTTON_HEIGHT = 50;
	private static final int BACK_BUTTON_WIDTH = 40;
	private static final int BACK_BUTTON_Y = PrisonBreakout.HEIGHT / 2 + 300;
	private static final int BACK_BUTTON_HEIGHT = 40;
	private static final int NEWGAME_BUTTON_WIDTH = 427;
	private static final int NEWGAME_BUTTON_HEIGHT = 54;
	private static final int NEWGAME_BUTTON_Y = PrisonBreakout.HEIGHT / 2;
	private static final int LOADGAME_BUTTON_WIDTH = 427;
	private static final int LOADGAME_BUTTON_HEIGHT = 54;
	private static final int LOADGAME_BUTTON_Y = PrisonBreakout.HEIGHT / 2 - 100;

	private PrisonBreakout game;
	private TweenManager tween;
	private static MainMenu mainInstance;
	private Sprite playButtonActive;
	private Sprite playButtonInActive;
	private Sprite exitButtonActive;
	private Sprite exitButtonInActive;
	private Sprite backgroundSprite;

	private Sprite helpButtonActive;
	private Sprite helpButtonInActive;

	private Sound mouseOverSound;
	private boolean checkPlayButtonMouseOver;
	private boolean checkExitButtonMouseOver;
	private boolean checkHelpButtonMouseOver;
	private Sprite volumeButtonFull;
	private Sprite volumeButtonMute;
	private boolean volumeMuted;
	private Sprite backButtonActive;
	private Sprite backButtonInActive;
	private boolean checkBackButtonMouseOver;
	private boolean buttonActive;
	private boolean playPressed;
	private Sprite loadGameActive;
	private Sprite newGameActive;
	private Sprite loadGameInActive;
	private Sprite newGameInActive;
	private boolean checkLoadButtonMouseOver;
	private boolean checkNewButtonMouseOver;
	private long time;

	private BitmapFont fontYellow;
	private boolean loadPressed;
	private Sprite loadButtonInActive;
	private Sprite loadButtonActive;
	private Sprite deleteButtonInActive;
	private Sprite deleteButtonActive;

	/**
	 * Constructs the screen with sprites and music.
	 * 
	 * @param game
	 */
	private MainMenu(PrisonBreakout game) {

		this.game = game;

		tween = new TweenManager();
		playButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_active.png")));
		playButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/play_inactive.png")));
		exitButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_active.png")));
		exitButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/exit_inactive.png")));
		backgroundSprite = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/background.png")));
		helpButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/help_active.png")));
		helpButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/help_inactive.png")));
		backButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back_active.png")));
		backButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/back.png")));
		loadGameActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/load_active.png")));
		loadGameInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/load_inactive.png")));
		newGameActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/new_active.png")));
		newGameInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/new_inactive.png")));
		volumeButtonFull = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/Volume-full.png")));
		volumeButtonMute = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/Volume-off.png")));
		loadButtonInActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/loadButton_inactive.png")));
		loadButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/loadButton_active.png")));
		deleteButtonInActive = new Sprite(
				new Texture(Gdx.files.internal("data/menuSprites/deleteButton_inactive.png")));
		deleteButtonActive = new Sprite(new Texture(Gdx.files.internal("data/menuSprites/deleteButton_active.png")));
		fontYellow = new BitmapFont(Gdx.files.internal("data/fonts/vision-bold-font.fnt"));
		mouseOverSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/MouseOver.ogg"));
		checkPlayButtonMouseOver = false;
		checkExitButtonMouseOver = false;
		checkHelpButtonMouseOver = false;
		checkBackButtonMouseOver = false;
		checkLoadButtonMouseOver = false;
		checkNewButtonMouseOver = false;
		buttonActive = true;
		playPressed = false;
		volumeMuted = false;

	}

	/**
	 * 
	 * Represents the transition of the buttons in MainMenu
	 * 
	 */
	@Override
	public void show() {
		time = System.currentTimeMillis();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Timeline.createSequence().beginSequence()

				.push(Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(helpButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(helpButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(loadGameInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(loadGameActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(newGameInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(newGameActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonFull, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(volumeButtonMute, SpriteAccessor.ALPHA).target(0))
				.push(Tween.from(backgroundSprite, SpriteAccessor.ALPHA, 0).target(0))
				.push(Tween.to(playButtonInActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(playButtonActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(helpButtonInActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(helpButtonActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(exitButtonInActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(exitButtonActive, SpriteAccessor.ALPHA, 0.1f).target(1))
				.push(Tween.to(volumeButtonFull, SpriteAccessor.ALPHA, 0).target(1))
				.push(Tween.to(volumeButtonMute, SpriteAccessor.ALPHA, 0).target(1)).end().start(tween);

	}

	/**
	 * 
	 * Renders the Main Menu Screen with background, buttons, volume, music, and
	 * load options.
	 * 
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tween.update(delta);

		game.getGameController().getSpriteBatch().begin();

		backgroundSprite.setSize(PrisonBreakout.WIDTH, PrisonBreakout.HEIGHT);
		backgroundSprite.draw(game.getGameController().getSpriteBatch());

		int x = PrisonBreakout.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 + 200;

		playButton(x);

		x = PrisonBreakout.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 200;

		exitButton(x);

		x = PrisonBreakout.WIDTH / 2 - HELP_BUTTON_WIDTH / 2 + 200;

		helpButton(x);

		x = PrisonBreakout.WIDTH / 2 - VOLUME_BUTTON_WIDTH / 2 + 650;

		volumeButton(x);

		if (playPressed) {
			playOptions();

		}
		if (loadPressed) {
			buttonActive = false;
			playPressed = false;
			loadOptions();
		}
		game.getGameController().getSpriteBatch().end();

	}

	/**
	 * Represents the position and area where the play button is drawn.
	 * 
	 * @param x coordinate of the button
	 */
	private void playButton(int x) {

		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			playButtonActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkPlayButtonMouseOver == false) {
					mouseOverSound.play(1f);
					checkPlayButtonMouseOver = true;

				}

				if (System.currentTimeMillis() > time + 750) {
					if (Gdx.input.isTouched()) {
						Timeline.createSequence().beginSequence()
								.push(Tween.set(playButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(playButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(helpButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(helpButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(exitButtonActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.set(exitButtonInActive, SpriteAccessor.ALPHA).target(0))
								.push(Tween.to(newGameInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(newGameActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(loadGameInActive, SpriteAccessor.ALPHA, 0.2f).target(1))
								.push(Tween.to(loadGameActive, SpriteAccessor.ALPHA, 0.2f).target(1)).end()
								.start(tween);
						playPressed = true;
						buttonActive = false;

					}
				}
			}
		} else {
			checkPlayButtonMouseOver = false;
			playButtonInActive.setPosition(x, PLAY_BUTTON_Y);
			playButtonInActive.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			playButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Represents the position and area where the new game and load game button is
	 * drawn.
	 * 
	 * @param x coordinate of the buttons
	 */
	private void playOptions() {
		int backX = PrisonBreakout.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 100;

		backButton(backX);

		int newGameX = PrisonBreakout.WIDTH / 2 - NEWGAME_BUTTON_WIDTH / 2 + 200;

		newGameButton(newGameX);

		int loadGameX = PrisonBreakout.WIDTH / 2 - LOADGAME_BUTTON_WIDTH / 2 + 200;

		loadGameButton(loadGameX);

	}

	/**
	 * Represents the position and area where the new game button is drawn. Once it
	 * is clicked, it will go into the <code>ActiveGame</code>.
	 * 
	 * @param x coordinate of the button
	 */
	private void newGameButton(int newGameX) {
		if (Gdx.input.getX() < newGameX + NEWGAME_BUTTON_WIDTH && Gdx.input.getX() > newGameX
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < NEWGAME_BUTTON_Y + NEWGAME_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > NEWGAME_BUTTON_Y) {

			newGameActive.setPosition(newGameX, NEWGAME_BUTTON_Y);
			newGameActive.setSize(NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
			newGameActive.draw(game.getGameController().getSpriteBatch());
			if (playPressed) {
				if (checkNewButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkNewButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					playPressed = false;
					buttonActive = true;
					game.setScreen(new Loading(game));
					game.getGameController().stopMusic();
					game.getGameController().setMusic("data/sounds/MainGameMusic.mp3");
					game.getGameController().playMusic();
					if (volumeMuted == true) {
						Music music = game.getGameController().getMusic();
						music.pause();
					}
				}
			}
		} else {
			checkNewButtonMouseOver = false;
			newGameInActive.setPosition(newGameX, NEWGAME_BUTTON_Y);
			newGameInActive.setSize(NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
			newGameInActive.draw(game.getGameController().getSpriteBatch());
		}
	}

	/**
	 * Represents the position and area where the load button is drawn. Once it is
	 * click it will execute <code>loadOptions()</code> which shows the list of
	 * saved games in the past.
	 * 
	 * @param x coordinate of the button
	 */
	private void loadGameButton(int loadGameX) {

		if (Gdx.input.getX() < loadGameX + LOADGAME_BUTTON_WIDTH && Gdx.input.getX() > loadGameX
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < LOADGAME_BUTTON_Y + LOADGAME_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > LOADGAME_BUTTON_Y) {

			loadGameActive.setPosition(loadGameX, LOADGAME_BUTTON_Y);
			loadGameActive.setSize(LOADGAME_BUTTON_WIDTH, LOADGAME_BUTTON_HEIGHT);
			loadGameActive.draw(game.getGameController().getSpriteBatch());
			if (playPressed) {
				if (checkLoadButtonMouseOver == false) {
					Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();

					getMouseOverSound.play(1f);

					checkLoadButtonMouseOver = true;

				}

				if (Gdx.input.isTouched()) {
					loadPressed = true;
					Tween.registerAccessor(BitmapFont.class, new BitmapAccessor());
					Timeline.createSequence().beginSequence()
							.push(Tween.set(newGameInActive, SpriteAccessor.ALPHA).target(0))
							.push(Tween.set(newGameActive, SpriteAccessor.ALPHA).target(0))
							.push(Tween.set(loadGameInActive, SpriteAccessor.ALPHA).target(0))
							.push(Tween.set(loadGameActive, SpriteAccessor.ALPHA).target(0))
							.push(Tween.to(fontYellow, SpriteAccessor.ALPHA, 0.2f).target(1)).end().start(tween);

				}

			}
		} else {
			checkLoadButtonMouseOver = false;
			loadGameInActive.setPosition(loadGameX, LOADGAME_BUTTON_Y);
			loadGameInActive.setSize(LOADGAME_BUTTON_WIDTH, LOADGAME_BUTTON_HEIGHT);
			loadGameInActive.draw(game.getGameController().getSpriteBatch());

		}
	}

	/**
	 * Represents a list of all the saved games the user has in the past. It will
	 * display the date and time of the saved game and the user will be able to load
	 * the game or delete that particular file.
	 * 
	 */
	private void loadOptions() {
		final GameManager gm = new GameManager(game.getGameController());

		int backX = PrisonBreakout.WIDTH / 2 - BACK_BUTTON_WIDTH / 2 - 100;

		backButton(backX);

		fontYellow.draw(game.getGameController().getSpriteBatch(), "Choose a date to load",
				PrisonBreakout.WIDTH / 2 + 100, PrisonBreakout.HEIGHT / 2 + 300);

		File folder = new File("data/bin"); // folder which contains the files
		File[] listOfFiles = folder.listFiles(); // making into an array
		int correctFiles = 0;// counter for the number of valid files

		// Checking if the folder in empty
		if (listOfFiles.length == 0) {
			fontYellow.draw(game.getGameController().getSpriteBatch(),

					"            You have no games saved! \n Please go back and start a new game",
					PrisonBreakout.WIDTH / 2, PrisonBreakout.HEIGHT / 2);

		} else {
			for (int i = 0; i < listOfFiles.length; i++) {

				// checking if the files only contain numbers and is greater than 12 for time
				// stamps
				if (listOfFiles[i].getName().matches("[0-9]+") && listOfFiles[i].getName().length() > 12) {

					// checking if it is a valid time stamp.
					if (isVaildTimeStamp(listOfFiles[i].getName())) {

						correctFiles++;// correctFile increments each time a file is valid

						// Formatting the time stamp to dd-MM-yyyy HH:mm:ss
						DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						long milliSeconds = Long.parseLong(listOfFiles[i].getName());
						Date date = new Date(milliSeconds);

						fontYellow.draw(game.getGameController().getSpriteBatch(), formatter.format(date) + "\n",

								PrisonBreakout.WIDTH / 2 + 50, PrisonBreakout.HEIGHT / 2 + i * 40);

						// Checking if the mouse is at the load button
						if (Gdx.input.getX() < PrisonBreakout.WIDTH / 2 + 350 + 100
								&& Gdx.input.getX() > PrisonBreakout.WIDTH / 2 + 350
								&& PrisonBreakout.HEIGHT - Gdx.input.getY() < PrisonBreakout.HEIGHT / 2 + i * 40

										+ 20
								&& PrisonBreakout.HEIGHT - Gdx.input.getY() > PrisonBreakout.HEIGHT / 2 + i * 40 - 20) {

							loadButtonActive.setSize(94, 28);

							loadButtonActive.setPosition(PrisonBreakout.WIDTH / 2 + 350,
									PrisonBreakout.HEIGHT / 2 + i * 40 - 20);

							loadButtonActive.draw(game.getGameController().getSpriteBatch());

							if (Gdx.input.justTouched()) {
								// Once load button is clicked it will execute gm.loadData()
								
								gm.loadData(Gdx.files.local("data/bin/" + listOfFiles[i].getName()));
								loadPressed = false;
								playPressed = false;
								buttonActive = true;
								Boolean muted = MainMenu.getInstance(game).checkSoundMuted();
								if (muted == true) {
									game.getGameController().stopMusic();
									game.getGameController().setMusic("data/sounds/MainGameMusic.mp3");

								} else {
									game.getGameController().stopMusic();
									game.getGameController().setMusic("data/sounds/MainGameMusic.mp3");
									game.getGameController().playMusic();
								}
								
								

							}
						} else {
							loadButtonInActive.setSize(94, 28);

							loadButtonInActive.setPosition(PrisonBreakout.WIDTH / 2 + 350,
									PrisonBreakout.HEIGHT / 2 + i * 40 - 20);
							loadButtonInActive.draw(game.getGameController().getSpriteBatch());
						}

						// Checking if the mouse is at the delete button
						if (Gdx.input.getX() < PrisonBreakout.WIDTH / 2 + 450 + 100
								&& Gdx.input.getX() > PrisonBreakout.WIDTH / 2 + 450
								&& PrisonBreakout.HEIGHT - Gdx.input.getY() < PrisonBreakout.HEIGHT / 2 + i * 40

										+ 20
								&& PrisonBreakout.HEIGHT - Gdx.input.getY() > PrisonBreakout.HEIGHT / 2 + i * 40 - 20) {

							deleteButtonActive.setSize(137, 28);

							deleteButtonActive.setPosition(PrisonBreakout.WIDTH / 2 + 450,
									PrisonBreakout.HEIGHT / 2 + i * 40 - 20);
							deleteButtonActive.draw(game.getGameController().getSpriteBatch());

							if (Gdx.input.justTouched()) {
								// Once clicked it will delete that file from folder

								Gdx.files.local("data/bin/" + listOfFiles[i].getName()).delete();

							}
						} else {
							deleteButtonInActive.setSize(137, 28);

							deleteButtonInActive.setPosition(PrisonBreakout.WIDTH / 2 + 450,
									PrisonBreakout.HEIGHT / 2 + i * 40 - 20);

							deleteButtonInActive.draw(game.getGameController().getSpriteBatch());
						}
					}
				} else {
					// If there are no correct files then cannot display them
					if (correctFiles == 0) {
						fontYellow.draw(game.getGameController().getSpriteBatch(),

								"            You have no games saved! \n Please go back and start a new game",
								PrisonBreakout.WIDTH / 2, PrisonBreakout.HEIGHT / 2);

					}
				}
			}
		}

	}

	/**
	 * Returns true if the timestamp is less than current time and parses Date.
	 * 
	 * @param String timeStamp
	 * @return valid or not
	 */
	private boolean isVaildTimeStamp(String timeStamp) {

		Date date = new Date(Long.parseLong(timeStamp));
		if (date.getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	/**
	 * Represents the position and area where the back button is drawn.
	 * 
	 * @param backX coordinate of the button
	 */
	private void backButton(int backX) {
		if (Gdx.input.getX() < backX + BACK_BUTTON_WIDTH && Gdx.input.getX() > backX
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y) {

			backButtonActive.setPosition(backX, BACK_BUTTON_Y);
			backButtonActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonActive.draw(game.getGameController().getSpriteBatch());

			if (checkBackButtonMouseOver == false) {
				Sound getMouseOverSound = MainMenu.getInstance(game).mouseOverSound();

				getMouseOverSound.play(1f);

				checkBackButtonMouseOver = true;

			}

			if (Gdx.input.isTouched()) {
				show();
				loadPressed = false;
				playPressed = false;
				buttonActive = true;

			}
		} else {
			checkBackButtonMouseOver = false;
			backButtonInActive.setPosition(backX, BACK_BUTTON_Y);
			backButtonInActive.setSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
			backButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Represents the position and area where the exit button is drawn.
	 * 
	 * @param x coordinate of the button
	 */
	private void exitButton(int x) {
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			exitButtonActive.setPosition(x, EXIT_BUTTON_Y);
			exitButtonActive.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			exitButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkExitButtonMouseOver == false) {
					mouseOverSound.play(1f);
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
	 * Represents the position and area where the help button is drawn.
	 * 
	 * @param x coordinate of the button
	 */
	private void helpButton(int x) {
		if (Gdx.input.getX() < x + HELP_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y) {

			helpButtonActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonActive.draw(game.getGameController().getSpriteBatch());
			if (buttonActive) {
				if (checkHelpButtonMouseOver == false) {
					mouseOverSound.play(1f);
					checkHelpButtonMouseOver = true;

				}
				if (Gdx.input.isTouched()) {
					game.setScreen(new Help(game));
				}
			}
		} else {
			checkHelpButtonMouseOver = false;
			helpButtonInActive.setPosition(x, HELP_BUTTON_Y);
			helpButtonInActive.setSize(HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
			helpButtonInActive.draw(game.getGameController().getSpriteBatch());

		}

	}

	/**
	 * Represents the position and area where the volume button is drawn. Checks
	 * whether the volume is muted for not.
	 * 
	 * @param x coordinate of the button
	 */
	private void volumeButton(int x) {
		if (Gdx.input.getX() < x + VOLUME_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() < VOLUME_BUTTON_Y + VOLUME_BUTTON_HEIGHT
				&& PrisonBreakout.HEIGHT - Gdx.input.getY() > VOLUME_BUTTON_Y && Gdx.input.justTouched()) {
			if (volumeMuted == false) {
				volumeMuted = true;

			} else if (volumeMuted == true) {
				volumeMuted = false;
			}
		}

		Music music = game.getGameController().getMusic();
		if (volumeMuted == false) {

			volumeButtonFull.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonFull.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonFull.draw(game.getGameController().getSpriteBatch());
			music.play();

		} else if (volumeMuted == true) {

			volumeButtonMute.setPosition(x, VOLUME_BUTTON_Y);
			volumeButtonMute.setSize(VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);
			volumeButtonMute.draw(game.getGameController().getSpriteBatch());
			music.pause();
			mouseOverSound.stop();

		}
	}

	/**
	 * Returns the mouse over sound
	 * 
	 * @return mouseOverSound
	 */
	public Sound mouseOverSound() {

		return mouseOverSound;
	}

	/**
	 * Returns true if the volume is muted
	 * 
	 * @return volumeMuted
	 */
	public boolean checkSoundMuted() {
		return volumeMuted;

	}

	/**
	 * Sets the volume muted true or false
	 * 
	 * @param boolean mute
	 */
	public void setVolumeMute(boolean mute) {
		volumeMuted = mute;

	}

	/**
	 * Returns the Sprite of volume button muted
	 * 
	 * @return volumeButtonMute
	 */
	public Sprite volumeButtonMuted() {
		return volumeButtonMute;

	}

	/**
	 * Returns the Sprite of volume button full (not muted)
	 * 
	 * @return volumeButtonFull
	 */
	public Sprite volumeButtonFull() {
		return volumeButtonFull;

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

		playButtonActive.getTexture().dispose();
		playButtonInActive.getTexture().dispose();
		exitButtonActive.getTexture().dispose();
		exitButtonInActive.getTexture().dispose();
		helpButtonActive.getTexture().dispose();
		helpButtonInActive.getTexture().dispose();
		volumeButtonFull.getTexture().dispose();
		volumeButtonMute.getTexture().dispose();
		mouseOverSound.dispose();
	}

	/**
	 * Returns the instance of MainMenu (Singleton)
	 * 
	 * @param game
	 * @return new MainMenu(game) if no instance or current instance.
	 */
	public static MainMenu getInstance(PrisonBreakout game) {
		if (mainInstance == null) {
			mainInstance = new MainMenu(game);
		}
		return mainInstance;
	}

}
