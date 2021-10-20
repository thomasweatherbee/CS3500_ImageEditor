package hw05.model.transforms.colors;

/**
 * Represents a Grayscale filter transformation, meant to turn everything into black and white.
 */
public class GrayscaleFilter extends AColorTransform {

  /**
   * Creates a new Grayscale filter.
   */
  public GrayscaleFilter() {
    super(new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}});
  }
}
