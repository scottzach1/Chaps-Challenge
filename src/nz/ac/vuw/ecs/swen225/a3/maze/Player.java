package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
  private Tiles location;

  public void setInventory(List<String> inventory) {
    this.inventory = inventory;
  }

  public void setTreasures(int treasures) {
    this.treasures = treasures;
  }

  private List<String> inventory = new ArrayList<String>();
  private int treasures = 0;

  /**
   * Create player.
   *
   * @param loc Initial location of player
   */
  public Player(Tiles loc) {
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
  public Tiles getLocation() {
    return location;
  }

  public void setLocation(Tiles location) {
    this.location = location;
  }

  public int getTreasures() {
    return treasures;
  }

  public void addTreasure() {
    treasures++;
  }

  public List<String> getInventory() {
    return inventory;
  }
}
