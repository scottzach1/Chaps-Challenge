package nz.ac.vuw.ecs.swen225.a3.maze;

public class Wall extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible field to false.
   */
  public Wall() {
    isAccessible = false;
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
