package nz.ac.vuw.ecs.swen225.a3.tests;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit Tests: Checks all assets are loaded correctly.
 */
class assetTest {

  /**
   * Checks whether a particular asset is loaded correctly.
   * @param fname asset name
   */
  public void checkAsset(String fname) {
    AssetManager.clearAssets();

    ImageIcon imageIcon = AssetManager.getScaledImage(fname);

    assertEquals(fname, imageIcon.getDescription());

    assertTrue(imageIcon.getIconWidth() >= 0);
    assertTrue(imageIcon.getIconHeight() >= 0);
  }

  /**
   * Checks the unknown asset can be loaded correctly.
   */
  @Test
  void checkUnknown() {
    checkAsset("unknown.png");
  }

  /**
   * Checks the free asset can be loaded correctly.
   */
  @Test
  void checkFree() {
    checkAsset("free.png");
  }

  /**
   * Checks the wall asset can be loaded correctly.
   */
  @Test
  void checkWall() {
    checkAsset("wall.png");
  }

  /**
   * Checks the Chap assets can be loaded correctly.
   */
  @Test
  void checkChap() {
    checkAsset("chap_front.png");
    checkAsset("chap_left.png");
    checkAsset("chap_back.png");
    checkAsset("chap_right.png");
  }

  /**
   * Checks the key assets can be loaded correctly.
   */
  @Test
  void checkKey() {
    checkAsset("key_green.png");
    checkAsset("key_red.png");
    checkAsset("key_blue.png");
    checkAsset("key_yellow.png");
  }

  /**
   * Checks the door assets can be loaded correctly.
   */
  @Test
  void checkDoor() {
    checkAsset("locked_door_green.png");
    checkAsset("locked_door_blue.png");
    checkAsset("locked_door_yellow.png");
    checkAsset("locked_door_red.png");
  }

  /**
   * Checks the water asset can be loaded correctly.
   */
  @Test
  void checkWater() {
    checkAsset("water.png");
  }

  /**
   * Checks the flipper asset can be loaded correctly.
   */
  @Test
  void checkFlippers() {
    checkAsset("flippers.png");
  }

  /**
   * Checks the number overlay assets can be loaded correctly.
   */
  @Test
  void testNumbers() {
    AssetManager.clearAssets();

    for (int i=-2; i<12; ++i) {
      String num = (i > 0 && i < 10) ? "" + i : "NaN";
      ImageIcon imageIcon = AssetManager.getNumberedScaledImage("free.png", i);

      assertEquals("free.png_" + num + ".png", imageIcon.getDescription());

      assertTrue(imageIcon.getIconWidth() >= 0);
      assertTrue(imageIcon.getIconHeight() >= 0);
    }
  }
}
