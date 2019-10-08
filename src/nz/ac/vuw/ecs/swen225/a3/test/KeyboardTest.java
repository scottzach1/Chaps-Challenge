package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;
import org.junit.jupiter.api.Test;


class KeyboardTest {

  /**
   * Test movement to left.
   *
   * @throws InterruptedException from sleep.
   */
  @Test
  void testMoveLeft() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testMoveRight() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testMoveDown() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testMoveUp() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testPauseGame() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testPauseResumeGameSpace() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testPauseResumeGameEscape() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testPauseResumeGameCtrlR() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

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
  @Test
  void testRestartGame() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    final GUI gui = application.getGui();

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

  @Test
  void testRestartLevel() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();
    Board board = application.getBoard();

    // Move player and check new location.
    Tile start = application.getBoard().getPlayerLocation();
    application.move(Tile.Direction.Down);
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


}
