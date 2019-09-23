package nz.ac.vuw.ecs.swen225.a3.tests;

import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit Tests: Checks all assets are loaded correctly.
 */
class testAssets {

  /**
   * Checks whether a particular asset is loaded correctly.
   * @param fname of file.
   */
  public void checkAsset(String fname) {

    AssetManager.loadAsset(fname);

    ImageIcon imageIcon = AssetManager.getScaledImage(fname);

    assertEquals(AssetManager.ASSET_PATH + fname, imageIcon.getDescription());

    assertTrue(imageIcon.getIconWidth() >= 0);
    assertTrue(imageIcon.getIconHeight() >= 0);
  }

  @Test
  void checkUnknown() {
    checkAsset("unknown.png");
  }

  @Test
  void checkFree() {
    checkAsset("free.png");
  }

  @Test
  void checkWall() {
    checkAsset("wall.png");
  }

  @Test
  void checkChap() {
    checkAsset("chap_front.png");
    checkAsset("chap_left.png");
    checkAsset("chap_back.png");
    checkAsset("chap_right.png");
  }

  @Test
  void checkKey() {
    checkAsset("key_green.png");
    checkAsset("key_red.png");
    checkAsset("key_blue.png");
    checkAsset("key_yellow.png");
  }

  @Test
  void checkDoor() {
    checkAsset("locked_door_green.png");
    checkAsset("locked_door_blue.png");
    checkAsset("locked_door_yellow.png");
    checkAsset("locked_door_red.png");
  }

  @Test
  void checkWater() {
    checkAsset("water.png");
  }

  @Test
  void checkFlippers() {
    checkAsset("flippers.png");
  }
}
