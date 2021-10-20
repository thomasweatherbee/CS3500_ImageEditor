package hw05.view;

import java.awt.Image;
import java.util.List;

/**
 * A GUI view for a picture editor that provides high-level operations to a listener and allows for
 * the retrieval of specific argument types (eg. a color, path, or integer).
 */
public interface IPicEditorGUI {

  /**
   * Gets a requested number of integer arguments, each with a correspondsing label.
   *
   * @param args   number of integer arguments to take in
   * @param labels labels corresponding to each integer argument
   * @return a list of arguments of size args
   * @throws IllegalArgumentException if labels is not of length args
   */
  int[] requestArgs(int args, String... labels) throws IllegalArgumentException;

  /**
   * Gets a user-selected file path.
   *
   * @return a file path
   */
  String requestFilePath();

  /**
   * Gets a user-selected color.
   *
   * @return a color in the form of an RGB integer array
   */
  int[] requestColor();

  /**
   * Updates the preview image and layer visibility menu.
   *
   * @param toChange     new preview image
   * @param visibilities new layer visibilities
   * @throws IllegalArgumentException if the given image or visibilities list is null
   */
  void update(Image toChange, List<Boolean> visibilities) throws IllegalArgumentException;

  /**
   * Displays the given message to the user.
   *
   * @param e message to display.
   */
  void displayError(String e);

  /**
   * Sets the listener to the view's high-level operations to the specified listener.
   *
   * @param listener listener to set the view's listener to
   * @throws IllegalArgumentException if listener is null
   */
  void setListener(IViewListener listener) throws IllegalArgumentException;

  /**
   * Gets a user-selected directory (as opposed to specific file path).
   *
   * @return a user-selected directory
   */
  String requestDirectory();

  /**
   * Displays the GUI.
   */
  void startView();
}