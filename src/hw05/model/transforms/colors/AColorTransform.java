package hw05.model.transforms.colors;

import hw05.model.picture.IPicture;
import hw05.model.picture.IPixel;
import hw05.model.picture.RGBPixelImpl;
import hw05.model.transforms.ATransform;
import hw05.model.transforms.ITransform;

/**
 * Abstract class representing a color transformation to a single pixel.
 */
public abstract class AColorTransform extends ATransform implements ITransform {

  private final double[][] matrix;

  /**
   * Creates a new color transformation with the given transformation matrix.
   *
   * @param matrix transformation matrix that RGB vector will be multiplied by
   */
  public AColorTransform(double[][] matrix) {
    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Color transformation matrix must be 3x3");
    }
    this.matrix = matrix.clone();
  }

  @Override
  protected IPixel applyToPixel(IPicture in, int x, int y) {
    double[] rgb = {0, 0, 0};
    int[] currPixelRGB = in.getRGBAt(x, y);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        rgb[i] += currPixelRGB[j] * matrix[i][j];
      }
    }
    return new RGBPixelImpl(clampRGB(
        new int[]{(int) Math.round(rgb[0]), (int) Math.round(rgb[1]), (int) Math.round(rgb[2])}));
  }
}