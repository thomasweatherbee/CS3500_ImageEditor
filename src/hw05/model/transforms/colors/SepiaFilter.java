package hw05.model.transforms.colors;

/**
 * Represents a new sepia filter transformation, meant to give everything a brown-ish color.
 */
public class SepiaFilter extends AColorTransform {

  /**
   * Creates a new sepia filter.
   */
  public SepiaFilter() {
    super(new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}});
  }
}
