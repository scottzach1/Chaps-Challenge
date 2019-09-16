package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * GUI class extends JFrame and is responsible with
 * maintain the Graphical Interface.
 */
public class GUI extends JFrame implements ComponentListener {
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
  private Dashboard dashboard;
  private DashboardHolder dashboardHolder;
  private JMenuBar menuBar;
  private ChapsChallenge application;

  // Layout object
  private GridBagConstraints constraints = new GridBagConstraints();

  /**
   * Constructor: Creates a new JFrame and sets preferred sizes.
   * Creates and adds all relevant GUI components then redraws.
   */
  public GUI(ChapsChallenge chaps_challenge) {
    application = chaps_challenge;

    //Create & init the frame.
    setPreferredSize(screenDimension.getSize());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setResizable(true);
    setMinimumSize(new Dimension(screenDimension.width / 5, screenDimension.height / 5));
    setVisible(true);
    setFocusable(true);
    getContentPane().setBackground(BACKGROUND_COLOUR);

    addComponentListener(this);

    // Add components.
    canvas = new Canvas(application);
    dashboard = new Dashboard(chaps_challenge);
    dashboardHolder = new DashboardHolder(dashboard);
    menuBar = new MenuOptions(chaps_challenge);


    // Set GridBag
    setLayout(new GridBagLayout());
    addLayoutComponents();
    addKeyListener(application);
    setFocusable(true);
    setFocusableWindowState(true);

    // Render.
    redraw();
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
    new PauseAction("Pause", this, application)
        .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
  }

  /**
   * Handles GUI actions related to resuming
   * the game.
   */
  public void resumeGame() {
    // TODO: This needs to be implemented.
  }

  /**
   * Handles GUI actions related to saving
   * the game.
   */
  public void saveGame() {
    // TODO: This needs to be implemented.
  }

  /**
   * Handles GUI actions related to loading
   * the game.
   */
  public void loadGame() {
    // TODO: This needs to be implemented.
  }

  /**
   * Handles GUI actions related to resetting to
   * previous level.
   */
  public void previousLevel() {
    // TODO: THis needs to be implemented.
  }

  /**
   * Handles GUI actions related to restarting the
   * game.
   */
  public void restartGame() {
    // TODO: This needs to be implemented.
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

  /**
   * Redraws the GUI JFrame.
   * * Revalidates.
   * * Repaints.
   */
  private void redraw() {
    revalidate();
    repaint();
  }


  /**
   * Resize dimensions and redraw window on
   * screen resize.
   *
   * @param e event.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    screenDimension = getSize();

    screenWidth = screenDimension.width;
    screenHeight = screenDimension.height - MENU_HEIGHT;
    canvasWidth = (screenDimension.width * 2) / 3;
    dashboardWidth = (screenDimension.width) / 3;

    revalidate();
    repaint();
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

  }

  /**
   * Overridden but not utilized.
   *
   * @param e event.
   */
  @Override
  public void componentHidden(ComponentEvent e) {

  }

}
