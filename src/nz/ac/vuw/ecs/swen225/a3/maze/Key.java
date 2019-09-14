package nz.ac.vuw.ecs.swen225.a3.maze;

public class Key extends Tiles {
  private String colour;


  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the key to the parameter.
   * @param colour the colour of the key.
   */
  public Key(String colour) {
    isAccessible = true;
    this.colour = colour;
  }


  /**
   * Standard toString method.
   * @return the name of the tile + the colour
   */
  @Override
  public String toString() {
    return colour + " Key";
  }
}
