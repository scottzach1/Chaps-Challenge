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
public class Canvas extends JPanel {

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

    renderCanvas();
  }

  public void createCanvasComponents(){

  }

  /**
   * Creates a board then renders it.
   * <p>
   * NOTE: This is just a test method and not intended
   * in final product.
   * Renders the board stored in application on the  canvas.
   */
  public void renderCanvas() {
//    System.out.println("CANVAS RENDER");
    // Clear components.
    components.clear();
    removeAll();

    // Retrieve tiles and add all components.
    components.addAll(application.getTilesToRender()
        .map(t -> AssetManager.getScaledImage(t.getCombinedUrl()))
        .map(JLabel::new)
        .collect(Collectors.toList()));

    revalidateComponents();
  }


  /**
   * Revalidate's components on GridBagLayout to
   * VIEW_SIZE x VIEW_SIZE.
   * <p>
   * DOES NOT REPAINT.
   */
  public void revalidateComponents() {
//    System.out.println("CANVAS REVALIDATE");
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


  public void resize(){
//    System.out.println("CANVAS RESIZE");
    int cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;
    AssetManager.scaleImages(cellSize);
    renderCanvas();
    revalidateComponents();
  }
}
