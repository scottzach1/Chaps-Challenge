package nz.ac.vuw.ecs.swen225.a3.maze;

public class Free extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public Free() {
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
}
