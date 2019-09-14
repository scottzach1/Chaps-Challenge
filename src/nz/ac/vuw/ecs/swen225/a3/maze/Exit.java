package nz.ac.vuw.ecs.swen225.a3.maze;

public class Exit extends Tiles {
  public Exit() {
    isAccessible = false;
  }

  @Override
  public String toString() {
    return "Exit";
  }
}
