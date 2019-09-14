package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.renderer.Canvas;

import javax.swing.ImageIcon;
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
          // f should be a png filename.
          System.out.println(f);
          ImageIcon imageIcon = new ImageIcon(f);
          if (imageIcon.getIconHeight() <= 0 || imageIcon.getIconWidth() <= 0)
            imageIcon = new ImageIcon("assets/unknown.png");
          baseImageIcons.put(f, imageIcon);
          scaledImageIcons.put(f, imageIcon);
          System.out.println(imageIcon.getIconHeight() + "x" + imageIcon.getIconWidth());
        });
  }

  /**
   * Rescales icons to new size.
   * @param newCellSize Cell size.
   */
  public static void scaleIcons(int newCellSize) {
    if (cellSize == newCellSize) return;
    System.out.println("Getting newCellSize" + newCellSize);
    cellSize = newCellSize;
    for (String key : baseImageIcons.keySet()) {
      Image baseImage = baseImageIcons.get(key).getImage();
      ImageIcon scaledIcon = new ImageIcon(
          baseImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
      scaledImageIcons.put(key, scaledIcon);
    }
  }

  public static ImageIcon getScaledImage(String fname) {
    return scaledImageIcons.get(fname);
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
    System.out.println("Read " + baseImageIcons.size() + " .png's from assets/ .");
  }
}