package nz.ac.vuw.ecs.swen225.a3.maze;

public class Bandit extends Mob {

  Tile.Direction direction = Tile.Direction.Down;

  /**
   * Creates new Bandit mob.
   */
  public Bandit() {
    setImageUrl("Bandit");
  }

  @Override
  public void advanceByTick() {
    if (getHost() == null) return;

    final double seed = Math.random();

    Tile target;

    // 50% Chance continue straight.
    if (seed <= 0.50) {
      target = getHost().getDir(direction);
    }
    // 15% Chance CW
    if (seed <= 0.65) {
      direction = direction.clockWise();
      target = getHost().getDir(direction);
    }
    // 15% Chance ACW
    else if (seed <= 0.80) {
      direction = direction.clockWise();
      target = getHost().getDir(direction);
    }
    // 15% Chance Don't move
    else if (seed <= 0.95) {
      target = getHost();
    }
    // 5% Chance Move backwards.
    else {
      direction = direction.reverse();
      target = getHost().getDir(direction);
    }

    occupyHost(target);
  }
}
