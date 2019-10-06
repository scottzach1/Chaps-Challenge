package nz.ac.vuw.ecs.swen225.a3.maze;

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

  public void setBoardSize(int boardSize) {
    this.boardSize = boardSize;
  }

  public void setAllTiles(List<Tile> allTiles) {
    this.allTiles = allTiles;
  }

  private List<Tile> allTiles;
  private Tile[][] tiles = new Tile[boardSize][boardSize];

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
    try {
      if (!allLevels.contains(level)){
        allLevels.add(0,level);
        currentLevel=0;
      }
      parseBoard(level);
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
          setTile(index / 20, index % 20, new Free());
          break;
        case "#":
          setTile(index / 20, index % 20, new Wall());
          break;
        case "T":
          setTile(index / 20, index % 20, new Treasure());
          break;
        case "?":
          setTile(index / 20, index % 20, new InfoField("Test"));
          break;
        case "Exit":
          setTile(index / 20, index % 20, new Exit());
          break;
        case "ExitLock":
          setTile(index / 20, index % 20, new ExitLock());
          break;
        case "C":
          if (foundChap) throw new MultiplePlayersFoundException();
          foundChap = true;
          Free tile = new Free();

          // FIXME: This might not be the best place.
          tile.imageUrl = "chap_front.png";

          setTile(index / 20, index % 20, tile);
          break;
        default:
          // Must be a colored key or door
          String itemType = v.substring(0, 1);

          // Check for invalid token
          if (!(itemType.equals("K") || itemType.equals("D"))) throw new ParsingException();

          String colour = v.substring(1).toLowerCase();

          // Create colored key or door
          if (itemType.equals("K")) {
            setTile(index / 20, index % 20, new Key(colour));
          } else {
            setTile(index / 20, index % 20, new LockedDoor(colour));
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
    allTiles.stream().filter(p -> p.getType() == Tile.Type.ExitLock).map(c -> (ExitLock)c).forEach(s -> s.setTotalTreasures(treasureCount));
  }

  /**
   * Add tile to 2d array and store row and column.
   *
   * @param row Row index
   * @param col Col index
   * @param t   Tile to add
   */
  public void setTile(int row, int col, Tile t) {
    t.setRow(row);
    t.setCol(col);
    tiles[row][col] = t;
  }

  /**
   * Add neighboring tiles to board tiles.
   */
  public void setupAdjacency() {
    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        Tile t = tiles[row][col];

        //Separate ordinal in different line to stop line limit > 100 characters.
        int leftOrdinal = Tile.Direction.Left.ordinal();
        t.adjacent.add(leftOrdinal,col != 0 ? tiles[row][col - 1] : new Wall());
        int rightOrdinal = Tile.Direction.Right.ordinal();
        t.adjacent.add(rightOrdinal,col != boardSize-1 ? tiles[row][col + 1] : new Wall());
        int upOrdinal = Tile.Direction.Up.ordinal();
        t.adjacent.add(upOrdinal,row != 0 ? tiles[row-1][col ] : new Wall());
        int downOrdinal = Tile.Direction.Down.ordinal();
        t.adjacent.add(downOrdinal,row != boardSize-1 ? tiles[row+1][col] : new Wall());
      }
    }
  }

  /**
   * Gets stream of View_Size x View_Size cells focused on player.
   *
   * @return Stream of all cells, left to right, top to bottom.
   */
  public Stream<Tile> getStream(Tile t) {
    List<Tile> tileList = new ArrayList<>();
    for (int r = t.getRow() - Canvas.VIEW_SIZE / 2; r <= t.getRow() + Canvas.VIEW_SIZE / 2; ++r) {
      for (int c = t.getCol() - Canvas.VIEW_SIZE / 2; c <= t.getCol() + Canvas.VIEW_SIZE / 2; ++c) {
        if (r < 0 || c < 0 || r >= boardSize || c >= boardSize) tileList.add(new Wall());
        else tileList.add(tiles[r][c]);
      }
    }
    return tileList.stream();
  }

  /**
   * Get player location from board description.
   * Searches board for instance of Chap
   *
   * @return Tile player found on
   */
  public Tile getPlayerLocation() {
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

  public Tile getTile(int row, int col) {
    if (row >= boardSize || col >= boardSize) return null;
    if (row < 0 || col < 0) return null;
    return tiles[row][col];
  }

  /**
   * Get allTiles list.
   * @return list of allTiles in board or null if not filled
   */
  public List<Tile> getAllTiles(){
    return allTiles;
  }

  private void addLevels(){
    allLevels = new ArrayList<>();
    allLevels.add(
              "_|_|_|T|T|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|_|_|_|_|KYellow|KBlue|KRed|KGreen|_|_|C|_|_|_|_|_|_|_|"
            + "T|DBlue|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "T|DRed|_|_|_|_|_|_|_|_|_|#|ExitLock|#|_|_|_|_|_|_|"
            + "#|#|_|_|_|_|_|_|_|_|_|#|Exit|#|_|_|_|_|_|_|"
            + "T|DGreen|_|_|_|_|_|_|_|_|_|#|#|#|_|_|_|_|_|_|"
            + "#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "T|DYellow|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
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
              "_|_|_|_|_|_|_|_|_|#|KRed|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|?|_|#|T|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|#|_|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|#|#|#|#|_|_|#|_|#|_|#|#|#|#|_|_|_|"
            + "_|_|_|#|T|T|#|_|_|#|_|#|_|#|T|KYellow|#|_|_|_|"
            + "_|_|_|#|T|T|#|_|_|#|_|#|_|#|T|T|#|_|_|_|"
            + "_|_|_|#|#|DYellow|#|_|_|_|_|_|_|#|DGreen|#|#|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|#|#|DBlue|#|_|_|_|_|_|_|#|DRed|#|#|_|_|_|"
            + "_|_|_|#|T|T|#|_|_|#|_|#|_|#|T|T|#|_|_|_|"
            + "_|_|_|#|KGreen|T|#|_|_|#|_|#|_|#|T|KBlue|#|_|_|_|"
            + "_|_|_|#|#|#|#|_|_|#|_|#|_|#|#|#|#|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|#|C|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|#|ExitLock|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|#|Exit|#|_|_|_|_|_|_|_|_|");
            
    allLevels.add(
              "Exit|ExitLock|_|_|_|_|_|_|_|C|_|KRed|_|_|_|_|_|DBlue|T|KGreen|"
            + "#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|T|T|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|T|_|_|_|#|#|#|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|"
            + "KYellow|T|T|DGreen|_|_|_|_|_|_|_|_|_|_|_|_|_|T|T|T|"
            + "#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|"
            + "_|_|_|_|_|_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|"
            + "#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|"
            + "T|T|T|_|_|_|_|_|_|_|_|_|_|_|_|_|DYellow|T|T|T|"
            + "#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "T|T|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "KBlue|T|DRed|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
            
    allLevels.add(
              "_|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|#|T|KYellow|#|#|#|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|DGreen|T|KBlue|#|Exit|ExitLock|DBlue|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|#|C|_|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|#|_|_|T|T|_|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|_|#|_|_|_|_|_|_|_|_|#|_|_|_|_|_|"
            + "_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|#|DYellow|#|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|"
            + "_|_|_|_|_|#|_|_|_|_|_|_|_|_|#|_|_|_|_|_|"
            + "_|_|_|_|_|_|#|_|_|_|_|KRed|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|#|_|_|#|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|"
            + "#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|T|T|T|"
            + "T|T|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|T|T|T|"
            + "KGreen|T|DRed|_|_|_|_|_|_|_|_|_|_|_|_|_|_|T|T|T|");
    
    allLevels.add(
              "KBlue|DGreen|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|KGreen|"
            + "#|#|_|_|_|_|T|_|_|_|_|_|_|T|_|_|_|_|#|DRed|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|#|#|#|#|#|#|#|#|#|#|#|#|#|#|_|_|_|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|DYellow|ExitLock|Exit|#|_|_|_|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|#|#|#|#|_|_|_|"
            + "_|T|_|#|_|_|T|_|_|_|_|_|_|_|_|_|#|_|T|_|"
            + "_|_|_|#|_|_|T|T|_|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|_|T|T|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|_|_|T|T|KYellow|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|_|_|T|T|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|_|T|T|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|T|T|_|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|T|_|#|_|_|T|_|_|_|_|_|_|_|_|_|#|_|T|_|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|"
            + "_|_|_|#|DBlue|#|#|#|#|#|#|#|#|#|#|#|#|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|T|_|_|_|_|_|_|T|_|_|_|_|_|_|"
            + "C|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|KRed|");

    currentLevel=0;
  }

  public boolean setNextLevel(){
    if (currentLevel<allLevels.size()-1){
      currentLevel++;
      setLevel(allLevels.get(currentLevel));
      return true;
    }
    return false;
  }

  public void setCurrentLevel(int level){
    if (level<allLevels.size()){
      currentLevel= level;
      setLevel(allLevels.get(currentLevel));
    }
  }

  public int getCurrentLevel() {
    return currentLevel;
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
