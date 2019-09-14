package nz.ac.vuw.ecs.swen225.a3.maze;

public class Treasure extends Tiles {
  public Treasure() {
    isAccessible = true;
  }

  @Override
  public String toString() {
    return "Treasure";
  }
}
