package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Tiles {
  boolean isAccessible;

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  boolean isActive;

  /**
   * Checks if the current tile is accessible.
   * @return if it is accessible
   */
  boolean getIsAccessible() {
    return isAccessible;
  }

  /**
   * Sets the boolean isAccessible based on the parameter.
   * @param accessible boolean to set isAccessible to.
   */
  public void setAccessible(boolean accessible) {
    isAccessible = accessible;
  }

  public enum Direction{
    Left,Right,Up,Down
  }

  Tiles[] adjacent = new Tiles[4];
  
  /**
   * Gets the tile to the left.
   * @return left of tile.
   */
  public Tiles getLeft() {
    return adjacent[Direction.Left.ordinal()];
  }
  
  /**
   * Gets the tile to the right.
   * @return right of tile.
   */
  public Tiles getRight() {
    return adjacent[Direction.Right.ordinal()];
  }
  
  /**
   * Gets the tile above.
   * @return up of tile.
   */
  public Tiles getUp() {
    return adjacent[Direction.Up.ordinal()];
  }
  
  /**
   * Gets the tile below.
   * @return down of tile.
   */
  public Tiles getDown() {
    return adjacent[Direction.Down.ordinal()];
  }


}
