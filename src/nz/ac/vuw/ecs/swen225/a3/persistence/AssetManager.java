package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import nz.ac.vuw.ecs.swen225.a3.renderer.CombinedImageIcon;


/**
 * AssetManager class is responsible for dealing with storing and scaling all the assets used in the
 * game.
 *
 * @author Zac Scott.
 */
public class AssetManager {

  private static String assetPath = "assets/";

  private static boolean loaded = false;
  private static boolean scaling = false;

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
   * Loads an asset from an input stream.
   *
   * @param inputStream containing asset.
   * @param fname       of asset.
   * @throws IOException on ImageIO read.
   */
  public static void loadAssetFromInputStream(InputStream inputStream, String fname)
      throws IOException {
    fname = fname.toLowerCase();
    BufferedImage bufferedImage = ImageIO.read(inputStream);

    // -- Load base image -- //

    ImageIcon baseIcon = new ImageIcon(bufferedImage);

    if (baseImageIcons.containsKey(fname)) {
      return;
    }

    if (baseIcon.getIconHeight() <= 0 || baseIcon.getIconWidth() <= 0) {
      baseIcon = new ImageIcon(assetPath + fname);
    }

    baseIcon.setDescription(fname);

    // Else use unknown from local path.
    if (baseIcon.getIconWidth() <= 0 || baseIcon.getIconHeight() <= 0) {
      baseIcon = new ImageIcon(assetPath + "unknown.png");
      baseIcon.setDescription("unknown.png");
    }

    //  -- Load scaled image -- //

    ImageIcon scaledIcon = new ImageIcon(baseIcon.getImage()
        .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

    scaledIcon.setDescription(baseIcon.getDescription());

    // -- Save icons -- //

    baseImageIcons.put(fname, baseIcon);
    scaledImageIcons.put(fname, scaledIcon);
  }

  /**
   * Loads asset from filename.
   *
   * @param fname filename.
   */
  private static void loadAsset(String fname) {
    // Load unknown asset if first run.
    if (loaded != (loaded = true)) {
      loadAsset("unknown.png");
    }

    fname = fname.toLowerCase();
    if (baseImageIcons.containsKey(fname)) {
      return;
    }

    // -- Load base image -- //
    ImageIcon baseIcon = new ImageIcon(assetPath + fname);
    baseIcon.setDescription(fname);

    // Else use unknown from local path.
    if (baseIcon.getIconWidth() <= 0 || baseIcon.getIconHeight() <= 0) {
      baseIcon = new ImageIcon(assetPath + "unknown.png");
      baseIcon.setDescription("unknown.png");
    }

    //  -- Load scaled image -- //

    ImageIcon scaledIcon = new ImageIcon(baseIcon.getImage()
        .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

    scaledIcon.setDescription(baseIcon.getDescription());

    // -- Save icons -- //

    baseImageIcons.put(fname, baseIcon);
    scaledImageIcons.put(fname, scaledIcon);
  }

  /**
   * Rescales icons to new size.
   *
   * @param newCellSize Cell size.
   */
  public static void scaleImages(int newCellSize) {
    if (newCellSize == 0) {
      return;
    }

    if (scaling != (scaling = true)) {
      cellSize = newCellSize;
      for (String key : baseImageIcons.keySet()) {
        ImageIcon baseImage = baseImageIcons.get(key);
        ImageIcon scaledIcon = new ImageIcon(baseImage.getImage()
            .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        scaledIcon.setDescription(baseImage.getDescription());
        scaledImageIcons.put(key, scaledIcon);
      }

      scaling = false;
    }
  }

  /**
   * Gets an ImageIcon at the last scaled size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImage(String fname) {
    fname = fname.toLowerCase();

    if (fname.contains("-")) {
      return getOverlaidImages(fname);
    }

    loadAsset(fname); // Check asset exists.

    return scaledImageIcons.get(fname);
  }

  /**
   * Gets an overlayed ImageIcon of the files within the conjoined filename. Filename has '-'
   * delimitation. Ie, "free.png-flippers.png"
   *
   * @param mergedFnames of files with "-" delimiter.
   * @return combined imageIcon.
   */
  private static ImageIcon getOverlaidImages(String mergedFnames) {

    List<String> fnames = new ArrayList<>(Arrays.asList(mergedFnames.split("-")));
    List<ImageIcon> layers = new ArrayList<>();
    StringBuilder description = new StringBuilder();

    // Get icons.
    for (int i = 0; i != fnames.size(); ++i) {
      layers.add(getScaledImage(fnames.get(i)));
      description.append(fnames.get(i));
      // Also append joiner if not last image.
      if (i != fnames.size() - 1) {
        description.append("-");
      }
    }

    // Get combined icon.
    ImageIcon combinedIcon = new CombinedImageIcon(layers);
    combinedIcon.setDescription(description.toString());

    return combinedIcon;
  }

  /**
   * Gets an ImageIcon at the last scaled size, with a number overlay. Number will be clipped from
   * [1,9].
   *
   * @param fname  file path.
   * @param number to overlay from [1,9].
   * @return ImageIcon.
   */
  public static ImageIcon getNumberedScaledImage(String fname, int number) {
    fname = fname.toLowerCase();
    // Number clipping.
    String nname = ((number < 10 && number > 0) ? number : "NaN") + ".png";

    return getOverlaidImages(fname + "-" + nname);
  }

  /**
   * Gets an ImageIcon at the specified size.
   *
   * @param fname file path.
   * @return ImageIcon.
   */
  public static ImageIcon getScaledImageInstance(String fname, int newCellSize) {
    fname = fname.toLowerCase();
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

  public static String combineFnames(String base, String overlay) {
    return base + "-" + overlay;
  }


  public static int getCellSize() {
    return cellSize;
  }
}