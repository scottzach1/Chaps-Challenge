package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;
import nz.ac.vuw.ecs.swen225.a3.renderer.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * Board class.
 * Describes board object and stores Tile array
 */
public class Board {

  private int boardSize = 20;
  private List<Tiles> allTiles;
  private Tiles[][] tiles = new Tiles[boardSize][boardSize];

  public List <String> allLevels;
  private int currentLevel;
  private int treasureCount=0;
  /**
   * Constructor.
   */
  public Board() {
    addLevels();
    try {
      parseBoard(allLevels.get(currentLevel));
    } catch (ParsingException p) {
      System.out.println(p.getMessage());
      throw new Error(p.getMessage());
    } catch (MultiplePlayersFoundException m) {
      System.out.println(m.getMessage());
      throw new Error(m.getMessage());
    }catch(PlayerNotFoundException pnf){
      System.out.println(pnf.getMessage());
      throw new Error(pnf.getMessage());
    }
    setupAdjacency();
  }

  public void setLevel(String level){
    allLevels.add(level);
    this.currentLevel=allLevels.size()-1;
    try {
      parseBoard(allLevels.get(currentLevel));
    } catch (ParsingException p) {
      System.out.println(p.getMessage());
      throw new Error(p.getMessage());
    } catch (MultiplePlayersFoundException m) {
      System.out.println(m.getMessage());
      throw new Error(m.getMessage());
    } catch(PlayerNotFoundException pnf){
      System.out.println(pnf.getMessage());
      throw new Error(pnf.getMessage());
    }
    setupAdjacency();
  }

  /**
   * Parse board string into tile array.
   *
   * @param level String representation of board
   */
  private void parseBoard(String level) throws MultiplePlayersFoundException, ParsingException, PlayerNotFoundException {
    boolean foundChap = false;
    String[] values = level.split("\\|");
    int index = 0;
    for (String v : values) {
      switch (v) {
        case "_":
          addTile(index / 20, index % 20, new Free());
          break;
        case "#":
          addTile(index / 20, index % 20, new Wall());
          break;
        case "T":
          addTile(index / 20, index % 20, new Treasure());
          break;
        case "?":
          addTile(index / 20, index % 20, new InfoField("Test"));
          break;
        case "Exit":
          addTile(index / 20, index % 20, new Exit());
          break;
        case "ExitLock":
          addTile(index / 20, index % 20, new ExitLock());
          break;
        case "C":
          if (foundChap) throw new MultiplePlayersFoundException();
          foundChap = true;
          Free tile = new Free();

          // FIXME: This might not be the best place.
          tile.imageUrl = "chap_front.png";
          AssetManager.loadAsset(tile.imageUrl);

          addTile(index / 20, index % 20, tile);
          break;
        default:
          // Must be a colored key or door
          String itemType = v.substring(0, 1);

          // Check for invalid token
          if (!(itemType.equals("K") || itemType.equals("D"))) throw new ParsingException();

          String colour = v.substring(1).toLowerCase();

          // Create colored key or door
          if (itemType.equals("K")) {
            addTile(index / 20, index % 20, new Key(colour));
          } else {
            addTile(index / 20, index % 20, new LockedDoor(colour));
          }
      }
      index++;
    }
    if (!foundChap){
      throw new PlayerNotFoundException();
    }

    allTiles = new ArrayList<>();
    for (int row = 0; row < boardSize; row++) {
      allTiles.addAll(Arrays.asList(tiles[row]).subList(0, boardSize));
    }

    // Count number of treasures
    treasureCount = (int) allTiles.stream().filter(p -> p.toString().equals("Treasure")).count();

    // Set all exit locks to require correct number of treasures
    allTiles.stream().filter(p -> p.getType() == Tiles.Type.ExitLock).map(c -> (ExitLock)c).forEach(s -> s.setTotalTreasures(treasureCount));
  }

  /**
   * Add tile to 2d array and store row and column.
   *
   * @param row Row index
   * @param col Col index
   * @param t   Tile to add
   */
  private void addTile(int row, int col, Tiles t) {
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

        //Separate ordinal in different line to stop line limit > 100 characters.
        int leftOrdinal = Tiles.Direction.Left.ordinal();
        t.adjacent.add(leftOrdinal,col != 0 ? tiles[row][col - 1] : new Wall());
        int rightOrdinal = Tiles.Direction.Right.ordinal();
        t.adjacent.add(rightOrdinal,col != boardSize-1 ? tiles[row][col + 1] : new Wall());
        int upOrdinal = Tiles.Direction.Up.ordinal();
        t.adjacent.add(upOrdinal,row != 0 ? tiles[row-1][col ] : new Wall());
        int downOrdinal = Tiles.Direction.Down.ordinal();
        t.adjacent.add(downOrdinal,row != boardSize-1 ? tiles[row+1][col] : new Wall());
      }
    }
  }

  /**
   * Gets stream of View_Size x View_Size cells focused on player.
   *
   * @return Stream of all cells, left to right, top to bottom.
   */
  public Stream<Tiles> getStream(Tiles t) {
    List<Tiles> tilesList = new ArrayList<>();
    for (int r = t.getRow() - Canvas.VIEW_SIZE / 2; r <= t.getRow() + Canvas.VIEW_SIZE / 2; ++r) {
      for (int c = t.getCol() - Canvas.VIEW_SIZE / 2; c <= t.getCol() + Canvas.VIEW_SIZE / 2; ++c) {
        if (r < 0 || c < 0 || r >= boardSize || c >= boardSize) tilesList.add(new Wall());
        else tilesList.add(tiles[r][c]);
      }
    }
    return tilesList.stream();
  }

  /**
   * Get player location from board description.
   * Searches board for instance of Chap
   *
   * @return Tile player found on
   */
  public Tiles getPlayerLocation() {
    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (tiles[r][c].getImageUrl().equals("chap_front.png")) {
          return tiles[r][c];
        }
      }
    }
   return null;
  }

  public int getTreasureCount() {
    return treasureCount;
  }

  /**
   * Get allTiles list.
   * @return list of allTiles in board or null if not filled
   */
  public List<Tiles> getAllTiles(){
    return allTiles;
  }

  private void addLevels(){
    allLevels = new ArrayList<>();
    allLevels.add(
              "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|ExitLock|C|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|Exit|_|_|_|_|"
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
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");

    allLevels.add(
        "ExitLock|KBlue|DBlue|_|_|_|_|_|_|_|?|T|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|?|C|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|?|?|?|_|_|_|_|_|_|"
            + "_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|_|_|_|_|_|"
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
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");

    currentLevel=0;
  }

  public void setNextLevel(){
    if (currentLevel<allLevels.size()-1){
      currentLevel++;
      setLevel(allLevels.get(currentLevel));
      return;
    }
    //todo end of game screen

  }

  /**
   * Return boardSize.
   * @return integer board size.
   */
  public int getBoardSize(){
    return boardSize;
  }

  /**
   * Exception thrown when no chap is present in level description.
   */
  public class PlayerNotFoundException extends Exception {
    @Override
    public String getMessage() {
      return "No Chap in string description of level";
    }
  }

  /**
   * Exception thrown when multiple chaps present in level description.
   */
  public class MultiplePlayersFoundException extends Exception {
    @Override
    public String getMessage() {
      return "Multiple Chaps in string description of level";
    }
  }

  /**
   * Exception thrown when invalid token found in level description.
   */
  public class ParsingException extends Exception {
    @Override
    public String getMessage() {
      return "Invalid token in string description of level";
    }
  }

  @Override
  public String toString() {
    return allLevels.get(currentLevel);
  }
}
