package nz.ac.vuw.ecs.swen225.a3.application;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.lang.invoke.SwitchPoint;
import java.util.stream.Stream;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class Chaps_Challenge {

  private Board board;
  private Player player;

  /**
   * Create main game application.
   */
  private Chaps_Challenge(){
    board = new Board();
    new JsonReadWrite(board);
    try {
      player = new Player(board.getPlayerLocation());
    }
    catch(Board.PlayerNotFoundException e){
      System.out.println("Error, player not found in level description");
      throw new Error("Player not found");
    }
  }

public void move(Tiles.Direction direction){

  Tiles currentLocation =  player.getLocation();
  switch (direction){
    case Up:
      player.setLocation(currentLocation.getUp());
      break;
    case Down:
      player.setLocation(currentLocation.getDown());
      break;
    case Left:
      player.setLocation(currentLocation.getLeft());
      break;
    case Right:
      player.setLocation(currentLocation.getRight());
      break;
  }


}

public void restartGame(){}

public void pauseGame(){}

public void resumeGame(){}

public int timeLeft(){return 0;}

public void loadGame(){}

public void saveGame(){}

  /** Get tiles around player to render on screen.
   * @return Stream of tiles to be drawn
   */
  public Stream<Tiles> getTilesToRender(){
    return board.getStream(player.getLocation());
}

  /**
   * Chaps_Challenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    Chaps_Challenge game = new Chaps_Challenge();
    new GUI(game);
  }
}
