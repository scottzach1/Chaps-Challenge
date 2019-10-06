package nz.ac.vuw.ecs.swen225.a3.tests;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class backendTest {

  /**
   * The default chaps challenge should start at level 1;
   * which is also the default for a new board.
   */
  @Test
   void loadDefault(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    Board board = new Board();
    assertEquals(chapsChallenge.getBoard().toString(), board.toString());
  }

  /**
   * Loads a new level into the game
   */
  @Test
  void overrideLevel(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);
    assertEquals(chapsChallenge.getBoard().toString(), level);
  }

  /**
   * Creates an invalid board with 3 Chaps
   * Should throw an error.
   */
   @Test
   void overrideInvalid(){
     ChapsChallenge chapsChallenge = new ChapsChallenge();
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
       chapsChallenge.setLevel(level);
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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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
      chapsChallenge.setLevel(level);
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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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
      chapsChallenge.setLevel(level);
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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

      chapsChallenge.setLevel(level);

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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);
    chapsChallenge.move(Tile.Direction.Up);

    assertTrue(    chapsChallenge.getPlayer().getInventory().contains("Blue Key"));
  }

  /**
   * Checks that chap can open a door if he has the right key.
   */
  @Test
  void chapOpenDoor(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);
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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);

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
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);
    chapsChallenge.move(Tile.Direction.Up);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Right);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start, end);
  }

  /**
   * Checks that chap can exit with if there are no treasures.
   */
  @Test
  void chapExit(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);
  }

  /**
   * Checks that chap can exit with if there are no treasures.
   */
  @Test
  void chapExit3Treasures(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
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

    chapsChallenge.setLevel(level);

    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);
    chapsChallenge.move(Tile.Direction.Up);

    assertEquals(chapsChallenge.getPlayer().getTreasures(), 3);

    Tile start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    Tile end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);

    start = chapsChallenge.getPlayer().getLocation();
    chapsChallenge.move(Tile.Direction.Up);
    end = chapsChallenge.getPlayer().getLocation();
    assertEquals(start.getUp(), end);
  }

  /**
   * Restarts the game.
   */
  @Test
  void restartGame(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.restartGame();
    //todo assert something here
  }

  /**
   * Saves the game.
   */
  @Test
  void saveGame(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.saveGame();
    //todo assert something here
  }

  /**
   * Loads the game
   */
  @Test
  void loadGame(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.loadGame();
    //todo assert something here
  }


  /**
   * Checks timeout feature
   */
  @Test
  void checkTimeout(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.timeOut();
    //todo assert something here
  }

  /**
   * Goes to the previous level
   */
  @Test
  void prevLevel(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.previousLevel();
    //todo assert something here
  }

  /**
   * Pauses the game.
   * Un-pauses the game.
   * Timer should be the same.
   */
  @Test
  void pauseGame(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    assertFalse(chapsChallenge.isGamePaused());
    //todo Front end fix this
  }

  /**
   * Check going to the next level works.
   */
  @Test
  void nextLevel(){
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    chapsChallenge.getBoard().setNextLevel();
    //todo not finished, currently buggy
  }
}
