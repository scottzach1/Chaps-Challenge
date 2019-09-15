package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;

/**
 * Chip and Chap.
 * Chap’s challenge is a creative clone of the (first level of the)
 * 1989 Atari game Chips Challenge. To learn more about Chip’s Challenge.
 */
public class Main {

  // The three components of the dashboard
  private static int level, time, chipsLeft;

  // Getters of the three dashboard components
  public static int getLevel(){ return level;}
  public static int getTime() {return time;}
  public static int getChipsLeft(){return chipsLeft;}

  /**
   * Main invocation point for running the game.
   *
   * @param args main arguments
   */
  public static void main(String[] args) {
    System.out.println("Todo: Make game.");
    String game = "made";
    new Board();
  }
}
