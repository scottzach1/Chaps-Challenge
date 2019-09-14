package nz.ac.vuw.ecs.swen225.a3.renderer;

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

  static Map<String, ImageIcon> baseImageIcons = new HashMap<>();
  static Map<String, ImageIcon> scaledImageIcons = new HashMap<>();

  /**
   * Constructor: Asset manager finds all files in the assets/ directory.
   * If unable to read files in the directory an IOException will be thrown.
   */
  public AssetManager() throws IOException {
    // Load files from assets/ into baseImageIcons.
      Files.walk(Paths.get("assets\\"))
          .filter(Files::isRegularFile)
          .map(Path::toString)
          .filter(f -> f.endsWith(".png"))
          .forEach(f -> baseImageIcons.put(f, new ImageIcon(f)));

      scaleIcons(Canvas.cellSize);

//      System.out.println("Loaded " + baseImageIcons.size() + " assets.");
  }

  public void scaleIcons(int cellSize) {
    for (String key : baseImageIcons.keySet()) {
      Image baseImage = baseImageIcons.get(key).getImage();
      ImageIcon scaledIcon = new ImageIcon(
          baseImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
      scaledImageIcons.put(key, scaledIcon);
    }
  }

  public static void main(String[] args) {
    try {
      AssetManager assetManager = new AssetManager();
    } catch (IOException e) {
      System.out.println("Unable to read files from folder: " + e);
    }
  }
}
