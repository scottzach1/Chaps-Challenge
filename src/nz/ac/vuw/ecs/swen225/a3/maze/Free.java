package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Free extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible to true.
   */
  public Free() {
    super(Type.Free);
    isAccessible = true;
    imageUrl = "free.png";
    defaultImageUrl = "free.png";

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
    return "Free";
  }


  /**
   * Checks if the interaction between a character and a tile is valid.
   *
   * @param p The player
   * @return if the interaction is valid
   */
  @Override
  public boolean interact(Player p) {
    return isAccessible;
  }

  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
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
  public Tiles setTileFromJson(JsonReader json) {
    JsonObject tile = json.readObject();
    isAccessible = tile.getBoolean("isAccessible");
    setRow(tile.getInt("row"));
    setCol(tile.getInt("col"));
    imageUrl = tile.getString("imageUrl");
    defaultImageUrl = tile.getString("defaultImageUrl");
    return this;
  }
}
