package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PauseMenu extends JPanel {

  private ArrayList<JButton> components = new ArrayList<>();
  private ChapsChallenge application;
  private Color foreground, background, otherForeground, otherBackground;
  private JPanel panel;

  public PauseMenu(ChapsChallenge aChapsChallenge){
    application = aChapsChallenge;


    setPreferredSize(new Dimension(GUI.screenWidth, GUI.screenHeight));
    setBackground(new Color(18, 48, 55));
    setLayout(new BorderLayout());
    setVisible(true);

    background = new Color(52, 67, 63);
    foreground = new Color(193, 193, 193);
    otherBackground = background.brighter().brighter();
    otherForeground = foreground.darker().darker();

    panel = new JPanel();
    panel.setPreferredSize(new Dimension(GUI.screenWidth / 4, GUI.screenHeight - (GUI.screenHeight / 10)));
    panel.setBackground(getBackground().brighter().brighter());
    panel.setLayout(new GridBagLayout());
  }

  public void createComponents(){
    removeAll();
    components.clear();
    // Create the buttons
    JButton resume = new JButton("Resume");
    JButton restart = new JButton("Restart");
    JButton quit = new JButton("Quit");

    // Size the buttons
    resume.setPreferredSize(new Dimension(GUI.screenWidth / 2, GUI.screenHeight / 6));
    restart.setPreferredSize(new Dimension(GUI.screenWidth / 2, GUI.screenHeight / 6));
    quit.setPreferredSize(new Dimension(GUI.screenWidth / 2, GUI.screenHeight / 6));

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
    resume.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        application.resumeGame();
      }
    });

    restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        application.resumeGame();
        application.restartGame();
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        application.exitGame();
      }
    });

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


  public void renderPauseMenu(){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridx = 0;

    for (int i = 0; i < components.size(); i++){
      gbc.gridy = i;
      panel.add(components.get(i), gbc);
    }

    add(panel, BorderLayout.CENTER);
  }


  public void resize(){
    createComponents();
    renderPauseMenu();
  }
  /**
   * @param component
   * @param oldFont
   * @param text
   * @return
   */
  private Font findFont(Component component, Font oldFont, String text) {
    // Get the size of the area the text can take up
    int boxWidth = (GUI.screenWidth / 2);
    int boxHeight = (GUI.screenHeight / 6);
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
