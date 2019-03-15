package com.mygdx.prisonescapegame;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Dialogue parses and formats all data within a given XML document to select the appropriate dialogue to be displayed.
 * The XML document is expected to have a root node containing all subsequent nodes. All subsequent nodes are to be named
 * for each entity (NPC, Item) which is to have a given dialogue.
 * <p>
 * The only required node to enable dialogue is <code>dialogue</code> within the entity node.
 * Additional functionality can be provided with the use of <code>state</code>, <code>objective</code>, and <code>choice</code> nodes.
 * 
 * @author Sean Corcoran
 * 
 * @version 0.3
 * @since 0.1
 * 
 */



public class Dialogue 
{
	private XmlReader xReader;
	private String xmlDoc = "data/story/amalgamation.xml";
	private Element root;
	private Element entityRoot;
	private HashMap<String,String[]> choiceMap;
	private boolean hasChoice;
	
	public Dialogue()
	{ 
		xReader = new XmlReader();
		root = xReader.parse(Gdx.files.internal(xmlDoc));
	}
	
	/**
	 * getDialogue is used to retrieve the dialogue needed for a given entity. It is passed the parameter name which is
	 * used to set the new root node. It checks each <code>dialogue</code> child node from the root.
	 * <p>
	 * <code>dialogue</code> nodes can be selectively chosen depending on the current state of the game by using
	 * the attribute <code>currState</code> which can be checked against <code>GameSettings.gameState</code> in order
	 * to have dynamic dialogue according to the state of the game.
	 * <p>
	 * When checking for dialogue, game values can be updated when the correct node is selected such as the game state and 
	 * current objective. This will help facilitate the progression of the game.
	 * 
	 * @param name	The name of the interacted with entity
	 * @return		<code>String</code> Returns a String of the selected dialogue for the given entity.
	 */
	public String getDialogue(String name)
	{
		entityRoot = root.getChildByName(name); //new root xml node
		Iterator iterator_dialogue = entityRoot.getChildrenByName("dialogue").iterator(); 

		while(iterator_dialogue.hasNext())
		{
			hasChoice = false;
			Element currElement = (Element) iterator_dialogue.next();
			if(currElement.hasAttribute("currState") == false || currElement.get("currState").equals(GameSettings.gameState))
			{
				String text = currElement.getText();

				if(currElement.hasChild("state")) //Update state if exists
				{
					setState(currElement.getChildByName("state").getText());
				}
				if(currElement.hasChild("objective")) //Updates current objective
				{
					setObjective(currElement.getChildByName("objective").getText());
				}
				if(currElement.hasChild("choice"))
				{
					hasChoice = true;
					choiceMap = new HashMap<String,String[]>();
					Iterator iterateChoices = currElement.getChildrenByName("choice").iterator();
					while(iterateChoices.hasNext())
					{
						Element currChoice = (Element) iterateChoices.next();
						String choice = currChoice.getText();
						String choiceText = "";
						String[] choiceData = new String[3];
						if(currChoice.hasChild("dialogue"))
						{
							choiceText = currChoice.getChildByName("dialogue").getText();
						}
						choiceData[0] = choiceText;
						choiceData[1] = checkObjectiveGet(currChoice);
						choiceData[2] = checkStateGet(currChoice);

						choiceMap.put(choice, choiceData);
					}
				}
				return text;
			}
		}
		return null;
	}
	
	public boolean hasDialogue(String name)
	{
		if(root.hasChild(name))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * getChoices() returns a Hash Map using the choice names as keys (yes, no etc) and the values being an array of strings.
	 * This array contains the dialogue itself as well as the state and objective to update to should it contain them.
	 * 
	 */
	public HashMap<String,String[]> getChoices()
	{
		return choiceMap;
	}
	
	public boolean hasChoices()
	{
		return hasChoice; 
	}
	
	public void setObjective(String objective)
	{
		GameSettings.currentObjective = objective;
	}
	
	public void setState(String state)
	{
		GameSettings.gameState = state;
	}
		
	private String checkObjectiveGet(Element e)
	{
		if(e.hasChild("objective"))
		{
			return e.getChildByName("objective").getText();
		}
		return "";
	}
	
	private String checkStateGet(Element e)
	{
		if(e.hasChild("state"))
		{
			return e.getChildByName("state").getText();
		}
		return "";
	}
	
}
