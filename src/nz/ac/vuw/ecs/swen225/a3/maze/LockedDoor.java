package nz.ac.vuw.ecs.swen225.a3.maze;

public class LockedDoor extends Tiles {
  private String colour;

  public LockedDoor(String colour) {
    isAccessible = false;
    this.colour = colour;
  }

  @Override
  public String toString() {
    return colour + " LockedDoor";
  }
}
