package nz.ac.vuw.ecs.swen225.a3.maze;

public class Chap extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public Chap() {
    isAccessible = true;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Chap";
  }
}
