package hw05.model.picture;

/**
 * Represents an immutable image. The client has the ability to view the image with height, width,
 * and RGB at point methods.
 */
public interface IPicture {

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Gets the width of an image.
   *
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Gets a list of int values representing the RGB of a specified pixel.
   *
   * @param x x-coordinate of pixel
   * @param y y-coordinate of pixel
   * @return RGB values of pixel
   * @throws IllegalArgumentException if pixel is out of bounds
   */
  int[] getRGBAt(int x, int y) throws IllegalArgumentException;

  /**
   * Returns a deep copy of this image.
   *
   * @return copy of this image
   */
  IPicture getPicCopy();
}
