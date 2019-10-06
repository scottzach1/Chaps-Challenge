package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Canvas displays the game maze on the screen.
 */
public class Canvas extends JPanel {

  public final static int VIEW_SIZE = 9;

  private ArrayList<JLabel> components = new ArrayList<>();

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

    createCanvasComponents();

  }

  public void createCanvasComponents() {
    removeAll();
    components.clear();
    for (int row = 0; row < VIEW_SIZE; row++) {
      for (int col = 0; col < VIEW_SIZE; col++) {
        JLabel item = new JLabel();
        item.setIcon(AssetManager.getScaledImage("free.png"));
        components.add(item);
      }
    }
    refreshComponents();
  }

  /**
   * Creates a board then renders it.
   * <p>
   * NOTE: This is just a test method and not intended
   * in final product.
   * Renders the board stored in application on the  canvas.
   */
  public void refreshComponents() {

    // Retrieve tiles and add all components.
    // Convert the Stream to List
    AtomicInteger i = new AtomicInteger();
    application.getTilesToRender().collect(Collectors.toList()).forEach(T -> {
      try {
        components.get(i.get()).setIcon(AssetManager.getScaledImage(T.getCombinedUrl()));
        i.getAndIncrement();
      } catch (Exception e){
      }
    });

  }


  /**
   * Revalidate's components on GridBagLayout to
   * VIEW_SIZE x VIEW_SIZE.
   * <p>
   * DOES NOT REPAINT.
   */
  public void renderCanvasComponents() {

    if(getWidth() <= 0)
      return;

    removeAll();
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
    repaint();
  }


  public void resize() {
    int cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;
    AssetManager.scaleImages(cellSize);
    createCanvasComponents();
    refreshComponents();
    renderCanvasComponents();

    revalidate();
    repaint();
  }
}
