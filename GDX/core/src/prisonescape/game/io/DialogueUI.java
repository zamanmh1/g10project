package prisonescape.game.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import prisonescape.game.GameHandler;
import prisonescape.game.model.Dialogue;
import prisonescape.game.model.Tile;
import prisonescape.game.model.actors.Item;
import prisonescape.game.screens.Credits;
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
	//private boolean isVisible = false;
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
				//isVisible = this.isVisible();
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
					//isVisible = this.isVisible();
					Tile target = gameHandler.getMapScreen().getTiledModel().getTile(gameHandler.getPlayer().getX() + gameHandler.getPlayer().getFacing().getMoveX(), gameHandler.getPlayer().getY() + gameHandler.getPlayer().getFacing().getMoveY()); // If player facing actor to interact with.
					if (!target.getTeleporter() == true) {
						hide();
						beenCalled = false;
					}
					hide();
					beenCalled = false;
				}
				if(d.hasPuzzle() && (object.equals("Teach me") || object.equals("Hack"))) //Relies on button name being "puzzle"
				{
					gameHandler.getGame().setScreen(new Puzzle(gameHandler.getGame(), d.getChoices().get(object)[3]));
					updateValues(object);
					beenCalled = false;
				}
				if(d.hasEnding() && gameHandler.getGameState().endsWith("z"))
				{
					setEnding();
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
					if(d.hasEnding() && gameHandler.getGameState().endsWith("z"))
					{
						//gameHandler.getGame().setScreen(new Credits(gameHandler.getGame()));
						setEnding();
					}
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
		if(!cData[4].isEmpty())
		{
			String[] mapChange = cData[4].split(",");
			gameHandler.setMap(mapChange[0], Integer.parseInt(mapChange[1]), Integer.parseInt(mapChange[2]));
			gameHandler.getPlayer().setFrozen(false);
		}
	}
	
	public boolean beenCalled()
	{
		return beenCalled;
	}
	
	public boolean isVisible()
	{
		return isVisible();
	}
	
	public void hideAll(Stage s)
	{
		s.getRoot().findActor("dialog").remove();
	}
	
	private void setEnding()
	{
		gameHandler.getGame().setScreen(new Credits(gameHandler.getGame()));
	}
}
