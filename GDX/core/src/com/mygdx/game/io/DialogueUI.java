package com.mygdx.game.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.prisonescapegame.Dialogue;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sean Corcoran
 * 
 * @version 0.1
 * @since 0.1
 * 
 */

public class DialogueUI extends ScreenAdapter
{
	private Skin skin;
	private Dialogue d = new Dialogue();
	public boolean beenCalled = false;
	

//	public Label getLabel(Stage s)
//	{
//		if(s.getActors().size != 0)
//		{
//			s.getActors().pop();
//		}
//		Label label;
//		LabelStyle style;
//		BitmapFont font = new BitmapFont();
//		
//		style = new LabelStyle();
//		style.font = font;
//		if(title != null)
//		{
//			label = new Label(text, style);
//			return label;
//		}
//		return null;
//	}
	public void showDialogue(Stage s, String name)
	{
		skin = new Skin(Gdx.files.internal("data/story/skin/uiskin.json"));
		final String dText = d.getDialogue(name);
		Dialog dialog = new Dialog(name, skin)
		{
			{
				text(dText);
				//button("OK");
			}
//			@Override
//			protected void result(Object object)
//			{
//				
//			}
		};
		dialog.key(Keys.E, true);
		//dialog.show(s);
		dialog.setMovable(false);
		dialog.setModal(false);
		dialog.sizeBy(dialog.getPrefWidth(), 5);
		//dialog.pack();
		s.addActor(dialog);
	}
	
}
