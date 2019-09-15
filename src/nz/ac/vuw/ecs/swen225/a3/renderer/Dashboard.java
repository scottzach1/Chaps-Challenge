package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Dashboard extends JPanel implements ComponentListener {


  private final int GRID_WIDTH = 4, GRID_HEIGHT = 8;
  private final JLabel[] BLANKS;
  private JLabel[] chipsBag;
  GridBagConstraints constraints = new GridBagConstraints();

  public Dashboard() {
    chipsBag = new JLabel[8];
    BLANKS = new JLabel[8];
    setBlanks();

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


    constraints.weightx = 1;

    
    constraints.gridx = 1;
    constraints.weighty = 5;
    constraints.gridwidth = GRID_WIDTH / 2;

    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.PAGE_END;
    add(level, constraints);

    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.PAGE_START;
    add(levelNum, constraints);

    constraints.gridy = 2;
    constraints.anchor = GridBagConstraints.PAGE_END;
    add(time, constraints);

    constraints.gridy = 3;
    constraints.anchor = GridBagConstraints.PAGE_START;
    add(timeNum, constraints);

    constraints.gridy = 4;
    constraints.anchor = GridBagConstraints.PAGE_END;
    add(chipsLeft, constraints);

    constraints.gridy = 5;
    constraints.anchor = GridBagConstraints.PAGE_START;
    add(chipsLeftNum, constraints);


    constraints.anchor = GridBagConstraints.PAGE_END;
    constraints.weighty = 1;
    constraints.gridwidth = 1;
    int size = Math.min((getWidth() / GRID_WIDTH) - paddingOfBox, (getHeight() / GRID_HEIGHT) - paddingOfBox);
    for (int col = 0; col < 4; col++){
      JLabel content = chipsBag[col];
      content.setPreferredSize(new Dimension(size, size));
      constraints.gridx = col;
      constraints.gridy = 6;
      add(content, constraints);
    }


    revalidate();
  }


  public void fillChipsBag(ArrayList<String> contents) {
    if (contents == null) {
      chipsBag = Arrays.copyOf(BLANKS, BLANKS.length);
      return;
    }

    for (int i = 0; i < chipsBag.length; i++) {
      try {
        JLabel content = new JLabel();
        content.setIcon(new ImageIcon("assets/" + contents.get(i) + ".png"));
        chipsBag[i] = content;
      } catch (Exception e) {
        chipsBag[i] = BLANKS[i];
      }
    }
  }

  private void setBlanks() {
    for (int i = 0; i < BLANKS.length; i++) {
      JLabel blank = new JLabel();
      blank.setIcon(new ImageIcon("assets/free.png"));
      BLANKS[i] = blank;
      chipsBag[i] = blank;
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

      // - Set the the size of the box to what is given
      setPreferredSize(new Dimension(width, height));
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
