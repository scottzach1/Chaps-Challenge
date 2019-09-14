package nz.ac.vuw.ecs.swen225.a3.maze;

public class InfoField extends Tiles {

  private String info;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the information contained in the tile.
   */
   InfoField(String info) {
    isAccessible = true;
    this.info = info;
    imageUrl = "assets/info_field.png";
  }

  /**
   * Standard toString method.
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "InfoField";
  }


  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {
  //TODO change print to a popup
    System.out.println(info);
    return isAccessible;  }
}
