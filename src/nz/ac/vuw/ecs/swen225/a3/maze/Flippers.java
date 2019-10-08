package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

/**
 * A flipper tile, which can be picked up by the player. The possession of flippers allows the
 * player to step on 9 water tiles per flipper.
 *
 * @author Luisa Kristen 300444458
 */
public class Flippers extends Tile {

  private boolean collected;

  /**
   * Constructor for flippers. Sets the type and images of the flipper.
   */
  public Flippers() {
    super(Type.Flippers);
    isAccessible = true;
    imageUrl = "flippers.png";
    defaultImageUrl = "free.png";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    if (!collected) {
      int steps = 9;
      for (int i = 0; i < steps; i++) {
        p.addItem(this.toString());
      }
      imageUrl = defaultImageUrl;
      collected = true;
    }
    return isAccessible;
  }

  /**
   * Return json representation of this tile.
   *
   * @return Json string of tile properties.
   */
  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
        .add("isAccessible", getIsAccessible())
        .add("type", getType().toString())
        .add("row", getRow())
        .add("col", getCol())
        .add("imageUrl", getImageUrl())
        .add("defaultImageUrl", getDefaultImageUrl());

    JsonObject jsonObject = objectBuilder.build();

    try (Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(jsonObject);
      return writer.toString();
    } catch (IOException e) {
      throw new Error("Error parsing " + this.toString() + " to json");
    }
  }

  /**
   * Set tile properties from json.
   *
   * @param json the json to read the object from
   */
  @Override
  public Tile setTileFromJson(JsonReader json) {
    JsonObject tile = json.readObject();
    isAccessible = tile.getBoolean("isAccessible");
    setRow(tile.getInt("row"));
    setCol(tile.getInt("col"));
    imageUrl = tile.getString("imageUrl");
    defaultImageUrl = tile.getString("defaultImageUrl");
    return this;
  }


  /**
   * Standard toString method.
   *
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Flippers";
  }
}
