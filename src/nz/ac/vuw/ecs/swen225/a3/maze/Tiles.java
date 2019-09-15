package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Tiles {

  public enum Type{
    Free, Treasure, Exit, ExitLock, InfoFeild, Key, LockedDoor, Wall
  }

  boolean isAccessible;
  private int row;
  private int col;
  private Type type;
  String imageUrl;
  String defaultImageUrl;

  public Tiles(Type t){
    type = t;
  }

  public Tiles(){};

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public Type getType(){return type;}

  public String getImageUrl() {
    return imageUrl;
  }

  public String getDefaultImageUrl(){return defaultImageUrl;}


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

  /**
   * Checks if the interaction between a character and a tile is valid.
   *  @param p The player
   * @return if the interaction is valid
   */
  abstract boolean interact(Player p);

  public void setTileOccupied(){
    imageUrl = "assets/chap.png";
  }

  public void setTileUnoccupied(){
    imageUrl = defaultImageUrl;
  }

}
