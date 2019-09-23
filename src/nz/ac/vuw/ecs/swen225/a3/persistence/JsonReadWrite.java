package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;

import java.io.File;

/**.
 * @author Group.
 *
 */
public class JsonReadWrite {
  
  /**.
   * @param b = the board.
   */
  public JsonReadWrite(Board b) {
    System.out.println("Todo: Output board information into JSON file.");
  }

  public void saveToFile(int saveNum){
    File file = new File("chips_challenge_save_" + saveNum);
  }

}
