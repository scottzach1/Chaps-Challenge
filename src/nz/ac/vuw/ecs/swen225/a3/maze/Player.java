package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.HashMap;

public class Player {
  private Tiles location;
  private HashMap<String,Integer> inventory = new HashMap<>();

  /**
   * Create player.
   * @param loc Initial location of player
   */
  public Player(Tiles loc){
    location = loc;
  }

  /**
   * Check if player has item.
   * @param item String description of item required
   * @return true if player holds item, false otherwise
   */
  public boolean getItem(String item){
    if(inventory.get(item) != null && inventory.get(item) > 0){
      inventory.put(item,inventory.get(item)-1);
      return true;
    }
    else{
      return false;
    }
  }

  /**
   * Add item to players inventory.
   * @param item String description of item added
   */
  public void addItem(String item) {
    if(inventory.get(item) == null){
      inventory.put(item,1);
    }
    else{
      inventory.put(item,inventory.get(item)+1);
    }
  }

  /**
   * Get tile player is occupying.
   * @return Tile
   */
  public Tiles getLocation(){
    return location;
  }

}
