package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * CombinedImageIcon: Helpful class for stacking icons.
 * https://stackoverflow.com/questions/17088599/how-to-combine-two-icons-in-java
 * @author Zac Scott 300447976.
 */
class CombinedImageIcon extends ImageIcon {

  /**
   * Default serial number.
   */
  private static final long serialVersionUID = 1L;

  /**
   * CombinedImageIcon: Create an ImageIcon from a base Icon underneath layers of icons.
   *
   * @param layers Layers to stack.
   */
  CombinedImageIcon(List<ImageIcon> layers) {
    super();
    BufferedImage combinedImage = new BufferedImage(layers.get(0).getIconWidth(),
        layers.get(0).getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = combinedImage.createGraphics();
    layers.forEach(imageIcon -> g.drawImage(imageIcon.getImage(), 0, 0, null));
    g.dispose();
    setImage(combinedImage);
  }
}
