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
 * <p>
 * You may also use <code>puzzle, item, warp</code> to create a puzzle screen, spawn/remove items and warp to a new map.
 * 
 * @author Sean Corcoran
 * 
 * @version 0.7
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
						String[] choiceData = new String[6];
						if(currChoice.hasChild("dialogue"))
						{
							choiceText = currChoice.getChildByName("dialogue").getText();
						}
						if(currChoice.hasChild("item"))
						{	
							String[] newItem = currChoice.getChildByName("item").getText().split(",");
							if(!newItem[1].equals("remove"))
							{
								spawnItem(newItem);
							}
							else
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
						choiceData[5] = checkEndingGet(currChoice);

						choiceMap.put(choice, choiceData);
					}
				}
				return text;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param name String name of a potential entity with dialogue
	 * @return <code>boolean</code> Whether the given name has a node defined in the XML document.
	 */
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
	
	/**
	 * 
	 * @return Whether or not the current dialogue node contains a choice tag
	 */
	public boolean hasChoices()
	{
		return hasChoice; 
	}
	
	/**
	 * 
	 * @param objective String to set the current objective
	 */
	public void setObjective(String objective)
	{
		controller.setCurrentObjective(objective);
	}
	
	/**
	 * 
	 * @param state String to set the current game state
	 */
	public void setState(String state)
	{

		controller.setGameState(state);
	}
	
	/**
	 * Checks the current element for an objective tag, if it exists it sets the objective
	 * @param e Current <code>Element</code> 
	 */
	public void checkObjectiveSet(Element e)
	{
		if(e.hasChild("objective"))
		{
			setObjective(e.getChildByName("objective").getText());
		}
	}
	
	/**
	 * Checks the current element for a state tag, if it exists it sets the game state
	 * @param e Current <code>Element</code>
	 */
	public void checkStateSet(Element e)
	{
		if(e.hasChild("state"))
		{
			setState(e.getChildByName("state").getText());
		}
	}
		
	/**
	 * Checks for an objective tag, if it exists, returns the given information inside the tag
	 * @param e Current <code>Element</code>
	 * @return The data required for setting the objective
	 */
	private String checkObjectiveGet(Element e)
	{
		if(e.hasChild("objective"))
		{
			return e.getChildByName("objective").getText();
		}
		return "";
	}
	
	/**
	 * Checks for a state tag, if it exists, returns the given information inside the tag
	 * @param e Current <code>Element</code>
	 * @return The data for setting the state
	 */
	private String checkStateGet(Element e)
	{
		if(e.hasChild("state"))
		{
			return e.getChildByName("state").getText();
		}
		return "";
	}
	
	/**
	 * Checks for a puzzle tag, if it exists, return the given information inside the tag
	 * @param e Current <code>Element</code>
	 * @return The data for setting the state after a puzzle is completed
	 */
	private String checkPuzzleGet(Element e)
	{
		if(e.hasChild("puzzle"))
		{
			return e.getChildByName("puzzle").getText();
		}
		return "";
	}
	
	/**
	 * Checks for a warp tag, if it exists, return the given information inside the tag
	 * @param e Current <code>Element</code>
	 * @returns The data for setting the new location in a map
	 */
	private String checkWarpGet(Element e)
	{
		if(e.hasChild("warp"))
		{
			return e.getChildByName("warp").getText();
		}
		return "";
	}
	
	private String checkEndingGet(Element e)
	{
		if(e.hasChild("ending"))
		{
			return e.getChildByName("ending").getText();
		}
		return "";
	}
	
	/**
	 * 
	 * @return Whether a puzzle tag exists in the current node
	 */
	public boolean hasPuzzle()
	{
		return hasPuzzle;
	}
	
	/**
	 * 
	 * @return Whether an ending tag exists in the current node
	 */
	public boolean hasEnding()
	{
		return hasEnding;
	}
	
	/**
	 * Spawns a new item from the XML
	 * @param newItem String array containing the data for a new item
	 */
	private void spawnItem(String[] newItem)
	{
		if(!controller.getItemHandler().itemExists(newItem[0]) && !controller.getItemHandler().foundItemExists(newItem[0]))
		{
			Item i = new Item(new Sprite(new Texture(Gdx.files.internal(newItem[1]))), newItem[0], newItem[2], newItem[3], Integer.parseInt(newItem[4]), Integer.parseInt(newItem[5]));
			controller.getItemHandler().addItem(i.getName(), i);
//			if(controller.getItemHandler().foundItemExists(newItem[0]))
//			{
//				removeItem(newItem[0]);
//			}
			//controller.getMapScreen().addItemToMap(i);
			//Adds the item sprite to the map, but doesn't seem to make it an interactable actor.
		}
	}
	
	/**
	 * Removes the given item from the map
	 * @param name of the item
	 */
	private void removeItem(String name)
	{
		controller.getItemHandler().removeItem(name);
		//Item i = controller.getItemHandler().getAllItems().get(name);
		//controller.getMapScreen().removeItemFromMap(i);
		//Removes the item from the map render, still has collision
	}
	
	/**
	 * 
	 * @return The current chapter according the the state
	 */
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
	
	/**
	 * Updates the XML reader to look at the next chapter
	 */
	private void updateReader()
	{
		root = xReader.parse(Gdx.files.internal(xmlLoc + getChapter() + ".xml"));
	}
	
}
