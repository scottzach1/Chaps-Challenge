package nz.ac.vuw.ecs.swen225.a3.maze;

public class LockedDoor extends Tiles {
  private String colour;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the door to the parameter.
   * @param colour the colour of the door.
   */
  public LockedDoor(String colour) {
    isAccessible = false;
    this.colour = colour;
  }

  /**
   * Standard toString method.
   * @return the name of the tile + the colour
   */
  @Override
  public String toString() {
    return colour + " LockedDoor";
  }
}
