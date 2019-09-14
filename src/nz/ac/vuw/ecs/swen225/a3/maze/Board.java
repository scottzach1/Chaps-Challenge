package nz.ac.vuw.ecs.swen225.a3.maze;

/** Board class.
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
  }

  /** Parse board string into tile array.
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
          tiles[index/boardSize][index%boardSize] = new InfoField();
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

}
