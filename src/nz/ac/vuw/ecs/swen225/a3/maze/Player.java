package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The player of the game, represented as Chap. Holds an inventory of items that have been picked
 * up, and a count of treasures that have been collected.
 *
 * @author Luisa Kristen 300444458
 * @author Zac Durant 300449785
 */
public class Player {

  private Tile location;
  private List<String> inventory = new ArrayList<>();
  private Map<Tile.Direction, String> images = new HashMap<>();
  private int treasures = 0;


  /**
   * Create player.
   *
   * @param loc Initial location of player
   */
  public Player(Tile loc) {
    location = loc;

    images.put(Tile.Direction.Down, "chap_front.png");
    images.put(Tile.Direction.Left, "chap_left.png");
    images.put(Tile.Direction.Up, "chap_back.png");
    images.put(Tile.Direction.Right, "chap_right.png");
  }

  /**
   * Gets filename of asset from given direction.
   *
   * @param direction player is facing.
   * @return filename of asset.
   */
  public String getImageUrl(Tile.Direction direction) {
    return images.get(direction);
  }

  /**
   * Sets the inventory to a list.
   *
   * @param inventory the inventory to set to.
   */
  public void setInventory(List<String> inventory) {
    this.inventory = inventory;
  }

  /**
   * Sets the treasures to a certain amount.
   *
   * @param treasures amount to set to.
   */
  public void setTreasures(int treasures) {
    this.treasures = treasures;
  }

  /**
   * Check if player has item.
   *
   * @param item String description of item required
   * @return true if player holds item, false otherwise
   */
  boolean getItem(String item) {
    return inventory.contains(item);
  }

  /**
   * Add item to players inventory.
   *
   * @param item String description of item added
   */
  void addItem(String item) {
    inventory.add(item);
  }

  /**
   * Removes item from players inventory.
   *
   * @param item String description of item removed
   */
  void removeItem(String item) {
    inventory.remove(item);
  }

  /**
   * Get tile player is occupying.
   *
   * @return Tile
   */
  public Tile getLocation() {
    return location;
  }

  /**
   * Sets the location of the player.
   *
   * @param location the location to set to.
   */
  public void setLocation(Tile location) {
    this.location = location;
  }

  /**
   * Gets the number of treasures.
   *
   * @return treasures.
   */
  public int getTreasures() {
    return treasures;
  }

  /**
   * Adds a treasure to the current amount.
   */
  void addTreasure() {
    treasures++;
  }

  /**
   * Gets the players inventory.
   *
   * @return the current inventory.
   */
  public List<String> getInventory() {
    return inventory;
  }
}
