package com.mygdx.game.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.prisonescapegame.Dialogue;

/**
 * DialogueUI creates the dialogue boxes used to display the text the <code>Dialogue</code> class provides.
 * 
 * @author Sean Corcoran
 * 
 * @version 0.3
 * @since 0.1
 * 
 */

public class DialogueUI extends ScreenAdapter
{
	private Skin skin = new Skin(Gdx.files.internal("data/story/skin/uiskin.json"));
	private Dialogue d = new Dialogue();
	public boolean beenCalled = false;
	
	public void showDialogue(final Stage s, final String name)
	{
		final String dText = d.getDialogue(name);
		Dialog dialog = new Dialog(name, skin)
		{
			{
				text(dText);
				if(d.hasChoices())
				{
					for(String key : d.getChoices().keySet())
					{
						button(key, key); //key,key: one is btn text, other is passing the name to result
					}
				}
			}
			@Override
			protected void result(Object object)
			{
				if(!object.equals("E"))
				{
					showAlternateDialog(s, name, d.getChoices().get(object));
					updateValues(object);
				}
			}
		};
		dialog.key(Keys.E, "E");
		dialog.setMovable(false);
		dialog.setModal(false);
		dialog.sizeBy(dialog.getPrefWidth(), dialog.getPrefHeight());
		s.addActor(dialog);
	}
	
	private void showAlternateDialog(Stage s, String name, final String[] cText)
	{
		Dialog dialog = new Dialog(name, skin)
		{
			{
				text(cText[0]);
			}
			@Override
			protected void result(Object object)
			{
				hide();
			}
		};
		dialog.key(Keys.E, true);
		dialog.setMovable(false);
		dialog.setModal(false);
		dialog.sizeBy(dialog.getPrefWidth(), dialog.getPrefHeight());
		s.addActor(dialog);
	}
	
	private void updateValues(Object o)
	{
		String[] cData = d.getChoices().get(o);
		if(!cData[1].isEmpty()) //If Objectives is not empty
		{
			d.setObjective(cData[1]);
		}
		if(!cData[2].isEmpty())//If State is not empty
		{
			d.setState(cData[2]);
		}
	}
}
