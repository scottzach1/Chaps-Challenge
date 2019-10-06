package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to allow recording and replaying gameplay
 */
public class RecordAndPlay {
  private static String saveName;
  private static List<Tile.Direction> moves = new ArrayList();
  private static String gameState;

  /**
   * Create new recording saved in file with name s
   * @param s Name of save
   */
  public static void newSave(ChapsChallenge g, String s){
    saveName = s;
    moves.clear();
    gameState = JsonReadWrite.getGameState(g);
  }

  /**
   * Add action to action history
   */
  public static void addAction(Tile.Direction direction){
    // Check a recording is active
    if(saveName != null){
      moves.add(direction);
    }
  }

  /**
   * Save action history to file
   */
  public static void saveGame(){
    if(saveName != null){
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

      // Array of tiles
      for(Tile.Direction d : moves){
        arrayBuilder.add(d.toString());
      }
      JsonObjectBuilder builder =  Json.createObjectBuilder()
          .add("game",gameState)
          .add("moves",arrayBuilder);

      // Save moves to file
      try(Writer writer = new StringWriter()) {
        Json.createWriter(writer).write(builder.build());
        try {
          BufferedWriter bw = new BufferedWriter(new FileWriter(saveName));
          bw.write(writer.toString());
          bw.close();
        }
        catch(IOException e){
          throw new Error("Failed to save moves");
        }
      }
      catch(IOException e){
        throw new Error("Failed to save moves");
      }
    }
  }

}
