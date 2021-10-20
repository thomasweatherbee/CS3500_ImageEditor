package hw05.view.picoutput;

/**
 * Represents the output to a file for an IPicEditor.
 */
public interface IPicEditorOut {

  /**
   * Exports a picture from an IPicEditor to path f.
   *
   * @param f path of exported picture
   */
  void exportPicture(String f);
}
