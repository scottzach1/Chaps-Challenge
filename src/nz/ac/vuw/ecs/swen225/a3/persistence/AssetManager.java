package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.renderer.Canvas;

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

  public static final String ASSET_PATH = "assets/";

  /**
   * Private static fields to store important GUI data.
   */
  private static Map<String, ImageIcon> baseImageIcons = new HashMap<>();
  private static Map<String, ImageIcon> scaledImageIcons = new HashMap<>();
  private static int cellSize = 10;

  /**
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
   * Finds asset
   */
  public static void loadAsset(String fname) {
    fname = ASSET_PATH + fname;
    if (baseImageIcons.containsKey(fname)) return;

    ImageIcon imageIcon = new ImageIcon(fname);
    if (imageIcon.getIconWidth() <= 0 || imageIcon.getIconHeight() <= 0)
      imageIcon = new ImageIcon(ASSET_PATH + "unknown.png");

    baseImageIcons.put(fname, imageIcon);
    scaledImageIcons.put(fname, imageIcon);
  }

  /**
   * Rescales icons to new size.
   *
   * @param newCellSize Cell size.
   */
  public static void scaleIcons(int newCellSize) {
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
   * Gets an ImageIcon and the last scaled size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImage(String fname) {
    fname = ASSET_PATH + fname;

    ImageIcon scaledIcon = scaledImageIcons.get(fname);
    if (scaledIcon == null) {
      scaledIcon = scaledImageIcons.get(ASSET_PATH + "unknown.png");
    }
    return scaledIcon;
  }

  /**
   * Invocation point for testing asset manager can read files from
   * assets directory correctly.
   *
   * @param args ignored.
   */
  public static void main(String[] args) {
    try {
      AssetManager.loadAssets();
    } catch (IOException e) {
      System.out.println("Unable to read files from folder: " + e);
    }
    System.out.println("Read " + baseImageIcons.size() + " .png's from: " + ASSET_PATH);
  }
}