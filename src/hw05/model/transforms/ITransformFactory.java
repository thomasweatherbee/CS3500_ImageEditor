package hw05.model.transforms;

/**
 * ITransform factory--creates new ITransforms with the specified arguments (mostly applicable to
 * transforms that require arguments). Helps to reduce code changes to the existing ITransform
 * interface.
 */
public interface ITransformFactory {

  /**
   * Gets a new ITransform with the parameters specified.
   *
   * @param args parameters to construct a transform with
   * @return a new ITransform with the given args
   */
  ITransform get(int... args);
}
