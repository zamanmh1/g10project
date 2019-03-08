package com.mygdx.prisonescapegame;


import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * CLASS DESCRIPTION
 * 
 * @author Sean Corcoran
 * 
 * @version 0.2
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
		//entityRoot = root.getChildByName("entity");
	}
	
	public String getDialogue(String name)
	{
		entityRoot = root.getChildByName(name); //new root xml node
		
		Iterator iterator_dialogue = entityRoot.getChildrenByName("dialogue").iterator(); 
		
		while(iterator_dialogue.hasNext())
		{
			hasChoice = false;
			Element currElement = (Element) iterator_dialogue.next();
			if(currElement.hasAttribute("currState") == false || currElement.get("currState").equals(GameSettings.getGameState()))
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
		GameSettings.setGameState(state);
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
	
	public String checkObjectiveGet(Element e)
	{
		if(e.hasChild("objective"))
		{
			return e.getChildByName("objective").getText();
		}
		return "";
	}
	
	public String checkStateGet(Element e)
	{
		if(e.hasChild("state"))
		{
			return e.getChildByName("state").getText();
		}
		return "";
	}
}
