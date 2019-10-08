package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;

public class SneakySnek extends Mob {

  /**
   * Creates new Sneaky Snake mob.
   */
  public SneakySnek() {
    setImageUrl("snek_front.png");
    setMobName("Sneaky Snek");

    direction = Tile.Direction.Down;

    images.put(Tile.Direction.Down, "snek_front.png");
    images.put(Tile.Direction.Left, "snek_left.png");
    images.put(Tile.Direction.Up, "snek_back.png");
    images.put(Tile.Direction.Right, "snek_right.png");

    safeTiles.clear();
    safeTiles.add(Tile.Type.Water);
  }

  @Override
  public void advanceByTick() {
    if (getHost() == null) {
      return;
    }

    // If player exists, calculate targeting ai.
    if (board != null && board.getPlayerLocation() != null) {
      Tile player = board.getPlayerLocation();

      Tile target = host;

      double distance = host.getDistance(player);
      boolean foundTarget;

      // If player within 5 tiles, approach player.
      if (distance < 5) {

        // Calculate move to right.
        if (host.getCol() < player.getCol()) {
          Tile newTarget = host.getDir(Tile.Direction.Right);
          foundTarget = safeTiles.contains(target.getType());
          double newDistance = newTarget.getDistance(player);
          if (foundTarget && newDistance <= distance) {
            target = newTarget;
            distance = newDistance;
          }
        }

        // Calculate move up.
        if (host.getRow() > player.getRow()) {
          Tile newTarget = host.getDir(Tile.Direction.Up);
          foundTarget = safeTiles.contains(target.getType());
          double newDistance = newTarget.getDistance(player);
          if (foundTarget && newDistance <= distance) {
            target = newTarget;
            distance = newDistance;
          }
        }

        // Calculate move to left.
        if (host.getCol() > player.getCol()) {
          Tile newTarget = host.getDir(Tile.Direction.Left);
          foundTarget = safeTiles.contains(target.getType());
          double newDistance = newTarget.getDistance(player);
          if (foundTarget && newDistance <= distance) {
            target = newTarget;
            distance = newDistance;
          }
        }

        // Calculate move down.
        if (host.getRow() < player.getRow()) {
          Tile newTarget = host.getDir(Tile.Direction.Down);
          foundTarget = safeTiles.contains(target.getType());
          double newDistance = newTarget.getDistance(player);
          if (foundTarget && newDistance <= distance) {
            target = newTarget;
            distance = newDistance;
          }
        }

        // Occupy cell.
        if (safeTiles.contains(target.getType())) {
          setImageUrl(images.get(direction));
          occupyHost(target);
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
