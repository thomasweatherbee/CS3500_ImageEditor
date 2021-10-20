package hw05.model.transforms;

import hw05.model.picture.IPicture;

/**
 * Represents a transformation that can be applied to an IPicture, altering the image in a specified
 * programmatic way.
 */
public interface ITransform {

  /**
   * Applies this transformation to an image.
   *
   * @param in image to be transformed
   * @return transformed image
   */
  IPicture apply(IPicture in);
}
