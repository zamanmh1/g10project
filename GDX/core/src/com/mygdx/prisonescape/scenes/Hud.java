package com.mygdx.prisonescape.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.prisonescapegame.PrisonEscapeGame;

public class Hud {
	public Stage stage;
	private Viewport viewport;
	
	
	

	Label playerLabel;
	Label missionObjectiveLabel;
	
	public Hud(SpriteBatch sb) {
		
		
		viewport = new FitViewport(PrisonEscapeGame.WIDTH, PrisonEscapeGame.HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		missionObjectiveLabel = new Label(" Mission objective: Spike his food with laxitives", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		playerLabel = new Label("Player 01", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(missionObjectiveLabel).expandX().pad(10);
		table.add(playerLabel).expand().padTop(10);
		
		stage.addActor(table);
		
		
		
	}
	

}
