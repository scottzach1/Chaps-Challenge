package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;

import javax.json.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


/**.
 * @author Group.
 *
 */
public class JsonReadWrite {

  public static void saveGameState(ChapsChallenge game){
    String jsonString = "";

    // Json dump board
    Board board = game.getBoard();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for(Tiles t : board.getAllTiles()){
      arrayBuilder.add(t.getJson());
    }
    JsonObjectBuilder builder =  Json.createObjectBuilder()
        .add("boardSize",board.getBoardSize())
        .add("allTiles",arrayBuilder);

    // Compose board section
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonString = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse Board");
    }

    // Json Dump Player
    Player player = game.getPlayer();
      builder = Json.createObjectBuilder()
        .add("location",player.getLocation().getJson())
        .add("inventory",arrayBuilder)
        .add("treasures",player.getTreasures());
  }

}
