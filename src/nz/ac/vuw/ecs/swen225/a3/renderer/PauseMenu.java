package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * PauseMenu is a small JPanel that hosts buttons the
 * user can interact with whilst the game is paused.
 *
 * Full Credit: https://stackoverflow.com/a/45778122/9621945
 */
class PauseMenu extends JPanel {
  private static final Color BG = new Color(123, 63, 0);

  /**
   * Constructor: Populates JPanel with JLabels as buttons.
   */
  public PauseMenu() {
    JLabel pausedLabel = new JLabel("PAUSED");
    pausedLabel.setForeground(Color.ORANGE);
    JPanel pausedPanel = new JPanel();
    pausedPanel.setOpaque(false);
    pausedPanel.add(pausedLabel);

    setBackground(BG);
    int eb = 15;
    setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
    setLayout(new GridLayout(0, 1, 10, 10));
    add(pausedPanel);
    add(new JButton(new CloseDialog("RESUME")));
    add(new JButton(new CloseDialog("RESTART")));
    add(new JButton(new CloseDialog("EXIT TO MAP")));
  }

  /**
   * CloseDialog is an action to make the dialog no longer visible.
   */
  private class CloseDialog extends AbstractAction {
    public CloseDialog(String name) {
      super(name);
    }

    /**
     * Action performed.
     * @param e event e.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      Component comp = (Component) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();  // here -- dispose of the JDialog
    }
  }
}