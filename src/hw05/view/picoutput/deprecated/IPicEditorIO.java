package hw05.view.picoutput.deprecated;

/**
 * Represents the Input/Output to a file for an IPicEditor.
 */
public interface IPicEditorIO {

  /**
   * Imports a picture to an IPicEditor located at path f.
   *
   * @param f path of the picture
   */
  void importPicture(String f);

  /**
   * Exports a picture from an IPicEditor to path f.
   *
   * @param f path of exported picture
   */
  void exportPicture(String f);
}
