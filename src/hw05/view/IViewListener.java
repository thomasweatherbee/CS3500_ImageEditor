package hw05.view;

/**
 * Listens for high-level operations in the view class and handles them accordingly.
 */
public interface IViewListener {

  /**
   * Handles a specified operation as specified by the implementer.
   *
   * @param op high-level operation to be handled.
   */
  void handleOperation(String op);
}