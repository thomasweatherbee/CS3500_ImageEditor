package hw05.model.oldeditor;

/**
 * Factory class that creates new instances IPicEditorSCFactoryof a string command compatible
 * picture editor.
 */
public interface IPicEditorSCFactory {

  /**
   * Gets an instance of this factory's IPicEditorStringCommand.
   *
   * @return a new instance of this factory's IPicEditorStringCommand
   */
  IPicEditorStringCommand getPicEditor();

}
