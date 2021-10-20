package hw05.model.picture;

/**
 * Represents an immutable pixel, many of which can be put together to make up an image. The client
 * has the ability to look in and view the pixel's RGB value, regardless of what colorspace the
 * pixel was initialized in.
 */
public interface IPixel {

  /**
   * Gets the RGB value of this pixel in the form of 3 ints in an array.
   *
   * @return the RGB value of this pixel
   */
  int[] getRGB();

  /**
   * Gets a duplicate pixel with the same RGB value.
   *
   * @return copy IPixel
   */
  IPixel clone();
}
