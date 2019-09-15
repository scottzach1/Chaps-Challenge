package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * PauseAction shows PauseMenu and darken rest of GUI
 * using GlassPane.
 *
 * Full Credit: https://stackoverflow.com/a/45778122/9621945
 */
class PauseAction extends AbstractAction {

  private static final int ALPHA = 175; // how much see-thru. 0 to 255
  private static final Color GP_BG = new Color(0, 0, 0, ALPHA);
  private PauseMenu pauseMenu = new PauseMenu();  // jpanel shown in JDialog
  private GUI gui;

  /**
   * Creates a pause action and stores GUI for convenience.
   * @param name action name.
   * @param gui main JFrame.
   */
  public PauseAction(String name, GUI gui) {
    super(name);
    this.gui = gui;
  }

  /**
   * Action invoked, load pause menu.
   * @param e event.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // create our glass pane
    JPanel glassPane = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        // magic to make it dark without side-effects
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
      }
    };
    glassPane.setOpaque(false);
    glassPane.setBackground(GP_BG);


    // set our glass pane
    gui.setGlassPane(glassPane);  // set the glass pane
    glassPane.setVisible(true);  // and show the glass pane

    // create a *modal* JDialog
    JDialog dialog = new JDialog(gui, "", Dialog.ModalityType.APPLICATION_MODAL);
    dialog.getContentPane().add(pauseMenu);  // add its JPanel to it
    dialog.setUndecorated(true); // Keep position relative to game.
    dialog.pack(); // size it
    dialog.setLocationRelativeTo(gui); // ** Center it over the JFrame **
    dialog.setVisible(true);  // display it, pausing the GUI below it

    // at this point the dialog is no longer visible, so get rid of glass pane
    glassPane.setVisible(false);
  }
}
