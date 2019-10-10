package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.ImageIcon;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import org.junit.jupiter.api.Test;

/**
 * JUnit Tests: Checks all assets are loaded correctly.
 * @author Zac Scott
 */
class AssetTest {

  /**
   * Checks whether a particular asset is loaded correctly.
   *
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
   * Checks unknown assets are loaded as unknown.png
   */
  @Test
  void checkInvalidFile() {
    AssetManager.clearAssets();

    ImageIcon invalidIcon = AssetManager.getScaledImage("1234");

    assertEquals("unknown.png", invalidIcon.getDescription());

    assertTrue(invalidIcon.getIconWidth() >= 0);
    assertTrue(invalidIcon.getIconHeight() >= 0);
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
   * Checks the number overlay assets can be loaded correctly.
   */
  @Test
  void testNumbers() {
    AssetManager.clearAssets();

    for (int i = -2; i < 12; ++i) {
      String num = (i > 0 && i < 10) ? "" + i : "NaN";
      ImageIcon imageIcon = AssetManager.getNumberedScaledImage("free.png", i);

      assertEquals("free.png-" + num + ".png", imageIcon.getDescription());

      assertTrue(imageIcon.getIconWidth() >= 0);
      assertTrue(imageIcon.getIconHeight() >= 0);
    }
  }

    /**
     * Checks for no Illegal Arg exception on cellSize = 0.
     */
    @Test
    void testCellSize0() {
      checkAsset("free.png");

      try {
        AssetManager.scaleImages(0);
      } catch (IllegalArgumentException e ) {
        fail("Program should not throw exception on 0 cell size.");
      }
    }

  /**
   * Checks for no Illegal Arg exception on cellSize = 0.
   */
  @Test
  void testScaledInstance() {
    AssetManager.clearAssets();

    ImageIcon imageIcon = AssetManager.getScaledImageInstance("free.png", 50);

    assertEquals(imageIcon.getIconWidth(), 50);
    assertEquals(imageIcon.getIconHeight(), 50);
  }

  /**
   * Checks AssetManager scales stored assets to specified sizes.
   */
  @Test
  void testScaleAssets1() throws InterruptedException {
    // Sleep to allow concurrent test windows to finish scaling.
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    AssetManager.clearAssets();

    AssetManager.getScaledImage("free.png");
    AssetManager.scaleImages(50);

    assertEquals(50, AssetManager.getScaledImage("free.png").getIconHeight());
  }

  /**
   * Checks AssetManager scales new assets to previously specified sizes.
   */
  @Test
  void testScaleAssets2() {
    AssetManager.clearAssets();

    AssetManager.scaleImages(50);
    AssetManager.getScaledImage("wall.png");

    assertEquals(50, AssetManager.getScaledImage("wall.png").getIconHeight());
  }
}
