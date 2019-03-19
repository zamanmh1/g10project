package prisonescape.game.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import prisonescape.game.GameHandler;
import prisonescape.game.model.Dialogue;
import prisonescape.game.screens.Puzzle;

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
	private Dialogue d;
	private boolean beenCalled = false;
	private GameHandler gameHandler;
	
	public DialogueUI(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
		d = new Dialogue(gameHandler);
	}
	
	public void showDialogue(final Stage s, final String name)
	{
		beenCalled = true;
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
				if(object.equals("E"))
				{
					hide();
					beenCalled = false;
				}
				if(d.hasPuzzle() && object.equals("puzzle")) //Relies on button name being "puzzle"
				{
					gameHandler.getGame().setScreen(new Puzzle(gameHandler.getGame(), d.getChoices().get(object)[3]));
					updateValues(object);
				}
			}
		};
		dialog.setName("dialog");
		dialog.key(Keys.E, "E");
		dialog.setMovable(false);
		dialog.setModal(false);
		dialog.sizeBy(dialog.getPrefWidth(), dialog.getPrefHeight());
		s.addActor(dialog);
	}
	
	private void showAlternateDialog(Stage s, String name, final String[] cText)
	{
		beenCalled = true;
		Dialog altDialog = new Dialog(name, skin)
		{
			{
				text(cText[0]);
			}
			@Override
			protected void result(Object object)
			{
				if(object.equals(true))
				{
					hide();
					beenCalled = false;
				}
			}
		};
		altDialog.key(Keys.E, true);
		altDialog.setMovable(false);
		altDialog.setModal(false);
		altDialog.sizeBy(altDialog.getPrefWidth(), altDialog.getPrefHeight());
		s.addActor(altDialog);
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
	
	public boolean beenCalled()
	{
		return beenCalled;
	}
	
	public void hideAll(Stage s)
	{
		s.getRoot().findActor("dialog").remove();
	}
}
