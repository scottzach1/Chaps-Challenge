package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.*;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


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


    // Json dump board
    Board board = game.getBoard();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for(Tile t : board.getAllTiles()){
      arrayBuilder.add(t.getJson());
    }
    JsonObjectBuilder builder =  Json.createObjectBuilder()
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

    // Dump game info
    builder =  Json.createObjectBuilder()
        .add("timeLeft",game.getTimeLeft())
        .add("board",jsonBoard)
        .add("player",jsonPlayer);

    // Compose game section
    try(Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonGame = writer.toString();
    }
    catch(IOException e){
      throw new Error("Failed to parse game");
    }

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("saveGame.txt"));
      writer.write(jsonGame);
      writer.close();
    }
    catch(IOException e){}
  }

  public static ChapsChallenge loadGameState(String saveGame){
    JsonObject game = null;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(saveGame));
      JsonReader jReader = Json.createReader(new StringReader(reader.readLine()));
      game = jReader.readObject();
    }catch(IOException e){}

    int timeLeft = game.getInt("timeLeft");

    // Parse board
    JsonReader boardJsonReader = Json.createReader(new StringReader(game.getString("board")));
    JsonObject board = boardJsonReader.readObject();

    int boardSize = board.getInt("boardSize");

    JsonArray allTilesJson = board.getJsonArray("allTiles");

    List<Tile> allTiles = new ArrayList<>();

    for (JsonString j : allTilesJson.getValuesAs(JsonString.class)) {
      allTiles.add(createTileFromJson(j.getString()));
    }

    Board b = new Board();
    for(Tile t : allTiles){
      b.setTile(t.getRow(),t.getCol(),t);
    }

    b.setBoardSize(boardSize);
    b.setAllTiles(allTiles);

    // Parse player
    JsonReader playerJsonReader = Json.createReader(new StringReader(game.getString("player")));
    JsonObject player = playerJsonReader.readObject();

    int treasures = player.getInt("treasures");
    JsonArray inventoryJson = player.getJsonArray("inventory");

    List<String> inventory = new ArrayList<>();

    for (JsonString j : inventoryJson.getValuesAs(JsonString.class)) {
      inventory.add(j.getString());
    }

    Tile location = createTileFromJson(player.getString("location"));
    location = b.getTile(location.getRow(),location.getCol());

    Player p = new Player(location);
    p.setInventory(inventory);
    p.setTreasures(treasures);

    b.setupAdjacency();


    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.setBoard(b);
    chapsChallenge.setTimeLeft(timeLeft);
    chapsChallenge.setPlayer(p);

    return chapsChallenge;
  }

  public static Tile createTileFromJson(String tile){
    JsonReader reader = Json.createReader(new StringReader(tile));
    JsonObject tileObject = reader.readObject();
    String type = tileObject.getString("type");
    switch (type){
      case "Free":
        return new Free().setTileFromJson(Json.createReader(new StringReader(tile)));
      case "Treasure":
        return new Treasure().setTileFromJson(Json.createReader(new StringReader(tile)));
      case "Exit":
        return new Exit().setTileFromJson(Json.createReader(new StringReader(tile)));
      case "ExitLock":
        return new ExitLock().setTileFromJson(Json.createReader(new StringReader(tile)));
      case "InfoField":
        return new InfoField("").setTileFromJson(Json.createReader(new StringReader(tile)));
      case "Key":
        return new Key("").setTileFromJson(Json.createReader(new StringReader(tile)));
      case "LockedDoor":
        return new LockedDoor("").setTileFromJson(Json.createReader(new StringReader(tile)));
      default:
        return new Wall().setTileFromJson(Json.createReader(new StringReader(tile)));
    }
  }

  public static void main(String[] args) {
    ChapsChallenge game = loadGameState("saveGame.txt");
  }

}
