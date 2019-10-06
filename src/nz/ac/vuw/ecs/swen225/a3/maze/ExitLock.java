package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class ExitLock extends Tile {
  private int totalTreasures;// amount of treasures that still need to be collected

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public ExitLock() {
    super(Type.ExitLock);
    isAccessible = false;
    imageUrl = "exit_lock.png";
    defaultImageUrl = "free.png";
  }

  /**
   * Standard toString method.
   *
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "ExitLock";
  }


  public void setTotalTreasures(int t) {
    totalTreasures = t;
  }

  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */

  @Override
  public boolean interact(Player p) {
    if (totalTreasures == p.getTreasures()) {
      imageUrl = defaultImageUrl;
      isAccessible = true;
    }
    return isAccessible;
  }

  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
        .add("totalTreasures",totalTreasures)
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
    totalTreasures = tile.getInt("totalTreasures");
    return this;  }
}
