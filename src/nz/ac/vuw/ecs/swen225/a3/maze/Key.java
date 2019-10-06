package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Key extends Tile {
  private String colour;

  /**
   * Constructor.
   * Sets the isAccessible to true.
   * Sets the colour of the key to the parameter.
   *
   * @param colour the colour of the key.
   */
  public Key(String colour) {
     super(Type.Key);
    isAccessible = true;
    this.colour = colour;
    imageUrl = "key_" + colour + ".png";
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
    p.addItem(this.toString());
    imageUrl = defaultImageUrl;
    return isAccessible;
  }

  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
        .add("colour",colour)
        .add("isAccessible",getIsAccessible())
        .add("type", getType().toString())
        .add("row", getRow())
        .add("col", getCol())
        .add("imageUrl",getImageUrl())
        .add("defaultImageUrl",getDefaultImageUrl());

    JsonObject jsonObject = objectBuilder.build();

    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(jsonObject);
      return writer.toString();
    }catch(IOException e) {throw new Error("Error parsing " + this.toString() + " to json");}
  }

  @Override
  public Tile setTileFromJson(JsonReader json) {
    JsonObject tile = json.readObject();
    isAccessible = tile.getBoolean("isAccessible");
    setRow(tile.getInt("row"));
    setCol(tile.getInt("col"));
    imageUrl = tile.getString("imageUrl");
    defaultImageUrl = tile.getString("defaultImageUrl");
    colour = tile.getString("colour");
    return this;
  }

  /**
   * Standard toString method.
   *
   * @return the name of the tile + the colour
   */
  @Override
  public String toString() {
    return "key_" + colour;
  }
}
