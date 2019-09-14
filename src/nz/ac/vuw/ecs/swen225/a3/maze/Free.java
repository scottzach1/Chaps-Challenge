package nz.ac.vuw.ecs.swen225.a3.maze;

public class Free extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
   Free() {
    isAccessible = true;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Free";
  }



  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {
    return false;
  }
}
