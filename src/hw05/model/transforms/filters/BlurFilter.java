package hw05.model.transforms.filters;

/**
 * Represents a blur filter transformation, meant to make edges less apparent.
 */
public class BlurFilter extends AFilterTransform {

  /**
   * Constructs a new blur filter.
   */
  public BlurFilter() {
    super(new double[][]{
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}});
  }
}
