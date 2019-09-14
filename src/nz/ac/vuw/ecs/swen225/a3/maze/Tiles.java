package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Tiles {
  boolean isAccessible;

  boolean getIsAccessible() {
    return isAccessible;
  }

  public enum Direction{
    Left,Right,Up,Down
  }

  Tiles[] adajacent = {};

}
