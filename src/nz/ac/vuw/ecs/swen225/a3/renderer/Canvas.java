package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

/**
 * Canvas displays the game maze on the screen.
 * @author Zac Scott 300447976, Harrison Cook 300402048
 */
public class Canvas extends JPanel {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;

  public static final int VIEW_SIZE = 9;

  private ArrayList<JLabel> components = new ArrayList<>();

  private int cellSize;

  private ChapsChallenge application;
  private AssetManager assetManager;


  /**
   * Constructor: Creates and initializes canvas to the correct size Then renders the board.
   */
  Canvas(ChapsChallenge app) {
    application = app;
    Gui gui = application.getGui();
    assetManager = gui.getAssetManager();

    setPreferredSize(new Dimension(gui.getCanvasWidth(), gui.getScreenHeight()));

    setLayout(new GridBagLayout());
    setBackground(Gui.BACKGROUND_COLOUR);
  }

  /**
   * Creates canvas components.
   */
  private void createCanvasComponents() {
    removeAll();
    components.clear();
    for (int row = 0; row < VIEW_SIZE; row++) {
      for (int col = 0; col < VIEW_SIZE; col++) {
        JLabel item = new JLabel();
        item.setIcon(assetManager.getScaledImage("free.png"));
        components.add(item);
      }
    }
  }

  /**
   * Creates a board then renders it.
   * NOTE: This is just a test method and not intended in final product. Renders the board stored in
   * application on the  canvas.
   */
  void refreshComponents() {
    // Retrieve tiles and add all components.
    // Convert the Stream to List
    AtomicInteger i = new AtomicInteger();
    application.getTilesToRender().collect(Collectors.toList()).forEach(t -> {
      try {
        components.get(i.get()).setIcon(assetManager.getScaledImage(t.getCombinedUrl()));
        i.getAndIncrement();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

  }


  /**
   * Revalidate's components on GridBagLayout to VIEW_SIZE x VIEW_SIZE.
   * DOES NOT REPAINT.
   */
  private void renderCanvasComponents() {

    if (getWidth() <= 0) {
      return;
    }

    removeAll();
    GridBagConstraints constraints = new GridBagConstraints();
    if (components.size() > 0) {
      constraints.gridy = 0;
      for (int i = 0; i < components.size(); i++) {
        constraints.gridx = i % VIEW_SIZE;
        constraints.gridy = i / VIEW_SIZE;
        if (components.get(i) != null) {
          add(components.get(i), constraints);
        }
      }
    } else {
      this.removeAll();
    }

    revalidate();
    repaint();
  }


  /**
   * Recalculates cell size then resize the canvas.
   */
  void resize() {

    cellSize = Math.min(getWidth(), getHeight()) / VIEW_SIZE;
    assetManager.scaleImages(cellSize);

    createCanvasComponents();
    refreshComponents();
    renderCanvasComponents();

    revalidate();
    repaint();
  }
}
