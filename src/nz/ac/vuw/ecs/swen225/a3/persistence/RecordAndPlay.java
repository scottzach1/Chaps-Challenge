package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

import javax.json.*;
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
  private static boolean isRunning;

  /**
   * Create new recording saved in file with name s
   * @param s Name of save
   */
  public static void newSave(ChapsChallenge g, String s){
    saveName = s;
    isRunning = true;
    moves.clear();
    gameState = JsonReadWrite.getGameState(g);
  }

  /**
   * Add action to action history
   */
  public static void addAction(Tile.Direction direction){
    // Check a recording is active
    if(isRunning){
      moves.add(direction);
    }
  }

  /**
   * Save action history to file
   */
  public static void saveGame(){
    if(isRunning){
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

      isRunning = false;
    }
  }

  public static boolean loadRecording(String fileName){
    JsonObject object;
    try {
      // Load game state
      JsonReadWrite.loadGameState(fileName);

      // Load moves into array

      moves.clear();

      try {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        JsonReader jReader = Json.createReader(new StringReader(reader.readLine()));
        object = jReader.readObject();
      } catch (IOException e) {
        throw new GameNotFoundException();
      }
    }
    catch(GameNotFoundException e){
      throw new Error(e.getMessage());
    }

    JsonArray movesJson = object.getJsonArray("moves");

    // Parse moves into array
    for (JsonString j : movesJson.getValuesAs(JsonString.class)) {
      switch (j.toString()){
        case "\"Left\"":
          moves.add(Tile.Direction.Left);
          break;
        case "\"Right\"":
          moves.add(Tile.Direction.Right);
          break;
        case "\"Up\"":
          moves.add(Tile.Direction.Up);
          break;
        case "\"Down\"":
          moves.add(Tile.Direction.Down);
          break;
      }
    }

    return true;
  }

  /**
   * Get if recording is active
   * @return isRunning
   */
  public static boolean getIsRunning(){
    return isRunning;
  }

}
