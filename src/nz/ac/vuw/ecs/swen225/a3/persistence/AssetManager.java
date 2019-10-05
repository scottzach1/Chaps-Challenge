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

  private static String assetPath = "assets/";
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
    loaded = false;
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

    if (baseImageIcons.containsKey(fname)) return;

    // Load base image.
    ImageIcon baseIcon = new ImageIcon(assetPath + fname);
    baseIcon.setDescription(fname);
    if (baseIcon.getIconWidth() <= 0 || baseIcon.getIconHeight() <= 0) {
      baseIcon = new ImageIcon(assetPath + "unknown.png");
      baseIcon.setDescription("unknown.png");
    }

    // Load scaled image.
    ImageIcon scaledIcon = new ImageIcon(
        baseIcon.getImage().
            getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
    scaledIcon.setDescription(baseIcon.getDescription());

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
      ImageIcon baseImage = baseImageIcons.get(key);
      ImageIcon scaledIcon = new ImageIcon(baseImage.getImage()
          .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
      scaledIcon.setDescription(baseImage.getDescription());
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

    return scaledImageIcons.get(fname);
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
    // Number clipping.
    String nname = ((number < 10 && number > 0) ? number : "NaN") + ".png";

    // Check assets exist.
    loadAsset(fname);
    loadAsset(nname);

    // Get icons.
    ImageIcon baseIcon = scaledImageIcons.get(fname);
    ImageIcon numberIcon = scaledImageIcons.get(nname);

    // Return overlaid image.
    ImageIcon combinedIcon = new CombinedImageIcon(baseIcon, numberIcon);
    combinedIcon.setDescription(fname + "_" + nname);
    return combinedIcon;
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
    ImageIcon baseIcon = baseImageIcons.get(fname);
    String desc = baseIcon.getDescription();

    // Scale new image
    baseIcon = new ImageIcon(baseIcon.getImage()
        .getScaledInstance(newCellSize, newCellSize, Image.SCALE_SMOOTH));

    baseIcon.setDescription(desc);
    return baseIcon;
  }
}