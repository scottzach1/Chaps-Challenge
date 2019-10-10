package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ComponentEvent;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.renderer.Gui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests some gui components.
 * Due to lack of JUnit support with Swing, there are
 * some components that cannot be tested. Generally
 * these are any methods that invoke a new dialog or
 * option pane. These pauses the JUnit test suite and
 * result in a Gui hang.
 *
 * Namely:
 * GUI.
 * * saveRecording()
 * * loadGame()
 * * noFileFound()
 * * exitGame()
 * * renderInfoField()
 * *
 *
 * @author Zac Scott 300447976
 */
class FrontendTest {

  @BeforeAll public static void setup(){
    BackendTest.testing = true;
  }

  /**
   * Tests Gui is loaded fully.
   */
  @Test
  void launchWindow() {
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    assertNotNull(chapsChallenge.getGui());
  }

  /**
   * Tests shown and hidden, resized often results in GUI hang on
   * JUnit. Have avoided checking.
   */
  @Test
  void componentEvents() {
    ChapsChallenge chapsChallenge = new ChapsChallenge();
    Gui gui = chapsChallenge.getGui();
    assertFalse(chapsChallenge.isGamePaused());

    // Disrupt JFrame, should pause
    gui.componentHidden(new ComponentEvent(gui, ComponentEvent.COMPONENT_HIDDEN));
    assertTrue(chapsChallenge.isGamePaused());

    // Show JFrame, should remain paused.
    gui.componentShown(new ComponentEvent(gui, ComponentEvent.COMPONENT_SHOWN));
    assertTrue(chapsChallenge.isGamePaused());
  }
}
