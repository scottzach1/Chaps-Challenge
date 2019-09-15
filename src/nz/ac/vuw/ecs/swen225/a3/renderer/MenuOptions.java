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
    
    // Create the quit button
    JMenuItem closeItem = new JMenuItem("Quit");
    closeItem.addActionListener(arg0 -> System.exit(0));
    file.add(closeItem);
    
    // Create the save button
    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.addActionListener(arg0 -> saveGame());
    file.add(saveItem);
    
    //create the load button
    JMenuItem loadItem = new JMenuItem("Load");
    loadItem.addActionListener(arg0 -> loadGame());
    file.add(loadItem);
    
    // Create the game menu
    JMenu game = new JMenu("Game");
    
    // Create the previous level button
    JMenuItem previousItem = new JMenuItem("Previous Level");
    previousItem.addActionListener(arg0 -> previousLevel());
    game.add(previousItem);
    
    // Create the Pause button
    JMenuItem pauseItem = new JMenuItem("Pause");
    pauseItem.addActionListener(arg0 -> pauseGame());
    game.add(pauseItem);

    // Add components
    add(file);
    add(game);
  }
    
  /**
   * Save game method.
   */
  public void saveGame() {
    
  }
  
  /**
   * Load game method.
   */
  public void loadGame() {
    
  }
  
  /**
   * Previous level method.
   */
  public void previousLevel() {
    
  }
  
  /**
   * Pause game method.
   */
  public void pauseGame() {
    
  }
}
