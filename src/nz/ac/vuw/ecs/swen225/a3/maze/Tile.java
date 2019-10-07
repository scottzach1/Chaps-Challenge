package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;


public abstract class Tile {

  private boolean isOccupied;
  boolean isAccessible;
  private int row;
  private int col;
  private Type type;
  String imageUrl;
  String defaultImageUrl;
  List<Tile> adjacent = new ArrayList<>();


  /**
   * Enum for all the different types of tiles.
   * Used for JSON.
   */
  public enum Type{
    Free, Treasure, Exit, ExitLock, InfoField, Key, LockedDoor, Wall, Water, Flippers
  }


  /**
   * Enum of directions to go from a tile.
   */
  public enum Direction {
    Left, Right, Up, Down;

    Direction reverse() {
      switch (this) {
        case Left: return Right;
        case Right: return Left;
        case Up: return Down;
        default: return Up;
      }
    }

    Direction clockWise() {
      switch (this) {
        case Left: return Up;
        case Up: return Right;
        case Right: return Down;
        default: return Left;
      }
    }

    Direction antiClockWise() {
      switch (this) {
        case Left: return Down;
        case Down: return Right;
        case Right: return Up;
        default: return Left;
      }
    }
  }


  /**
   * Sets boolean representing whether the tile is occupied.
   * A cell is occupied if it has a mob on it.
   * @param occupied tile is occupied by mob.
   */
  void setOccupied(boolean occupied) {
    this.isOccupied = occupied;
    isAccessible = !occupied;
  }

  /**
   * Returns a boolean representing whether the tile is occupied.
   * A cell is occupied if it has a mob on it.
   * @return boolean tile is occupied by mob.
   */
  public boolean isOccupied() { return isOccupied; }

  /**
   * Constructor taking a type of tile as a parameter.
   *
   * @param t the type of tile.
   */
  public Tile(Type t){
    type = t;
  }

  /**
   * Gets the row of the tile.
   * @return the row.
   */
  public int getRow() {
    return row;
  }

  /**
   * Sets the row of the tile.
   * @param row the row to set it to.
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Gets the column of the tile.
   * @return the column.
   */
  public int getCol() {
    return col;
  }

  /**
   * Sets the column of the tile.
   * @param col the column to set it to.
   */
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Gets the type of tile.
   * @return the type.
   */
  public Type getType(){return type;
  }

  /**
   * Gets the image URL.
   * @return the Image URL.
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Gets the default image URL.
   * @return the default image URL.
   */
  public String getDefaultImageUrl() {
    return defaultImageUrl;
  }

  /**
   * Gets the full URL name
   * @return the combination of the  image url and the default.
   */
  public String getCombinedUrl() {
    return AssetManager.combineFnames(defaultImageUrl, imageUrl);
  }

  /**
   * Checks if the current tile is accessible.
   * @return if it is accessible.
   */
  public boolean getIsAccessible() {
    return isAccessible;
  }

  /**
   * Gets the tile to the left.
   *
   * @return left of tile.
   */
  public Tile getLeft() {
    return adjacent.get(Direction.Left.ordinal());
  }

  /**
   * Gets the tile to the right.
   *
   * @return right of tile.
   */
  public Tile getRight() {
    return adjacent.get(Direction.Right.ordinal());
  }


  /**
   * Gets the tile above.
   *
   * @return up of tile.
   */
  public Tile getUp() {
    return adjacent.get(Direction.Up.ordinal());
  }

  /**
   * Gets the tile below.
   *
   * @return down of tile.
   */
  public Tile getDown() {
    return adjacent.get(Direction.Down.ordinal());
  }

  /**
   * Gets the tile in the specified direction.
   *
   * @param direction of tile.
   * @return tile in direction.
   */
  Tile getDir(Direction direction) {
    switch (direction) {
      case Right: return getRight();
      case Down: return getDown();
      case Up: return getUp();
      default: return getLeft();
    }
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  public abstract boolean interact(Player p);

  public void setTileOccupied(String fname) {
    imageUrl = fname;
  }

  /**
   * Return json representation of this tile.
   * @return Json string of tile properties.
   */
  public abstract String getJson();

  /**
   * Set tile properties from json.
   */
  public abstract Tile setTileFromJson(JsonReader json);

  /**
   * Sets teh tile URL to the default URL.
   */
  public void setTileUnoccupied() {
    imageUrl = defaultImageUrl;
  }

}
