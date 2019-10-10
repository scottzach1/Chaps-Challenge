package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

public class HelpMenu extends JPanel {

  private Gui gui;
  private ChapsChallenge application;
  private Color buttonForeground;
  private Color buttonBackground;
  private Color otherForeground;
  private Color otherBackground;

  private int buttonHeight, buttonWidth, titleWidth, titleHeight, textWidth, textHeight;

  private int pageNumber;

  private CustomTextPane title;
  private MenuButton next, prev;
  private CustomTextPane text;

  private SimpleAttributeSet centerAlign;


  public HelpMenu(ChapsChallenge aChapsChallenge) {
    application = aChapsChallenge;
    gui = application.getGui();

    pageNumber = 1;

    setSizes();

    centerAlign = new SimpleAttributeSet();
    StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);

    setPreferredSize(new Dimension(gui.getScreenWidth(), gui.getScreenHeight()));
    setBackground(Gui.BACKGROUND_COLOUR.darker());
    setLayout(new GridBagLayout());
    setVisible(true);

    buttonBackground = Gui.BACKGROUND_COLOUR.darker().darker();
    buttonForeground = new Color(193, 193, 193);
    otherBackground = buttonBackground.darker();
    otherForeground = buttonForeground.brighter();
  }

  private void setSizes(){
    buttonHeight = gui.getScreenHeight() / 16;
    buttonWidth = gui.getScreenWidth() / 8;

    titleWidth = gui.getScreenWidth() / 2;
    titleHeight = gui.getScreenHeight() / 8;

    textWidth = gui.getScreenWidth() / 2;
    textHeight = (gui.getScreenHeight() * 6) / 8;
  }

  public void createPageOne() {
    pageNumber = 1;
    title = new CustomTextPane("CHAPS CHALLENGE", centerAlign, null, buttonForeground, false, titleWidth, titleHeight);
    next = new MenuButton("NEXT", e -> gui.helpMenuPageTwo(), buttonWidth, buttonHeight);
    prev = new MenuButton("BACK", e -> {application.resumeGame(); application.restartLevel();}, buttonWidth, buttonHeight);
    text = new CustomTextPane(pageOneText, centerAlign, otherBackground, buttonForeground, false, textWidth, textHeight);
  }

  public void createPageTwo() {
    pageNumber = 2;
    title = new CustomTextPane("CONTROLS", centerAlign, null, buttonForeground, false, titleWidth, titleHeight);
    next = new MenuButton("BACK", e -> {application.resumeGame(); application.restartLevel();}, buttonWidth, buttonHeight);
    prev = new MenuButton("PREV", e -> gui.helpMenuPageOne(), buttonWidth, buttonHeight);
    text = new CustomTextPane(pageTwoText, centerAlign, null, buttonForeground, false, textWidth, textHeight);
  }

  public void renderPage() {
    if (title == null) {
      return;
    }

    removeAll();

    GridBagConstraints gbc = new GridBagConstraints();


    // Add the title to the top of the page
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.weightx = 4;
    gbc.weighty = 2;
    add(title, gbc);

    // Add the text to the middle of the page
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.weightx = 4;
    gbc.weighty = 20;
    add(text, gbc);


    // Add the left button
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(prev, gbc);

    // Add the right button
    gbc.gridx = 4;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(next, gbc);

    revalidate();
    repaint();
  }


  public void resize(){
    setSizes();
    switch (pageNumber){
      case 2:
        createPageTwo();
        break;
      default:
        createPageOne();
        break;
    }

    renderPage();
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
      setFont(findFont(this, new Dimension(width, height), new Font("Ariel", Font.BOLD, 30), name));

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
        Color foreground, boolean border, int width, int height) {

      // Basic setup:
      // - Not editable
      setEditable(false);
      // - Set the background color to the parsed in color
      setBackground(background);
      // - Set the foreground color to the parsed in color
      setForeground(foreground);
      // - Set the font, using a below method to find the font size
      setFont(findFont(this, new Dimension(width, height), new Font("Arial", Font.BOLD, 20), text));

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
  private Font findFont(Component component, Dimension componentSize, Font oldFont, String text) {
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


  String pageOneText = "The premise of the game is that friendly monster 'Chip' has"
      + "\nmet Melinda-the-Mental-Marvel at the scare school science laboratory. Chip must navigate "
      + "\nthrough Melinda's 'House Of Horrors', a series of increasingly difficult puzzles, "
      + "\nIn order to prove himself and gain membership to the very exclusive Bit Busters Club.\n"
      + "\n"
      + "Chip's Challenge consists of a series of two-dimensional levels which feature the player 'Chip'"
      + "\nand various game elements such as computer chips, locked doors, water and lethal bully monsters. "
      + "\nGameplay involves using arrow keys to move Chip about each of the levels in turn, collecting"
      + "\nenough chips to open the chip socket at the end of each level, get to the exit, and move on to the next level.";


  String pageTwoText = "CTRL-X  - exits the game, the current game state will be lost, the next time the "
      + "\n\tgame is started, it will resume from the last unfinished level\n"
      + "CTRL-S  - exit the game, saves the game state, game will resume next time the application will be started\n"
      + "CTRL-R  - resume a saved game\n"
      + "CTRL-P  - start a new game at the last unfinished level\n"
      + "CTRL-1 - start a new game at level 1\n"
      + "SPACE - pause the game and display a “game is paused” dialog\n"
      + "ESC - close the “game is paused” dialog and resume the game\n"
      + "UP, DOWN, LEFT, RIGHT ARROWS -- move Chap within the maze\n";
}
