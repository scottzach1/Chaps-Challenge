package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

/**
 * The side object of the GUI that displays the level, time, chipsLeft.
 */
public class Dashboard extends JPanel {

  // Colour Space.
  static final Color TEXT_COLOUR = new Color(0, 0, 0);
  static final Color ACCENT_COLOUR = new Color(0, 255, 0);
  static final Color BACKGROUND_COLOUR = new Color(192, 192, 192);


  private ArrayList<Component> components;

  /*
  DASHBOARD FIELDS
   */
  private final int GRID_WIDTH = 4, GRID_HEIGHT = 8;
  private ArrayList<JLabel> chapsBag;
  private ChapsChallenge chapsChallenge;

  /**
   * The Dashboard constructor.
   * sets preferred Size, background color, layout, adds a components listener and a key listener
   *
   * @param aChapsChallenge - The parent ChapsChallenge object the GUI to create this DashBoard
   */
  Dashboard(ChapsChallenge aChapsChallenge) {
    chapsChallenge = aChapsChallenge;

    setPreferredSize(new Dimension(GUI.dashboardWidth, DashboardHolder.dashboardHeight));

    setBackground(BACKGROUND_COLOUR);

    setLayout(new GridBagLayout());
  }

  /**
   * Adds the components of the dashboard.
   * This consists of two JPanels and their related parts.
   */
  protected void addComponents() {
    components = new ArrayList<>();
    removeAll();
    // reset the GridBagConstraints
    GridBagConstraints constraints = new GridBagConstraints();

    /*
    CREATE THE CONSTANTS FOR THE CUSTOM TEXT PANES
     */

    // Create the padding around the borders such that preferred sizes are not flush against the sides of the dash border
    int paddingOfBox = Math.min(getWidth() / 10, getHeight() / 60);

    // Create the alignment for the custom text
    // - CENTER Aligned
    SimpleAttributeSet centerAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);
    // - RIGHT Aligned
    SimpleAttributeSet rightAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);

    // CustomTextPane of the center aligned level text
    CustomTextPane level = new CustomTextPane("LEVEL", centerAlign, null, TEXT_COLOUR, false);
    // CustomTextPane of the right aligned level value
    CustomTextPane levelNum = new CustomTextPane("1", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);
    // CustomTextPane of the center aligned time text
    CustomTextPane time = new CustomTextPane("TIME", centerAlign, null, TEXT_COLOUR, false);
    // CustomTextPane of the right aligned time value
    CustomTextPane timeNum = new CustomTextPane(chapsChallenge.timeLeft() + "", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);
    // CustomTextPane of the center aligned chipsLeft text
    CustomTextPane chipsLeft = new CustomTextPane("CHIPS LEFT", centerAlign, null, TEXT_COLOUR, false);
    // CustomTextPane of the right aligned chipsLeft value
    CustomTextPane chipsLeftNum = new CustomTextPane(chapsChallenge.getPlayerInventory().size() + "", rightAlign, TEXT_COLOUR, ACCENT_COLOUR, true);

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
    // Fills chips bag with contents analysed in the ChapsChallenge object
    fillChapsBag();
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
      // Get the blank or content from chaps bag, and set it's preferred size
      JLabel j = chapsBag.get(i);
      j.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / (3 * 2)));

      // Place this object in the list on a 4 * 2 grid
      bottomPanelConstraints.gridx = (i % 4);
      bottomPanelConstraints.gridy = (i / 4);

      // Add the panel
      bottomPanel.add(j, bottomPanelConstraints);
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


  public void renderComponents(){
    revalidate();
    repaint();
  }


  /**
   * Given an arraylist of strings (asset names), their png will be added to
   * the content panel on the dashboard.
   */
  private void fillChapsBag() {
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

  private Dimension getTextBoxDimension(){
    // Box sizes using the aforementioned padding
    int boxWidth = (getWidth() / GRID_WIDTH);
    int boxHeight = (getHeight() / GRID_HEIGHT);
    return new Dimension(boxWidth, boxHeight);
  }

  private Dimension getFontSize(FontMetrics metrics, Font font, String text) {
    // get the height of a line of text in this font and render context
    int hgt = metrics.getHeight();
    // get the advance of my text in this font and render context
    int adv = metrics.stringWidth(text);
    // calculate the size of a box to hold the text with some padding.
    Dimension size = new Dimension(adv + 2, hgt + 2);
    return size;
  }

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
