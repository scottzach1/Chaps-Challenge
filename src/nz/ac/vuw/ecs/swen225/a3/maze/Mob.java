package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * Mob are NPCs in the game that move and interact without any input from the user.
 * Functionally, they act as a spider that latches onto a Tile and scale the board.
 * Although the Constructor takes a Board, the board is not modified at all.
 */
public abstract class Mob {

  private String imageUrl = "unknown.png";
  private Tile host;

  /**
   * @param newImageUrl
   */
  public void setImageUrl(String newImageUrl) {
    imageUrl = newImageUrl;
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
    }

    // Set new host.
    host = target;

    if (target != null)
      target.setTileOccupied(imageUrl);
  }

  /**
   * Calls occupy host with null as the target.
   */
  public void leaveHost() {
    occupyHost(null);
  }

  /**
   * Returns host of Mob.
   * @return host of Mob.
   */
  Tile getHost() {
    return host;
  }


}
