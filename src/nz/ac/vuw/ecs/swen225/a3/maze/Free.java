package nz.ac.vuw.ecs.swen225.a3.maze;

public class Free extends Tiles {
  public Free() {
    isAccessible = true;
  }

  @Override
  public String toString() {
    return "Free";
  }
}
