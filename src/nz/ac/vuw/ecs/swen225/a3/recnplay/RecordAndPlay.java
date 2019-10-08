package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;

/**
 * Class to allow recording and replaying game play. Saves and records each movement done, and
 * allows these to be played back.
 *
 * @author Zac Durant 300449785
 */
public class RecordAndPlay {

  private static String saveName;
  private static List<Tile.Direction> moves = new ArrayList();
  private static String gameState;
  private static boolean isRecording;

  private static long delay = 200;
  private static boolean isRunning;

  /**
   * Set playback delay.
   *
   * @param d time in millis.
   */
  public static void setDelay(long d) {
    delay = d;
  }

  /**
   * Get state of playback.
   *
   * @return Running boolean.
   */
  public static boolean getIsRunning() {
    return isRunning;
  }

  /**
   * Create new recording saved in file with name specified.
   *
   * @param s Name of save.
   */
  public static void newSave(ChapsChallenge g, String s) {
    saveName = s;
    isRecording = true;
    moves.clear();
    gameState = JsonReadWrite.getGameState(g);
  }

  /**
   * Add action to action history.
   *
   * @param direction the direction moved.
   */
  public static void addAction(Tile.Direction direction) {
    // Check a recording is active
    if (isRecording) {
      moves.add(direction);
    }
  }

  /**
   * Save action history to file.
   */
  public static void saveGame() {
    if (isRecording) {
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

      // Array of tiles
      for (Tile.Direction d : moves) {
        arrayBuilder.add(d.toString());
      }
      JsonObjectBuilder builder = Json.createObjectBuilder()
          .add("game", gameState)
          .add("moves", arrayBuilder);

      // Save moves to file
      try (Writer writer = new StringWriter()) {
        Json.createWriter(writer).write(builder.build());
        try {
          BufferedWriter bw = new BufferedWriter(new FileWriter(saveName));
          bw.write(writer.toString());
          bw.close();
        } catch (IOException e) {
          throw new Error("Failed to save moves");
        }
      } catch (IOException e) {
        throw new Error("Failed to save moves");
      }

      isRecording = false;
    }
  }

  /**
   * Load game state and move list from recording file.
   *
   * @param fileName File name.
   * @param game game object to be updated.
   */
  public static void loadRecording(String fileName, ChapsChallenge game) {
    JsonObject object = null;
    try {
      // Load game state
      //JsonReadWrite.loadGameState(fileName, game);

      // Load moves into array

      moves.clear();

      try {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        JsonReader jsonReader = Json.createReader(new StringReader(reader.readLine()));
        object = jsonReader.readObject();
      } catch (IOException e) {
        //TODO: deal withs
      }

      JsonArray movesJson = object != null ? object.getJsonArray("moves") : null;

      if (movesJson != null) {
        // Parse moves into array
        for (JsonString j : movesJson.getValuesAs(JsonString.class)) {
          switch (j.toString()) {
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
            default:
              break;
          }
        }
      }
      if (moves.size() > 0) {
        isRunning = true;
      }
      game.update();
    } catch (Exception e) {
      throw new Error(e.getMessage());
    }

  }

  /**
   * Step replay forward one step.
   *
   * @param game Game object.
   */
  public static void step(ChapsChallenge game) {
    if (moves.size() > 0 && isRunning) {
      game.move(moves.get(0));
      moves.remove(0);
      if (moves.size() == 0) {
        isRunning = false;
      }
      game.update();
    }
  }

  /**
   * Run through move list until replay is complete.
   *
   * @param game Game object.
   */
  public static void run(ChapsChallenge game) {
    Runnable runnable = () -> {
      while (moves.size() > 0 && isRunning) {
        try {
          game.move(moves.get(0));
          moves.remove(0);
          game.update();
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          System.out.println("Running through recording was interupted:" + e);
        }
      }
      isRunning = false;
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }

  /**
   * Get if recording is active.
   *
   * @return isRecording.
   */
  public static boolean getIsRecording() {
    return isRecording;
  }

}
