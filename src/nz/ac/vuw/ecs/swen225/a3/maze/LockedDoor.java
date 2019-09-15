package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

public class LockedDoor extends Tiles {
  private String colour;


  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the door to the parameter.
   *
   * @param colour the colour of the door.
   */
  LockedDoor(String colour) {
    super(Type.LockedDoor);
    isAccessible = false;
    this.colour = colour;
    imageUrl = "locked_door_" + colour + ".png";
    defaultImageUrl = "free.png";

    AssetManager.loadAsset(imageUrl);
    AssetManager.loadAsset(defaultImageUrl);
  }

  /**
   * Standard toString method.
   *
   * @return the name of the tile + the colour
   */
  @Override
  public String toString() {
    return colour + " LockedDoor";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    if (p.getItem(colour + " Key")) {
      setAccessible(true);
      imageUrl = defaultImageUrl;
    }
    return isAccessible;
  }
}
