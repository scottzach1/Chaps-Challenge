package nz.ac.vuw.ecs.swen225.a3.renderer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

/**
 * DashBoardHolder contains the dashboard such that it can be resized.
 * @author Harrison Cook 300402048.
 */
public class DashboardHolder extends JPanel {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;
  private Dashboard dashboard;
  private ChapsChallenge application;
  private int dashboardHeight;

  /**
   * Constructor creates a new dashboard but does no render.
   */
  DashboardHolder(ChapsChallenge chapsChallenge) {
    application = chapsChallenge;
    Gui gui = application.getGui();
    setPreferredSize(new Dimension(gui.getDashboardWidth(), gui.getScreenHeight()));

    dashboard = new Dashboard(chapsChallenge, this);

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());
  }

  /**
   * Recalculates and renders the dashboard.
   */
  void renderDashboard() {
    removeAll();
    dashboardHeight = AssetManager.getScaledImage("free.png").getIconHeight() * Canvas.VIEW_SIZE;

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = dashboardHeight;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(dashboard, gbc);
    refreshDashboard();
  }

  void refreshDashboard(){
    dashboard.refreshDashboardComponents();
    revalidate();
    repaint();
  }

  /**
   * Resize's the dashboard to the new calculated size.
   */
  void resize() {
    dashboard.createDashboardComponents();
    dashboard.renderDashboardComponents();
    renderDashboard();
  }

  /**
   * Gets the height of the dashboard.
   *
   * @return dashboard height.
   */
  int getDashboardHeight() {
    return dashboardHeight;
  }
}
