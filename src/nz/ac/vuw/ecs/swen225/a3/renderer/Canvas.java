package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
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

  /**
   * Gets cell size.
   * @return cell size.
   */
  public static int getCellSize() {
    return cellSize;
  }

  public Canvas() {

    setPreferredSize(new Dimension(GUI.canvasSize, GUI.canvasSize));
    cellSize = getWidth() / VIEW_SIZE;

    addComponentListener(this);

    // TODO: Remove me.
    setBackground(Color.red);

    setLayout(new GridBagLayout());

    renderABoard();
  }

  private void renderABoard() {
    Board board = new Board();

    components.clear();
    removeAll();

    components.addAll(board.getStream()
        .map(t -> AssetManager.getScaledImage(t.getImageUrl()))
        .map(JLabel::new).collect(Collectors.toList()));

    revalidateComponents();
    repaint();
  }

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

    renderABoard();

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
