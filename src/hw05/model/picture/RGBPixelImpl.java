package hw05.model.picture;

import java.util.Arrays;

/**
 * Represents an implementation of a pixel, storing information in RGB form.
 */
public class RGBPixelImpl implements IPixel {

  private final int[] colors;

  /**
   * Creates a new RGB pixel.
   *
   * @param rgb RGB value for this pixel
   */
  public RGBPixelImpl(int[] rgb) {
    colors = new int[ensureWithin(3, 3, rgb.length)];
    colors[0] = ensureWithin(0, 255, rgb[0]);
    colors[1] = ensureWithin(0, 255, rgb[1]);
    colors[2] = ensureWithin(0, 255, rgb[2]);
  }

  /**
   * Convenience constructor allowing for passing in r, g, and b values seperately.
   *
   * @param r red value
   * @param g green value
   * @param b blue value
   */
  public RGBPixelImpl(int r, int g, int b) {
    this(new int[]{r, g, b});
  }

  /**
   * Ensures that a given int is within the specified bounds.
   *
   * @param lower   lower bound
   * @param higher  higher bound
   * @param toCheck int to check
   * @return toCheck if it is with the bounds
   * @throws IllegalArgumentException if toCheck is out of bounds
   */
  private int ensureWithin(int lower, int higher, int toCheck) throws IllegalArgumentException {
    if (toCheck < lower || toCheck > higher) {
      throw new IllegalArgumentException(
          toCheck + " is not within bounds " + lower + " - " + higher);
    } else {
      return toCheck;
    }
  }

  @Override
  public int[] getRGB() {
    return Arrays.copyOf(colors, 3);
  }

  @Override
  public IPixel clone() {
    return new RGBPixelImpl(colors[0], colors[1], colors[2]);
  }
}