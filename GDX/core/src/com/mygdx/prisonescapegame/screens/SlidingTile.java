package com.mygdx.prisonescapegame.screens;

import com.badlogic.gdx.graphics.Texture;

public class SlidingTile
{
  Texture texture;
  Texture hoverTexture;
  int finishRow;
  int finishCol;
  int currentRow;
  int currentCol;
  boolean used = false;
  String imageLoc = "data/puzzles/";
  
  public SlidingTile(int x, int y)
  {
    this.finishRow = x;
    this.finishCol = y;
    
    this.texture = new Texture(this.imageLoc + x + y + ".jpg");
    this.hoverTexture = new Texture(this.imageLoc + x + y + "_hover.jpg");
  }
  
  public int getRow()
  {
    return this.currentRow;
  }
  
  public int getCol()
  {
    return this.currentCol;
  }
  
  public Texture getTexture()
  {
    return this.texture;
  }
  
  public Texture getHoverTexture()
  {
    return this.hoverTexture;
  }
  
  public void clearTexture()
  {
    this.texture = new Texture(this.imageLoc + "black.png");
    this.hoverTexture = new Texture(this.imageLoc + "black.png");
  }
  
  public boolean inCorrectPosition(int x, int y)
  {
    return (x == this.finishRow) && (y == this.finishCol);
  }
}

