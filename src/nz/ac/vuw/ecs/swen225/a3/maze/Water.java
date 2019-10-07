package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Water extends Tile {

  /**
   * Constructor.
   * Sets the isAccessible field to false.
   */
  public Water() {
    super(Type.Water);
    isAccessible = false;

    imageUrl = "water.png";
    defaultImageUrl = "water.png";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player.
   * @return if the interaction is valid.
   */
  @Override
  public boolean interact(Player p) {
    if (p.getItem("Flippers")) {
      p.removeItem("Flippers");
      return true;
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
   * @param json the json to read the object from.
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
   * @return the name of the tile.
   */
  @Override
  public String toString() {
    return "Water";
  }
}
