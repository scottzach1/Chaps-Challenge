package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Canvas displays the game maze on the screen.
 */
public class Canvas extends JPanel {

  /**
   * Constructor: Creates and initializes canvas to the correct size.
   */
  static int VIEW_SIZE = 9;
  static int cellSize = 1;

  public Canvas() {
    setPreferredSize(new Dimension(GUI.canvasSize, GUI.canvasSize));
    cellSize = getWidth() / 9;

    // TODO: Remove me.
    setBackground(Color.red);
  }

}
