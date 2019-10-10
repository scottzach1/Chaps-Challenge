package nz.ac.vuw.ecs.swen225.a3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.ImageIcon;
import nz.ac.vuw.ecs.swen225.a3.persistence.AssetManager;

import org.junit.jupiter.api.Test;

/**
 * JUnit Tests: Checks all assets are loaded correctly.
 * @author Zac Scott 300447976
 */
class AssetTest {

  /**
   * Checks whether a particular asset is loaded correctly.
   *
   * @param fname asset name
   */
  void checkAsset(String fname) {
    AssetManager assetManager = new AssetManager();

    ImageIcon imageIcon = assetManager.getScaledImage(fname);

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
    ImageIcon invalidIcon = new AssetManager().getScaledImage("1234");

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
    AssetManager assetManager = new AssetManager();

    for (int i = -2; i < 12; ++i) {
      String num = (i > 0 && i < 10) ? "" + i : "nan";
      ImageIcon imageIcon = assetManager.getNumberedScaledImage("free.png", i);

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
      AssetManager assetManager = new AssetManager();
      assetManager.getScaledImage("free.png");

      try {
        assetManager.scaleImages(0);
      } catch (IllegalArgumentException e ) {
        fail("Program should not throw exception on 0 cell size.");
      }
    }

  /**
   * Checks AssetManager scales stored assets to specified sizes.
   */
  @Test
  void testScaleAssets1() {
    AssetManager assetManager = new AssetManager();

    assetManager.getScaledImage("free.png");
    assetManager.scaleImages(50);

    assertEquals(50, assetManager.getScaledImage("free.png").getIconHeight());
  }

  /**
   * Checks AssetManager scales new assets to previously specified sizes.
   */
  @Test
  void testScaleAssets2() {
    AssetManager assetManager = new AssetManager();

    assetManager.scaleImages(50);
    assetManager.getScaledImage("wall.png");

    assertEquals(50, assetManager.getScaledImage("wall.png").getIconHeight());
  }
}
