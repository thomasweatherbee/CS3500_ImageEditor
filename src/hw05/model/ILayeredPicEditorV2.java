package hw05.model;

import hw05.model.oldeditor.ILayeredPicEditor;

/**
 * Extends the prior layered picture editor interface, adding functionality for the use of
 * multi-argumented edit commands (eg. mosaic and resize).
 */
public interface ILayeredPicEditorV2 extends ILayeredPicEditor, IPicEditorV2 {

  /**
   * Runs an edit command with the specified arguments.
   *
   * @param command command to be run
   * @param args    arguments for the command
   */
  void edit(String command, int... args) throws IllegalArgumentException;
}
