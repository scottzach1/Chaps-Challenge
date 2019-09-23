package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;

import javax.json.*;
import java.io.*;


/**.
 * @author Group.
 *
 */
public class JsonReadWrite {

  /**
   * Json dump game state (TimeLeft, Board state and player state) to file "save.txt".
   * @param game Instance of Chaps Challenge
   */
  public static void saveGameState(ChapsChallenge game){
    String jsonGame = "";
    String jsonBoard = "";
    String jsonPlayer = "";

    // Dump game info
    JsonObjectBuilder builder =  Json.createObjectBuilder()
        .add("timeLeft",game.getTimeLeft());

    // Compose game section
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonGame = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse game");
    }

    // Json dump board
    Board board = game.getBoard();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for(Tiles t : board.getAllTiles()){
      arrayBuilder.add(t.getJson());
    }
    builder =  Json.createObjectBuilder()
        .add("boardSize",board.getBoardSize())
        .add("allTiles",arrayBuilder);

    // Compose board section
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonBoard = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse Board");
    }

    // Json Dump Player
    Player player = game.getPlayer();
    arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for(String i : player.getInventory()){
      arrayBuilder.add(i);
    }
    builder = Json.createObjectBuilder()
      .add("location",player.getLocation().getJson())
      .add("inventory",arrayBuilder)
      .add("treasures",player.getTreasures());

    // Compose player section
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonPlayer = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse Player");
    }

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("saveGame.txt"));
      writer.write(jsonGame);
      writer.close();
      writer = new BufferedWriter(new FileWriter("saveBoard.txt"));
      writer.write(jsonBoard);
      writer.close();
      writer = new BufferedWriter(new FileWriter("savePlayer.txt"));
      writer.write(jsonPlayer);
      writer.close();
    }
    catch(IOException e){}
  }

  public ChapsChallenge loadGameState(String saveGame, String saveBoard, String savePlayer){

    return null;
  }

}
