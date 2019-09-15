package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.io.IOException;
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
  private Player player;

  private long totalTime = 100; //100 seconds, todo change with levels
  private long startTime;
  private long timeLeft = totalTime;

  private boolean gamePaused=false;
  /**
   * Create main game application.
   */
  private ChapsChallenge() {
    // Load the assets.
    try {
      AssetManager.loadAssets();
    } catch (IOException e) {
      System.out.println("Unable to load assets from assets/ directory.");
      throw new Error("Assets not found");
    }

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
   * Pauses the game.
   */
  public void pauseGame() {
    gamePaused=true;
  }

  /**
   * Resumes the game.
   */
  public void resumeGame() {
    gamePaused=false;
    startTime=System.currentTimeMillis();
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

  public void loadGame() {
  }

  public void saveGame() {
  }

  public void restartGame() {
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
   * ChapsChallenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    ChapsChallenge game = new ChapsChallenge();
    new GUI(game);
  }
}
