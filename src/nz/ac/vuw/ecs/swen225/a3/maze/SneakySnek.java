package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.HashMap;
import java.util.Map;

public class SneakySnek extends Mob {

  private Tile.Direction direction;
  private Map<Tile.Direction, String> images;

  public SneakySnek(Player player) {
    super(player);
    setImageUrl("snek_front.png");
    setMobName("Sneaky Snek");

    direction = Tile.Direction.Down;

    images = new HashMap<>();
    images.put(Tile.Direction.Down, "snek_front.png");
    images.put(Tile.Direction.Left, "snek_left.png");
    images.put(Tile.Direction.Up, "snek_back.png");
    images.put(Tile.Direction.Right, "snek_right.png");
  }

  @Override
  public void advanceByTick() {
    if (getHost() == null || player == null) return;

    final double seed = Math.random();

    Tile target;
    Tile playerTile = player.getLocation();
    Tile.Direction targDirection = direction;

    // Target player if within 5 cells.
    if (player.getLocation().getDistance(getHost()) <= 5) {
      target = getHost();
      double distance = playerTile.getDistance(target);

      targDirection = targDirection.antiClockWise();

      while (playerTile.getDistance(target) > distance || target.getType() != Tile.Type.Water) {
        targDirection = targDirection.clockWise();
        target = getHost().getDir(targDirection);
      }
    }
    // Normal perry movement.
    else {
      // 50% Chance continue straight.
      if (seed <= 0.50) {
        target = getHost().getDir(targDirection);
      }
      // 25% Chance CW
      else if (seed <= 0.75) {
        targDirection = targDirection.clockWise();
        target = getHost().getDir(targDirection);
      }
      // 25% Chance ACW
      else {
        targDirection = targDirection.clockWise();
        target = getHost().getDir(targDirection);
      }
    }

    if (target.getType() != Tile.Type.Water && !target.isOccupied())
      advanceByTick();
    else {
      direction = targDirection;
      setImageUrl(images.get(direction));
      occupyHost(target);
    }
  }
}
