package hw05.model.transforms.filters;

import hw05.model.picture.IPicture;
import hw05.model.picture.IPixel;
import hw05.model.picture.RGBPixelImpl;
import hw05.model.transforms.ATransform;
import hw05.model.transforms.ITransform;

/**
 * Abstract class representing a kernel-based filter transformation for a single pixel using its
 * surrounding area.
 */
public abstract class AFilterTransform extends ATransform implements ITransform {

  private final double[][] kernel;
  private final int kernelSize;

  /**
   * Constructs a new AFilterTransform with the given kernel.
   *
   * @param kernel matrix of values corresponding to weights of neighboring pixels
   * @throws IllegalArgumentException if kernel is not square or kernel does not have odd
   *                                  dimensions
   */
  public AFilterTransform(double[][] kernel) throws IllegalArgumentException {
    this.kernel = kernel.clone(); //can shallow copy because of primitive types
    this.kernelSize = kernel.length;
    if (kernel.length != kernel[0].length) {
      throw new IllegalArgumentException("Kernel must be square");
    }
    if (kernelSize % 2 != 1) {
      throw new IllegalArgumentException("Kernel must have odd dimensions");
    }
  }

  @Override
  protected IPixel applyToPixel(IPicture in, int x, int y) {
    int offset = (kernelSize - 1) / 2;
    double[] rgb = {0, 0, 0};
    for (int i = -1 * offset; i <= offset; i++) {
      for (int j = -1 * offset; j <= offset; j++) {
        if (!(x + i < 0 || x + i >= in.getWidth() || y + j < 0 || y + j >= in.getHeight())) {
          int[] currPixelRGB = in.getRGBAt(x + i, y + j);
          for (int k = 0; k < 3; k++) {
            rgb[k] += currPixelRGB[k] * kernel[j + offset][i + offset];
          }
        }
      }
    }

    return new RGBPixelImpl(clampRGB(
        new int[]{(int) Math.round(rgb[0]), (int) Math.round(rgb[1]), (int) Math.round(rgb[2])}));
  }
}