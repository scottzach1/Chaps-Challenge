package nz.ac.vuw.ecs.swen225.a3.application;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.InfoField;
import nz.ac.vuw.ecs.swen225.a3.maze.MobManager;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.a3.renderer.GameMenu.MenuType;
import nz.ac.vuw.ecs.swen225.a3.renderer.Gui;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 * ChapsChallenge maintains the functionality of the game.
 * It also provides the link between the Maze, Renderer and Persistence packages.
 *
 * @author Luisa Kristen 300444458.
 */
public class ChapsChallenge {

  private Board board;
  private Gui gui;
  private Player player;

  private long totalTime = 100; //100 seconds
  private long startTime;
  private long timeLeft = totalTime;
  private boolean gamePaused = false;

  private final int fps = 20;

  private MobManager mobManager;

  private File saveFile;
  private File loadFile;

  private Thread thread;

  private static boolean threadMade;

  /**
   * Create main game application.
   */
  public ChapsChallenge() {
    LevelManager.loadLevels();
    // Load the board.
    board = new Board(this);
    board.setup();
    player = new Player(board.getPlayerLocation());
    mobManager = new MobManager(board);

    startTime = System.currentTimeMillis();

    // Creates a GUI and gives it a keyListener
    gui = new Gui(this);
    gui.addLayoutComponents();

  }

  /**
   * Gets the GUI of the game.
   * @return GUI of the game.
   */
  public Gui getGui() {
    return gui;
  }

  /**
   * Moves the player in the direction specified.
   * Checks if the tile is able to be moved onto by interacting with it.
   * If valid, it sets the location of the player to the new tile.
   *
   * @param direction the direction to move in.
   */
  public void move(Tile.Direction direction) {
    if (gamePaused) {
      return;
    }

    Tile currentLocation = player.getLocation();
    Tile nextLocation;
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
      default:
        nextLocation = null;
    }
    if (nextLocation == null || !nextLocation.interact(player)) {
      currentLocation.setTileOccupied(player.getImageUrl(direction));
      return; //invalid move
    }
    if (nextLocation.isOccupied()) { // stepped on a mob
      gameOver(MenuType.DEATH);
      return;
    }
    currentLocation.setTileUnoccupied();
    nextLocation.setTileOccupied(player.getImageUrl(direction));
    player.setLocation(nextLocation);

    checkFields();

    if (RecordAndPlay.getIsRecording()) {
      RecordAndPlay.addAction(direction);
    }
  }

  private void checkFields() {
    if (player.getLocation().getType() == Tile.Type.Exit) {
      if (!board.setNextLevel()) {
        gameOver(MenuType.WINNER);
        return;
      }
      player = new Player(board.getPlayerLocation());
      timeLeft = totalTime;
    }

    if (player.getLocation().getType() == Tile.Type.InfoField) {
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
    startTime = System.currentTimeMillis();
    gui.pauseGame();
  }

  /**
   * Resumes the game.
   */
  public void resumeGame() {
    gamePaused = false;
    startTime = System.currentTimeMillis();
    gui.resumeGame();
    if(!threadMade)runningThread();
  }

  /**
   * Loads the game.
   */
  public void loadGame() {
    gamePaused = true;
    startTime = System.currentTimeMillis();
    if (gui.loadGame()) {
      try {
        //TODO: use the field "loadFile" - a File object
        JsonReadWrite.loadGameStateFromFile(loadFile.getAbsolutePath(), this);
      } catch (Exception e) {
        gui.noFileFound();
        return;
      }
      System.out.println("Game loaded.");
    }
    resumeGame();
  }

  /**
   * Saves the game.
   */
  public void saveGame() {
    gamePaused = true;
    startTime = System.currentTimeMillis();
    if (gui.saveGame()) {
      JsonReadWrite.saveGameState(this, saveFile.getAbsolutePath());
    }
    resumeGame();
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    board.setCurrentLevel(0);
    resetLogistics();
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
  }

  /**
   * Sets the game to the next level.
   * If there is no next level, current level is restarted.
   */
  public void nextLevel() {
    int current = board.getCurrentLevel();
    if (current < board.getFinalLevel()) {
      board.setCurrentLevel(current + 1);
    } else {
      board.setCurrentLevel(board.getFinalLevel());
    }
    resetLogistics();
  }

  /**
   * Restart current level.
   */
  public void restartLevel() {
    int current = board.getCurrentLevel();
    board.setCurrentLevel(current);
    resetLogistics();
  }

  /**
   * Sets the game to the desired level.
   * If level isn't valid, ignore.
   * @param level number.
   */
  public void setLevel(int level) {
    if (level < 0 || level > board.getFinalLevel()) {
      return;
    }

    board.setCurrentLevel(level);
    resetLogistics();
  }

  /**
   * Exits the game.
   */
  public void exitGame() {
    gamePaused = true;
    startTime = System.currentTimeMillis();
    if (gui.exitGame()) {
      System.exit(0);
    }
    resumeGame();
  }

  /**
   * Running thread opens a new thread (double threaded).
   * and runs a timer, updating the dashboard every second
   */
  private void runningThread() {
    threadMade = true;
    Runnable runnable = new Runnable() {

      private int timeCheck = 0;

      @Override
      public void run() {
        // While the time has not run out
        while (true) {
          // Only run while the game is not paused
          if (!gamePaused) {
            // Attempt to sleep the thread if there is time left
            try {
              if (timeLeft > 0) {
                // Every second
                if (timeCheck == 0) {
                  // Update the dashboard and mobs
                  gui.updateDashboard();
                  mobManager.advanceByOneTick();
                }
                // Update the board every 1/fps second
                gui.updateBoard();

                // Restricts the frame rate to 30 fps
                Thread.sleep(1000 / fps);

                // Tick counter cycles (0, 1)
                timeCheck = (timeCheck + 1) % fps;
              } else {
                throw new InterruptedException("TIMED OUT");
              }
            } catch (InterruptedException e) {
              // If anything was to go unsuccessfully, then control crash the game with a time out
              gameOver(MenuType.TIMEOUT);
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
   * Return total number of treasures in the level.
   *
   * @return Total number of treasures.
   */
  public int getTotalTreasures() {
    return board.getTreasureCount();
  }

  /**
   * Get tiles around player to render on screen.
   *
   * @return Stream of tiles to be drawn.
   */
  public Stream<Tile> getTilesToRender() {
    return board.getStream(player.getLocation());
  }


  /**
   * Gets the current level of this game.
   *
   * @return Level the level currently held by board.
   */
  public int getLevel() {
    return board.getCurrentLevel();
  }

  /**
   * Get board object.
   *
   * @return Board object.
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

  public void gameOver(MenuType reason) {
    gamePaused = true;
    gui.gameOver(reason);
  }


  /**
   * Get time remaining.
   *
   * @return long time in seconds
   */
  public long getTimeLeft() {
    return timeLeft;
  }

  public void setSaveFile(File saveFile) {
    this.saveFile = saveFile;
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
  public void resetLogistics() {
    timeLeft = totalTime;
    startTime = System.currentTimeMillis();
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
   *
   * @param timeLeft the time left.
   */
  public void setTimeLeft(long timeLeft) {
    this.timeLeft = timeLeft;
  }

  /**
   * Gets the mob manager.
   *
   * @return the mob manager.
   */
  public MobManager getMobManager() {
    return mobManager;
  }

  /**
   * Sets the player to a new player.
   *
   * @param player the player to set.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }


  /**
   * Checks if there is a running thread, which gets interrupted. and creates a new one.
   */
  public void startRunningThread() {
    if (thread != null && thread.isAlive()) {
      thread.interrupt();
    }
    if(!threadMade)runningThread();
  }

  public void setMobManager(MobManager mobManager) {
    this.mobManager = mobManager;
  }

  /**
   * ChapsChallenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    new ChapsChallenge();
  }
}
