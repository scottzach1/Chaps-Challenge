package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordAndPlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashSet;

/**
 * GUI class extends JFrame and is responsible with
 * maintain the Graphical Interface.
 */
public class GUI extends JFrame implements ComponentListener, KeyListener {
  // Nothing important
  private static final long serialVersionUID = 1L;

  // Colour Space.
  static final Color BACKGROUND_COLOUR = new Color(67, 104, 101);

  // Dimension of the frame, based on screen size.
  private static Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
  static final int MENU_HEIGHT = screenDimension.height / 30;
  static int screenWidth = screenDimension.width;
  static int screenHeight = screenDimension.height - MENU_HEIGHT;
  static int canvasWidth = (screenDimension.width * 2) / 3;
  static int dashboardWidth = (screenDimension.width) / 3;

  // ChapsChallenge component fields.
  private Canvas canvas;
  private DashboardHolder dashboardHolder;
  private PauseMenu pauseMenu;
  private JMenuBar menuBar;
  private ChapsChallenge application;

  // Layout object
  private GridBagConstraints constraints = new GridBagConstraints();

  // HashSet of actively pressed keys
  private HashSet<Integer> activeKeys;
  private String direction;

  private boolean loaded;
  private boolean resizing;

  private int resizeCycle;


  /**
   * Constructor: Creates a new JFrame and sets preferred sizes.
   * Creates and adds all relevant GUI components then redraws.
   */
  public GUI(ChapsChallenge chaps_challenge) {
    loaded = false;
    resizeCycle = 0;
    direction = "";
    application = chaps_challenge;
    // Create new set for hosting keys currently pressed
    activeKeys = new HashSet<>();

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

    // Add components.
    canvas = new Canvas(application);
    dashboardHolder = new DashboardHolder(application);
    pauseMenu = new PauseMenu(application);
    ;
    menuBar = new MenuOptions(application);


    // Set GridBag
    setLayout(new GridBagLayout());
    addLayoutComponents();
    addKeyListener(this);
    setFocusable(true);
    setFocusableWindowState(true);
  }

  /**
   * Sets up GridBagLayout with all screen components.
   */
  private void addLayoutComponents() {
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
   * Handles GUI actions related to pausing
   * the game.
   */
  public void pauseGame() {
    getContentPane().removeAll();
    constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;
    pauseMenu.renderPauseMenu();

    add(pauseMenu, constraints);
    redraw();
    componentResized(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
  }

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
    componentResized(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
  }

  /**
   * Handles GUI actions related to saving
   * the game.
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
   * Handles GUI actions related to loading
   * the game.
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


  public void noFileFound() {
    // Button options
    String[] options = {"Okay"};

    // Create and display the JOptionPane
    JOptionPane.showOptionDialog(null,
        "Invalid file\n",
        "WARNING",
        JOptionPane.OK_OPTION,
        JOptionPane.ERROR_MESSAGE,
        null,
        options,
        options[0]);
    application.loadGame();
  }

  /**
   * Handles GUI actions related to exiting the game.
   * Invokes a pop up menu to confirm the player wants to exit the game.
   */
  public boolean exitGame() {
    // Button options
    String[] options = {"Yes please", "Opps, wrong button"};

    // Create and display the JOptionPane
    int choice = JOptionPane.showOptionDialog(null,
        "Would you like to exit the game?\n",
        "QUIT?",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);

    // (True) = Exit Game, (False) = Don't Exit Game
    return choice != JOptionPane.CLOSED_OPTION && choice != 1;
  }


  public void updateBoard() {
    if (resizing)
      return;
    runMove();
    canvas.refreshComponents();
    redraw();
  }

  public void updateDashboard() {
    if (resizing)
      return;
    dashboardHolder.renderDashboard();
    redraw();
  }

  public void renderInfoField(String text) {
    // Button options
    String[] options = {"Okay"};

    // Create and display the JOptionPane
    JOptionPane.showOptionDialog(null,
        text + "\n",
        "INFO",
        JOptionPane.OK_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);
  }

  /**
   * Redraws the GUI JFrame.
   * * Revalidates.
   * * Repaints.
   */
  private void redraw() {
    revalidate();
    repaint();
  }


  public void gameOver(String reason) {
    // TODO: Implement method and add tests.
  }


  public void endGame() {
    // TODO: Implement method and add tests.
  }


  /**
   * Resize dimensions and redraw window on
   * screen resize.
   *
   * @param e event.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    resizing = true;
    resizeCycle++;

    System.out.println("RESIZE: " + resizeCycle);

    screenDimension = getSize();
    screenWidth = screenDimension.width;
    screenHeight = screenDimension.height - MENU_HEIGHT;
    canvasWidth = (screenDimension.width * 2) / 3;
    dashboardWidth = (screenDimension.width) / 3;



    if (!application.isGamePaused()) {

      if (canvas != null && dashboardHolder != null) {
        System.out.println("CANVAS + DASHBOARD");
        canvas.resize();
        dashboardHolder.resize();
        loaded = true;
      }
      redraw();

      if (loaded && resizeCycle == 2) {
        System.out.println("AND ONE FOR GOOD LUCK");
        pauseMenu.createComponents();
        componentResized(e);
        application.startRunningThread();
      }
    } else {
      if (pauseMenu != null) {
        System.out.println("PAUSE MENU");
        pauseMenu.resize();
      }
      redraw();
    }
    resizing = false;
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentMoved(ComponentEvent e) {

  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentShown(ComponentEvent e) {
    if (application.isGamePaused()) application.resumeGame();
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentHidden(ComponentEvent e) {
    if (!application.isGamePaused()) application.pauseGame();
  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void keyTyped(KeyEvent e) {/* UNUSED */}

  /**
   * Handles events occuring after a key is pressed.
   * First adding it to the list of keys pressed, then dealing with all
   * active keys in the 'activeKeys' set.
   *
   * @param e - The key pressed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // Add the key pressed to the current list of pressed keys
    activeKeys.add(e.getKeyCode());
    // CTRL + X
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_X) && activeKeys.size() == 2)
      application.exitGame();
    // CTRL + S
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_S) && activeKeys.size() == 2) {
      application.saveGame();
      application.exitGame();
    }
    // CTRL + L
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_L) && activeKeys.size() == 2) {
      application.loadGame();
    }
    // CTRL + P
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_P) && activeKeys.size() == 2) {
      application.restartLevel();
    }
    // CTRL + 1
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_1) && activeKeys.size() == 2) {
      application.restartGame();
    }
    // SPACE
    if (activeKeys.contains(KeyEvent.VK_SPACE) && activeKeys.size() == 1) {
      if (application.isGamePaused()) application.resumeGame();
      else application.pauseGame();
    }
    // CTRL + R
    if (activeKeys.contains(KeyEvent.VK_CONTROL) && activeKeys.contains(KeyEvent.VK_R) && activeKeys.size() == 2) {
      if (application.isGamePaused()) application.resumeGame();
    }
    // ESC
    if (activeKeys.contains(KeyEvent.VK_ESCAPE) && activeKeys.size() == 1) {
      if (application.isGamePaused()) application.resumeGame();
    }

    if (RecordAndPlay.getIsRunning())
      return;

    /*
    PLAYER CONTROLS
     */
    // Move Up
    if ((activeKeys.contains(KeyEvent.VK_UP) || activeKeys.contains(KeyEvent.VK_W)) && activeKeys.size() == 1) {
      direction = "UP";
    }
    // Move Down
    if ((activeKeys.contains(KeyEvent.VK_DOWN) || activeKeys.contains(KeyEvent.VK_S)) && activeKeys.size() == 1) {
      direction = "DOWN";
    }
    // Move Left
    if ((activeKeys.contains(KeyEvent.VK_LEFT) || activeKeys.contains(KeyEvent.VK_A)) && activeKeys.size() == 1) {
      direction = "LEFT";
    }
    // Move Right
    if ((activeKeys.contains(KeyEvent.VK_RIGHT) || activeKeys.contains(KeyEvent.VK_D)) && activeKeys.size() == 1) {
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


  private void runMove() {
    // Go UP
    if (direction.equals("UP"))
      application.move(Tile.Direction.Up);
    // Go DOWN
    if (direction.equals("DOWN"))
      application.move(Tile.Direction.Down);
    // Go LEFT
    if (direction.equals("LEFT"))
      application.move(Tile.Direction.Left);
    // Go RIGHT
    if (direction.equals("RIGHT"))
      application.move(Tile.Direction.Right);


    direction = "";
  }


}
