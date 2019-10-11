package nz.ac.vuw.ecs.swen225.a3.application;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.InfoField;
import nz.ac.vuw.ecs.swen225.a3.maze.MobManager;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.a3.renderer.GameMenu.MenuType;
import nz.ac.vuw.ecs.swen225.a3.renderer.Gui;

/**
 * Chip and Chap. Chap’s challenge is a creative clone of the (first level of the) 1989 Atari game
 * Chips Challenge. To learn more about Chip’s Challenge. ChapsChallenge maintains the functionality
 * of the game. It also provides the link between the Maze, Renderer and Persistence packages.
 *
 * @author Luisa Kristen 300444458.
 */
public class ChapsChallenge {

  private Board board;
  private Gui gui;
  private Player player;

  private long totalTime = 100; //100 seconds
  private long timeLeft = totalTime;
  private boolean gamePaused = false;

  private int fps = 20;
  private MobManager mobManager;

  private File saveFile;
  private File loadFile;

  private Thread thread;

  /**
   * Create main game application.
   */
  public ChapsChallenge() {
    AssetManager assetManager = new AssetManager();
    LevelManager.loadLevels(assetManager);
    // Load the board.
    board = new Board(this);
    board.setup();
    player = new Player(board.getPlayerLocation());
    mobManager = new MobManager(board);

    // Creates a GUI and gives it a keyListener
    gui = new Gui(this, assetManager);
    gui.addLayoutComponents();

  }

  /**
   * Gets the GUI of the game.
   *
   * @return GUI of the game.
   */
  public Gui getGui() {
    return gui;
  }

  /**
   * Moves the player in the direction specified. Checks if the tile is able to be moved onto by
   * interacting with it. If valid, it sets the location of the player to the new tile.
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
      if (!nextLevel()) {
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
    gui.resumeGame();
  }

  /**
   * Loads the game.
   */
  public void loadGame() {
    gamePaused = true;
    if (gui.loadGame()) {
      try {
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
    if (gui.saveGame()) {
      JsonReadWrite.saveGameState(this, saveFile.getAbsolutePath());
    }
    resumeGame();
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    gui.resetMenuSettings();
    board.setCurrentLevel(0);
    resetLogistics();
  }

  /**
   * Sets the game to the previous level. If there is no previous level, level 1 is restarted.
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
   * Sets the game to the next level. If there is no next level, end screen is displayed.
   */
  public boolean nextLevel() {
    int current = board.getCurrentLevel();
    if (current < board.getFinalLevel()) {
      board.setCurrentLevel(current + 1);
    } else {
      return false;
    }
    resetLogistics();
    return true;
  }

  /**
   * Restart current level.
   */
  public void restartLevel() {
    gui.resetMenuSettings();
    int current = board.getCurrentLevel();
    RecordAndPlay.endRecording();
    board.setCurrentLevel(current);
    resetLogistics();
  }

  /**
   * Sets the game to the desired level. If level isn't valid, ignore.
   *
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
    gui.setPlayerDead();
    gui.gameOver(MenuType.QUITTER);
  }

  /**
   * Running thread opens a new thread (double threaded). and runs a timer, updating the dashboard
   * every second
   */
  private void runningThread() {

    Runnable runnable = new Runnable() {

      private int timeCheck = 0;
      private boolean pastFirstSecond = false;

      @Override
      public void run() {
        // While the time has not run out
        while (true) {
          // Only run while the game is not paused
          if (!gamePaused && !RecordAndPlay.getIsRunning()) {
            // Attempt to sleep the thread if there is time left
            if (timeLeft > 0) {
              // Update the board every 1/fps second
              gui.updateBoard();
              gui.updateDashboard();

              // Every second
              if (timeCheck == 0) {
                // Update the dashboard and mobs
                mobManager.advanceByOneTick();
                if (pastFirstSecond) {
                  timeLeft--;
                } else {
                  pastFirstSecond = true;
                }
              }
              // Restricts the frame rate to 30 fps
              try {
                Thread.sleep(1000 / fps);
                // Tick counter cycles (0, 1)
                timeCheck = (timeCheck + 1) % fps;
              } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e);
              }

            } else {
              // When the player runs out of time
              gameOver(MenuType.TIMEOUT);
            }
          } else {
            try {
              Thread.sleep(10);
            } catch (InterruptedException e) {
              System.out.println("Interrupted: " + e);
            }
          }
        }
      }
    };
    thread = new Thread(runnable);
    thread.start();
  }

  /**
   * Returns the amount of time left to play.
   *
   * @return the time left to play
   */
  public int timeLeft() {
    if (timeLeft <= 0) {
      return 0;
    }
    return (int) timeLeft;
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

  /**
   * Called when game over is reached.
   *
   * @param reason for game over.
   */
  public void gameOver(MenuType reason) {
    if (RecordAndPlay.getIsRunning()) {
      return;
    }
    RecordAndPlay.endRecording();
    gamePaused = true;
    gui.setPlayerDead();
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

  /**
   * Sets the current save file.
   *
   * @param saveFile to set
   */
  public void setSaveFile(File saveFile) {
    this.saveFile = saveFile;
  }

  /**
   * Sets the current load file.
   *
   * @param loadFile to set
   */
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
   * Resets the logistics of the game. Resets timer. Makes a new Mob manager. Makes a new Player.
   */
  public void resetLogistics() {
    timeLeft = totalTime;
    player = new Player(board.getPlayerLocation());
    gui.setPlayerAlive();
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
      return;
    }
    runningThread();
  }

  /**
   * Sets the mob manager.
   *
   * @param mobManager to set.
   */
  public void setMobManager(MobManager mobManager) {
    this.mobManager = mobManager;
  }

  /**
   * Sets the refresh-rate of the program (frames per second).
   *
   * @param fps frames per second.
   */
  public void setFps(int fps) {
    this.fps = fps;
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
