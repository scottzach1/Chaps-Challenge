package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;


/**
 * A locked door. This is a tile that can only be stepped on if the player possesses a key with the
 * same colour. By stepping on the locked door, one copy of the key will disappear out the
 * inventory. Turns into a free tile when unlocked.
 *
 * @author Luisa Kristen 300444458
 */
public class LockedDoor extends Tile {

  private String colour;
  private boolean active = true;

  public LockedDoor() {
    super(Type.LockedDoor);
  }

  /**
   * Constructor. Sets the isAccessible to true. Sets the colour of the door to the parameter.
   *
   * @param colour the colour of the door.
   */
  public LockedDoor(String colour) {
    super(Type.LockedDoor);
    isAccessible = false;
    this.colour = colour;
    imageUrl = "locked_door_" + colour + ".png";
    defaultImageUrl = "free.png";
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player.
   * @return if the interaction is valid.
   */
  @Override
  public boolean interact(Player p) {
    if (!active) {
      return true;
    }
    if (p.getItem("key_" + colour)) {
      isAccessible = true;
      imageUrl = defaultImageUrl;
      p.removeItem("key_" + colour); // removes the key from the inventory
      active = false;
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
        .add("colour", colour)
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
    colour = tile.getString("colour");
    return this;
  }

  /**
   * Standard toString method.
   *
   * @return the name of the tile + the colour.
   */
  @Override
  public String toString() {
    return colour + " LockedDoor";
  }

}
