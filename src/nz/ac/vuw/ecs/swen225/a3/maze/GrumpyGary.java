package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;

public class GrumpyGary extends Mob {

  /**
   * Creates a new Grumpy Gary.
   */
  public GrumpyGary() {
    setImageUrl("gary_front.png");
    setMobName("Grumpy Gary");

    direction = Tile.Direction.Down;

    images.put(Tile.Direction.Down, "gary_front.png");
    images.put(Tile.Direction.Left, "gary_left.png");
    images.put(Tile.Direction.Up, "gary_back.png");
    images.put(Tile.Direction.Right, "gary_right.png");
  }

  @Override
  public void advanceByTick() {
    if (getHost() == null) {
      return;
    }

    // If player exists, calculate targeting ai.
    if (board != null && board.getPlayerLocation() != null) {
      Tile player = board.getPlayerLocation();

      // If player within 5 tiles, calculate whether to charge.
      if (host.getDistance(player) < 6) {
        Tile vision = host;
        boolean doCharge = false;

        // Look ahead 5 moves, if collision charge.
        for (int i = 0; i != 5; ++i) {
          vision = vision.getDir(direction);
          if (vision == null) {
            break;
          }
          if (vision == player) {
            doCharge = true;
          }
        }

        // Advance in current direction.
        if (doCharge) {
          // Occupy cell.
          setImageUrl(images.get(direction));
          occupyHost(host.getDir(direction));
          return;
        }
      }
    }

    // Otherwise, revert to default path finding.
    super.advanceByTick();
  }

  /**
   * Return json representation of this tile.
   *
   * @return Json string of tile properties.
   */
  @Override
  public String getJson() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
        .add("imageUrl",imageUrl)
        .add("mobName", mobName)
        .add("host", host.getJson())
        .add("active", active)
        .add("direction",direction.toString());

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
   * @param json Json string.
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
