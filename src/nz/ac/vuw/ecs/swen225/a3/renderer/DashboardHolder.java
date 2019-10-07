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
    setPreferredSize(new Dimension(GUI.dashboardWidth, GUI.screenHeight));
    dashboard = new Dashboard(chapsChallenge);

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());
  }

  public void renderDashboard() {
    removeAll();
    dashboardHeight = AssetManager.getScaledImage("free.png").getIconHeight() * Canvas.VIEW_SIZE;


    dashboard.refreshDashboardComponents();

    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = dashboardHeight;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(dashboard, gbc);
    revalidate();
    repaint();
  }

  public void resize() {
    dashboard.createDashboardComponents();
    dashboard.renderDashboardComponents();
    dashboard.refreshDashboardComponents();
    renderDashboard();
  }
}
