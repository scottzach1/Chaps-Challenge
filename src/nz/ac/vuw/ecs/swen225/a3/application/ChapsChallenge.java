package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.awt.event.ComponentEvent;
import java.io.File;
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

  private long totalTime = 100; //100 seconds
  private long startTime;
  private long timeLeft = totalTime;
  private boolean gamePaused = false;

  private final int fps = 20;
  private Thread thread;

  private MobManager mobManager;

  private File saveFile, loadFile;

  /**
   * Create main game application.
   */
  public ChapsChallenge() {
    // Load the board.
    board = new Board();

    mobManager = new MobManager(board);

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
  public void move(Tile.Direction direction) {
    if (gamePaused) return;

    Tile currentLocation = player.getLocation();
    Tile nextLocation = null;
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
    if (nextLocation.isOccupied()) { // stepped on a mob
      restartLevel();
      return;
    }
    currentLocation.setTileUnoccupied();
    nextLocation.setTileOccupied("chap_front.png");
    player.setLocation(nextLocation);

    checkFields();

    if (RecordAndPlay.getIsRecording()) {
      RecordAndPlay.addAction(direction);
    }

  }

  private void checkFields() {
    if (player.getLocation().getType() == Tile.Type.Exit) {
      if (!board.setNextLevel())
        gameEnd();
      player = new Player(board.getPlayerLocation());
      timeLeft = totalTime;
    }

    if (player.getLocation().getType() == Tile.Type.InfoField){
      InfoField info = (InfoField) player.getLocation();
      gui.renderInfoField(info.getInfo());
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
  }

  /**
   * Loads the game.
   */
  public void loadGame() {
    gamePaused = false;
    gui.loadGame();
    try {
      //TODO: use the field "loadFile" - a File object
      JsonReadWrite.loadGameState("saveGame.txt", this);
    } catch (Exception e) {
      gui.noFileFound();
    }
    System.out.println("Game loaded.");
    resumeGame();
  }

  /**
   * Saves the game.
   */
  public void saveGame() {
    gamePaused = true;
    if (gui.saveGame())
      JsonReadWrite.saveGameState(this, "saveGame.txt");
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    gamePaused = true;
    if (gui.restartGame()) {
      board.setCurrentLevel(0);
      resetLogistics();

    }
    gamePaused = false;
  }

  /**
   * Sets the game to the previous level.
   * If there is no previous level, level 1 is restarted.
   */
  public void previousLevel() {
    int current = board.getCurrentLevel();
    if (current > 0) {
      board.setCurrentLevel(current - 1);
    } else {
      board.setCurrentLevel(0);
    }
    resetLogistics();
    gui.previousLevel();

  }

  public void restartLevel() {
    int current = board.getCurrentLevel();
    board.setCurrentLevel(current);
    resetLogistics();
  }

  /**
   * Exits the game.
   */
  public void exitGame() {
    gamePaused = true;
    if (gui.exitGame())
      System.exit(0);
    resumeGame();
  }

  private void timeOut() {
    //gui.timeOut();
    gameOver();
  }

  /**
   * Running thread opens a new thread (double threaded) and
   * runs a timer, updating the dashboard every second
   */
  private void runningThread() {
    Runnable runnable = new Runnable() {

      private int i = 0, chapsOldBagCounter = 0;

      @Override
      public void run() {
        // Waits the thread long enough for everything to load
        try {
          Thread.sleep(400);
        } catch (Exception e) {
        }

        gui.componentResized(new ComponentEvent(gui, 1));

        // While the time has not run out
        while (true) {

          // Only run while the game is not paused
          if (!gamePaused) {
            // Attempt to sleep the thread if there is time left
            try {
              if (timeLeft > 0) {
                // Every second
                if (i == 0) {
                  // Update the dashboard and mobs
                  gui.updateDashboard();
                  mobManager.advanceByOneTick();
                }
                // Update the board every 1/fps second
                gui.updateBoard();

                // Restricts the frame rate to 30 fps
                Thread.sleep(1000 / fps);

                // Tick counter cycles (0, 1)
                i = (i + 1) % fps;
              } else
                throw new InterruptedException("TIMED OUT");
            }
            // If anything was to go unsuccessfully, then control crash the game with a time out
            catch (InterruptedException e) {
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
    return board.getTreasureCount();
  }

  /**
   * Get tiles around player to render on screen.
   *
   * @return Stream of tiles to be drawn
   */
  public Stream<Tile> getTilesToRender() {
    return board.getStream(player.getLocation());
  }

  /**
   * Sets the board to the string, changes the level.
   *
   * @param level the level to change to.
   */
  public void setCustomLevel(String level) {
    board.setLevel(level);
    player = new Player(board.getPlayerLocation());
    resetLogistics();
  }

  /**
   * Gets the current level of this game
   *
   * @return Level the level currently held by board
   */
  public int getLevel() {
    return board.getCurrentLevel();
  }

  /**
   * Get board object.
   *
   * @return Board object
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Get Player object.
   *
   * @return player object
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Loads the game over screen.
   */
  private void gameOver() {
    //gui.gameOver(); //todo Front end implement
  }

  /**
   * Loads the game end screen.
   * For winning.
   */
  private void gameEnd() {
    //gui.endGame(); //todo front end implement
  }

  /**
   * Get time remaining.
   *
   * @return long time in seconds
   */
  public long getTimeLeft() {
    return timeLeft;
  }

  public File getSaveFile() {
    return saveFile;
  }

  public void setSaveFile(File saveFile) {
    this.saveFile = saveFile;
  }

  public File getLoadFile() {
    return loadFile;
  }

  public void setLoadFile(File loadFile) {
    this.loadFile = loadFile;
  }

  /**
   * Update gui.
   */
  public void update() {
    gui.updateBoard();
  }

  /**
   * Resets the logistics of the game.
   * Resets timer.
   * Makes a new Mob manager.
   * Makes a new Player.
   */
  private void resetLogistics() {
    timeLeft = totalTime;
    startTime = System.currentTimeMillis();
    mobManager = new MobManager(board);
    player = new Player(board.getPlayerLocation());
  }

  /**
   * Sets the current board to a new board.
   *
   * @param board the new board.
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  /**
   * Sets the time left.
   * @param timeLeft the time left.
   */
  public void setTimeLeft(long timeLeft) {
    this.timeLeft = timeLeft;
  }

  /**
   * Gets the mob manager.
   * @return the mob manager.
   */
  public MobManager getMobManager() {
    return mobManager;
  }

  /**
   * Sets the player to a new player.
   * @param player the player to set.
   */
  public void setPlayer(Player player) {
    this.player = player;
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
