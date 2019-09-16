package nz.ac.vuw.ecs.swen225.a3.renderer;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class DashboardHolder extends JPanel implements ComponentListener {

  private Dashboard dashboard;
  private GridBagConstraints gbc;

  public static int dashboardHeight;

  public DashboardHolder(Dashboard aDashboard){
    dashboard = aDashboard;

    setBackground(null);
    setForeground(null);
    setBorder(null);
    setLayout(new GridBagLayout());

    addComponentListener(this);

    renderDashboard();
  }

  public void renderDashboard(){
    dashboardHeight = AssetManager.getScaledImage("assets/free.png").getIconHeight() * Canvas.VIEW_SIZE;

    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = dashboardHeight;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(dashboard, gbc);
    dashboard.renderComponents();
  }

  @Override
  public void componentResized(ComponentEvent e) {
    removeAll();
    renderDashboard();
    revalidate();
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
}
