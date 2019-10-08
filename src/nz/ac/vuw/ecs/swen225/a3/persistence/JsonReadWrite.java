package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.ExitLock;
import nz.ac.vuw.ecs.swen225.a3.maze.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.InfoField;
import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.Mob;
import nz.ac.vuw.ecs.swen225.a3.maze.PassivePerry;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
import nz.ac.vuw.ecs.swen225.a3.maze.Wall;


/**
 * Saves and loads the current game state to a JSON file.
 *
 * @author Zac Durant 300449785
 */
public class JsonReadWrite {

  /**
   * Json dump game state (TimeLeft, Board state and player state) to file "save.txt".
   *
   * @param game Instance of Chaps Challenge.
   */
  public static void saveGameState(ChapsChallenge game, String name) {
    String jsonGame = getGameState(game);
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(name));
      writer.write(jsonGame);
      writer.close();
    } catch (IOException e) {
      System.out.println("Error saving game" + e);
    }
  }

  /**
   * Get game state to write to file.
   *
   * @param game Game object.
   * @return Json string.
   */
  public static String getGameState(ChapsChallenge game) {
    String jsonGame = "";
    String jsonBoard = "";
    String jsonPlayer = "";
    JsonString jsonMobs;

    // Json dump board
    Board board = game.getBoard();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for (Tile t : board.getAllTiles()) {
      arrayBuilder.add(t.getJson());
    }
    JsonObjectBuilder builder = Json.createObjectBuilder()
        .add("boardSize", board.getBoardSize())
        .add("allTiles", arrayBuilder);

    // Compose board section
    try (Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonBoard = writer.toString();
    } catch (IOException e) {
      throw new Error("Failed to parse Board");
    }

    // Json Dump Player
    Player player = game.getPlayer();
    arrayBuilder = Json.createArrayBuilder();

    // Array of tiles
    for (String i : player.getInventory()) {
      arrayBuilder.add(i);
    }
    builder = Json.createObjectBuilder()
        .add("location", player.getLocation().getJson())
        .add("inventory", arrayBuilder)
        .add("treasures", player.getTreasures());

    // Compose player section
    try (Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonPlayer = writer.toString();
    } catch (IOException e) {
      throw new Error("Failed to parse Player");
    }

    // Json dump mobs
    arrayBuilder = Json.createArrayBuilder();

    // Array of mobs
    for (Mob i : game.getMobManager().getMobs()) {
      arrayBuilder.add(i.getJson());
    }

    // Dump game info
    builder = Json.createObjectBuilder()
        .add("timeLeft", game.getTimeLeft())
        .add("board", jsonBoard)
        .add("player", jsonPlayer)
        .add("mobs", arrayBuilder);

    // Compose game section
    try (Writer writer = new StringWriter()) {
      Json.createWriter(writer).write(builder.build());
      jsonGame = writer.toString();
    } catch (IOException e) {
      throw new Error("Failed to parse game");
    }
    return jsonGame;
  }

  /**
   * Load game state from file.
   *
   * @param saveGame Name of file.
   * @param g Game object.
   * @return Updated game Object.
   * @throws GameNotFoundException Thrown when file not found.
   */
  public static ChapsChallenge loadGameState(String saveGame, ChapsChallenge g)
      throws GameNotFoundException {
    JsonObject game;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(saveGame));
      JsonReader jsonReader = Json.createReader(new StringReader(reader.readLine()));
      game = jsonReader.readObject();
    } catch (IOException e) {
      throw new GameNotFoundException();
    }

    if (game.containsKey("game")) {
      JsonReader gameJsonReader = Json.createReader(new StringReader(game.getString("game")));
      game = gameJsonReader.readObject();
    }

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
    for (Tile t : allTiles) {
      b.setTile(t.getRow(), t.getCol(), t);
    }

    b.setBoardSize(boardSize);
    b.setAllTiles(allTiles);

    // Parse player
    JsonReader playerJsonReader = Json.createReader(new StringReader(game.getString("player")));
    JsonObject player = playerJsonReader.readObject();

    JsonArray inventoryJson = player.getJsonArray("inventory");

    List<String> inventory = new ArrayList<>();

    for (JsonString j : inventoryJson.getValuesAs(JsonString.class)) {
      inventory.add(j.getString());
    }

    Tile location = createTileFromJson(player.getString("location"));
    location = b.getTile(location.getRow(), location.getCol());

    int treasures = player.getInt("treasures");

    Player p = new Player(location);
    p.setInventory(inventory);
    p.setTreasures(treasures);

    // Parse mobs
    JsonArray mobsArray = game.getJsonArray("mobs");

    Set<Mob> mobs = new HashSet<>();

    for (JsonString j : mobsArray.getValuesAs(JsonString.class)) {
      mobs.add(createMobFromJson(j.getString()));
    }

    b.setupAdjacency();

    // Ensure mobs are using correct tiles from board
    for (Mob m : mobs) {
      m.setHost(b.getTile(m.getHost().getRow(), m.getHost().getCol()));
    }
    int timeLeft = game.getInt("timeLeft");

    // Set game properties
    g.setBoard(b);
    g.setTimeLeft(timeLeft);
    g.setPlayer(p);
    g.getMobManager().setMobs(mobs);

    return g;
  }

  /**
   * Create mob object from JSON description.
   *
   * @param string JSON representation.
   * @return Mob object.
   */
  private static Mob createMobFromJson(String string) {
    JsonReader reader = Json.createReader(new StringReader(string));
    JsonObject mobObject = reader.readObject();
    String name = mobObject.getString("mobName");
    Mob m;
    switch (name) {
      case "Passive Perry":
        m = new PassivePerry();
        m.setMobFromJson(Json.createReader(new StringReader(string)));
        return m;
      default:
        m = new PassivePerry();
        m.setMobFromJson(Json.createReader(new StringReader(string)));
        return m;
    }
  }

  /**
   * Create tile object from JSON description.
   *
   * @param tile JSON representation.
   * @return Tile object.
   */
  public static Tile createTileFromJson(String tile) {
    JsonReader reader = Json.createReader(new StringReader(tile));
    JsonObject tileObject = reader.readObject();
    String type = tileObject.getString("type");

    // Use reflection to find correct class
    // Done to allow dynamic drop in of new tile types

    try {
      Class c = Class.forName("nz.ac.vuw.ecs.swen225.a3.maze." + type);
      Object o = c.getDeclaredConstructor().newInstance();
      Method m = c.getDeclaredMethod("setTileFromJson", JsonReader.class);
      return (Tile) m.invoke(o, Json.createReader(new StringReader(tile)));
    } catch (ClassNotFoundException e) {
      for (Class classes : LevelManager.classSet) {
        if (classes.getName().equals(type)) {
          try {
            Object o = classes.getDeclaredConstructor().newInstance();
            Method m = classes.getDeclaredMethod("setTileFromJson", JsonReader.class);
            return (Tile) m.invoke(o, Json.createReader(new StringReader(tile)));
          } catch (InstantiationException ex) {
            ex.printStackTrace();
          } catch (InvocationTargetException ex) {
            ex.printStackTrace();
          } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
          } catch (IllegalAccessException ex) {
            ex.printStackTrace();
          }
        }
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return new Wall().setTileFromJson(Json.createReader(new StringReader(tile)));
  }
}

