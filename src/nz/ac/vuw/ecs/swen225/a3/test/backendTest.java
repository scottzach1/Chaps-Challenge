package nz.ac.vuw.ecs.swen225.a3.test;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.GameNotFoundException;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonReadWrite;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class backendTest {

  private ChapsChallenge chapsChallenge = new ChapsChallenge();

  /**
   * The default chaps challenge should start at level 1;
   * which is also the default for a new board.
   */
  @Test
   void loadDefault(){
    chapsChallenge = new ChapsChallenge();
    Board board = new Board();
    assertEquals(chapsChallenge.getBoard().toString(), board.toString());
  }

  /**
   * Loads a new level into the game
   */
  @Test
  void overrideLevel(){
    String level =
        "ExitLock|KBlue|DGreen|_|_|_|_|_|_|_|?|T|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|ExitLock|C|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|T|T|T|T|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|?|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|#|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|#|_|_|#|_|_|_|_|_|_|_|Exit|_|_|_|_|"
        + "_|_|_|_|#|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
        + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    chapsChallenge.setCustomLevel(level);
    assertEquals(chapsChallenge.getBoard().toString(), level);
  }

  /**
   * Creates an invalid board with 3 Chaps
   * Should throw an error.
   */
   @Test
   void overrideInvalid(){
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

     boolean failed=false;
     try {
       chapsChallenge.setCustomLevel(level);
     }catch (Error e) {
       failed=true;
     }
     assertTrue(failed);
  }

  /**
   * Creates an invalid board with no chaps
   * Should throw an error.
   */
  @Test
  void overrideInvalid2(){
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

    boolean failed=false;
    try {
      chapsChallenge.setCustomLevel(level);
    }catch (Error e) {
      failed=true;
    }
    assertTrue(failed);
  }

  /**
   * Creates an invalid board with invalid characters
   * Should throw an error.
   */
  @Test
  void overrideInvalid3(){
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

    boolean failed=false;
    try {
      chapsChallenge.setCustomLevel(level);
    }catch (Error e) {
      failed=true;
    }
    assertTrue(failed);
  }
  /**
   * Checks that chap can move correctly.
   */
  @Test
  void chapMove(){
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

      chapsChallenge.setCustomLevel(level);

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
  void chapPickUpKey(){
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

    chapsChallenge.setCustomLevel(level);
    chapsChallenge.move(Tile.Direction.Up);
    assertTrue(   chapsChallenge.getPlayer().getInventory().contains("key_blue"));
  }

  /**
   * Checks that chap can open a door if he has the right key.
   */
  @Test
  void chapOpenDoor(){
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

    chapsChallenge.setCustomLevel(level);
    chapsChallenge.move(Tile.Direction.Up);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Right);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getRight(), end);
  }
  /**
   * Checks that chap cannot open a door if he does not have any keys.
   *
   */
  @Test
  void chapOpenDoorInvalid(){
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

    chapsChallenge.setCustomLevel(level);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start, end);
  }

  /**
   * Checks that chap cannot open a door if he does not have the right coloured key.
   *
   */
  @Test
  void chapOpenDoorInvalid2(){
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

    chapsChallenge.setCustomLevel(level);
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
  void chapExit(){
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

    chapsChallenge.setCustomLevel(level);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    int current = chapsChallenge.getBoard().getCurrentLevel();

    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(current+1, chapsChallenge.getBoard().getCurrentLevel());

  }

  /**
   * Checks that chap can exit with if there are no treasures.
   */
  @Test
  void chapExit3Treasures(){
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

    chapsChallenge.setCustomLevel(level);

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

    assertEquals(current+1, chapsChallenge.getBoard().getCurrentLevel());

  }


  /**
   * Tests that you cannot stand on water without flippers
   */
  @Test
  void invalidWater() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|W|W|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|W|W|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|W|W|_|_|_|_|_|_|_|_|_|_|"
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

    chapsChallenge.setCustomLevel(level);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(start, chapsChallenge.getPlayer().getLocation());
  }


  /**
   * Tests that you can stand on water with flippers
   */
  @Test
  void validWater() {
    String level =
        "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|W|W|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|W|W|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|W|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|F|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|C|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"
            + "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

    chapsChallenge.setCustomLevel(level);

    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(start.getUp(), chapsChallenge.getPlayer().getLocation());
  }

  /**
   * Restarts the game.
   */
  @Test
  void restartGame(){
    chapsChallenge.restartGame();
    assertEquals(chapsChallenge.getBoard().getCurrentLevel(),0);
  }

  /**
   * Saves the game.
   * Moves.
   * Loads game.
   * Position should be the position before moving.
   */
  @Test
  void saveAndLoadGame(){

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

    chapsChallenge.setCustomLevel(level);

    JsonReadWrite.saveGameState(chapsChallenge, "saveTest.txt");
    Tile tile = chapsChallenge.getPlayer().getLocation();

    chapsChallenge.move(Tile.Direction.Left);
    chapsChallenge.move(Tile.Direction.Left);

    try {
      JsonReadWrite.loadGameState("saveTest.txt", chapsChallenge);
    } catch (GameNotFoundException e) {
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
      JsonReadWrite.loadGameState("failed.txt", chapsChallenge);
    } catch (GameNotFoundException e) {
      System.out.println(e.getMessage());
      failed = true;
    }
    assertTrue(failed);
  }

  /**
   * Goes to the previous level, invalid
   */
  @Test
  void prevLevel(){
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.previousLevel();
    assertEquals(current, 0);
  }

  /**
   * Goes to the previous level, valid
   */
  @Test
  void prevLevel2(){
    chapsChallenge.getBoard().setCurrentLevel(2);
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.previousLevel();
    assertEquals(current-1, chapsChallenge.getBoard().getCurrentLevel());
  }

  /**
   * Pauses the game.
   * Un-pauses the game.
   */
  @Test
  void pauseGame(){
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
  void nextLevel(){
    int current = chapsChallenge.getBoard().getCurrentLevel();
    chapsChallenge.getBoard().setNextLevel();
    int updated = chapsChallenge.getBoard().getCurrentLevel();

    assertEquals(current+1, updated);
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


}