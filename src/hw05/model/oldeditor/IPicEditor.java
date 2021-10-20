package hw05.model.oldeditor;

import hw05.model.picture.IPicture;
import java.util.List;

/**
 * Represents a picture editor. Gives the client the ability to load an image and then perform
 * programmatic edits on it. Also allows the client to view properties of the image such as width,
 * height, and RGB values.
 */
public interface IPicEditor extends IPicture {

  /**
   * Applies a requested edit operation to the internal picture.
   *
   * @param op edit operation specifier
   * @throws IllegalStateException if the editor has not yet been started
   */
  void edit(EditOperation op) throws IllegalStateException;

  /**
   * Generates a checkerboard image.
   *
   * @param width      width of image
   * @param height     height of image
   * @param color1     first color of checkerboard
   * @param color2     other color of checkerboard
   * @param squareSize size of checkers
   * @return width x height checkerboard image with checkers of size squareSize
   */
  List<List<int[]>> generateImage(int width, int height, int[] color1, int[] color2,
      int squareSize);

  /**
   * Starts either the editor or a new topmost layer with specified list of list of RGB values
   * representing an image.
   *
   * @param input list of list of RGB values representing the input image, stored in a list of
   *              columns
   * @throws IllegalArgumentException if input is an invalid image
   */
  void start(List<List<int[]>> input) throws IllegalArgumentException;
}
