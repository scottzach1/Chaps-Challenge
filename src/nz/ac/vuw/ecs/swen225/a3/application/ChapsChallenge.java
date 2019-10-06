package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.MobManager;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.persistence.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

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

  public void setPlayer(Player player) {
    this.player = player;
  }

  private Player player;

  private long totalTime = 100; //100 seconds, todo change with levels
  private long startTime;


  public void setBoard(Board board) {
    this.board = board;
  }

  public void setTimeLeft(long timeLeft) {
    this.timeLeft = timeLeft;
  }

  private long timeLeft = totalTime;

  private boolean gamePaused = false;

  private Thread thread;

  private MobManager mobManager;

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
      System.out.println("MOBOBOMOBOBOB");
      restartLevel();
    }
    currentLocation.setTileUnoccupied();
    nextLocation.setTileOccupied("chap_front.png");
    player.setLocation(nextLocation);

    checkFields();

    if(RecordAndPlay.getIsRecording()){
      RecordAndPlay.addAction(direction);
    }

  }

  private void checkFields(){
    if (player.getLocation().getType() == Tile.Type.Exit){
      if (!board.setNextLevel())
        gameEnd();
      player = new Player(board.getPlayerLocation());
      timeLeft=totalTime;
    }

    if (player.getLocation().getType() == Tile.Type.InfoField){
      //gui.displayInfoTile(player.getLocation()); // todo implement
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
    try {
      //TODO: allow choice of save file
      JsonReadWrite.loadGameState("saveGame.txt",this);
    }
    catch(Exception e){
      //TODO: deal with game not found error
      System.out.println(e.getMessage());
    }
    gui.loadGame();
    System.out.println("Game loaded.");
  }

  /**
   * Saves the game.
   */
  public void saveGame() {
    JsonReadWrite.saveGameState(this,"saveGame.txt");
    gui.saveGame();
  }

  /**
   * Restarts the game.
   */
  public void restartGame() {
    board.setCurrentLevel(0);
    gui.restartGame();
  }

  /**
   * Sets the game to the previous level.
   */
  public void previousLevel() {
    int current = board.getCurrentLevel();
    if (current>0){
      board.setCurrentLevel(current-1);
    }
    else{
      board.setCurrentLevel(0);
    }

    player = new Player(board.getPlayerLocation());
    gui.previousLevel();
  }

  public void restartLevel(){
    int current = board.getCurrentLevel();
      board.setCurrentLevel(current);

    player = new Player(board.getPlayerLocation());
  }

  /**
   * Exits the game.
   */
  public void exitGame() {
    if (gui.exitGame())
      System.exit(0);
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
      @Override
      public void run() {
        while (true) {
          if (!gamePaused) {
            mobManager.advanceByOneTick();
            gui.updateBoard();
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
  public Stream<Tile> getTilesToRender() {
    return board.getStream(player.getLocation());
  }

  /**
   * Sets the board to the string, changes the level.
   * @param level the level to change to.
   */
  public void setCustomLevel(String level){
    board.setLevel(level);
    player = new Player(board.getPlayerLocation());
  }

  /**
   * Gets the current level of this game
   * @return Level the level currently held by board
   */
  public int getLevel(){
    return board.getCurrentLevel();
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

  private void gameOver() {
    //gui.gameOver(); //todo Front end implement
    exitGame();
  }

  private void gameEnd() {
    //gui.endGame(); //todo front end implement
  }

  /**
   * Get time remaining.
   * @return long time in seconds
   */
  public long getTimeLeft() {
    return timeLeft;
  }


  /**
   * Update gui.
   */
  public void update(){
    gui.updateBoard();
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
