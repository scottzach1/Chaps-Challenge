package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Dashboard extends JPanel implements ComponentListener {


  private final int GRID_WIDTH = 4, GRID_HEIGHT = 8;
  private final JLabel BLANK;
  private ArrayList<JLabel> chipsBag;
  GridBagConstraints constraints = new GridBagConstraints();

  public Dashboard() {
    chipsBag = new ArrayList<>();
    BLANK = setBlank();

    setPreferredSize(new Dimension(GUI.dashboardWidth, GUI.screenHeight));

    // TODO: Remove me.
    setBackground(Color.MAGENTA);

    addComponentListener(this);
    setLayout(new GridBagLayout());
    renderComponents();
  }

  private void renderComponents() {


    /*
    CREATE THE CONSTANTS FOR THE CUSTOM TEXT PANES
     */

    // Create the padding around the borders such that preferred sizes are not flush against the sides of the dash border
    int paddingOfBox = Math.min(getWidth() / 10, getHeight() / 60);

    // Box sizes using the aforementioned padding
    int boxWidth = getWidth() - paddingOfBox;
    int boxHeight = (getHeight() / GRID_HEIGHT) - paddingOfBox;

    // Create the alignment for the custom text
    // - CENTER Aligned
    SimpleAttributeSet centerAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);
    // - LEFT Aligned
    SimpleAttributeSet leftAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
    // - RIGHT Aligned
    SimpleAttributeSet rightAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);

    CustomTextPane level = new CustomTextPane("LEVEL", boxWidth, boxHeight, centerAlign, null, Color.black, false);
    CustomTextPane levelNum = new CustomTextPane("1", boxWidth, boxHeight, rightAlign, Color.black, Color.green, true);
    CustomTextPane time = new CustomTextPane("TIME", boxWidth, boxHeight, centerAlign, null, Color.black, false);
    CustomTextPane timeNum = new CustomTextPane("100", boxWidth, boxHeight, rightAlign, Color.black, Color.green, true);
    CustomTextPane chipsLeft = new CustomTextPane("CHIPS LEFT", boxWidth, boxHeight, centerAlign, null, Color.black, false);
    CustomTextPane chipsLeftNum = new CustomTextPane("11", boxWidth, boxHeight, rightAlign, Color.black, Color.green, true);

    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.gridx = 1;
    constraints.gridwidth = GRID_WIDTH / 2;

    // Add the three title and their numeric values
    constraints.gridy = 0;
    add(level, constraints);
    constraints.gridy = 1;
    add(levelNum, constraints);
    constraints.gridy = 2;
    add(time, constraints);
    constraints.gridy = 3;
    add(timeNum, constraints);
    constraints.gridy = 4;
    add(chipsLeft, constraints);
    constraints.gridy = 5;
    add(chipsLeftNum, constraints);

    // Add 8 spaces for chips bag contents
    constraints.fill = GridBagConstraints.NONE;
    constraints.gridwidth = 1;
    constraints.gridy = 6;

    constraints.gridx = 0;
    add(chipsBag.get(0), constraints);
    constraints.gridx = 1;
    add(chipsBag.get(1), constraints);
    constraints.gridx = 2;
    add(chipsBag.get(2), constraints);
    constraints.gridx = 3;
    add(chipsBag.get(3), constraints);

    constraints.gridy = 7;

    constraints.gridx = 0;
    add(chipsBag.get(4), constraints);
    constraints.gridx = 1;
    add(chipsBag.get(5), constraints);
    constraints.gridx = 2;
    add(chipsBag.get(6), constraints);
    constraints.gridx = 3;
    add(chipsBag.get(7), constraints);

    revalidate();
  }


  /**
   * Given an arraylist of strings (asset names), their png will be added to
   * the content panel on the dashboard.
   *
   * @param contents - A list of items in chips bag
   */
  public void fillChipsBag(ArrayList<String> contents) {
    for (int i = 0; i < 8; i++) {
      try {
        JLabel content = new JLabel();
        content.setIcon(new ImageIcon("assets/" + contents.get(i) + ".png"));
        chipsBag.set(i, content);
      } catch (Exception e) {
        chipsBag.set(i, BLANK);
      }
    }
  }

  /**
   * Creates a list of blank JPanels in the case that "fillChipsBag" fails or is passed null.
   */
  private JLabel setBlank() {
    JLabel blank = new JLabel();
    blank.setIcon(new ImageIcon("assets/free.png"));
    return blank;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    removeAll();
    renderComponents();
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
   * Private class to create multiple custom text panes for the DashBoard.
   */
  private class CustomTextPane extends JTextPane {

    /**
     * CustomTextPane Constructor, read params for instructions.
     *
     * @param text          - The Text in the text pane
     * @param width         - The width of the text pane
     * @param height        - The height of the text pane
     * @param textAlignment - The alignment of the text, if null, default used
     * @param background    - Color of the back ground
     * @param foreground    - Color of the foreground
     * @param border        - If true: Add a matte border of foreground color, if false, no border
     */
    public CustomTextPane(String text, int width, int height, SimpleAttributeSet textAlignment, Color background, Color foreground, boolean border) {

      // Basic setup:
      // - Not editable
      setEditable(false);
      // - Set the background color to the parsed in color
      setBackground(background);
      // - Set the foreground color to the parsed in color
      setForeground(foreground);

      // - If the border boolean parsed in is true, make one
      if (border) {
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, foreground));
      }
      // - Else remove any presets
      else {
        setBorder(null);
      }

      // Create a default font for the box making the text fit snug
      setFont(new Font("Ariel", Font.BOLD, Math.min(width * 2 / GRID_HEIGHT, height * 2 / (GRID_HEIGHT / 2))));

      // Try Align Text
      if (textAlignment != null) {
        try {
          // Create a Styled document (allows for the editing of JTextPane)
          StyledDocument doc = getStyledDocument();

          // Insert the text with the alignment
          doc.insertString(doc.getLength(), text, textAlignment);
          doc.setParagraphAttributes(doc.getLength(), 1, textAlignment, false);
        }
        // If the right alignment fails, just insert it
        catch (Exception e) {
          System.out.println(e);
          setText(text);
        }
      } else {
        setText(text);
      }
    }


  }
}
