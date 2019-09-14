package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Tiles {
  boolean isAccessible;
  private boolean isActive;

  public String getImageUrl() {
    return imageUrl;
  }

  String imageUrl;

  public boolean isActive() {
    return isActive;
  }

   void setActive(boolean active) {
    isActive = active;
  }

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
   void setAccessible(boolean accessible) {
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

  /**
   * Checks if the interaction between a character and a tile is valid.
   *  @param p The player
   * @return if the interaction is valid
   */
  abstract boolean interact(Player p);

}
