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
  private static final Color BG = new Color(67, 65, 66);

  /**
   * Constructor: Populates JPanel with JLabels as buttons.
   */
  public PauseMenu(GUI gui) {
    JLabel pausedLabel = new JLabel("PAUSED");
    pausedLabel.setForeground(Color.WHITE);
    JPanel pausedPanel = new JPanel();
    pausedPanel.setOpaque(false);
    pausedPanel.add(pausedLabel);

    setBackground(BG);
    int eb = 15;
    setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
    setLayout(new GridLayout(0, 1, 10, 10));
    add(pausedPanel);

    // Add Resume button.
    add(new JButton(new CloseDialog("RESUME", gui) {
      /**
       * Closes menu and resumes game.
       * @param e event e.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        gui.resumeGame();
      }
    }));

    // Add Restart button.
    add(new JButton(new CloseDialog("RESTART", gui) {
      // TODO: Add restart functionality when present.
    }));

    // Add Exit button.
    add(new JButton(new CloseDialog("QUIT", gui) {
      /**
       * Quits game.
       * @param e event e.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    }));
  }

  /**
   * CloseDialog is an action to make the dialog no longer visible.
   */
  private static class CloseDialog extends AbstractAction {
    GUI gui;

    public CloseDialog(String name, GUI gui) {
      super(name);
      this.gui = gui;
    }

    /**
     * Closes dialogue on GUI.
     * @param e event e.
     */
    public void actionPerformed(ActionEvent e) {
      Component comp = (Component) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();  // here -- dispose of the JDialog
    }
  }
}