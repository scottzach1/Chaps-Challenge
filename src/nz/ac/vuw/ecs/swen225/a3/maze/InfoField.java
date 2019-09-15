package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

public class InfoField extends Tiles {

  private String info;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the information contained in the tile.
   */
   InfoField(String info) {
     super(Type.InfoFeild);
    isAccessible = true;
    this.info = info;
    imageUrl = "info_field.png";
    defaultImageUrl = "info_field.png";

     AssetManager.loadAsset(imageUrl);
     AssetManager.loadAsset(defaultImageUrl);
  }

  /**
   * Standard toString method.
   *
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
  public boolean interact(Player p) {
    //TODO change print to a popup
    System.out.println(info);
    return isAccessible;
  }
}
