package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.util.Date;
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

  long startTime;
  long elapsedTime;
  long totalTime = 100; //100 seconds, todo change with levels


  /**
   * Create main game application.
   */
  private ChapsChallenge() {
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

  public void restartGame() {
  }

  public void pauseGame() {
  }

  public void resumeGame() {
  }

  /**
   * Checks the amount of time that has elapsed since the start of the game.
   * Subtracts this from the total time available.
   * @return the time left to play
   */
  public int timeLeft() {
    elapsedTime = System.currentTimeMillis() - startTime;
    return (int) (totalTime - TimeUnit.MILLISECONDS.toSeconds(elapsedTime));
  }

  public void loadGame() {
  }

  public void saveGame() {
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
