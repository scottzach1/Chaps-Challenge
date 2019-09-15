package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

public class Wall extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible field to false.
   */
   Wall() {
     super(Type.Wall);
     isAccessible = false;
    imageUrl = "wall.png";
    defaultImageUrl = "wall.png";

     AssetManager.loadAsset(imageUrl);
     AssetManager.loadAsset(defaultImageUrl);
  }


  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    return isAccessible;
  }

  /**
   * Standard toString method.
   *
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Wall";
  }
}
