package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.renderer.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Board class.
 * Describes board object and stores Tile array
 */
public class Board {

  private int boardSize = 20;
  private Tiles[][] tiles = new Tiles[boardSize][boardSize];
  private static String level1 =
      "_|KBlue|DBlue|_|_|_|_|_|_|_|?|T|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
          + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";


  /**
   * Constructor.
   */
  public Board() {
    parseBoard(level1);
    setupAdjacency();
  }

  /**
   * Parse board string into tile array.
   *
   * @param level String representation of board
   */
  private void parseBoard(String level) {
    String[] values = level.split("\\|");
    int index = 0;
    for (String v : values) {
      switch (v) {
        case "_":
          addTile(index/20,index%20,new Free());
          break;
        case "#":
          addTile(index/20,index%20,new Wall());
          break;
        case "T":
          addTile(index/20,index%20,new Treasure());
          break;
        case "?":
          addTile(index/20,index%20,new InfoField("Test"));
          break;
        case "Exit":
          addTile(index/20,index%20,new Exit());
          break;
        case "C":
          addTile(index/20,index%20,new Chap());
          break;
        default:
          // Must be a colored key or door
          String itemType = v.substring(0, 1);
          String colour = v.substring(1);

          // Create colored key or door
          if (itemType.equals("K")) {
            addTile(index/20,index%20,new Key(colour));
          } else {
            addTile(index/20,index%20,new LockedDoor(colour));
          }
      }
      index++;
    }

  }

  /**
   * Add tile to 2d array and store row and column.
   * @param row Row index
   * @param col Col index
   * @param t Tile to add
   */
  private void addTile(int row, int col, Tiles t){
    t.setRow(row);
    t.setCol(col);
    tiles[row][col] = t;
  }

  /**
   * Add neighboring tiles to board tiles.
   */
  private void setupAdjacency() {
    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        Tiles t = tiles[row][col];
        t.adjacent[Tiles.Direction.Left.ordinal()] = col != 0 ? tiles[row][col - 1] : null;
        t.adjacent[Tiles.Direction.Right.ordinal()] = col != boardSize - 1 ? tiles[row][col + 1] : null;
        t.adjacent[Tiles.Direction.Up.ordinal()] = row != 0 ? tiles[row - 1][col] : null;
        t.adjacent[Tiles.Direction.Down.ordinal()] = row != boardSize - 1 ? tiles[row + 1][col] : null;
      }
    }
  }

  /**
   * Gets stream of View_Size x View_Size cells focused on player.
   * @return Stream of all cells, left to right, top to bottom.
   */
  public Stream<Tiles> getStream(Tiles t) {
    List<Tiles> tilesList = new ArrayList<>();
    for (int r = t.getRow()-Canvas.VIEW_SIZE/2; r <= t.getRow()+Canvas.VIEW_SIZE/2; ++r) {
      for (int c = t.getCol()-Canvas.VIEW_SIZE/2; c <= t.getCol()+Canvas.VIEW_SIZE/2; ++c) {
        if(r < 0 || c < 0) tilesList.add(new Wall());
        else tilesList.add(tiles[r][c]);
      }
    }
    return tilesList.stream();
  }

  /**
   * Get player location from board description.
   * Searches board for instance of Chap
   * @return Tile player found on
   * @throws PlayerNotFoundException when no chap present
   */
  public Tiles getPlayerLocation() throws PlayerNotFoundException {
    for(int r = 0; r < boardSize; r++){
      for(int c = 0; c < boardSize; c++){
        if(tiles[r][c] instanceof Chap){
          return tiles[r][c];
        }
      }
    }
    throw new PlayerNotFoundException();
  }

  /**
   * Exception thrown when no chap is present in level description.
   */
  public class PlayerNotFoundException extends Exception{

  }
}
