package nz.ac.vuw.ecs.swen225.a3.maze;

public class Treasure extends Tiles {
  private boolean active; //an active tile is a tile which is not blank.


  /**
   * Constructor.
   * Sets the isAccessible field to true.
   */
   Treasure() {
    isAccessible = true;
    active=true;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Treasure";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {

    active=false;
    return isAccessible;
  }
}
