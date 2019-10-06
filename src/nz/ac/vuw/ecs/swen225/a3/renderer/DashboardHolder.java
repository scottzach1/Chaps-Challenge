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

  public static int dashboardHeight;

  public DashboardHolder(ChapsChallenge chapsChallenge) {
    dashboard = new Dashboard(chapsChallenge);

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());
    renderDashboard();
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
    removeAll();
    dashboard.createDashboardComponents();
    dashboard.renderDashboardComponents();
    renderDashboard();
  }
}
