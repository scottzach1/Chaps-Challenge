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
   * Creates all the components for the dashboard.
   * - This is separated such that the components are not needing to be
   * resized and recreated every update, only their values need to
   * be redone
   */
  public void createDashboardComponents() {
    removeAll();

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

  /**
   * Adds the components of the dashboard.
   * This consists of two JPanels and their related parts.
   */
  protected void renderDashboardComponents() {

    if (level == null || levelNum == null || time == null || timeNum == null || level == null || levelNum == null || chapsBagImages == null)
      createDashboardComponents();

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

  /**
   * Updates the components text within the dashboard
   */
  public void refreshDashboardComponents() {
    // If the components don't exist then ignore the command
    // Usually a resizing error will refresh the components before they're instantiated
    if (levelNum == null || timeNum == null || chipsLeftNum == null) {
      createDashboardComponents();
      return;
    }

    levelNum.setText(chapsChallenge.getLevel() + "");
    timeNum.setText(chapsChallenge.timeLeft() + "");
    chipsLeftNum.setText((chapsChallenge.getTotalTreasures() - chapsChallenge.getTreasures()) + "");

    // Refresh Chaps bag
    fillChapsBag();
  }

  /**
   * Attempts to fill chaps bag with the contents from ChapsChallenge.
   * - All duplicated are mapped to an integer of occurrences
   * - All occurrences are then sent to the asset manager to create an icon with a numerical
   * value below it.
   * - If chaps bag contains less than 8 items then all other slots are filled with blanks
   */
  private void fillChapsBag() {
    // Cycle through 8 blocks to create a new Label for each bag item
    chapsBag = new HashMap<>();
    ArrayList<String> items = new ArrayList<>(chapsChallenge.getPlayerInventory());


    // If chaps bag is null or empty, set it up with blank JLabels
    if (chapsBagImages == null || chapsBagImages.size() == 0) {
      fillChapsBagWithBlanks();
      return;
    }

    // Find all duplicates
    for (int i = 0; i < items.size(); i++) {
      // Add a new item to the bag if it doesn't already exist
      if (!chapsBag.containsKey(items.get(i)))
        chapsBag.put(items.get(i), 1);
        // If it exists in the bag, then increment the number associated with it by 1
      else
        chapsBag.put(items.get(i), chapsBag.get(items.get(i)) + 1);
    }

    // Add all the items combined with their image overlay of how many
    int i = 0;
    for (String s : chapsBag.keySet()) {
      JLabel item = chapsBagImages.get(i);

      // See if the parsed item exists
      item.setIcon(AssetManager.getNumberedScaledImage("free.png-" + s + ".png", chapsBag.get(s)));
      i++;
    }

    // Fill the rest of the bag with blanks (using j as i is predefined)
    for (int j = items.size(); j < 8; j++) {
      JLabel item = chapsBagImages.get(j);
      item.setIcon(AssetManager.getScaledImage("free.png"));
    }
  }


  /**
   * Fills chaps dashboard bag with blanks when no items exist in them.
   */
  private void fillChapsBagWithBlanks() {
    chapsBagImages = new ArrayList<>();
    if (getWidth() > 0)
      for (int i = 0; i < 8; i++) {
        JLabel item = new JLabel(AssetManager.getScaledImage("free.png"));
        item.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (6 * 2)));
        chapsBagImages.add(item);
      }
  }


  /**
   * Private class to create multiple custom JTextPanes for the DashBoard.
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
      // - Set the font, using a below method to find the font size
      setFont(findFont(this, new Font("Arial", Font.BOLD, 20), text));

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
          setText(text);
        }
      } else {
        setText(text);
      }
    }


    /**
     * @param component
     * @param oldFont
     * @param text
     * @return
     */
    private Font findFont(Component component, Font oldFont, String text) {
      // Get the size of the area the text can take up
      int boxWidth = (Dashboard.this.getWidth() / GRID_WIDTH);
      int boxHeight = (Dashboard.this.getHeight() / GRID_HEIGHT);
      Dimension componentSize = new Dimension(boxWidth, boxHeight);

      // The default size and text if no size is found to fit
      Font savedFont = oldFont;

      // Cycle through font sizes, from 0 to 150 to find a fitting size
      for (int i = 0; i < 150; i++) {
        // Create a new font to test on, incrementing it's size with i
        Font newFont = new Font(savedFont.getFontName(), savedFont.getStyle(), i);

        // Get the approximate relative size the text will take up
        FontMetrics metrics = component.getFontMetrics(newFont);
        int height = metrics.getHeight();
        int width = metrics.stringWidth(text);
        // calculate the size of a box to hold the text with some padding.
        Dimension d = new Dimension(width + 2, height + 2);

        //
        if (componentSize.height - (componentSize.height / 3) < d.height) {
          return savedFont;
        }
        savedFont = newFont;
      }

      // If no sizes fit, resort back to the given font size
      return oldFont;
    }
  }
}
