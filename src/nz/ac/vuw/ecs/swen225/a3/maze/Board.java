package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.a3.renderer.Canvas;

/**
 * Board class. Holds a 2D array of all the current tiles. These tiles contain information about the
 * board, such as where Chap is.
 *
 * @author Luisa Kristen 300444458
 * @author Zac Durant 300449785
 */
public class Board {

  private int boardSize = 20;

  private List<Tile> allTiles;
  private Tile[][] tiles = new Tile[boardSize][boardSize];

  private int treasureCount = 0;

  private ChapsChallenge chapsChallenge;

  /**
   * Constructs and parses a new board.
   * @param chapsChallenge to make board from
   */
  public Board(ChapsChallenge chapsChallenge) {
    this.chapsChallenge = chapsChallenge;
  }
  
  /**
   * Sets up the board.
   */
  public void setup() {
    JsonReadWrite.loadGameState(LevelManager.getCurrentLevelStream(0), chapsChallenge);
    setupAdjacency();
  }

  /**
   * Add tile to 2d array and store row and column.
   *
   * @param row Row index
   * @param col Col index
   * @param t Tile to add
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
        t.adjacent.add(leftOrdinal, col != 0 ? tiles[row][col - 1] : new Wall());
        int rightOrdinal = Tile.Direction.Right.ordinal();
        t.adjacent.add(rightOrdinal, col != boardSize - 1 ? tiles[row][col + 1] : new Wall());
        int upOrdinal = Tile.Direction.Up.ordinal();
        t.adjacent.add(upOrdinal, row != 0 ? tiles[row - 1][col] : new Wall());
        int downOrdinal = Tile.Direction.Down.ordinal();
        t.adjacent.add(downOrdinal, row != boardSize - 1 ? tiles[row + 1][col] : new Wall());
      }
    }
  }

  /**
   * Gets stream of View_Size x View_Size cells focused on player.
   * @param t 
   *
   * @return Stream of all cells, left to right, top to bottom.
   */
  public Stream<Tile> getStream(Tile t) {
    List<Tile> tileList = new ArrayList<>();
    for (int r = t.getRow() - Canvas.VIEW_SIZE / 2; r <= t.getRow() + Canvas.VIEW_SIZE / 2; ++r) {
      for (int c = t.getCol() - Canvas.VIEW_SIZE / 2; c <= t.getCol() + Canvas.VIEW_SIZE / 2; ++c) {
        if (r < 0 || c < 0 || r >= boardSize || c >= boardSize) {
          tileList.add(new Wall());
        } else {
          tileList.add(tiles[r][c]);
        }
      }
    }
    return tileList.stream();
  }

  /**
   * Get player location from board description. Searches board for instance of Chap
   *
   * @return Tile player found on
   */
  public Tile getPlayerLocation() {
    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (tiles[r][c].getImageUrl().startsWith("chap")) {
          return tiles[r][c];
        }
      }
    }
    return null;
  }

  /**
   * Gets number of treasure remaining.
   *
   * @return number of treasure remaining.
   */
  public int getTreasureCount() {
    return treasureCount;
  }

  /**
   * Sets the treasure count to the number specified.
   *
   * @param treasureCount the treasure count.
   */
  public void setTreasureCount(int treasureCount) {
    this.treasureCount = treasureCount;
  }

  /**
   * Returns the tile corresponding to the row and col specified.
   *
   * @param row the row of the tile.
   * @param col the col of the tile.
   * @return the tile to return.
   */
  public Tile getTile(int row, int col) {
    if (row >= boardSize || col >= boardSize) {
      return null;
    }
    if (row < 0 || col < 0) {
      return null;
    }
    return tiles[row][col];
  }

  /**
   * Get allTiles list.
   *
   * @return list of allTiles in board or null if not filled
   */
  public List<Tile> getAllTiles() {
    return allTiles;
  }
  
  /**
   * Sets the board level to the desired level. Retains level if given level is invalid.
   *
   * @param level to set.
   */
  public void setCurrentLevel(int level) {
    if (level >= 0 && level < LevelManager.getNumLevels()) {
      JsonReadWrite.loadGameState(LevelManager.getCurrentLevelStream(level), chapsChallenge);
      LevelManager.currentLevel = level;
    }
  }

  /**
   * Gets the current level the board is on.
   *
   * @return level of board.
   */
  public int getCurrentLevel() {
    return LevelManager.getCurrentLevelInt();
  }

  /**
   * Gets the final level number.
   *
   * @return last level of board.
   */
  public int getFinalLevel() {
    return LevelManager.getNumLevels() - 1;
  }

  /**
   * Return boardSize.
   *
   * @return integer board size.
   */
  public int getBoardSize() {
    return boardSize;
  }

  /**
   * Gets the tiles.
   *
   * @return the tiles.
   */
  public Tile[][] getTiles() {
    return tiles;
  }

  /**
   * Sets the tiles.
   *
   * @param tiles 2D array of tiles.
   */
  public void setTiles(Tile[][] tiles) {
    this.tiles = tiles;
  }

  /**
   * Sets the size of the board.
   *
   * @param boardSize the new size of the board.
   */
  public void setBoardSize(int boardSize) {
    this.boardSize = boardSize;
  }

  /**
   * Sets the list of tiles to a new list.
   *
   * @param allTiles the new tiles.
   */
  public void setAllTiles(List<Tile> allTiles) {
    this.allTiles = allTiles;
  }

  /**
   * Gets the current game.
   *
   * @return the current game.
   */
  ChapsChallenge getChapsChallenge() {
    return chapsChallenge;
  }

  /**
   * Exception thrown when no chap is present in level description.
   */
  public static class PlayerNotFoundException extends Exception {

    /**
     * Default serial number.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
      return "No Chap in string description of level";
    }
  }

  /**
   * Exception thrown when multiple chaps present in level description.
   */
  public static class MultiplePlayersFoundException extends Exception {

    /**
     * Default serial number.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
      return "Multiple Chaps in string description of level";
    }
  }

  /**
   * Gets string representation of the board.
   *
   * @return string representation of the board.
   */
  @Override
  public String toString() {
    StringBuilder toReturn = new StringBuilder();
    for (Tile[] tile : tiles) {
      IntStream.range(0, tile.length).forEach(j -> {
        toReturn.append(tile[j]);
        toReturn.append(" , ");
      });
    }
    return toReturn.substring(0, toReturn.length() - 2);
  }

}
