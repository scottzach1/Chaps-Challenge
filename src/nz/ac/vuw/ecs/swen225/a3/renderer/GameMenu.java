package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

/**
 * Pause menu is a jpanel that replaces the game screen with a menu of buttons the user can chose.
 *
 * @author Harrison Cook 300402048, Zac Scott 300447976.
 */
public class GameMenu extends JPanel {

  public enum MenuType {
    PAUSE, DEATH, TIMEOUT, WINNER, ERROR, QUITTER
  }

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;
  private ArrayList<MenuButton> menuButtons = new ArrayList<>();
  private CustomTextPane textPane;
  private int textPaneFontSize;
  private ChapsChallenge application;
  private Gui gui;
  private Color buttonForeground;
  private Color buttonBackground;
  private Color otherForeground;
  private Color otherBackground;
  private JPanel panel;
  private MenuType menuType;
  private MenuType oldMenuType;
  private SimpleAttributeSet centerAlign;

  /**
   * Constructor for the PauseMenu, by Default it is set to pause
   *
   * @param chapsChallenge - The chapsChallenge object for the game this object is in
   */
  GameMenu(ChapsChallenge chapsChallenge) {
    application = chapsChallenge;
    gui = application.getGui();
    menuType = MenuType.PAUSE;
    oldMenuType = MenuType.PAUSE;

    centerAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);

    setPreferredSize(new Dimension(gui.getScreenWidth(), gui.getScreenHeight()));
    setBackground(Gui.BACKGROUND_COLOUR.darker());
    setLayout(new BorderLayout());
    setVisible(true);

    buttonBackground = Gui.BACKGROUND_COLOUR.darker().darker();
    buttonForeground = new Color(193, 193, 193);
    otherBackground = buttonBackground.darker();
    otherForeground = buttonForeground.brighter();

    panel = new JPanel();
    panel.setPreferredSize(
        new Dimension(gui.getScreenWidth() / 4, gui.getScreenHeight() - (gui.getScreenHeight() / 10)));
    panel.setBackground(Gui.BACKGROUND_COLOUR);
    panel.setLayout(new GridBagLayout());
  }

  /**
   * Creates the buttons and text necessary for the pause menu
   */
  private void createPauseComponents() {
    removeAll();
    panel.removeAll();
    menuButtons.clear();

    // Create the width and height of the components
    int width = gui.getScreenWidth() / 2;
    int height = gui.getScreenHeight() / (3 * 2);

    // Create the buttons
    MenuButton resume = new MenuButton("Resume", e -> application.resumeGame(), width, height);

    MenuButton restart = new MenuButton("Restart", e -> {
      application.resumeGame();
      application.restartLevel();
    }, width, height);

    MenuButton quit = new MenuButton("Quit", e -> application.exitGame(), width, height);

    menuButtons.add(resume);
    menuButtons.add(restart);
    menuButtons.add(quit);

    // Create the text for the menu
    textPane = new CustomTextPane("PAUSED", centerAlign, null, buttonForeground, false);
  }

  /**
   * Creates the buttons and text necessary for the timeout menu
   */
  private void createOtherComponents() {
    removeAll();
    panel.removeAll();
    menuButtons.clear();

    // Create the width and height of the components
    int width = gui.getScreenWidth() / 2;
    int height = gui.getScreenHeight() / (3 * 2);

    MenuButton restartLevel = new MenuButton("Restart Level", e -> {
      application.resumeGame();
      application.restartLevel();
    }, width, height);

    MenuButton restartGame = new MenuButton("Restart Game", e -> {
      application.resumeGame();
      application.restartGame();
    }, width, height);

    MenuButton quit = new MenuButton("Quit", e -> application.exitGame(), width, height);

    menuButtons.add(restartLevel);
    menuButtons.add(restartGame);
    menuButtons.add(quit);

    // Create the text for the menu
    switch (menuType) {
      case TIMEOUT:
        textPane = new CustomTextPane("OUT OF TIME", centerAlign, null, buttonForeground, false);
        break;
      case DEATH:
        textPane = new CustomTextPane("YOU DIED", centerAlign, null, buttonForeground, false);
        break;
      case WINNER:
        textPane = new CustomTextPane("YOU WON !!!", centerAlign, null, buttonForeground, false);
        break;
      default:
        textPane = new CustomTextPane("An Error Occurred", centerAlign, null, buttonForeground, false);
        break;
    }
  }

  /**
   * Creates the buttons and text necessary for the timeout menu
   */
  private void createQuitComponents() {
    removeAll();
    panel.removeAll();
    menuButtons.clear();

    // Create the width and height of the components
    int width = gui.getScreenWidth() / 2;
    int height = gui.getScreenHeight() / (3 * 2);

    MenuButton yesButton = new MenuButton("Yes please", e -> System.exit(0), width, height);

    MenuButton noButton = new MenuButton("Opps, NO!", e -> application.resumeGame(), width, height);

    menuButtons.add(yesButton);
    menuButtons.add(noButton);

    // Create the text for the menu
    textPane = new CustomTextPane("QUIT?", centerAlign, null, buttonForeground, false);
  }


  /**
   * Renders the pause menu.
   */
  void renderMenu() {
    if (menuType != oldMenuType || textPane == null || menuButtons.size() <= 0) {
      switchMenu();
    }

    GridBagConstraints gbc = new GridBagConstraints();

    // Add the CustomTextPane
    gbc.weighty = 2;
    gbc.weightx = 4;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 4;
    panel.add(textPane, gbc);

    // Add the buttons
    gbc.gridx = 1;
    gbc.gridwidth = 2;
    gbc.weighty = 1;
    gbc.weightx = 2;

    for (int i = 0; i < menuButtons.size(); i++) {
      // Reset the color of the button
      menuButtons.get(i).setBackground(buttonBackground);
      menuButtons.get(i).setForeground(buttonForeground);
      gbc.gridy = i + 1;
      panel.add(menuButtons.get(i), gbc);
    }

    add(panel, BorderLayout.CENTER);
  }


  private void switchMenu() {
    switch (menuType){
      case PAUSE:
        createPauseComponents();
        break;
      case QUITTER:
        createQuitComponents();
        break;
      default:
        createOtherComponents();
        break;
    }
  }

  /**
   * Recalculates then resize components.
   */
  void resize() {
    switchMenu();
    renderMenu();
  }

  /**
   * Sets the type of menu for this object to display.
   *
   * @param mt - The menu type to change to
   */
  void setMenuType(MenuType mt) {
    menuType = mt;
  }


  /**
   * A Private class that extends JButton. Allows for implementing more than one type of Menu with
   * PauseMenu.
   */
  private class MenuButton extends JButton {

    /**
     * Default serial number.
     */
    private static final long serialVersionUID = 1L;


    /**
     * MenuButton Constructor
     *
     * @param name   - Name and text of the button
     * @param action - An ActionListener to perform
     * @param width  - The buttons preferred width
     * @param height - The buttons preferred height
     */
    MenuButton(String name, ActionListener action, int width, int height) {
      super(name);
      setBackground(buttonBackground);
      setForeground(buttonForeground);
      addActionListener(action);
      setPreferredSize(new Dimension(width, height));
      setFont(findFont(this, new Font("Ariel", Font.BOLD, 30), name));

      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          setBackground(otherBackground);
          setForeground(otherForeground);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          setBackground(buttonBackground);
          setForeground(buttonForeground);
        }
      });
    }
  }

  /**
   * Private class to create multiple custom JTextPanes for the DashBoard.
   */
  private class CustomTextPane extends JTextPane {

    /**
     * Default serial number.
     */
    private static final long serialVersionUID = 1L;


    /**
     * CustomTextPane Constructor, read params for instructions.
     *
     * @param text          - The Text in the text pane
     * @param textAlignment - The alignment of the text, if null, default used
     * @param background    - Color of the back ground
     * @param foreground    - Color of the foreground
     * @param border        - If true: Add a matte border of foreground color, if false, no border
     */
    private CustomTextPane(String text, SimpleAttributeSet textAlignment, Color background,
        Color foreground, boolean border) {

      // Basic setup:
      // - Not editable
      setEditable(false);
      // - Set the background color to the parsed in color
      setBackground(background);
      // - Set the foreground color to the parsed in color
      setForeground(foreground);
      // - Set the font, using a below method to find the font size
      setFont(new Font("Arial", Font.BOLD, textPaneFontSize));

      // - If the border boolean parsed in is true, make one
      if (border) {
        setBorder(
            BorderFactory.createMatteBorder(getFont().getSize() / 10, getFont().getSize() / 10,
                getFont().getSize() / 10, getFont().getSize() / 10, foreground));
      } else { //remove any presets
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
        } catch (Exception e) { // If the right alignment fails, just insert it

          setText(text);
        }
      } else {
        setText(text);
      }
    }
  }


  /**
   * Takes a given font and attempts to find the correct size given some parameters.
   *
   * @param component containing font.
   * @param oldFont   old font to change.
   * @param text      to display
   * @return font with correct size and values.
   */
  private Font findFont(Component component, Font oldFont, String text) {
    // Get the size of the area the text can take up
    int boxWidth = (gui.getScreenWidth() / 2);
    int boxHeight = (gui.getScreenHeight() / 6);
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
        textPaneFontSize = (int) (savedFont.getSize() * 1.5);
        return savedFont;
      }
      savedFont = newFont;
    }

    // If no sizes fit, resort back to the given font size
    return oldFont;
  }
}

