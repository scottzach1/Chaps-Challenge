package nz.ac.vuw.ecs.swen225.a3.maze;

public class ExitLock extends Tiles {
  private int totalTreasures;// amount of treasures that still need to be collected

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public ExitLock(int treasures) {
    isAccessible = false;
    totalTreasures=treasures;
    imageUrl = "assets/exit_lock.png";
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "ExitLock";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
   if (totalTreasures==p.getTreasures()){
      setActive(false);
    }
    return isAccessible;
  }
}
