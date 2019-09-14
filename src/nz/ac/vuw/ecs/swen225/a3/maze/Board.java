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
      "_|KBlue|DBlue|_|_|_|_|_|_|_|?|T|C|_|_|_|_|_|_|_|"
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
          tiles[index / boardSize][index % boardSize] = new Free();
          break;
        case "#":
          tiles[index / boardSize][index % boardSize] = new Wall();
          break;
        case "T":
          tiles[index / boardSize][index % boardSize] = new Treasure();
          break;
        case "?":
          tiles[index / boardSize][index % boardSize] = new InfoField("Test");
          break;
        case "Exit":
          tiles[index / boardSize][index % boardSize] = new Exit();
          break;
        case "C":
          tiles[index / boardSize][index % boardSize] = new Chap();
          break;
        default:
          // Must be a colored key or door
          String itemType = v.substring(0, 1);
          String colour = v.substring(1);

          // Create colored key or door
          if (itemType.equals("K")) {
            tiles[index / boardSize][index % boardSize] = new Key(colour);
          } else {
            tiles[index / boardSize][index % boardSize] = new LockedDoor(colour);
          }
      }
      index++;
    }

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
   * Gets stream of all Cells, currently top left 9x9 cells.
   * From left to right, top to bottom.
   * @return Stream of all cells, left to right, top to bottom.
   */
  public Stream<Tiles> getStream() {
    List<Tiles> tilesList = new ArrayList<>();
    for (int r = 0; r != Canvas.VIEW_SIZE; ++r) {
      for (int c = 0; c != Canvas.VIEW_SIZE; ++c) {
        tilesList.add(tiles[r][c]);
      }
    }
    return tilesList.stream();
  }

}
