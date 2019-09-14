package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * MenuOptions extends JMenuBar and is responsible for hosting the
 * options in the menu bar at the top of the GUI.
 */
public class MenuOptions extends JMenuBar {

  /**
   * Constructor: Creates MenuBar and adds all game relevant
   * components.
   */
  public MenuOptions() {
    // Set the Size of the Control panel
    setPreferredSize(new Dimension(GUI.screenWidth, GUI.MENU_HEIGHT));
    setFont(new Font("Serif", Font.BOLD, 18));

    // Create the file menu
    JMenu file = new JMenu("File");

    JMenuItem closeItem = new JMenuItem("Close");
    closeItem.addActionListener(arg0 -> System.exit(0));
    file.add(closeItem);

    // Add components
    add(file);

    // TODO: Remove me.
    setBackground(Color.CYAN);
  }
}
