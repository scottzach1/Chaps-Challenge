package nz.ac.vuw.ecs.swen225.a3.maze;

public class Key extends Tiles {
  private String colour;

  public Key(String colour) {
    isAccessible = true;
    this.colour = colour;
  }

  @Override
  public String toString() {
    return colour + " Key";
  }
}
