package nz.ac.vuw.ecs.swen225.a3.maze;

public class ExitLock extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public ExitLock() {
    isAccessible = false;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "ExitLock";
  }
}
