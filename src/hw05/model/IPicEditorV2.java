package hw05.model;

import hw05.model.oldeditor.IPicEditor;

/**
 * Version 2 of the original picture editor which adds support for passing arguments to an edit
 * command.
 */
public interface IPicEditorV2 extends IPicEditor {

  /**
   * Returns the list of commands this editor can handle.
   *
   * @return the list of comands this editor can handle
   */
  String[] getCommands();

  /**
   * Gets the number of arguments a given command expects.
   *
   * @param command command to find the number of args for
   * @return the number of arguments command expects
   * @throws IllegalArgumentException if command isn't supported
   */
  int getNumArgs(String command) throws IllegalArgumentException;

  /**
   * Gets the names of the arguments a command expects.
   *
   * @param op command whose argument names are being requested
   * @return an array of strings describing the command's arguments
   * @throws IllegalArgumentException if command isn't supported
   */
  String[] getCommandArgs(String op) throws IllegalArgumentException;

  /**
   * Performs an edit operation using the given command and arguments.
   *
   * @param command operation to be performed
   * @param args    arguments pertaining to the operation, varies by operation
   * @throws IllegalArgumentException if the specified command isn't supported
   */
  void edit(String command, int... args) throws IllegalArgumentException;
}
