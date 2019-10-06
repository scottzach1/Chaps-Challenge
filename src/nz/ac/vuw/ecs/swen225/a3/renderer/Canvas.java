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

  public final static int VIEW_SIZE = 9;

  private ArrayList<Component> components = new ArrayList<>();

  private ChapsChallenge application;

  /**
   * Constructor: Creates and initializes canvas to the correct size
   * Then renders the board.
   */
  Canvas(ChapsChallenge app) {

    application = app;
    setPreferredSize(new Dimension(GUI.canvasWidth, GUI.screenHeight));

    setLayout(new GridBagLayout());
    setBackground(GUI.BACKGROUND_COLOUR);

    addComponentListener(this);
  }

  /**
   * Creates a board then renders it.
   * <p>
   * NOTE: This is just a test method and not intended
   * in final product.
   * Renders the board stored in application on the  canvas.
   */
  public void renderCanvas() {
    // Clear components.
    components.clear();
    removeAll();

    // Retrieve tiles and add all components.
    components.addAll(application.getTilesToRender()
        .map(t -> AssetManager.getScaledImage(t.getImageUrl()))
        .map(JLabel::new)
        .collect(Collectors.toList()));

    // Renders new components.
    revalidateComponents();
    repaint();
  }


  /**
   * Revalidate's components on GridBagLayout to
   * VIEW_SIZE x VIEW_SIZE.
   * <p>
   * DOES NOT REPAINT.
   */
  private void revalidateComponents() {
    GridBagConstraints constraints = new GridBagConstraints();
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
   *
   * @param e event.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    int cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;
    AssetManager.scaleImages(cellSize);

    renderCanvas();
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
