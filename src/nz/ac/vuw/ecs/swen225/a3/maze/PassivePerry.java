package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;


public class PassivePerry extends Mob {

  /**
   * Creates new Passive Perry mob.
   */
  public PassivePerry() {
    setImageUrl("perry_front.png");
    setMobName("Passive Perry");

    direction = Tile.Direction.Down;

    images.put(Tile.Direction.Down, "perry_front.png");
    images.put(Tile.Direction.Left, "perry_left.png");
    images.put(Tile.Direction.Up, "perry_back.png");
    images.put(Tile.Direction.Right, "perry_right.png");

    seedRow = 5;
    seedCol = 0;
    remaining = 0;
  }

  /**
   * Return json representation of this tile.
   *
   * @return Json string of tile properties.
   */
  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
        .add("imageUrl", imageUrl)
        .add("mobName", mobName)
        .add("host", host.getJson())
        .add("active", active)
        .add("direction", direction.toString());

    JsonObject jsonObject = objectBuilder.build();

    try (Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(jsonObject);
      return writer.toString();
    } catch (IOException e) {
      throw new Error("Error parsing " + this.toString() + " to json");
    }
  }

  /**
   * Update this mobs fields from a json.
   *
   * @param json Json string
   */
  public void setMobFromJson(JsonReader json) {
    JsonObject mob = json.readObject();
    imageUrl = mob.getString("imageUrl");
    mobName = mob.getString("mobName");
    host = JsonReadWrite.createTileFromJson(mob.getString("host"));
    active = mob.getBoolean("active");
    for (Tile.Direction d : Tile.Direction.values()) {
      if (d.toString().equals(mob.getString("direction"))) {
        direction = d;
      }
    }
  }
}
