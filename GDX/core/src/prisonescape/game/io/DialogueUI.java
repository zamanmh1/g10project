package prisonescape.game.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import prisonescape.game.GameHandler;
import prisonescape.game.model.Dialogue;
import prisonescape.game.model.Tile;
import prisonescape.game.model.actors.Item;
import prisonescape.game.model.actors.Item.ITEM_TYPE;
import prisonescape.game.model.actors.MapActor;
import prisonescape.game.screens.Credits;
import prisonescape.game.screens.Puzzle;

/**
 * DialogueUI creates the dialogue boxes used to display the text the <code>Dialogue</code> class provides.
 * 
 * @author Sean Corcoran, Sam Ward
 * 
 * @version 1.0
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
	
	/**
	 * Displays the dialogue box to show the interacted with entity's text
	 * @param s a Scene2D <code>Stage</code> used to add the dialogue box to
	 * @param name The String name of the interacted with entity used to title the dialogue box and get the dialogue
	 */
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
					Tile target = gameHandler.getMapScreen().getTiledModel().getTile(gameHandler.getPlayer().getX() + gameHandler.getPlayer().getFacing().getMoveX(), gameHandler.getPlayer().getY() + gameHandler.getPlayer().getFacing().getMoveY()); // If player facing actor to interact with.
					if (!target.getTeleporter() == true) {
						hide();
						beenCalled = false;
					}
					if(target.getActor() != null) {
						MapActor a = target.getActor();
						if (a instanceof Item) {
							Item i = (Item) a;
							if (i.getType() == ITEM_TYPE.INTERACT) {
								hide();
								beenCalled = false;
							}
						}
					}
				}
				if(d.hasPuzzle() && (object.equals("Teach me") || object.equals("Hack"))) //Relies on button name being "puzzle"
				{
					gameHandler.getGame().setScreen(new Puzzle(gameHandler.getGame(), d.getChoices().get(object)[3]));
					updateValues(object);
					beenCalled = false;
				}
				if(d.hasEnding() && gameHandler.getGameState().endsWith("z"))
				{
					setEnding(d.getChoices().get(object)[5]);
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
	
	/**
	 * If the initial dialogue contains a choice, then a follow up dialogue is made and displayed here according to the choice made
	 * @param s A Scene2D <code>Stage</code> to add the dialogue box to
	 * @param name The String name of the interacted with entity
	 * @param cText A String Array of data from the choice
	 */
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
						setEnding(cText[5]); //Ending text
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
	
	/**
	 * Updates any values made after a choice such as the objective, state and map changes after warping
	 * @param o The given object key for a hashmap of data value for that choicex
	 */
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
	
	/**
	 * 
	 * @return Whether a dialogue box has been called and is currently displayed
	 */
	public boolean beenCalled()
	{
		return beenCalled;
	}
	
	/**
	 * 
	 * @return Whether a dialogue box is currently visible
	 */
	public boolean isVisible()
	{
		return isVisible();
	}
	
	/**
	 * If a dialog box is present on screen, remove all dialog boxes from the stage
	 * @param s A Scene2D <code>Stage</code>
	 */
	public void hideAll(Stage s)
	{
		s.getRoot().findActor("dialog").remove();
	}
	
	/**
	 * Creates a new Credits screen when a choice is made leading to the ending of the game
	 */
	private void setEnding(String endingNumber)
	{
		Credits credits = new Credits(gameHandler.getGame());
		gameHandler.getGame().setScreen(credits);
		switch(endingNumber.charAt(0))
		{
		case '1':
			credits.ending1();
			break;
		case '2':
			credits.ending2();
			break;
		case '3':
			credits.ending3();
			break;
		}
	}
}
