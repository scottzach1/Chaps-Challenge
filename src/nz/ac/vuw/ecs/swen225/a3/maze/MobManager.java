package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

import java.util.HashSet;
import java.util.Set;

public class MobManager {

  private Board board;

  /**
   * Get set of mobs.
   * @return Mobs set
   */
  public Set<Mob> getMobs() {
    return mobs;
  }

  /**
   * Set the collection of mobs.
   * @param mobs Set to use
   */
  public void setMobs(Set<Mob> mobs) {
    this.mobs = mobs;
  }

  private Set<Mob> mobs;

  /**
   * Creates a new Mob manager.
   *
   * @param board board to control mob.
   */
  public MobManager(Board board, Player player) {
    // TODO: This shouldn't take board in the future.
    this.board = board;
    this.mobs = new HashSet<>();

    Mob perryOne = new PassivePerry();
    perryOne.occupyHost(board.getTile(7,7));
    addMob(perryOne);

    Mob perryTwo = new PassivePerry();
    perryTwo.occupyHost(board.getTile(11,11));
    addMob(perryTwo);

    Mob sneakySnek = new SneakySnek(player);
    sneakySnek.occupyHost(board.getTile(19, 19));
    sneakySnek.setBoard(board);
    sneakySnek.setPlayer(player);
    addMob(sneakySnek);
  }

  /**
   * Advances all mobs by one tick.
   */
  public void advanceByOneTick() {
    for (Mob mob : mobs) {
      mob.advanceByTick();
    }
  }


  /**
   * Adds a mob to be tracked by the game.
   *
   * @param mob to add.
   */
  private void addMob(Mob mob) {
    if (mob == null) return;

    mobs.add(mob);
  }

  /**
   * Removes a mob from the managers tracking
   * set.
   *
   * @param mob to remove.
   */
  public void removeMob(Mob mob) {
    mobs.remove(mob);
  }

  /**
   * Removes all mobs from the managers tracking
   * set.
   */
  public void removeAllMobs() {
    mobs.clear();
  }

}