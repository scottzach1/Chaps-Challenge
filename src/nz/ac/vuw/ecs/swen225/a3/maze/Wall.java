package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Wall extends Tiles {

  /**
   * Constructor.
   * Sets the isAccessible field to false.
   */
   Wall() {
     super(Type.Wall);
     isAccessible = false;
    imageUrl = "wall.png";
    defaultImageUrl = "wall.png";

     AssetManager.loadAsset(imageUrl);
     AssetManager.loadAsset(defaultImageUrl);
  }


  /**
   * Checks if the interaction between a character and a tile is valid.
   *
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

  /**
   * Standard toString method.
   *
   * @return the name of the tile
   */
  @Override
  public String toString() {
    return "Wall";
  }
}
