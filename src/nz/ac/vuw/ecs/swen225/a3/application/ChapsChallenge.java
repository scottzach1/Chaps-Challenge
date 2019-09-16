package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class ChapsChallenge implements KeyListener {

  private Board board;
  private GUI gui;
  private Player player;

  private long totalTime = 100; //100 seconds, todo change with levels
  private long startTime;
  private long timeLeft = totalTime;

  private boolean gamePaused=false;

  // HashSet of actively pressed keys
  HashSet<Integer> activeKeys;
  /**
   * Create main game application.
   */
  private ChapsChallenge() {
    // Load the board.
    board = new Board();
    new JsonReadWrite(board);
    try {
      player = new Player(board.getPlayerLocation());
    } catch (Board.PlayerNotFoundException e) {
      System.out.println("Error, player not found in level description");
      throw new Error("Player not found");
    }
    startTime = System.currentTimeMillis();

    // Create new set for hosting keys currently pressed
    activeKeys = new HashSet<>();
    // Creates a GUI and gives it a keyListener
    gui = new GUI(this);
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
    if (!nextLocation.interact(player)) {
      return; //invalid move
    }
    player.setLocation(nextLocation);
  }

  /**
   * Checks the amount of time that has elapsed since the start of the game.
   * Subtracts this from the total time available.
   * @return the time left to play
   */
  public int timeLeft() {
    if (gamePaused) {
      return (int) timeLeft;
    }
    long elapsedTime = System.currentTimeMillis() - startTime;
    timeLeft -= TimeUnit.MILLISECONDS.toSeconds(elapsedTime);

    startTime=System.currentTimeMillis();

    return (int) timeLeft;
  }

  /**
   * Pauses the game.
   */
  public void pauseGame() {
    gamePaused=true;
    gui.pauseGame();
  }

  /**
   * Resumes the game.
   */
  public void resumeGame() {
    gamePaused=false;
    startTime=System.currentTimeMillis();
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

  /**
   * Returns a list of strings containing the players inventory.
   * Strings in format item - number_of_items.
   * @return the list of items.
   */
  public List<String> getPlayerInventory(){
    List <String> toReturn = new ArrayList<>();
    HashMap<String, Integer> inventory = player.getInventory();
    for (String key : inventory.keySet()){
      String s = key+" - "+inventory.get(key);
      toReturn.add(s);
    }
    return toReturn;
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
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void keyTyped(KeyEvent e) {/* UNUSED */}

  /**
   * Handles events occuring after a key is pressed.
   * First adding it to the list of keys pressed, then dealing with all
   * active keys in the 'activeKeys' set.
   * @param e - The key pressed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // Add the key pressed to the current list of pressed keys
    activeKeys.add(e.getKeyCode());
    // CTRL + X
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_X) && activeKeys.size() == 2)
      gui.exitGame();
    // CTRL + S
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_S) && activeKeys.size() == 2){
      saveGame();
      gui.exitGame();
    }
    // CTRL + R
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_R) && activeKeys.size() == 2){
      // TODO: Resume a saved game
    }
    // CTRL + P
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_P) && activeKeys.size() == 2){
      // TODO: Start a new game at the last UNFINISHED level
    }
    // CTRL + 1
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_1) && activeKeys.size() == 2){
      // TODO: Start a new game from LEVEL 1
    }
    // SPACE
    if (activeKeys.contains(KeyEvent.VK_SPACE) && activeKeys.size() == 1) {
      if (gamePaused) resumeGame();
      else pauseGame();
    }
    // ESC
    if (activeKeys.contains(KeyEvent.VK_ESCAPE) && activeKeys.size() == 1){
      resumeGame();
    }

    /*
    PLAYER CONTROLS
     */
    // Move Up
    if ((activeKeys.contains(KeyEvent.VK_UP) || activeKeys.contains(KeyEvent.VK_W)) && activeKeys.size() == 1)
      move(Tiles.Direction.Up);
    // Move Down
    if ((activeKeys.contains(KeyEvent.VK_DOWN) || activeKeys.contains(KeyEvent.VK_S)) && activeKeys.size() == 1)
      move(Tiles.Direction.Down);
    // Move Left
    if ((activeKeys.contains(KeyEvent.VK_LEFT) || activeKeys.contains(KeyEvent.VK_A)) && activeKeys.size() == 1)
      move(Tiles.Direction.Left);
    // Move Right
    if ((activeKeys.contains(KeyEvent.VK_RIGHT) || activeKeys.contains(KeyEvent.VK_D)) && activeKeys.size() == 1)
      move(Tiles.Direction.Right);
  }

  /**
   * Removes any key released from the set of activeKeys.
   * @param e - The key released
   */
  @Override
  public void keyReleased(KeyEvent e) {activeKeys.remove(e.getKeyCode());}

  /**
   * ChapsChallenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    ChapsChallenge game = new ChapsChallenge();
  }
}
