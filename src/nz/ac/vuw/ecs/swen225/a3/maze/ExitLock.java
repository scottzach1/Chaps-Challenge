package nz.ac.vuw.ecs.swen225.a3.maze;

public class ExitLock extends Tiles {
  public ExitLock() {
    isAccessible = false;
  }

  @Override
  public String toString() {
    return "ExitLock";
  }
}
