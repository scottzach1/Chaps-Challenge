package nz.ac.vuw.ecs.swen225.a3.maze;

public class InfoField extends Tiles {

  private String info;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the information contained in the tile.
   */
  public InfoField(String info) {
    isAccessible = true;
    this.info = info;
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "InfoField";
  } 
}
