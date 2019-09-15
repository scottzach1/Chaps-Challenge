package nz.ac.vuw.ecs.swen225.a3.maze;

public class Key extends Tiles {
  private String colour;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the key to the parameter.
   * @param colour the colour of the key.
   */
   Key(String colour) {
    isAccessible = true;
    this.colour = colour;
    imageUrl = "assets/key_" + colour + ".png";
    defaultImageUrl = "assets/free.png";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  boolean interact(Player p) {
    p.addItem(this.toString());
    imageUrl = defaultImageUrl;
    return isAccessible;
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
