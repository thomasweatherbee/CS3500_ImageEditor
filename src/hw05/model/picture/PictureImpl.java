package hw05.model.picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an implementation of a picture, storing information as a list of list of IPixels.
 */
public class PictureImpl implements IPicture {

  private final List<List<IPixel>> image = new ArrayList<>();

  /**
   * Creates a new PictureImpl with the specified pixels.
   *
   * @param image pixels to be used to construct the image
   */
  public PictureImpl(List<List<IPixel>> image) {
    Objects.requireNonNull(image);
    for (int i = 0; i < image.size(); i++) {
      this.image.add(new ArrayList<IPixel>());
      Objects.requireNonNull(image.get(i));
      for (int j = 0; j < image.get(i).size(); j++) {
        Objects.requireNonNull(image.get(i).get(j));
        this.image.get(i).add(image.get(i).get(j).clone());
      }
    }
  }

  @Override
  public int getHeight() {
    if (image.size() > 0) {
      return image.get(0).size();
    } else {
      return 0;
    }
  }

  @Override
  public int getWidth() {
    return image.size();
  }

  @Override
  public int[] getRGBAt(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
      throw new IllegalArgumentException("Index " + x + " " + y + " out of bounds");
    }
    return image.get(x).get(y).getRGB();
  }

  @Override
  public IPicture getPicCopy() {
    return new PictureImpl(image);
  }
}
