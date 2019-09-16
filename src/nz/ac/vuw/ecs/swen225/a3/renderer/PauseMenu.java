package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;

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
  PauseMenu(ChapsChallenge application) {
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
    add(new JButton(new CloseDialog("RESUME", application) {
      /**
       * Closes menu and resumes game.
       * @param e event e.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        application.resumeGame();
      }
    }));

    // Add Restart button.
    add(new JButton(new CloseDialog("RESTART", application) {
      /**
       * Closes menu and restarts game.
       * @param e event e.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        application.restartGame();
      }
    }));

    // Add Exit button.
    add(new JButton(new CloseDialog("QUIT", application) {
      /**
       * Quits game.
       * @param e event e.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        application.exitGame();
      }
    }));
  }

  /**
   * CloseDialog is an action to make the dialog no longer visible.
   */
  private static class CloseDialog extends AbstractAction {
    CloseDialog(String name, ChapsChallenge application) {
      super(name);
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