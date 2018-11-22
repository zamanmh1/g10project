package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends MapEntity {
	private String name;
	private String type;
	private String appearsIn;
	private float x, y;
	
	public Item(Sprite sprite, String name, String appearsIn, String type, float x, float y) {
		super(sprite);
		sprite.setSize(16, 16);
		this.name = name;
		this.appearsIn = appearsIn;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getAppearsIn() {
		return this.appearsIn;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}
