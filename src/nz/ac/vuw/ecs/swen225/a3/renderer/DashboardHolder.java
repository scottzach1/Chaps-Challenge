package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class DashboardHolder extends JPanel {

  private Dashboard dashboard;
  private GridBagConstraints gbc;
  private ChapsChallenge application;

  private int dashboardHeight;

  public DashboardHolder(ChapsChallenge chapsChallenge) {
    application = chapsChallenge;

    setPreferredSize(new Dimension(GUI.dashboardWidth, GUI.screenHeight));

    dashboard = new Dashboard(chapsChallenge, this);

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());
  }

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

  public void resize() {
    dashboard.createDashboardComponents();
    dashboard.renderDashboardComponents();
    renderDashboard();
  }

  protected int getDashboardHeight(){
    return dashboardHeight;
  }
}
