package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PassivePerry extends Mob {

  private Tile.Direction direction;
  private Map<Tile.Direction, String> images;

  /**
   * Creates new Bandit mob.
   */
  public PassivePerry(Player player) {
    super(player);
    setImageUrl("perry_front.png");
    setMobName("Passive Perry");

    direction = Tile.Direction.Down;

    images = new HashMap<>();
    images.put(Tile.Direction.Down, "perry_front.png");
    images.put(Tile.Direction.Left, "perry_left.png");
    images.put(Tile.Direction.Up, "perry_back.png");
    images.put(Tile.Direction.Right, "perry_right.png");
  }

  @Override
  public void advanceByTick() {
    if (getHost() == null) return;

    final double seed = Math.random();

    Tile target;
    Tile.Direction targDirection = direction;

    // 50% Chance continue straight.
    if (seed <= 0.50) {
      target = getHost().getDir(targDirection);
    }
    // 15% Chance CW
    else if (seed <= 0.65) {
      targDirection = targDirection.clockWise();
      target = getHost().getDir(targDirection);
    }
    // 15% Chance ACW
    else if (seed <= 0.80) {
      targDirection = targDirection.clockWise();
      target = getHost().getDir(targDirection);
    }
    // 15% Chance Don't move
    else if (seed <= 0.95) {
      target = getHost();
    }
    // 5% Chance Move backwards.
    else {
      targDirection = targDirection.reverse();
      target = getHost().getDir(targDirection);
    }

    if (target.getType() != Tile.Type.Free && !target.isOccupied())
      advanceByTick();
    else {
      direction = targDirection;
      setImageUrl(images.get(direction));
      occupyHost(target);
    }
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

    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(jsonObject);
      return writer.toString();
    }catch(IOException e) {throw new Error("Error parsing " + this.toString() + " to json");}
  }

  /**
   * Update this mobs fields from a json
   * @param json Json string
   */
  public void setMobFromJson(JsonReader json){
    JsonObject mob = json.readObject();
    imageUrl = mob.getString("imageUrl");
    mobName = mob.getString("mobName");
    host = JsonReadWrite.createTileFromJson(mob.getString("host"));
    active = mob.getBoolean("active");
    for(Tile.Direction d : Tile.Direction.values()){
      if(d.toString().equals(mob.getString("direction"))){
        direction = d;
      }
    }
  }
}
