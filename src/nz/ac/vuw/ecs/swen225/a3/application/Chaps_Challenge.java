package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;

import java.lang.invoke.SwitchPoint;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class Chaps_Challenge {

public void move(Tiles.Direction direction){

  switch (direction){
    case Up:
      break;
    case Down:
      break;
    case Left:
      break;
    case Right:
      break;
  }


}

public void restartGame(){}

public void pauseGame(){}

public void resumeGame(){}

public int timeLeft(){return 0;}

public void loadGame(){}

public void saveGame(){}

  /**
   * Chaps_Challenge invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    new Board();
    new GUI(new Chaps_Challenge());
  }
}
