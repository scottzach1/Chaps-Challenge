package nz.ac.vuw.ecs.swen225.a3.maze;

public class Wall extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible field to false.
   */
   Wall() {
    isAccessible = false;
  }


  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {
    return isAccessible;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Wall";
  }
}
