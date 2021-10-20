package hw05.model.transforms;

import hw05.model.picture.IPicture;
import hw05.model.picture.IPixel;
import hw05.model.picture.PictureImpl;
import hw05.model.picture.RGBPixelImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Downscales an image to a new specified height and width.
 */
public class ResizeTransform extends ATransform {

  final int newHeight;
  final int newWidth;

  /**
   * Creates a new resize transform that downscales an image to the specified width and height.
   *
   * @param width  width of downscaled image
   * @param height height of downscaled image
   */
  public ResizeTransform(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("New image cannot have a zero dimension");
    }
    newWidth = width;
    newHeight = height;
  }

  @Override
  public IPicture apply(IPicture in) {
    if (newHeight > in.getHeight() || newWidth > in.getWidth()) {
      throw new IllegalArgumentException("Cannot upsize image");
    }

    if (newHeight == in.getHeight() && newWidth == in.getWidth()) {
      return in.getPicCopy();
    }

    List<List<IPixel>> out = new ArrayList<>();
    for (int i = 0; i < newWidth; i++) {
      out.add(new ArrayList<IPixel>());
      for (int j = 0; j < newHeight; j++) {
        out.get(i).add(applyToPixel(in, i, j));
      }
    }
    return new PictureImpl(out);
  }

  @Override
  protected IPixel applyToPixel(IPicture in, int x, int y) {
    double inputX = (double) in.getWidth() / newWidth * x;
    double inputY = (double) in.getHeight() / newHeight * y;

    int flooredInputX = Math.toIntExact(Math.round(Math.floor(inputX)));
    int flooredInputY = Math.toIntExact(Math.round(Math.floor(inputY)));
    int ceiledInputX = flooredInputX + 1 >= in.getWidth() ? flooredInputX : flooredInputX + 1;
    int ceiledInputY = flooredInputY + 1 >= in.getHeight() ? flooredInputY : flooredInputY + 1;

    int[] outColor = {0, 0, 0};

    int[] topleft = in.getRGBAt(flooredInputX, flooredInputY);
    int[] topright = in.getRGBAt(ceiledInputX, flooredInputY);
    int[] botleft = in.getRGBAt(flooredInputX, ceiledInputY);
    int[] botright = in.getRGBAt(ceiledInputX, ceiledInputY);

    for (int i = 0; i < 3; i++) {
      int a = topleft[i];
      int b = topright[i];
      int c = botleft[i];
      int d = botright[i];

      outColor[i] = Math.toIntExact(Math.round(
          (a * (inputX - flooredInputX) + b * (ceiledInputX - inputX)) * (inputY - flooredInputY)
              + (c * (inputX - flooredInputX) + d * (ceiledInputX - inputX)) * (ceiledInputY
              - inputY)));
    }

    return new RGBPixelImpl(clampRGB(outColor));
  }
}
