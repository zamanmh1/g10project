package com.mygdx.prisonescapegame;


import java.io.FileNotFoundException;
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
 * @version 0.1
 * @since 0.1
 * 
 */



public class Dialogue 
{
	private XmlReader xReader;
	private String xmlDoc = "data/story/test2.xml";
	private Element root;
	private Element entityRoot;
	
	public Dialogue()
	{ 
		
		xReader = new XmlReader();
		root = xReader.parse(Gdx.files.internal(xmlDoc));
		//entityRoot = root.getChildByName("entity");
	}
	
	public String getDialogue(String name)
	{
		entityRoot = root.getChildByName(name); //new root xml node
		
		//System.out.println(entityRoot.getName());
		
		Iterator iterator_dialogue = entityRoot.getChildrenByName("dialogue").iterator(); 
		
		while(iterator_dialogue.hasNext())
		{
			Element currElement = (Element) iterator_dialogue.next();
			//System.out.println("Current State: " + currElement.get("currState") + " - Game State: " + GameSettings.getGameState());
			if(currElement.hasAttribute("currState") == false || currElement.get("currState").equals(GameSettings.getGameState()))
			{
				String text = currElement.getText();
				//System.out.println(text);
				
				if(currElement.hasChild("state"))
				{
					GameSettings.setGameState(currElement.getChildByName("state").getText());
					//System.out.println("state is " + GameSettings.getGameState());
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
	
	
}
