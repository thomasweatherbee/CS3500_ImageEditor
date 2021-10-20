package hw05.view.picoutput;

/**
 * Represents a way to export images or projects from a layered picture editor. Can export
 * individual images or save all images in the project to a directory in a form that can then be
 * loaded by the program again.
 */
public interface IPicEditorOutLayered extends IPicEditorOut {

  /**
   * Exports the entire project to the specified folder in the form of multiple image files and a
   * root text file connecting all of them.
   *
   * @param directory folder to save project in
   * @throws IllegalArgumentException if there is already a project saved at this location
   */
  void exportProject(String directory) throws IllegalArgumentException;

  /**
   * Exports a picture from an ILayeredPicEditorState to a specified path.
   *
   * @param file  path of exported picture
   * @param layer layer of image to be exported
   * @throws IllegalArgumentException if layer is out of bounds or path is invalid
   */
  void exportPictureAtLayer(String file, int layer) throws IllegalArgumentException;
}
