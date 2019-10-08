package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ComponentEvent;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.renderer.Gui;

import org.junit.jupiter.api.Test;


class FrontendTest {

  @Test
  void launchWindow() {
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    assertNotNull(chapsChallenge.getGui());
  }

  @Test
  void componentEvents() {
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    Gui gui = chapsChallenge.getGui();

    assertFalse(chapsChallenge.isGamePaused());
    gui.componentHidden(new ComponentEvent(gui, ComponentEvent.COMPONENT_HIDDEN));
    assertTrue(chapsChallenge.isGamePaused());
    gui.componentShown(new ComponentEvent(gui, ComponentEvent.COMPONENT_SHOWN));
    assertFalse(chapsChallenge.isGamePaused());
  }

}
