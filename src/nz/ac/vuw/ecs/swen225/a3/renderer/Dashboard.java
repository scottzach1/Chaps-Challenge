package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class Dashboard extends JPanel implements ComponentListener {


  private final int GRID_WIDTH = 4, GRID_HEIGHT = 8;
  private ArrayList<JLabel> chapsBag;
  private ChapsChallenge chapsChallenge;
  GridBagConstraints constraints;

  public Dashboard(ChapsChallenge aChapsChallenge) {
    chapsChallenge = aChapsChallenge;

    setPreferredSize(new Dimension(GUI.dashboardWidth, GUI.screenHeight));

    // TODO: Remove me.
    setBackground(Color.LIGHT_GRAY);

    addComponentListener(this);

    setLayout(new GridBagLayout());
    renderComponents();
  }

  private void renderComponents() {
    // reset the GridBagConstraints
    constraints = new GridBagConstraints();

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
    CustomTextPane timeNum = new CustomTextPane(chapsChallenge.timeLeft() + "", boxWidth, boxHeight, rightAlign, Color.black, Color.green, true);
    CustomTextPane chipsLeft = new CustomTextPane("CHIPS LEFT", boxWidth, boxHeight, centerAlign, null, Color.black, false);
    CustomTextPane chipsLeftNum = new CustomTextPane(chapsChallenge.getPlayerInventory().size() + "", boxWidth, boxHeight, rightAlign, Color.black, Color.green, true);
    fillChipsBag();


    /*
    CREATES THE TOP PANEL WITH THE THREE TITLES
     */
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridBagLayout());
    topPanel.setBackground(null);
    topPanel.setPreferredSize(new Dimension(getWidth() / 2, getHeight() * 2 / 3));
    GridBagConstraints topPanelConstraints = new GridBagConstraints();

    topPanelConstraints.fill = GridBagConstraints.BOTH;
    topPanelConstraints.weightx = 1;
    topPanelConstraints.weighty = 1;

    topPanelConstraints.gridy = 0;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(level, topPanelConstraints);
    topPanelConstraints.gridy = 1;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(levelNum, topPanelConstraints);
    topPanelConstraints.gridy = 2;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(time, topPanelConstraints);
    topPanelConstraints.gridy = 3;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(timeNum, topPanelConstraints);
    topPanelConstraints.gridy = 4;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(chipsLeft, topPanelConstraints);
    topPanelConstraints.gridy = 5;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(chipsLeftNum, topPanelConstraints);

    /*
    CREATES THE BOTTOM PANEL WITH ALL OF CHAPS ITEMS
     */
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridBagLayout());
    bottomPanel.setBackground(null);
    bottomPanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 3));
    GridBagConstraints bottomPanelConstraints = new GridBagConstraints();

    bottomPanelConstraints.fill = GridBagConstraints.BOTH;
    bottomPanelConstraints.gridx = 0;

    for (int i = 0; i < 8; i++) {
      JLabel j = chapsBag.get(i);
      j.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
      j.setIcon(AssetManager.getScaledImage("free.png"));
      j.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (3 * 2)));

      bottomPanelConstraints.gridx = (i % 4);
      bottomPanelConstraints.gridy = (i / 4);


      bottomPanel.add(j, bottomPanelConstraints);
    }


    constraints.weighty = 2;
    constraints.gridy = 0;
    add(topPanel, constraints);
    constraints.weighty = 1;
    constraints.gridy = 1;
    add(bottomPanel, constraints);

    revalidate();
  }


  /**
   * Given an arraylist of strings (asset names), their png will be added to
   * the content panel on the dashboard.
   */
  public void fillChipsBag() {
    chapsBag = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      try {
        JLabel content = new JLabel(AssetManager.getScaledImage(chapsChallenge.getPlayerInventory().get(i)));
        chapsBag.set(i, content);
      } catch (Exception e) {
        chapsBag.add(new JLabel(AssetManager.getScaledImage("free.png")));
      }
    }
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
