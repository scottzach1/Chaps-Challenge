package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * GUI class extends JFrame and is responsible with
 * maintain the Graphical Interface.
 */
public class GUI extends JFrame implements ComponentListener {
  // Nothing important
  private static final long serialVersionUID = 1L;

  // Dimension of the frame, based on screen size
  private static Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
  public static int SCREEN_SIZE = (Math.min(screenDimension.width, screenDimension.height));
  public static int CANVAS_SIZE = SCREEN_SIZE;
  public static int DASHBOARD_WIDTH = SCREEN_SIZE / 3;

  private Canvas canvas;
  private JMenuBar menuBar;


  /**
   * Constructor: Creates a new JFrame and sets preferred sizes.
   * Creates and adds all relevant GUI components then redraws.
   */
  public GUI() {
    //Create the frame.
    setPreferredSize(screenDimension.getSize());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setResizable(true);
    setMinimumSize(new Dimension(SCREEN_SIZE / 5, SCREEN_SIZE / 5));
    setVisible(true);

    // Add components.
    menuBar = new MenuOptions();
    setJMenuBar(menuBar);

    setupComponents();

    pack();

    // Render.
    redraw();
  }


  /**
   * Sets up GridBagLayout with all screen components.
   */
  public void setupComponents() {
    // Set Layout
    int padding = SCREEN_SIZE / 11;
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();


    // Create & Set Canvas
    Canvas canvas = new Canvas();
    constraints.insets = new Insets(padding,padding,padding,padding / 2);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 3;
    constraints.weighty = 3;
    constraints.fill = GridBagConstraints.BOTH;
    add(canvas, constraints);

    Dashboard dashboard = new Dashboard();
    constraints.insets = new Insets(padding,padding / 2,padding,padding);
    constraints.gridx = 3;
    constraints.gridy = 0;
    constraints.weightx = 1;
    constraints.weighty = 3;
    constraints.fill = GridBagConstraints.BOTH;
    add(dashboard, constraints);
  }

  /**
   * Redraws the GUI JFrame.
   * * Revalidates.
   * * Repaints.
   */
  public void redraw() {
    revalidate();
    repaint();

  }


  @Override
  public void componentResized(ComponentEvent e) {
    screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    SCREEN_SIZE = (Math.min(screenDimension.width, screenDimension.height));
    CANVAS_SIZE = SCREEN_SIZE;
    DASHBOARD_WIDTH = SCREEN_SIZE / 3;

    setupComponents();
  }

  @Override
  public void componentMoved(ComponentEvent e) {

  }

  @Override
  public void componentShown(ComponentEvent e) {

  }

  @Override
  public void componentHidden(ComponentEvent e) {

  }

  /**
   * Testing invocation point to check GUI.
   *
   * @param args (ignored).
   */
  public static void main(String[] args) {
    GUI gui = new GUI();
    System.out.printf("Screen width %d, height %d\n", SCREEN_SIZE, SCREEN_SIZE);
    System.out.printf("Dashboard width %d, height %d\n", DASHBOARD_WIDTH, CANVAS_SIZE);
  }
}
