package nz.ac.vuw.ecs.swen225.a3.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * CombinedImageIcon: Helpful class for stacking icons.
 * https://stackoverflow.com/questions/17088599/how-to-combine-two-icons-in-java
 */
public class CombinedImageIcon extends ImageIcon {

  /**
   * CombinedImageIcon: Create an ImageIcon from a base Icon underneath layers of
   * icons.
   * @param layers Layers to stack.
   */
  public CombinedImageIcon(List<ImageIcon> layers) {
    super();
    BufferedImage combinedImage = new BufferedImage(layers.get(0).getIconWidth(),
        layers.get(0).getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = combinedImage.createGraphics();
    layers.forEach(imageIcon -> g.drawImage(imageIcon.getImage(), 0, 0, null));
    g.dispose();
    setImage(combinedImage);
  }
}
