package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * DashBoardHolder contains the dashboard such that it can be resized.
 */
public class DashboardHolder extends JPanel {

  private Dashboard dashboard;
  private GridBagConstraints gbc;
  private ChapsChallenge application;

  private int dashboardHeight;

  /**
   * Constructor creates a new dashboard but does no render.
   * @param chapsChallenge
   */
  public DashboardHolder(ChapsChallenge chapsChallenge) {
    application = chapsChallenge;

    setPreferredSize(new Dimension(GUI.dashboardWidth, GUI.screenHeight));

    dashboard = new Dashboard(chapsChallenge, this);

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());
  }

  /**
   * Recalculates and renders the dashboard
   */
  public void renderDashboard() {
    removeAll();
    dashboardHeight = AssetManager.getScaledImage("free.png").getIconHeight() * Canvas.VIEW_SIZE;

    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = dashboardHeight;
    gbc.weightx = 1;
    gbc.weighty = 1;
    dashboard.refreshDashboardComponents();
    add(dashboard, gbc);

    revalidate();
    repaint();
  }

  /**
   * Resize's the dashboard to the new calculated size.
   */
  public void resize() {
    dashboard.createDashboardComponents();
    dashboard.renderDashboardComponents();
    renderDashboard();
  }

  /**
   * Gets the height of the dashboard.
   * @return dashboard height.
   */
  protected int getDashboardHeight(){
    return dashboardHeight;
  }
}
