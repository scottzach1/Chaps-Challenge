package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Board.MultiplePlayersFoundException;
import nz.ac.vuw.ecs.swen225.a3.maze.Board.PlayerNotFoundException;
import nz.ac.vuw.ecs.swen225.a3.maze.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.ExitLock;
import nz.ac.vuw.ecs.swen225.a3.maze.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.InfoField;
import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
import nz.ac.vuw.ecs.swen225.a3.maze.Wall;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Checks the functionality of the Maze and Application packages.
 *
 * @author Luisa Kristen 300444458
 */
public class BackendTest {

  public static boolean testing = false;


  @BeforeAll
  static void setup() {
    testing = true;
  }

  private ChapsChallenge chapsChallenge = new ChapsChallenge();

  private List<String> allLevels = new ArrayList<>();


  /**
   * Creates an invalid board with 3 Chaps Should throw an error.
   */
  @Test
  void overrideInvalid() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|C|C|C|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    boolean failed = false;
    try {
      setCustomLevel(level, chapsChallenge.getBoard());
    } catch (Error e) {
      failed = true;
    }
    assertTrue(failed);
  }

  /**
   * Creates an invalid board with no chaps Should throw an error.
   */
  @Test
  void overrideInvalid2() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    boolean failed = false;
    try {
      setCustomLevel(level, chapsChallenge.getBoard());
    } catch (Error e) {
      failed = true;
    }
    assertTrue(failed);
  }

  /**
   * Creates an invalid board with invalid characters Should throw an error.
   */
  @Test
  void overrideInvalid3() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|{|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|>|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|W|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|Q|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    boolean failed = false;
    try {
      setCustomLevel(level, chapsChallenge.getBoard());
    } catch (Error e) {
      failed = true;
    }
    assertTrue(failed);
  }

  /**
   * Checks that chap can move correctly.
   */
  @Test
  void chapMove() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Down);
    end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getDown(), end);

    start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Left);
    end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getLeft(), end);

    start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Right);
    end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getRight(), end);
  }

  /**
   * Checks that chap can pick up keys.
   */
  @Test
  void chapPickUpKey() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|KBlue|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());
    chapsChallenge.move(Tile.Direction.Up);
    assertTrue(chapsChallenge.getPlayer().getInventory().contains("key_blue"));
  }

  /**
   * Checks that chap can open a door if he has the right key.
   */
  @Test
  void chapOpenDoor() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|KBlue|DBlue|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());
    chapsChallenge.move(Tile.Direction.Up);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Right);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getRight(), end);
  }

  /**
   * Checks that chap cannot open a door if he does not have any keys.
   */
  @Test
  void chapOpenDoorInvalid() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|DGreen|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start, end);
  }

  /**
   * Checks that chap cannot open a door if he does not have the right coloured key.
   */
  @Test
  void chapOpenDoorInvalid2() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|KBlue|DGreen|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());
    chapsChallenge.move(Tile.Direction.Up);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Right);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start, end);
  }

  /**
   * Checks that chap can exit if there are no treasures.
   */
  @Test
  void chapExit() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|Exit|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|ExitLock|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    int current = chapsChallenge.getBoard().getCurrentLevel();

    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(current + 1, chapsChallenge.getBoard().getCurrentLevel());

  }

  /**
   * Checks that chap can exit with if there are no treasures.
   */
  @Test
  void chapExit3Treasures() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|Exit|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|ExitLock|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());

    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(chapsChallenge.getPlayer().getTreasures(), 3);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(current + 1, chapsChallenge.getBoard().getCurrentLevel());

  }

  /**
   * Restarts the game.
   */
  @Test
  void restartGame() {
    chapsChallenge.restartGame();
    assertEquals(chapsChallenge.getBoard().getCurrentLevel(), 0);
  }


  /**
   * Saves the game. Moves. Loads game. Position should be the position before moving.
   */

  @Test
  void saveAndLoadGame() {

    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|W|W|_|?|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|F|_|?|_|_|Exit|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|T|T|_|_|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|ExitLock|_|_|_|C|_|_|_|_|#|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|DBlue|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|KBlue|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    setCustomLevel(level, chapsChallenge.getBoard());
    JsonReadWrite.saveGameState(chapsChallenge, "saveTest.txt");
    Tile tile = chapsChallenge.getPlayer().getLocation();

    chapsChallenge.move(Tile.Direction.Left);

    try {
      JsonReadWrite.loadGameStateFromFile("saveTest.txt", chapsChallenge);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(tile.getCol(), chapsChallenge.getPlayer().getLocation().getCol());
    assertEquals(tile.getRow(), chapsChallenge.getPlayer().getLocation().getRow());
  }

  /**
   * Tries to load an invalid game.
   */
  @Test
  void loadGameInvalid() {
    boolean failed = false;
    try {
      JsonReadWrite.loadGameStateFromFile("failed.txt", chapsChallenge);
    } catch (IOException e) {
      failed = true;
    }
    assertTrue(failed);
  }



  /**
   * Goes to the previous level, valid.
   */
  @Test
  void prevLevel() {
    chapsChallenge.nextLevel();
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.previousLevel();
    assertEquals(current - 1, chapsChallenge.getBoard().getCurrentLevel());
  }

  /**
   * Pauses the game. Un-pauses the game.
   */
  @Test
  void pauseGame() {
    assertFalse(chapsChallenge.isGamePaused());
    chapsChallenge.pauseGame();

    assertTrue(chapsChallenge.isGamePaused());
    chapsChallenge.resumeGame();

    assertFalse(chapsChallenge.isGamePaused());
  }

  /**
   * Check going to the next level works.
   */
  @Test
  void nextLevel() {
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.nextLevel();
    int updated = chapsChallenge.getBoard().getCurrentLevel();

    assertEquals(current + 1, updated);
  }

  @Test
  void setLevel() {
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.setLevel(chapsChallenge.getBoard().getFinalLevel());
    int updated = chapsChallenge.getBoard().getCurrentLevel();

    assertNotEquals(current, updated);
  }

  @Test
  void nextLevelOnFinalLevel() {
    chapsChallenge.setLevel(chapsChallenge.getBoard().getFinalLevel());
    int current = chapsChallenge.getBoard().getCurrentLevel();

    chapsChallenge.nextLevel();
    int updated = chapsChallenge.getBoard().getCurrentLevel();

    assertEquals(current, updated);
  }


  /**
   * Parse board string into tile array.
   *
   * @param level String representation of board
   */
  private void parseTestBoards(String level, Board board)
      throws MultiplePlayersFoundException, PlayerNotFoundException {
    boolean foundChap = false;
    String[] values = level.split("\\|");
    int index = 0;
    for (String v : values) {
      switch (v) {
        case "W":
        case "F":
        case "_":
          board.setTile(index / 20, index % 20, new Free());
          break;
        case "#":
          board.setTile(index / 20, index % 20, new Wall());
          break;
        case "T":
          board.setTile(index / 20, index % 20, new Treasure());
          break;
        case "?":
          board.setTile(index / 20, index % 20, new InfoField("Test"));
          break;
        case "Exit":
          board.setTile(index / 20, index % 20, new Exit());
          break;
        case "ExitLock":
          board.setTile(index / 20, index % 20, new ExitLock());
          break;
        case "C":
          if (foundChap) {
            throw new MultiplePlayersFoundException();
          }
          foundChap = true;
          Free tile = new Free();

          // FIXME: This might not be the best place.
          tile.imageUrl = "chap_front.png";

          board.setTile(index / 20, index % 20, tile);
          break;
        default:
          // Must be a colored key or door
          String itemType = v.substring(0, 1);

          // Check for invalid token
          if (!(itemType.equals("K") || itemType.equals("D"))) {
            try {
              throw new Exception("Failed to parse");
            } catch (Exception e) {
              System.out.println("Failed to parse");
              return;
            }
          }

          String colour = v.substring(1).toLowerCase();

          // Create colored key or door
          if (itemType.equals("K")) {
            board.setTile(index / 20, index % 20, new Key(colour));
          } else {
            board.setTile(index / 20, index % 20, new LockedDoor(colour));
          }
      }
      index++;
    }
    if (!foundChap) {
      throw new PlayerNotFoundException();
    }
    Tile[][] tiles = board.getTiles();
    ArrayList<Tile> allTiles = new ArrayList<>();
    for (int row = 0; row < board.getBoardSize(); row++) {
      allTiles.addAll(Arrays.asList(tiles[row]).subList(0, board.getBoardSize()));
    }

    // Count number of treasures
    board.setTreasureCount(
        (int) allTiles.stream().filter(p -> p.toString().equals("Treasure")).count());

    // Set all exit locks to require correct number of treasures
    allTiles.stream().filter(p -> p.getType() == Tile.Type.ExitLock)
        .map(c -> (ExitLock) c).forEach(s -> s.setTotalTreasures(board.getTreasureCount()));

    board.setAllTiles(allTiles);
    board.setTiles(tiles);
  }

  /**
   * Sets the board to a custom level.
   *
   * @param level to set.
   */
  void setCustomLevel(String level, Board board) {
    try {
      if (!allLevels.contains(level)) {
        allLevels.add(0, level);
      }
      parseTestBoards(level, board);
    } catch (MultiplePlayersFoundException m) {
      System.out.println(m.getMessage());
      throw new Error(m.getMessage());
    } catch (PlayerNotFoundException pnf) {
      System.out.println(pnf.getMessage());
      throw new Error(pnf.getMessage());
    } catch (Exception e) {
      throw new Error(e.getMessage());
    }
    board.setupAdjacency();
    chapsChallenge.resetLogistics();
  }

}
