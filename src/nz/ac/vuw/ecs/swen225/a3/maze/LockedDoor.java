package nz.ac.vuw.ecs.swen225.a3.maze;

public class LockedDoor extends Tiles {
  private String colour;
  private boolean active; //an active tile is a tile which is not blank.

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the door to the parameter.
   * @param colour the colour of the door.
   */
   LockedDoor(String colour) {
    isAccessible = false;
    this.colour = colour;
    active=true;
  }

  /**
   * Standard toString method.
   * @return the name of the tile + the colour
   */
  @Override
  public String toString() {
    return colour + " LockedDoor";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {
    //active=false;
    return false;
  }
}
