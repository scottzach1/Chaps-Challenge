package nz.ac.vuw.ecs.swen225.a3.tests;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.renderer.GUI;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class keyboardTest {

  /**
   * Test movement to left.
   * @throws InterruptedException from sleep.
   */
  @Test
  void testMoveLeft() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

    // Get initial tile
    Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Input key
    gui.keyPressed(new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_LEFT));
    application.update();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getLeft(), newTile);
  }

  /**
   * Test movement to right.
   * @throws InterruptedException from sleep.
   */
  @Test
  void testMoveRight() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

    // Get initial tile
    Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Input key
    gui.keyPressed(new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_RIGHT));
    application.update();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getRight(), newTile);
  }

  /**
   * Test movement to down.
   * @throws InterruptedException from sleep.
   */
  @Test
  void testMoveDown() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

    // Get initial tile
    Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Input key
    gui.keyPressed(new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_DOWN));
    application.update();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getDown(), newTile);
  }

  /**
   * Test movement to up.
   * @throws InterruptedException from sleep.
   */
  @Test
  void testMoveUp() throws InterruptedException {
    // Make game
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

    // Get initial tile
    Tile oldTile = application.getBoard().getPlayerLocation();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Input key
    gui.keyPressed(new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_UP));
    application.update();

    // Wait till game has resized.
    while (gui.isResizing()) Thread.sleep(100);

    // Get new tile
    Tile newTile = application.getBoard().getPlayerLocation();

    // Compare.
    assertEquals(oldTile.getUp(), newTile);
  }
}
