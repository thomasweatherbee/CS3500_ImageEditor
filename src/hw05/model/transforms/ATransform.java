package hw05.model.transforms;

import hw05.model.picture.IPicture;
import hw05.model.picture.IPixel;
import hw05.model.picture.PictureImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for transforms.
 */
public abstract class ATransform implements ITransform {

  @Override
  public IPicture apply(IPicture in) {
    List<List<IPixel>> out = new ArrayList<>();
    for (int i = 0; i < in.getWidth(); i++) {
      out.add(new ArrayList<IPixel>());
      for (int j = 0; j < in.getHeight(); j++) {
        out.get(i).add(applyToPixel(in, i, j));
      }
    }
    return new PictureImpl(out);
  }

  /**
   * Caps RGB values to minimum 0 and maximum 255.
   *
   * @param rgb RGB array to be clamped
   * @return clamped array
   */
  protected int[] clampRGB(int[] rgb) {
    for (int i = 0; i < 3; i++) {
      if (rgb[i] < 0) {
        rgb[i] = 0;
      } else if (rgb[i] > 255) {
        rgb[i] = 255;
      }
    }
    return rgb;
  }

  /**
   * Applies this transformation to a single pixel.
   *
   * @param in image to be transformed
   * @param x  x-coordinate of pixel to be transformed
   * @param y  y-coordinate of pixel to be transformed
   * @return transformed pixel
   */
  protected abstract IPixel applyToPixel(IPicture in, int x, int y);

  public int getNumArgs() {
    return 1;
  }
}
