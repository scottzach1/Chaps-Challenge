package nz.ac.vuw.ecs.swen225.a3.persistence;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
   * Sets the path to look for assets.
   * @param path path to load assets.
   */
  public static void setAssetPath(String path) {
    assetPath = path;
  }

  /**
   * NOTE: THIS ONLY WORKS ON WINDOWS.
   * TO UTILISE ON MAC REPLACE "assets\\" WITH "assets/".
   * Finds all files in the assets/ directory.
   * If unable to read files in the directory an IOException will be thrown.
   */
  public static void loadAssets() throws IOException {
    // Load files from assets/ into baseImageIcons.
    Files.walk(Paths.get("assets\\"))
        .filter(Files::isRegularFile)
        .map(Path::toString)
        .filter(f -> f.endsWith(".png"))
        .map(f -> f.replace("\\", "/"))
        .forEach(f -> {
          ImageIcon imageIcon = new ImageIcon(f);
          baseImageIcons.put(f, imageIcon);
          scaledImageIcons.put(f, imageIcon);
        });
  }

  /**
   * Loads asset from filename.
   * @param fname filename.
   */
  public static void loadAsset(String fname) {
    fname = assetPath + fname;
    if (baseImageIcons.containsKey(fname)) return;

    ImageIcon imageIcon = new ImageIcon(fname);
    if (imageIcon.getIconWidth() <= 0 || imageIcon.getIconHeight() <= 0)
      imageIcon = new ImageIcon(assetPath + "unknown.png");

    baseImageIcons.put(fname, imageIcon);
    scaledImageIcons.put(fname, imageIcon);

    // Load unknown asset if first run.
    if (!loaded) {
      loaded = true;
      loadAsset("unknown.png");
    }
  }

  /**
   * Rescales icons to new size.
   *
   * @param newCellSize Cell size.
   */
  public static void scaleImages(int newCellSize) {
    if (cellSize == newCellSize) return;
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
    fname = assetPath + fname;

    ImageIcon scaledIcon = scaledImageIcons.get(fname);
    if (scaledIcon == null) {
      scaledIcon = scaledImageIcons.get(assetPath + "unknown.png");
    }
    return scaledIcon;
  }

  /**
   * Gets an ImageIcon at the last scaled size,
   * with a number overlay.
   */
  public static ImageIcon getNumberedScaledImage(String fname) {
    // TODO: Make it add the number overlay.
    fname = assetPath + fname;

    ImageIcon scaledIcon = scaledImageIcons.get(fname);
    if (scaledIcon == null) {
      scaledIcon = scaledImageIcons.get(assetPath + "unknown.png");
    }
    return scaledIcon;
  }

  /**
   * Gets an ImageIcon at the specified size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImageInstance(String fname, int newCellSize) {
    fname = assetPath + fname;

    ImageIcon baseIcon = baseImageIcons.get(fname);
    if (baseIcon == null) {
      baseIcon = baseImageIcons.get(assetPath + "unknown.png");
    }

    try {
      baseIcon = new ImageIcon(baseIcon.getImage()
          .getScaledInstance(newCellSize, newCellSize, Image.SCALE_SMOOTH));
    } catch (IllegalArgumentException e) {
      baseIcon = scaledImageIcons.get(fname);
    }

    return baseIcon;
  }
}