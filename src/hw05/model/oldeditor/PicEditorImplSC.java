package hw05.model.oldeditor;

/**
 * Represents an implementation of a string command-operated picture editor.
 */
public class PicEditorImplSC extends PicEditorImpl implements IPicEditorStringCommand {

  @Override
  public String[] getCommands() {
    return new String[]{"blur", "sharpen", "monochrome", "sepia"};
  }

  @Override
  public void editStringCommand(String command) {
    for (EditOperation op : edits.keySet()) {
      if (op.toString().equals(command.toUpperCase())) {
        edit(op);
        return;
      }
    }
    throw new IllegalArgumentException("Invalid edit command");
  }
}
