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

  @Test
  void testMoveLeft() {
    ChapsChallenge application = new ChapsChallenge();
    GUI gui = application.getGui();

    Board board = application.getBoard();
    Tile oldTile = board.getPlayerLocation();

    System.out.println(oldTile.getRow() + ", " + oldTile.getCol());

    KeyEvent e = new KeyEvent(gui, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_LEFT);
    gui.keyPressed(e);
    gui.updateBoard();
    
    Tile newTile = board.getPlayerLocation();

    System.out.println(newTile.getRow() + ", " + newTile.getCol());

    assertEquals(oldTile.getLeft(), newTile);
  }
}
