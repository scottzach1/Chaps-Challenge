package nz.ac.vuw.ecs.swen225.a3.maze;

public class Exit extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public Exit() {
    isAccessible = false;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Exit";
  }
}
