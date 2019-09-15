package nz.ac.vuw.ecs.swen225.a3.maze;

public class Exit extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
   Exit() {
    isAccessible = true;
    imageUrl = "assets/exit.png";
    defaultImageUrl = "assets/free.png";
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Exit";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    //todo continue to next level/end of game screen
    return isAccessible;
  }
}
