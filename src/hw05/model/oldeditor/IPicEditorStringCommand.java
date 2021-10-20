package hw05.model.oldeditor;

/**
 * Represents a picture editor (same functionality as IPicEditor) which can be edited using string
 * commands as opposed to EditOperations.
 */
public interface IPicEditorStringCommand extends IPicEditor {

  /**
   * Gets the list of valid edit commands for this editor.
   *
   * @return the list of valid commands
   */
  String[] getCommands();

  /**
   * Performs the specified edit on the image in this editor.
   *
   * @param command edit command to be executed
   */
  void editStringCommand(String command);
}
