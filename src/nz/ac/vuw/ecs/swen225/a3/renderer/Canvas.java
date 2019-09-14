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
  public Canvas() {
    setPreferredSize(new Dimension(GUI.CANVAS_SIZE, GUI.CANVAS_SIZE));

    // TODO: Remove me.
    setBackground(Color.red);
  }

}
