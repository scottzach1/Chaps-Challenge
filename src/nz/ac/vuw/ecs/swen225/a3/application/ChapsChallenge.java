package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class ChapsChallenge {

  private Board board;
  private GUI gui;
  private Player player;

  private long totalTime = 100; //100 seconds, todo change with levels
  private long startTime;

  private long timeLeft = totalTime;

  private boolean gamePaused = false;

  private Thread thread;

  /**
   * Create main game application.
   */
  public ChapsChallenge() {
    // Load the board.
    board = new Board();
    player = new Player(board.getPlayerLocation());
    startTime = System.currentTimeMillis();

    // Creates a GUI and gives it a keyListener
    gui = new GUI(this);

    // Start the running loop
    runningThread();
  }

  /**
   * Moves the player in the direction specified.
   * Checks if the tile is able to be moved onto by interacting with it.
   * If valid, it sets the location of the player to the new tile.
   *
   * @param direction the direction to move in.
   */
  public void move(Tiles.Direction direction) {
    if (gamePaused) return;

    Tiles currentLocation = player.getLocation();
    Tiles nextLocation = null;
    switch (direction) {
      case Up:
        nextLocation = currentLocation.getUp();
        break;
      case Down:
        nextLocation = currentLocation.getDown();
        break;
      case Left:
        nextLocation = currentLocation.getLeft();
        break;
      case Right:
        nextLocation = currentLocation.getRight();
        break;
    }
    if (nextLocation == null || !nextLocation.interact(player)) {
      return; //invalid move
    }
    currentLocation.setTileUnoccupied();
    nextLocation.setTileOccupied();
    player.setLocation(nextLocation);

    checkFields();

  }

  private void checkFields(){
    if (player.getLocation().getType() == Tiles.Type.Exit){
      board.setNextLevel();
      player = new Player(board.getPlayerLocation());
      timeLeft=totalTime;
    }

    if (player.getLocation().getType() == Tiles.Type.InfoField){
      //gui.displayInfoTile(player.getLocation());
    }

  }

  /**
   * Checks the amount of time that has elapsed since the start of the game.
   * Subtracts this from the total time available.
   *
   * @return the time left to play
   */
  public int timeLeft() {
    if (gamePaused) {
      return (int) timeLeft;
    }
    long elapsedTime = System.currentTimeMillis() - startTime;
    timeLeft -= TimeUnit.MILLISECONDS.toSeconds(elapsedTime);

    startTime = System.currentTimeMillis();

    return (int) timeLeft;
  }

  /**
   * Pauses the game.
   */
  public void pauseGame() {
    gamePaused = true;
    gui.pauseGame();
  }

  /**
   * Resumes the game.
   */
  public void resumeGame() {
    gamePaused = false;
    runningThread();
    startTime = System.currentTimeMillis();
    gui.resumeGame();
  }

  /**
   * Loads the game.
   */
  public void loadGame() {
    // TODO: Load Game
    gui.loadGame();
    System.out.println("Game loaded.");
  }

  /**
   * Saves the game.
   */
  public void saveGame() {
    // TODO: Save Game
    gui.saveGame();
    System.out.println("Game saved.");
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    // TODO: Restart Game
    gui.restartGame();
    System.out.println("Game restarted.");
  }

  /**
   * Sets the game to the previous level.
   */
  public void previousLevel() {
    // TODO: Previous Level
    gui.previousLevel();
    System.out.println("Game set to previous level.");
  }

  /**
   * Exits the game.
   */
  public void exitGame() {
    if (gui.exitGame())
      System.exit(0);
  }

  public void timeOut() {
    // TODO: Implement a time out in GUI and call here
  }

  /**
   * Running thread opens a new thread (double threaded) and
   * runs a timer, updating the dashboard every second
   */
  public void runningThread() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        while (true) {
          if (!gamePaused) {
            gui.updateDashboard();
            try {
              if (timeLeft > 0)
                Thread.sleep(1000);
              else
                throw new InterruptedException("TIMED OUT");
            } catch (InterruptedException e) {
              timeOut();
              return;
            }
          }
        }
      }
    };
    thread = new Thread(runnable);
    thread.start();
  }

  /**
   * Gets the gamePaused boolean.
   *
   * @return - True if game is paused
   */
  public boolean isGamePaused() {
    return gamePaused;
  }

  /**
   * Returns a list of strings containing the players inventory.
   *
   * @return the list of items.
   */
  public List<String> getPlayerInventory() {
    return player.getInventory();
  }

  /**
   * Return number of treasures player has retrieved.
   *
   * @return Number of treasures
   */
  public int getTreasures() {
    return player != null ? player.getTreasures() : 0;
  }

  /**
   * Return total number of treasures in the level
   *
   * @return Total number of treasures
   */
  public int getTotalTreasures() {
    return  board.getTreasureCount();
  }

  /**
   * Get tiles around player to render on screen.
   *
   * @return Stream of tiles to be drawn
   */
  public Stream<Tiles> getTilesToRender() {
    return board.getStream(player.getLocation());
  }

  /**
   * Sets the board to the string, changes the level.
   * @param level the level to change to.
   */
  public void setLevel(String level){
    board.setLevel(level);
    player = new Player(board.getPlayerLocation());
  }

  /**
   * Get board object.
   *
   * @return Board object
   */
  public Board getBoard(){
    return board;
  }

  /**
   * Get Player object.
   * @return player object
   */
  public Player getPlayer(){
    return player;
  }


  /**
   * Get time remaining.
   * @return long time in seconds
   */
  public long getTimeLeft() {
    return timeLeft;
  }

  /**
   * ChapsChallenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    ChapsChallenge game = new ChapsChallenge();
  }
}
