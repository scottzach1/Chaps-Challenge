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


/**
 * AssetManager class is responsible for dealing with storing and scaling all the assets used in the
 * game.
 *
 * @author Zac Scott 300447976.
 */
public class AssetManager {

  private final static String ASSET_PATH = "assets/";
  private final static String SEPARATOR = "-";

  private boolean scaling = false;

  /**
   * Private static fields to store important GUI data.
   */
  private Map<String, ImageIcon> baseImageIcons = new HashMap<>();
  private Map<String, ImageIcon> scaledImageIcons = new HashMap<>();
  private int cellSize = 10;

  /**
   * Loads an asset from an input stream.
   *
   * @param inputStream containing asset.
   * @param fname filename of asset.
   * @throws IOException on ImageIO read.
   */
  void loadAssetFromInputStream(InputStream inputStream, String fname)
      throws IOException {

    fname = fname.toLowerCase();
    BufferedImage bufferedImage = ImageIO.read(inputStream);

    ImageIcon imageIcon = new ImageIcon(bufferedImage);
    imageIcon.setDescription(fname);

    loadAsset(imageIcon, fname);
  }

  /**
   * Loads asset filename from asset directory.
   *
   * @param fname filename of asset.
   */
  private void loadAsset(String fname) {
    // Load unknown asset if first run.
    fname = fname.toLowerCase();
    if (baseImageIcons.containsKey(fname)) {
      return;
    }

    ImageIcon imageIcon = new ImageIcon(ASSET_PATH + fname);
    imageIcon.setDescription(fname);

    loadAsset(imageIcon, fname);
  }

  /**
   * Loads an asset from an ImageIcon.
   *
   * If invalid asset, will replace Image and description with
   * 'unknown.png' and corresponding image stored within the
   * assets directory.
   *
   * @param imageIcon ImageIcon to load.
   * @param fname filename of asset.
   */
  private void loadAsset(ImageIcon imageIcon, String fname) {
    // Don't load if already loaded.
    if (baseImageIcons.containsKey(fname)) {
      return;
    }

    // Line makes parameter name for ImageIcon easier to understand.
    ImageIcon baseIcon = new ImageIcon(imageIcon.getImage());

    // If underlying image is invalid, attempt to load from assets directory.
    if (baseIcon.getIconHeight() <= 0 || baseIcon.getIconWidth() <= 0) {
      baseIcon = new ImageIcon(ASSET_PATH + fname);
    }
    baseIcon.setDescription(fname);

    // Otherwise use 'unknown.png' asset from the local path.
    if (baseIcon.getIconWidth() <= 0 || baseIcon.getIconHeight() <= 0) {
      baseIcon = new ImageIcon(ASSET_PATH + "unknown.png");
      baseIcon.setDescription("unknown.png");
    }

    // Load the scaled image.
    ImageIcon scaledIcon = new ImageIcon(baseIcon.getImage()
        .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

    scaledIcon.setDescription(baseIcon.getDescription());

    // Store the icons.
    baseImageIcons.put(fname, baseIcon);
    scaledImageIcons.put(fname, scaledIcon);
  }

  /**
   * Rescales icons to new size.
   *
   * @param newCellSize Cell size.
   */
  public void scaleImages(int newCellSize) {
    if (newCellSize == 0) {
      return;
    }

    /*
     * Scales all assets stored within baseImageIcons and
     * stores these in scaledImageIcons.
     *
     * To avoid concurrent modification a scaling boolean
     * is used.
     */
    if (!scaling) {
      scaling = true;

      cellSize = newCellSize;
      for (String key : baseImageIcons.keySet()) {
        // Get base image.
        ImageIcon baseImage = baseImageIcons.get(key);
        // Make scaled image.
        ImageIcon scaledIcon = new ImageIcon(baseImage.getImage()
            .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        // Update image description
        scaledIcon.setDescription(baseImage.getDescription());
        // Store scaled asset.
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
  public ImageIcon getScaledImage(String fname) {
    fname = fname.toLowerCase();

    if (fname.contains(SEPARATOR)) {
      return getOverlaidImages(fname);
    }

    loadAsset(fname); // Check asset exists.

    return scaledImageIcons.get(fname);
  }

  /**
   * Gets an overlaid ImageIcon of the files within the conjoined filename.
   * Filename has '-' delimitation. Ie, "free.png-flippers.png"
   *
   * @param mergedFnames of files with SEPARATOR final field delimiter.
   * @return combined imageIcon.
   */
  private ImageIcon getOverlaidImages(String mergedFnames) {

    List<String> fnames = new ArrayList<>(Arrays.asList(mergedFnames.split(SEPARATOR)));
    List<ImageIcon> layers = new ArrayList<>();
    StringBuilder description = new StringBuilder();

    // Get icons and store in list, also join filenames.
    for (int i = 0; i != fnames.size(); ++i) {
      layers.add(getScaledImage(fnames.get(i)));
      description.append(fnames.get(i));
      // Append joiner if not last image.
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
  public ImageIcon getNumberedScaledImage(String fname, int number) {
    fname = fname.toLowerCase();
    // If number invalid, use '?' overlay asset instead.
    String nname = ((number < 10 && number > 0) ? number : "nan") + ".png";

    return getOverlaidImages(fname + SEPARATOR + nname);
  }

  public static String combineFnames(String base, String overlay) {
    return base + "-" + overlay;
  }

}