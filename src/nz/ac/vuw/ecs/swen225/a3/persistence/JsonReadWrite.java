package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tiles;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


/**.
 * @author Group.
 *
 */
public class JsonReadWrite {

  public static void saveGameState(ChapsChallenge game){
    Board board = game.getBoard();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for(Tiles t : board.getAllTiles()){
      arrayBuilder.add(t.getJson());
    }
    JsonObjectBuilder builder =  Json.createObjectBuilder()
        .add("boardSize",board.getBoardSize())
        .add("allTiles",arrayBuilder);

    String jsonString = "";
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonString = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse Board");
    }
  }

}
