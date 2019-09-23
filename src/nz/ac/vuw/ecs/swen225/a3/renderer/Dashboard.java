package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The side object of the GUI that displays the level, time, chipsLeft.
 */
public class Dashboard extends JPanel {

  /*
  DASHBOARD FIELDS
   */

  // Colour Space.
  static final Color TEXT_COLOUR = new Color(0, 0, 0);
  static final Color ACCENT_COLOUR = new Color(0, 255, 0);
  static final Color BACKGROUND_COLOUR = new Color(192, 192, 192);


  private ArrayList<Component> chapsBagComponents;


  // Padding around boarders of boxes
  int paddingOfBox;

  // Create the alignment for the custom text
  private SimpleAttributeSet centerAlign, rightAlign;

  // CustomTextPane constants
  CustomTextPane level, levelNum, time, timeNum, chipsLeft, chipsLeftNum;

  private final int GRID_WIDTH = 4, GRID_HEIGHT = 8;
  private HashMap<String, Integer> chapsBag;
  private ArrayList<JLabel> chapsBagImages;
  private ChapsChallenge chapsChallenge;

  /**
   * The Dashboard constructor.
   * sets preferred Size, background color, layout, adds a components listener and a key listener
   *
   * @param aChapsChallenge - The parent ChapsChallenge object the GUI to create this DashBoard
   */
  Dashboard(ChapsChallenge aChapsChallenge) {
    chapsChallenge = aChapsChallenge;
    chapsBag = new HashMap<>();
    chapsBagImages = new ArrayList<>();

    setPreferredSize(new Dimension(GUI.dashboardWidth, DashboardHolder.dashboardHeight));

    setBackground(BACKGROUND_COLOUR);

    setLayout(new GridBagLayout());

    paddingOfBox = Math.min(getWidth() / 10, getHeight() / 60);

    centerAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);
    rightAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
    createDashboardComponents();
  }

  /**
   * Adds the components of the dashboard.
   * This consists of two JPanels and their related parts.
   */
  protected void renderDashboardComponents() {
    removeAll();
    // reset the GridBagConstraints
    GridBagConstraints constraints = new GridBagConstraints();

    /*
    CREATES THE TOP PANEL WITH THE THREE TITLES (LEVEL, TIME, CHIPSLEFT)
     */
    // Create top panel and set it's necessary settings
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridBagLayout());
    topPanel.setBackground(null);
    topPanel.setPreferredSize(new Dimension((getWidth() / 2) - (paddingOfBox / 2), (getHeight() * 2 / 3) - paddingOfBox));

    // Create a new GridBagLayout for the top panel
    GridBagConstraints topPanelConstraints = new GridBagConstraints();

    // Settings that don't change for all the following components added
    topPanelConstraints.fill = GridBagConstraints.BOTH;
    topPanelConstraints.weightx = 1;
    topPanelConstraints.weighty = 1;

    // Add the level text
    topPanelConstraints.gridy = 0;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(level, topPanelConstraints);
    // Add the level value
    topPanelConstraints.gridy = 1;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(levelNum, topPanelConstraints);
    // Add the time text
    topPanelConstraints.gridy = 2;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(time, topPanelConstraints);
    // Add the time value
    topPanelConstraints.gridy = 3;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(timeNum, topPanelConstraints);
    // Add the chipsLeft text
    topPanelConstraints.gridy = 4;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_END;
    topPanel.add(chipsLeft, topPanelConstraints);
    // Add the chipsLeft value
    topPanelConstraints.gridy = 5;
    topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
    topPanel.add(chipsLeftNum, topPanelConstraints);

    /*
    CREATES THE BOTTOM PANEL WITH ALL OF CHAPS ITEMS
     */

    // Create the bottom JPanel and set all relevant settings
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridBagLayout());
    bottomPanel.setBackground(null);
    bottomPanel.setPreferredSize(new Dimension(getWidth() - paddingOfBox, (getHeight() / 3) - paddingOfBox));

    // Create a new GridBagLayout for the bottom panel
    GridBagConstraints bottomPanelConstraints = new GridBagConstraints();

    // Setting the don't change for all components
    bottomPanelConstraints.fill = GridBagConstraints.BOTH;
    bottomPanelConstraints.gridx = 0;

    // Cycle through 8 blocks to create a new Label for each bag item
    for (int i = 0; i < 8; i++) {
      // Place this object in the list on a 4 * 2 grid
      bottomPanelConstraints.gridx = (i % 4);
      bottomPanelConstraints.gridy = (i / 4);
      bottomPanelConstraints.weightx = 1;
      bottomPanelConstraints.weighty = 1;

      // Add the panel
      if (chapsBagImages.get(i) != null)
        bottomPanel.add(chapsBagImages.get(i), bottomPanelConstraints);
    }


    constraints.weighty = 2;
    constraints.gridy = 0;
    // Add the topPanel with 2/3 of the space
    add(topPanel, constraints);
    // Underneath add the bottomPanel with 1/3 of the space
    constraints.weighty = 1;
    constraints.gridy = 1;
    add(bottomPanel, constraints);
  }

  public void createDashboardComponents() {
    // Create the level text. Center aligned
    level = new CustomTextPane("LEVEL", centerAlign, null, TEXT_COLOUR, false);
    // Create the level number text. Right aligned
    levelNum = new CustomTextPane("1", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);
    // Create the time text. Center aligned
    time = new CustomTextPane("TIME", centerAlign, null, TEXT_COLOUR, false);
    // Create the tie number text. Right aligned
    timeNum = new CustomTextPane(chapsChallenge.timeLeft() + "", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);
    // Create the chipsLeft text. Center aligned
    chipsLeft = new CustomTextPane("CHIPS LEFT", centerAlign, null, TEXT_COLOUR, false);
    // Create the chipsLeft number text. Right aligned
    chipsLeftNum = new CustomTextPane(chapsChallenge.getTreasures() + "", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);

    // Refresh chapsbag
    fillChapsBag();
  }


  public void refreshDashboardComponents() {
    // TODO: chaps challenge needs to have a function to get the level num
    levelNum.setText("1");
    timeNum.setText(chapsChallenge.timeLeft() + "");
    chipsLeftNum.setText(chapsChallenge.getTreasures() + "");

    // Refresh Chaps bag
    fillChapsBag();
  }

  private void fillChapsBag() {
    // Cycle through 8 blocks to create a new Label for each bag item
    chapsBag = new HashMap<>();
    chapsBagImages = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>(chapsChallenge.getPlayerInventory());

    // Find all duplicates
    for (int i = 0; i < items.size(); i++) {
      System.out.println(items);
      // Add a new item to the bag
      if (!chapsBag.containsKey(items.get(i)))
        chapsBag.put(items.get(i), 1);
      else
        chapsBag.put(items.get(i), chapsBag.get(items.get(i)) + 1);
    }

    // Add all the items combined with their image overlay of how many
    for (String s : chapsBag.keySet()) {
      // See if the parsed item exists
      try {
        JLabel item = new JLabel(AssetManager.getNumberedScaledImage(s, chapsBag.get(s)));
        item.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (6 * 2)));
        chapsBagImages.add(item);
      }
      // If the parsed items doesnt exist, leave it unknown
      catch (Exception e) {
        JLabel item = new JLabel(AssetManager.getScaledImage("unknown.png"));
        item.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (6 * 2)));
        chapsBagImages.add(item);
      }
    }

    // For all free slots in the bag
    for (int i = chapsBagImages.size(); i < 8; i++) {
      JLabel item = new JLabel(AssetManager.getScaledImage("free.png"));
      item.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (6 * 2)));
      chapsBagImages.add(item);
    }
  }

  /**
   * @return
   */
  private Dimension getTextBoxDimension() {
    // Box sizes using the aforementioned padding
    int boxWidth = (getWidth() / GRID_WIDTH);
    int boxHeight = (getHeight() / GRID_HEIGHT);
    return new Dimension(boxWidth, boxHeight);
  }

  /**
   * @param metrics
   * @param font
   * @param text
   * @return
   */
  private Dimension getFontSize(FontMetrics metrics, Font font, String text) {
    // get the height of a line of text in this font and render context
    int hgt = metrics.getHeight();
    // get the advance of my text in this font and render context
    int adv = metrics.stringWidth(text);
    // calculate the size of a box to hold the text with some padding.
    Dimension size = new Dimension(adv + 2, hgt + 2);
    return size;
  }

  /**
   * @param component
   * @param componentSize
   * @param oldFont
   * @param text
   * @return
   */
  private Font findFont(Component component, Dimension componentSize, Font oldFont, String text) {
    //search up to 100
    Font savedFont = oldFont;
    for (int i = 0; i < 150; i++) {
      Font newFont = new Font(savedFont.getFontName(), savedFont.getStyle(), i);
      Dimension d = getFontSize(component.getFontMetrics(newFont), newFont, text);
      if (componentSize.height - (componentSize.height / 3) < d.height) {
        return savedFont;
      }
      savedFont = newFont;
    }
    return oldFont;
  }

  /**
   * Private class to create multiple custom text panes for the DashBoard.
   */
  private class CustomTextPane extends JTextPane {

    /**
     * CustomTextPane Constructor, read params for instructions.
     *
     * @param text          - The Text in the text pane
     * @param textAlignment - The alignment of the text, if null, default used
     * @param background    - Color of the back ground
     * @param foreground    - Color of the foreground
     * @param border        - If true: Add a matte border of foreground color, if false, no border
     */
    private CustomTextPane(String text, SimpleAttributeSet textAlignment, Color background, Color foreground, boolean border) {
      // Basic setup:
      // - Not editable
      setEditable(false);
      // - Set the background color to the parsed in color
      setBackground(background);
      // - Set the foreground color to the parsed in color
      setForeground(foreground);

      Font style = new Font("Arial", Font.BOLD, 20);
      setFont(findFont(this, getTextBoxDimension(), style, text));

      // - If the border boolean parsed in is true, make one
      if (border) {
        setBorder(BorderFactory.createMatteBorder(getFont().getSize() / 10, getFont().getSize() / 10,
            getFont().getSize() / 10, getFont().getSize() / 10, foreground));
      }
      // - Else remove any presets
      else {
        setBorder(null);
      }

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
