package hw05.model.transforms.filters;

/**
 * Represents a sharpen filter, meant to accentuate edges.
 */
public class SharpenFilter extends AFilterTransform {

  /**
   * Constructs a new sharpen filter.
   */
  public SharpenFilter() {
    super(new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}});
  }
}
