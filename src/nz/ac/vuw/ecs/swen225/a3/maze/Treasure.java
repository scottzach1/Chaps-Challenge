package nz.ac.vuw.ecs.swen225.a3.maze;

public class Treasure extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible field to true.
   */
  public Treasure() {
    isAccessible = true;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Treasure";
  }
}
