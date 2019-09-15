package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Canvas displays the game maze on the screen.
 */
public class Canvas extends JPanel implements ComponentListener {

  /**
   * Constructor: Creates and initializes canvas to the correct size.
   */
  public final static int VIEW_SIZE = 9;
  static int cellSize = 1;

  private GridBagConstraints constraints = new GridBagConstraints();
  private ArrayList<Component> components = new ArrayList<>();

  private ChapsChallenge application;

  /**
   * Gets cell size.
   * @return cell size.
   */
  public static int getCellSize() {
    return cellSize;
  }

  /**
   * Constructor: Initializes local variable then renders the board.
   */
  public Canvas(ChapsChallenge app) {

    application = app;
    setPreferredSize(new Dimension(GUI.canvasWidth, GUI.screenHeight));
    cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;

    addComponentListener(this);

    // TODO: Remove me.
    setBackground(Color.red);

    setLayout(new GridBagLayout());

    renderBoard();
  }

  /**
   * Renders the board stored in application on the  canvas.
   */
  private void renderBoard() {
    // Clear components.
    components.clear();
    removeAll();

    // Retrieve tiles and add all components.
    components.addAll(application.getTilesToRender()
        .map(t -> AssetManager.getScaledImage(t.getImageUrl()))
        .map(JLabel::new).collect(Collectors.toList()));

    // Renders new components.
    revalidateComponents();
    repaint();
  }


  /**
   * Revalidate's components on GridBagLayout to
   * VIEW_SIZE x VIEW_SIZE.
   *
   * DOES NOT REPAINT.
   */
  private void revalidateComponents() {
    constraints = new GridBagConstraints();
    if (components.size() > 0) {
      constraints.gridy = 0;
      for (int i = 0; i < components.size(); i++) {
        constraints.gridx = i % VIEW_SIZE;
        constraints.gridy = i / VIEW_SIZE;
        if (components.get(i) != null)
          add(components.get(i), constraints);
      }
    } else {
      this.removeAll();
    }

    revalidate();
  }

  /**
   * Recalculates cell size and repaints
   * when resized.
   * @param e event.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;
    AssetManager.scaleIcons(cellSize);

    renderBoard();
    repaint();
  }

  /**
   * Overridden but not utilized.
   * @param e event.
   */
  @Override
  public void componentMoved(ComponentEvent e) {

  }

  /**
   * Overridden but not utilized.
   * @param e event.
   */
  @Override
  public void componentShown(ComponentEvent e) {

  }

  /**
   * Overridden but not utilized.
   * @param e event.
   */
  @Override
  public void componentHidden(ComponentEvent e) {

  }
}
