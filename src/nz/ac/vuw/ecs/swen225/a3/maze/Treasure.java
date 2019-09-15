package nz.ac.vuw.ecs.swen225.a3.maze;

public class Treasure extends Tiles {


  /**
   * Constructor.
   * Sets the isAccessible field to true.
   */
   Treasure() {
    super(Type.Treasure);
    isAccessible = true;
    imageUrl = "assets/treasure.png";
    defaultImageUrl = "assets/free.png";
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Treasure";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    imageUrl = defaultImageUrl;
    return isAccessible;
  }
}
