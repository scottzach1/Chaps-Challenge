package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.HashMap;
import java.util.Map;

public class PassivePerry extends Mob {

  private Tile.Direction direction;
  private Map<Tile.Direction, String> images;

  /**
   * Creates new Bandit mob.
   */
  PassivePerry() {
    setImageUrl("chap_front.png");
    setMobName("Passive Perry");

    direction = Tile.Direction.Down;

    images = new HashMap<>();
    images.put(Tile.Direction.Down, "chap_front.png");
    images.put(Tile.Direction.Left, "chap_left.png");
    images.put(Tile.Direction.Up, "chap_back.png");
    images.put(Tile.Direction.Right, "chap_right.png");
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
}
