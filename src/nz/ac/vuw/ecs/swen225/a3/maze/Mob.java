package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonReader;

/**
 * Mob are NPCs in the game that move and interact without any input from the user.
 * Functionally, they act as a spider that latches onto a Tile and scale the board.
 * Although the Constructor takes a Board, the board is not modified at all.
 */
public abstract class Mob {

  protected String imageUrl = "unknown.png";
  protected String mobName = "Unnamed Mob.";
  protected Tile host;
  protected boolean active;

  /**
   * @param newMobName new name of mob.
   */
  void setMobName(String newMobName) {
    mobName = newMobName;
  }

  /**
   * @param newImageUrl new filename of mob image.
   */
  public void setImageUrl(String newImageUrl) {
    imageUrl = newImageUrl;
  }

  /**
   * Sets the active state of the mob.
   * Will not change if mob has no host.
   * @param active state of mob.
   */
  public void setActive(boolean active) {
    if (host != null) this.active = active;
  }

  /**
   * Moves the Mob by one Cell.
   * Needs to be implemented by concrete class.
   */
  public abstract void advanceByTick();

  /**
   * Occupies a new tile as a host.
   * @param target cell to occupy.
   */
  void occupyHost(Tile target) {
    // Reset old host.
    if (host != null) {
      host.setTileUnoccupied();
      host.setOccupied(false);
    }

    // Set new host.
    host = target;

    if (target != null) {
      target.setTileOccupied(target.getImageUrl() + "-" + imageUrl);
      target.setOccupied(true);
    }
    active = target != null;
  }

  /**
   * Calls occupy host with null as the target.
   */
  public void leaveHost() {
    occupyHost(null);
    active = false;
  }

  /**
   * Returns host of Mob.
   * @return host of Mob.
   */
  public Tile getHost() {
    return host;
  }

  public abstract String getJson();

  public abstract void setMobFromJson(JsonReader json);

  public void setHost(Tile t){
    host = t;
  }



}
