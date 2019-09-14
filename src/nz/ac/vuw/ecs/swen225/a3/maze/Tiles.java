package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Tiles {
  boolean isAccessible;

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

  public Tiles getLeft(){
    return adjacent[Direction.Left.ordinal()];
  }

  public Tiles getRight(){
    return adjacent[Direction.Right.ordinal()];
  }

  public Tiles getUp(){
    return adjacent[Direction.Up.ordinal()];
  }

  public Tiles getDown(){
    return adjacent[Direction.Down.ordinal()];
  }


}
