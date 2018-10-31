package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class MainMenuScreen implements Screen {

	private static final int LOGO_WIDTH = 300;
	private static final int LOGO_HEIGHT = 150;
	private static final int LOGO_Y = 500;
	private static final int PLAY_BUTTON_WIDTH = 300;
	private static final int PLAY_BUTTON_HEIGHT = 150;
	private static final int PLAY_BUTTON_Y = 300;
	private static final int EXIT_BUTTON_WIDTH = 300;
	private static final int EXIT_BUTTON_HEIGHT = 150;
	private static final int EXIT_BUTTON_Y = 200;

	PrisonEscapeGame game;

	Texture logo;
	Texture playButtonActive;
	Texture playButtonInActive;
	Texture exitButtonActive;
	Texture exitButtonInActive;

	public MainMenuScreen(PrisonEscapeGame game) {
		this.game = game;
		logo = new Texture("logo.jpg");
		playButtonActive = new Texture("play_active.jpg");
		playButtonInActive = new Texture("play_inactive.jpg");
		exitButtonActive = new Texture("exit_active.jpg");
		exitButtonInActive = new Texture("exit_inactive.jpg");

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();

		int x = PrisonEscapeGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {

			game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		} else {
			game.batch.draw(playButtonInActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

		}
		x = PrisonEscapeGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& PrisonEscapeGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {

			game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		} else {
			game.batch.draw(exitButtonInActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

		}

		game.batch.draw(logo, PrisonEscapeGame.WIDTH / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
		game.batch.end();

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

	}

}
