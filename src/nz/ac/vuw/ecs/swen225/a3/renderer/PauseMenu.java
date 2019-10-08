package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

/**
 * Pause menu is a jpanel that replaces the game screen with a menu of buttons the user can chose.
 * @author Harrison Cook.
 */
class PauseMenu extends JPanel {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;
  private ArrayList<JButton> components = new ArrayList<>();
  private ChapsChallenge application;
  private Color foreground;
  private Color background;
  private Color otherForeground;
  private Color otherBackground;
  private JPanel panel;

  PauseMenu(ChapsChallenge chapsChallenge) {
    application = chapsChallenge;

    setPreferredSize(new Dimension(Gui.screenWidth, Gui.screenHeight));
    setBackground(new Color(18, 48, 55));
    setLayout(new BorderLayout());
    setVisible(true);

    background = new Color(52, 67, 63);
    foreground = new Color(193, 193, 193);
    otherBackground = background.brighter().brighter();
    otherForeground = foreground.darker().darker();

    panel = new JPanel();
    panel.setPreferredSize(
        new Dimension(Gui.screenWidth / 4, Gui.screenHeight - (Gui.screenHeight / 10)));
    panel.setBackground(getBackground().brighter().brighter());
    panel.setLayout(new GridBagLayout());
  }

  void createComponents() {
    removeAll();
    components.clear();
    // Create the buttons
    JButton resume = new JButton("Resume");
    JButton restart = new JButton("Restart");
    JButton quit = new JButton("Quit");

    // Size the buttons
    resume.setPreferredSize(new Dimension(Gui.screenWidth / 2, Gui.screenHeight / 6));
    restart.setPreferredSize(new Dimension(Gui.screenWidth / 2, Gui.screenHeight / 6));
    quit.setPreferredSize(new Dimension(Gui.screenWidth / 2, Gui.screenHeight / 6));

    // Size the font of th buttons
    resume.setFont(findFont(this, new Font("Ariel", Font.BOLD, 30), resume.getText()));
    restart.setFont(findFont(this, new Font("Ariel", Font.BOLD, 30), restart.getText()));
    quit.setFont(findFont(this, new Font("Ariel", Font.BOLD, 30), quit.getText()));

    // Set their colors
    resume.setBackground(background);
    resume.setForeground(foreground);
    restart.setBackground(background);
    restart.setForeground(foreground);
    quit.setBackground(background);
    quit.setForeground(foreground);

    // Add their action listeners
    resume.addActionListener(e -> application.resumeGame());

    restart.addActionListener(e -> {
      application.resumeGame();
      application.restartGame();
    });

    quit.addActionListener(e -> application.exitGame());

    // Add mouse listeners
    resume.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        resume.setBackground(otherBackground);
        resume.setForeground(otherForeground);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        resume.setBackground(background);
        resume.setForeground(foreground);
      }
    });
    restart.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        restart.setBackground(otherBackground);
        restart.setForeground(otherForeground);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        restart.setBackground(background);
        restart.setForeground(foreground);
      }
    });
    quit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        quit.setBackground(otherBackground);
        quit.setForeground(otherForeground);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        quit.setBackground(background);
        quit.setForeground(foreground);
      }
    });

    components.add(resume);
    components.add(restart);
    components.add(quit);
  }


  /**
   * Renders the pause menu.
   */
  void renderPauseMenu() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridx = 0;

    for (int i = 0; i < components.size(); i++) {
      components.get(i).setBackground(background);
      components.get(i).setForeground(foreground);
      gbc.gridy = i;
      panel.add(components.get(i), gbc);
    }

    add(panel, BorderLayout.CENTER);
  }


  /**
   * Recalculates then resize components.
   */
  void resize() {
    createComponents();
    renderPauseMenu();
  }

  /**
   * Takes a given font and attempts to find the correct size given some parameters.
   *
   * @param component containing font.
   * @param oldFont old font to change.
   * @param text to display
   * @return font with correct size and values.
   */
  private Font findFont(Component component, Font oldFont, String text) {
    // Get the size of the area the text can take up
    int boxWidth = (Gui.screenWidth / 2);
    int boxHeight = (Gui.screenHeight / 6);
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
