package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class Main {

  /**
   * Main invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    System.out.println("Todo: Make game.");
    Board b = new Board();
    new JsonReadWrite(b);
  }
}
