package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private Tile location;
  private List<String> inventory = new ArrayList<>();
  private int treasures = 0;


  /**
   * Create player.
   *
   * @param loc Initial location of player
   */
  public Player(Tile loc) {
    location = loc;
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
   * Get tile player is occupying.
   *
   * @return Tile
   */
  public Tile getLocation() {
    return location;
  }

  /**
   * Sets the location of the player.
   * @param location the location to set to.
   */
  public void setLocation(Tile location) {
    this.location = location;
  }

  /**
   * Gets the number of treasures.
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
   * @return the current inventory.
   */
  public List<String> getInventory() {
    return inventory;
  }
}
