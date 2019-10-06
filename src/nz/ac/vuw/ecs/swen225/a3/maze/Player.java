package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private Tile location;

  public void setInventory(List<String> inventory) {
    this.inventory = inventory;
  }

  public void setTreasures(int treasures) {
    this.treasures = treasures;
  }

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

  public void setLocation(Tile location) {
    this.location = location;
  }

  public int getTreasures() {
    return treasures;
  }

  void addTreasure() {
    treasures++;
  }

  public List<String> getInventory() {
    return inventory;
  }
}
