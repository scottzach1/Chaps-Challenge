package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores and controls the ticking of all mobs in game.
 * @author Zac Scott 300447976.
 */
public class MobManager {

  public Board board;

  /**
   * Get set of mobs.
   *
   * @return Mobs set
   */
  public Set<Mob> getMobs() {
    return mobs;
  }

  /**
   * Set the collection of mobs.
   *
   * @param mobs Set to use
   */
  public void setMobs(Set<Mob> mobs) {
    this.mobs = mobs;
  }

  public Set<Mob> mobs = new HashSet<>();

  /**
   * Creates a new Mob manager.
   *
   * @param board board to control mob.
   */
  public MobManager(Board board) {
    this.setBoard(board);
    this.mobs = new HashSet<>();
  }

  /**
   * Advances all mobs by one tick.
   */
  public void advanceByOneTick() {
    for (Mob mob : mobs) {
      Tile before = mob.getHost();
      mob.advanceByTick();
      Tile after = mob.getHost();
      if(before != null && after != null && before != after && RecordAndPlay.getIsRecording()){
        for(Tile.Direction d : Tile.Direction.values()){
          if(before.getDir(d) == after){
            RecordAndPlay.storeMobMove(d,mob.id);
          }
        }
      }
    }
  }

  /**
   * Move a particular mob in a direction.
   * @param id Id of move to move.
   * @param d Direction to move mob.
   */
  public void moveMob(int id, Tile.Direction d){
    Mob m = mobs.stream().filter(p -> p.id == id).findFirst().orElse(null);
    if(m != null) m.occupyHost(m.getHost().getDir(d));
  }


  /**
   * Adds a mob to be tracked by the game.
   *
   * @param mob to add.
   */
  public void addMob(Mob mob) {
    if (mob == null) {
      return;
    }

    mobs.add(mob);
  }

  /**
   * Removes a mob from the managers tracking set.
   *
   * @param mob to remove.
   */
  public void removeMob(Mob mob) {
    mobs.remove(mob);
  }

  /**
   * Removes all mobs from the managers tracking set.
   */
  public void removeAllMobs() {
    mobs.clear();
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

}