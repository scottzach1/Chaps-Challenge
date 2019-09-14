package nz.ac.vuw.ecs.swen225.a3.maze;

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


  public Board() {
    parseBoard(level1);
    setupAdjacency();
  }

  /**
   * Parse board string into tile array.
   * @param level String representation of board
   */
  private void parseBoard(String level) {
    String[] values = level.split("\\|");
    int index = 0;
    for (String v : values) {
      switch (v) {
        case "_":
          tiles[index/boardSize][index%boardSize]= new Free();
          break;
        case "#":
          tiles[index/boardSize][index%boardSize] = new Wall();
          break;
        case "T":
          tiles[index/boardSize][index%boardSize] = new Treasure();
          break;
        case "?":
          tiles[index/boardSize][index%boardSize] = new InfoField("Test");
          break;
        case "Exit":
          tiles[index/boardSize][index%boardSize] = new Exit();
          break;
        case "C":
          tiles[index/boardSize][index%boardSize] = new Chap();
          break;
        default:
          // Must be a coloured key or door
          String itemType = v.substring(0,1);
          String colour = v.substring(1);

          // Create coloured key or door
          if(itemType.equals("K")) tiles[index/boardSize][index%boardSize] = new Key(colour);
          else
            tiles[index/boardSize][index%boardSize] = new LockedDoor(colour);
      }
      index++;
    }

  }

  /**
   * Add neighboring tiles to board tiles
   */
  private void setupAdjacency() {
    for (int row = 0; row < boardSize; row++){
      for (int col = 0; col < boardSize; col++){
        Tiles t = tiles[row][col];
        t.adjacent[Tiles.Direction.Left.ordinal()] = col != 0 ? tiles[row][col-1] : null;
        t.adjacent[Tiles.Direction.Right.ordinal()] = col != boardSize-1 ? tiles[row][col+1] : null;
        t.adjacent[Tiles.Direction.Up.ordinal()] = row != 0 ? tiles[row-1][col] : null;
        t.adjacent[Tiles.Direction.Down.ordinal()] = row != boardSize-1 ? tiles[row +1][col] : null;
      }
    }
  }

}
