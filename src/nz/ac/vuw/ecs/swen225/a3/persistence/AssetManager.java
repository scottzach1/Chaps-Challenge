package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.renderer.CombinedImageIcon;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * AssetManager class is responsible for dealing with storing and scaling
 * all the assets used in the game.
 */
public class AssetManager {

  public static String assetPath = "assets/";
  private static boolean loaded = false;

  /**
   * Private static fields to store important GUI data.
   */
  private static Map<String, ImageIcon> baseImageIcons = new HashMap<>();
  private static Map<String, ImageIcon> scaledImageIcons = new HashMap<>();
  private static int cellSize = 10;

  /**
   * Clears all assets stored by the asset manager.
   */
  public static void clearAssets() {
    baseImageIcons.clear();
    scaledImageIcons.clear();
  }

  /**
   * Sets the path to look for assets.
   * @param path path to load assets.
   */
  public static void setAssetPath(String path) {
    assetPath = path;
  }

  /**
   * Loads asset from filename.
   * @param fname filename.
   */
  private static void loadAsset(String fname) {
    // Load unknown asset if first run.
    if (!loaded) {
      loaded = true;
      loadAsset("unknown.png");
    }

    fname = assetPath + fname;
    if (baseImageIcons.containsKey(fname)) return;

    // Load base image.
    ImageIcon baseIcon = new ImageIcon(fname);
    if (baseIcon.getIconWidth() <= 0 || baseIcon.getIconHeight() <= 0) {
      baseIcon = new ImageIcon(assetPath + "unknown.png");
    }

    // Load scaled image.
    ImageIcon scaledIcon = new ImageIcon(
        baseIcon.getImage().
            getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

    baseImageIcons.put(fname, baseIcon);
    scaledImageIcons.put(fname, scaledIcon);
  }

  /**
   * Rescales icons to new size.
   *
   * @param newCellSize Cell size.
   */
  public static void scaleImages(int newCellSize) {
    cellSize = newCellSize;
    for (String key : baseImageIcons.keySet()) {
      Image baseImage = baseImageIcons.get(key).getImage();
      ImageIcon scaledIcon = new ImageIcon(
          baseImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
      scaledImageIcons.put(key, scaledIcon);
    }
  }

  /**
   * Gets an ImageIcon at the last scaled size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImage(String fname) {
    loadAsset(fname); // Check asset exists.

    return scaledImageIcons.get(assetPath + fname);
  }

  /**
   * Gets an ImageIcon at the last scaled size,
   * with a number overlay.
   *
   * Number will be clipped from [1,9].
   *
   * @param fname file path.
   * @param number to overlay from [1,9].
   * @return ImageIcon.
   */
  public static ImageIcon getNumberedScaledImage(String fname, int number) {
    String nname = number + ".png";

    // Number clipping.
    number = Math.max(number, 9);
    number = Math.min(number, 1);

    // Check assets exist.
    loadAsset(fname);
    loadAsset(nname);

    // Get icons.
    ImageIcon baseIcon = scaledImageIcons.get(assetPath + fname);
    ImageIcon numberIcon = scaledImageIcons.get(assetPath + nname);

    // Return overlaid image.
    return new CombinedImageIcon(baseIcon, numberIcon);
  }

  /**
   * Gets an ImageIcon at the specified size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImageInstance(String fname, int newCellSize) {
    loadAsset(fname);

    // Get base image
    ImageIcon baseIcon = baseImageIcons.get(assetPath + fname);

    // Scale new image
    baseIcon = new ImageIcon(baseIcon.getImage()
        .getScaledInstance(newCellSize, newCellSize, Image.SCALE_SMOOTH));

    return baseIcon;
  }
}