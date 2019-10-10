package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.a3.renderer.GameMenu.MenuType;

/**
 * GUI class extends JFrame and is responsible with maintain the Graphical Interface.
 *
 * @author Harrison Cook 300402048, Zac Scott 300447976.
 */
public class Gui extends JFrame implements ComponentListener, KeyListener {

  // Nothing important
  private static final long serialVersionUID = 1L;

  // Colour Space.
  static final Color BACKGROUND_COLOUR = new Color(67, 104, 101);


  // Dimension of the frame, based on screen size.
  private static final int inlet = 100;
  private Dimension screenDimension = new Dimension(
      Toolkit.getDefaultToolkit().getScreenSize().width - inlet,
      Toolkit.getDefaultToolkit().getScreenSize().height - inlet);
  private int menuHeight = screenDimension.height / 30;
  private int screenWidth = screenDimension.width;
  private int screenHeight = screenDimension.height - menuHeight;
  private int canvasWidth = (screenDimension.width * 2) / 3;
  private int dashboardWidth = (screenDimension.width) / 3;

  // ChapsChallenge component fields.
  private Canvas canvas;
  private DashboardHolder dashboardHolder;
  private GameMenu gameMenu;
  private JMenuBar menuBar;
  private ChapsChallenge application;

  // Layout object
  private GridBagConstraints constraints = new GridBagConstraints();

  // HashSet of actively pressed keys
  private HashSet<Integer> activeKeys;
  private String direction;

  private boolean isBusy;
  private boolean playerIsDead;

  private int resizeCycle;


  /**
   * Constructor: Creates a new JFrame and sets preferred sizes. Creates and adds all relevant GUI
   * components then redraws.
   */
  public Gui(ChapsChallenge chapsChallenge) {
    resizeCycle = 0;
    direction = "";
    application = chapsChallenge;
    playerIsDead = false;

    // Create new set for hosting keys currently pressed
    activeKeys = new HashSet<>();

    // Set GridBag
    setLayout(new GridBagLayout());

    //Create & init the frame.
    setPreferredSize(screenDimension.getSize());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(true);
    setMinimumSize(new Dimension(screenDimension.width / 5, screenDimension.height / 5));
    setVisible(true);
    setFocusable(true);
    setFocusableWindowState(true);
    getContentPane().setBackground(BACKGROUND_COLOUR);
    addComponentListener(this);
    addKeyListener(this);
    pack();
  }

  /**
   * Returns boolean state of whether gui is busy.
   *
   * @return is busy true or false.
   */
  public boolean isBusy() {
    return isBusy;
  }

  /**
   * Sets up GridBagLayout with all screen components.
   */
  public void addLayoutComponents() {
    // Add components.
    canvas = new Canvas(application);
    dashboardHolder = new DashboardHolder(application);
    gameMenu = new GameMenu(application);
    menuBar = new MenuOptionPane(application);

    // Add MenuBar.
    setJMenuBar(menuBar);
    constraints.fill = GridBagConstraints.BOTH;

    // Set Layout
    int padding = Math.min(screenHeight, canvasWidth) / 11;

    // Set Canvas.
    constraints.insets = new Insets(padding, padding, padding, padding / 2);
    constraints.weightx = 2;
    constraints.weighty = 2;
    add(canvas, constraints);

    // Set Dashboard.
    constraints.insets = new Insets(padding, padding / 2, padding, padding);
    constraints.weightx = 1;
    constraints.weighty = 1;
    add(dashboardHolder, constraints);
    pack();
  }

  /**
   * Handles GUI actions related to pausing the game.
   */
  public void pauseGame() {
    getContentPane().removeAll();
    constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;
    gameMenu.renderMenu();

    add(gameMenu, constraints);
    redraw();
  }

  /**
   * Closes the pause menu and loads the game screen.
   */
  public void resumeGame() {
    getContentPane().removeAll();
    // Add MenuBar.
    setJMenuBar(menuBar);
    constraints.fill = GridBagConstraints.BOTH;

    // Set Layout
    int padding = Math.min(screenHeight, canvasWidth) / 11;

    // Set Canvas.
    constraints.insets = new Insets(padding, padding, padding, padding / 2);
    constraints.weightx = 2;
    constraints.weighty = 2;
    add(canvas, constraints);

    // Set Dashboard.
    constraints.insets = new Insets(padding, padding / 2, padding, padding);
    constraints.weightx = 1;
    constraints.weighty = 1;
    add(dashboardHolder, constraints);
    redraw();
  }

  /**
   * Handles GUI actions related to saving the game.
   */
  public boolean saveGame() {
    JFileChooser jfc = new JFileChooser();
    jfc.setCurrentDirectory(new File("."));
    jfc.setDialogTitle("Save file");

    if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      application.setSaveFile(jfc.getSelectedFile());
      return true;
    }
    return false;
  }

  /**
   * Handles GUI actions related to loading the game.
   */
  public boolean loadGame() {
    JFileChooser jfc = new JFileChooser();
    jfc.setCurrentDirectory(new File("."));
    jfc.setDialogTitle("Load file");
    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      application.setLoadFile(jfc.getSelectedFile());
      return true;
    }
    return false;
  }

  /**
   * Creates a popup telling the user there was an invalid file.
   */
  public void noFileFound() {
    // Button options
    String[] options = {"Okay"};

    // Create and display the JOptionPane
    JOptionPane.showOptionDialog(null,
        "Invalid file\n",
        "WARNING",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.ERROR_MESSAGE,
        null,
        options,
        options[0]);
    application.loadGame();
  }

  /**
   * If not busy executes move and redraws dashboard.
   */
  public void updateBoard() {
    if (isBusy) {
      return;
    }
    runMove();
    canvas.refreshComponents();
    redraw();
  }

  /**
   * If not busy renders the dashboard.
   */
  public void updateDashboard() {
    if (isBusy) {
      return;
    }
    dashboardHolder.refreshDashboard();
    redraw();
  }

  /**
   * Displays info field text pop up.
   *
   * @param text to display.
   */
  public void renderInfoField(String text) {
    // Button options
    String[] options = {"Okay"};

    // Create and display the JOptionPane
    JOptionPane.showOptionDialog(null,
        text + "\n",
        "INFO",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);
  }

  /**
   * Redraws the GUI JFrame. * Revalidates. * Repaints.
   */
  private void redraw() {
    revalidate();
    repaint();
  }


  /**
   * Renders the game menu upon death, timing out, or winning.
   *
   * @param reason - MenuType to be displayed based on the reason for the game ending.
   */
  public void gameOver(MenuType reason) {
    getContentPane().removeAll();
    constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;

    switch (reason) {
      case TIMEOUT:
        gameMenu.setMenuType(MenuType.TIMEOUT);
        break;
      case DEATH:
        gameMenu.setMenuType(MenuType.DEATH);
        break;
      case WINNER:
        gameMenu.setMenuType(MenuType.WINNER);
        break;
      case QUITTER:
        gameMenu.setMenuType(MenuType.QUITTER);
        break;
      default:
        gameMenu.setMenuType(MenuType.ERROR);
    }

    gameMenu.renderMenu();

    add(gameMenu, constraints);
    redraw();
  }


  /**
   * Resize dimensions and redraw window on screen resize.
   *
   * @param e event.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    if(isBusy){
      return;
    }
    isBusy = true;
    resizeCycle++;

    //System.out.println(resizeCycle);

    screenDimension = getSize();
    screenWidth = screenDimension.width;
    screenHeight = screenDimension.height - menuHeight;
    canvasWidth = (screenDimension.width * 2) / 3;
    dashboardWidth = (screenDimension.width) / 3;

    if (!application.isGamePaused()) {

      if (canvas != null && dashboardHolder != null) {
        canvas.resize();
        dashboardHolder.resize();
        redraw();

        if (resizeCycle >= 1 && resizeCycle < 4) {
          isBusy = false;
          componentResized(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
          application.startRunningThread();
          return;
        }
      }

    } else {
      if (gameMenu != null) {
        gameMenu.resize();
      }
      redraw();
    }
    isBusy = false;
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentMoved(ComponentEvent e) {
    /* NOT UTILISED */
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentShown(ComponentEvent e) {
    /* NOT UTILISED */
  }

  /**
   * Pauses the game on component hidden.
   *
   * @param e event.
   */
  @Override
  public void componentHidden(ComponentEvent e) {
    if (!application.isGamePaused()) {
      application.pauseGame();
    }
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    /* NOT UTILISED */
  }

  /**
   * Handles events occurring after a key is pressed. First adding it to the list of keys pressed,
   * then dealing with all active keys in the 'activeKeys' set.
   *
   * @param e - The key pressed
   */
  @Override
  public void keyPressed(KeyEvent e) {

    // Add the key pressed to the current list of pressed keys
    activeKeys.add(e.getKeyCode());
    // CTRL + X
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_X)
        && activeKeys.size() == 2) {
      application.exitGame();
    }
    // CTRL + S
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_S)
        && activeKeys.size() == 2) {
      application.saveGame();
      application.exitGame();
    }
    // CTRL + L
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_L)
        && activeKeys.size() == 2) {
      application.loadGame();
    }
    // CTRL + P
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_P)
        && activeKeys.size() == 2) {
      application.restartLevel();
    }

    // CTRL + Number
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.size() == 2) {
      for (int i = 0; i != 10; ++i) {
        if (activeKeys.contains(KeyEvent.VK_1 + i)) {
          application.setLevel(i);
        }
      }
    }

    // SPACE
    if (activeKeys.contains(KeyEvent.VK_SPACE) && activeKeys.size() == 1) {
      if (playerIsDead) {
        return;
      }

      if (application.isGamePaused()) {
        application.resumeGame();
      } else {
        application.pauseGame();
      }
    }
    // CTRL + R
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_R)
        && activeKeys.size() == 2) {
      if (playerIsDead) {
        return;
      }

      if (application.isGamePaused()) {
        application.resumeGame();
      }
    }
    // ESC
    if (activeKeys.contains(KeyEvent.VK_ESCAPE) && activeKeys.size() == 1) {
      if (application.isGamePaused()) {
        application.resumeGame();
      }
    }
    // Return if recording is running
    if (RecordAndPlay.getIsRunning()) {
      return;
    }

    /*
    PLAYER CONTROLS
     */
    // Move Up
    if ((activeKeys.contains(KeyEvent.VK_UP) || activeKeys.contains(KeyEvent.VK_W))
        && activeKeys.size() == 1) {
      direction = "UP";
    }
    // Move Down
    if ((activeKeys.contains(KeyEvent.VK_DOWN) || activeKeys.contains(KeyEvent.VK_S))
        && activeKeys.size() == 1) {
      direction = "DOWN";
    }
    // Move Left
    if ((activeKeys.contains(KeyEvent.VK_LEFT) || activeKeys.contains(KeyEvent.VK_A))
        && activeKeys.size() == 1) {
      direction = "LEFT";
    }
    // Move Right
    if ((activeKeys.contains(KeyEvent.VK_RIGHT) || activeKeys.contains(KeyEvent.VK_D))
        && activeKeys.size() == 1) {
      direction = "RIGHT";
    }

    redraw();
  }

  /**
   * Removes any key released from the set of activeKeys.
   *
   * @param e - The key released
   */
  @Override
  public void keyReleased(KeyEvent e) {
    activeKeys.clear();
  }


  /**
   * Executes move in application in direction of the field 'direction'. Then clears direction
   * field.
   */
  private void runMove() {
    // Go UP
    if (direction.equals("UP")) {
      application.move(Tile.Direction.Up);
    }
    // Go DOWN
    if (direction.equals("DOWN")) {
      application.move(Tile.Direction.Down);
    }
    // Go LEFT
    if (direction.equals("LEFT")) {
      application.move(Tile.Direction.Left);
    }
    // Go RIGHT
    if (direction.equals("RIGHT")) {
      application.move(Tile.Direction.Right);
    }

    direction = "";
  }

  /**
   * Returns the whole width of this JFrame.
   *
   * @return - An integer representation of the screen width
   */
  int getScreenWidth() {
    return screenWidth;
  }

  /**
   * Returns the whole height of this JFrame.
   *
   * @return - An integer representation of the screen height
   */
  int getScreenHeight() {
    return screenHeight;
  }

  /**
   * Return 2/3 of the width of this JFrame.
   *
   * @return - The canvas width
   */
  int getCanvasWidth() {
    return canvasWidth;
  }

  /**
   * Return 1/3 of the width of this JFrame.
   *
   * @return - The Dashboard width
   */
  int getDashboardWidth() {
    return dashboardWidth;
  }

  /**
   * Return the height of the menu bar
   *
   * @return - The menu bar height
   */
  int getMenuHeight() {
    return menuHeight;
  }

  /**
   * Called when the player dies or times out. This means that the menu is not able to be quit out
   * of.
   */
  public void setPlayerDead() {
    playerIsDead = true;
  }

  /**
   * Called when the player restarts the level or game. This means that the player can access the
   * pause menu again
   */
  public void setPlayerAlive() {
    playerIsDead = false;
  }

  /**
   * Resets the menu type when restarting a level
   */
  public void resetMenuSettings() {
    gameMenu.setMenuType(MenuType.PAUSE);
  }
}