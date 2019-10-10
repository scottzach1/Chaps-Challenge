package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeAll;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile.Direction;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.renderer.Gui;


/**
 * Tests keyboard inputs effects on the game.
 * @author Zac Scott.
 */
class KeyboardTest {

  @BeforeAll
  public static void setup(){
    BackendTest.testing = true;
  }

  /**
   * Test movement to left.
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testMoveLeft() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    // Get initial tile
    final Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input left key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_LEFT));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getLeft(), newTile);
  }

  /**
   * Test movement to right.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testMoveRight() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    // Get initial tile
    final Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input right key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_RIGHT));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getRight(), newTile);
  }

  /**
   * Test movement to down.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testMoveDown() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();
    // THIS LINE IS TO AVOID THE INFO TILE POPUP!
    application.move(Direction.Left);

    // Get initial tile
    final Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input up key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_DOWN));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getDown(), newTile);
  }

  /**
   * Test movement to up.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testMoveUp() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    // Get initial tile
    final Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input up key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_UP));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getUp(), newTile);
  }

  /**
   * Test game pauses with space.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testPauseGame() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    assertFalse(application.isGamePaused());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input space key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertTrue(application.isGamePaused());
  }

  /**
   * Test game pauses and resumes with space.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testPauseResumeGameSpace() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    assertFalse(application.isGamePaused());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input space key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertTrue(application.isGamePaused());

    // Input space key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertFalse(application.isGamePaused());
  }

  /**
   * Test game pauses with space and resumes with escape.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testPauseResumeGameEscape() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    assertFalse(application.isGamePaused());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input Space key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Release Space key
    gui.keyReleased(
        new KeyEvent(gui, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertTrue(application.isGamePaused());

    // Input escape key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_ESCAPE));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertFalse(application.isGamePaused());
  }

  /**
   * Test game pauses with space and resumes with ctrl r.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testPauseResumeGameCtrlR() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();

    assertFalse(application.isGamePaused());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input Space key
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Release Space
    gui.keyReleased(
        new KeyEvent(gui, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_SPACE));
    application.update();

    assertTrue(application.isGamePaused());

    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input ctrl + R keys
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_CONTROL));
    application.update();
    while (gui.isBusy()) {
      Thread.sleep(100);
    }
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_R));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertFalse(application.isGamePaused());
  }

  /**
   * Test previous level.
   *
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testRestartGame() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    final Gui gui = application.getGui();

    assertEquals(0, application.getLevel());
    application.nextLevel();
    assertEquals(1, application.getLevel());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input ctrl + 1 keys
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_CONTROL));
    application.update();
    while (gui.isBusy()) {
      Thread.sleep(100);
    }
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_1));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    assertEquals(0, application.getLevel());
  }

  /**
   * Checks GUI restarts level on ctrl + p.
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testRestartLevel() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();
    Board board = application.getBoard();

    // Move player and check new location.
    Tile start = application.getBoard().getPlayerLocation();
    application.move(Tile.Direction.Left);
    assertNotEquals(start, board.getPlayerLocation());

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Input ctrl + P keys
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_CONTROL));
    application.update();
    while (gui.isBusy()) {
      Thread.sleep(100);
    }
    gui.keyPressed(
        new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
            KeyEvent.VK_P));
    application.update();

    // Wait while gui is busy.
    while (gui.isBusy()) {
      Thread.sleep(100);
    }

    // Check player reset position.
    Tile resetTile = board.getPlayerLocation();
    assertEquals(resetTile.getCol(), start.getCol());
    assertEquals(resetTile.getRow(), start.getRow());
  }

  /**
   * Tests Gui doesn't respond to KeyTyped input.
   * @throws InterruptedException from sleep.
   */
  @SuppressWarnings("deprecation")
  @Test
  void testKeyTyped() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    Gui gui = application.getGui();
    Board board = application.getBoard();
    final String boardState = board.toString();

    for (int keyEvent=KeyEvent.VK_UNDEFINED; keyEvent<=KeyEvent.VK_Z; ++keyEvent) {
      while (gui.isBusy()) {
        Thread.sleep(100);
      }
      // Input ctrl + P keys
      try {
        gui.keyTyped(
            new KeyEvent(gui, KeyEvent.KEY_TYPED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED,
                keyEvent));
      } catch (IllegalArgumentException e) {
        // Try next key.
      }
    }
    // Check board state still same.
    assertEquals(boardState, board.toString());
  }
}
