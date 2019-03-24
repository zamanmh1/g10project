package prisonescape.game.model;


import java.util.HashMap;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import prisonescape.game.GameHandler;
import prisonescape.game.model.actors.Item;

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
 * @version 0.5
 * @since 0.1
 * 
 */

public class Dialogue 
{
	private XmlReader xReader;
	private String xmlLoc = "data/story/chapter";
	private Element root;
	private Element entityRoot;
	private HashMap<String,String[]> choiceMap;
	private boolean hasChoice;
	private GameHandler controller;
	private boolean hasPuzzle;
	private boolean hasEnding;
	
	public Dialogue(GameHandler controller)
	{ 
		this.controller = controller;
		xReader = new XmlReader();
		//file will be named ../chapter[x].xml
		root = xReader.parse(Gdx.files.internal(xmlLoc + getChapter() + ".xml"));
	}
	
	/**
	 * getDialogue is used to retrieve the dialogue needed for a given entity. It is passed the parameter name which is
	 * used to set the new root node. It checks each <code>dialogue</code> child node from the root.
	 * <p>
	 * <code>dialogue</code> nodes can be selectively chosen depending on the current state of the game by using
	 * the attribute <code>currState</code> which can be checked against the game state in <code>GameHandler</code> in order
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
		updateReader();
		entityRoot = root.getChildByName(name); //new root xml node
		Iterator<Element> iterator_dialogue = entityRoot.getChildrenByName("dialogue").iterator(); 

		while(iterator_dialogue.hasNext())
		{
			hasChoice = false;
			hasPuzzle = false;
			hasEnding = false;
			Element currElement = (Element) iterator_dialogue.next();
			if(currElement.hasAttribute("currState") == false || currElement.get("currState").equals(controller.getGameState()))
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
					Iterator<Element> iterateChoices = currElement.getChildrenByName("choice").iterator();
					while(iterateChoices.hasNext())
					{
						Element currChoice = (Element) iterateChoices.next();
						String choice = currChoice.getText();
						String choiceText = "";
						String[] choiceData = new String[5];
						if(currChoice.hasChild("dialogue"))
						{
							choiceText = currChoice.getChildByName("dialogue").getText();
						}
						if(currChoice.hasChild("item"))
						{							
							String[] newItem = currChoice.getChildByName("item").getText().split(",");
							spawnItem(newItem);
							if(newItem[1].equals("remove"))
							{
								removeItem(newItem[0]);
							}
						}
						if(currChoice.hasChild("puzzle"))
						{
							hasPuzzle = true;
						}
						if(currChoice.hasChild("ending"))
						{
							hasEnding = true;
							/*
							 * Need to discuss how to make each ending 'different', like how do we indicate what happens
							 * after the fact? Can we pass text to the Credits class to display on screen
							 * e.g.
							 * "Your sentence is commuted, and your family are safe."
							 */
						}
						choiceData[0] = choiceText;
						choiceData[1] = checkObjectiveGet(currChoice);
						choiceData[2] = checkStateGet(currChoice);
						choiceData[3] = checkPuzzleGet(currChoice);
						choiceData[4] = checkWarpGet(currChoice);

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
	 * This array contains the dialogue itself as well as the state and objective to update to, should it contain them.
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
		controller.setCurrentObjective(objective);
	}
	
	public void setState(String state)
	{

		controller.setGameState(state);
	}
	
	public void checkObjectiveSet(Element e)
	{
		if(e.hasChild("objective"))
		{
			setObjective(e.getChildByName("objective").getText());
		}
	}
	
	public void checkStateSet(Element e)
	{
		if(e.hasChild("state"))
		{
			setState(e.getChildByName("state").getText());
		}
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
	
	private String checkPuzzleGet(Element e)
	{
		if(e.hasChild("puzzle"))
		{
			return e.getChildByName("puzzle").getText();
		}
		return "";
	}
	
	private String checkWarpGet(Element e)
	{
		if(e.hasChild("warp"))
		{
			return e.getChildByName("warp").getText();
		}
		return "";
	}
	
	public boolean hasPuzzle()
	{
		return hasPuzzle;
	}
	
	public boolean hasEnding()
	{
		return hasEnding;
	}
	
	private void spawnItem(String[] newItem)
	{
		if(!controller.getItemHandler().itemExists(newItem[0]))
		{
			Item i = new Item(new Sprite(new Texture(Gdx.files.internal(newItem[1]))), newItem[0], newItem[2], newItem[3], Integer.parseInt(newItem[4]), Integer.parseInt(newItem[5]));
			controller.getItemHandler().addItem(i.getName(), i);
			//controller.getMapScreen().addItemToMap(i);
			//Adds the item sprite to the map, but doesn't seem to make it an interactable actor.
		}
	}
	
	private void removeItem(String name)
	{
		controller.getItemHandler().removeItem(name);
		//Item i = controller.getItemHandler().getAllItems().get(name);
		//controller.getMapScreen().removeItemFromMap(i);
		//Removes the item from the map render, still has collision
	}
	
	private String getChapter()
	{
		//State follows a standard. e.g. 1.2(b) -> chapter.state(path)
		char state = controller.getGameState().charAt(0);
		String chapter = null;
		switch(state)
		{
		case '1':
			chapter = "1";
			break;
		case '2':
			chapter = "2";
			break;
		case '3':
			chapter = "3";
			break;
		}
		return chapter;
	}
	
	private void updateReader()
	{
		root = xReader.parse(Gdx.files.internal(xmlLoc + getChapter() + ".xml"));
	}
	
}
