package hw05.controller.text;

import hw05.controller.AController;
import hw05.controller.IController;
import hw05.model.oldeditor.ILayeredPicEditor;
import hw05.view.IPicEditorView;
import hw05.view.PicEditorTextView;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a controller for a picture editor. Functions as an in-between for the user and the
 * view/model. Accepts commands "help", "import", "export", "hide", "show", "edit", and "generate."
 */
public class TextControllerImpl extends AController implements IController {

  private final Scanner sc;
  private final IPicEditorView io;
  private final ILayeredPicEditor editor;

  private final Map<String, ICommand> commands;

  /**
   * Constructs a new controller with specified input and output channels.
   *
   * @param textIn  input text stream
   * @param textOut output text stream
   * @param editor  editor this controller controls
   */
  public TextControllerImpl(Readable textIn, Appendable textOut, ILayeredPicEditor editor) {
    this.sc = new Scanner(textIn);
    this.editor = editor;
    this.io = new PicEditorTextView(editor, textOut);
    this.commands = new HashMap<>();
    commands.put("help", () -> io.renderText(getCommands()));
    commands.put("import", new ImportCommand());
    commands.put("export", new ExportCommand());
    commands.put("hide",
        () -> editor.setLayerVisibility(getNextInt(), false));
    commands.put("show",
        () -> editor.setLayerVisibility(getNextInt(), true));
    commands.put("edit", () -> editor.editStringCommand(getNext()));
    commands.put("generate", () -> editor.addLayer(editor
        .generateImage(getNextInt(), getNextInt(),
            new int[]{getNextInt(), getNextInt(), getNextInt()},
            new int[]{getNextInt(), getNextInt(), getNextInt()},
            getNextInt())));
  }

  @Override
  public void useEditor() {
    io.renderText("Loaded. Type 'help' for a list of commands.\n");
    String next;
    try {
      next = getNext();
    } catch (IllegalStateException e) {
      io.renderText("Editor quit.");
      return;
    }

    boolean quit = false;
    while (!quit) {
      if (!commands.containsKey(next)) {
        io.renderText("Invalid command. Valid commands are:\n" + getCommands());
      } else {
        try {
          commands.get(next).apply();
          io.renderText("Command " + next + " run successfully.\n");
        } catch (IllegalArgumentException e) {
          io.renderText(e.getMessage() + "\n");
        } catch (IllegalStateException e) {
          if (e.getMessage().equals("quit")) {
            io.renderText("Editor quit.");
            quit = true;
          } else {
            io.renderText("Editor cannot be used before anything is imported.\n");
          }
        }
      }
      try {
        next = getNext();
      } catch (IllegalStateException e) {
        io.renderText("Editor quit.");
        return;
      }
    }
  }

  /**
   * Gets a list of commands that this controller recognizes.
   *
   * @return the list of valid commands applying to this controller
   */
  protected String getCommands() {
    StringBuilder out = new StringBuilder("help {displays list of possible commands}\n"
        + "import ['project', 'image'] [path] "
        + "{imports an image to the top layer or imports an entire previously saved project}\n"
        + "export ['project', 'image'] [path] "
        + "{exports an the topmost image or the entire project to the specified path}\n"
        + "hide [layer #] {makes the specified layer invisible}\n"
        + "show [layer #] {makes the specified layer visible}\n"
        + "edit [" + editor.getCommands()[0]);
    for (int i = 1; i < editor.getCommands().length; i++) {
      out.append(", " + editor.getCommands()[i]);
    }
    out.append("] {modifies the top layer with the specified edit subcommand}\n"
        + "generate [height] [width] [r1] [g1] [b1] [r2] [g2] [b2] [squaresize] "
        + "{generates a checkerboard image}\n"
        + "q {quits}\n");
    return out.toString();
  }

  /**
   * Gets the next string from the scanner.
   *
   * @return the next string from the scanner
   * @throws IllegalStateException if the next string is a quit sequence
   */
  protected String getNext() throws IllegalStateException {
    String next = sc.next();
    if (next.equalsIgnoreCase("q")) {
      throw new IllegalStateException("quit");
    }
    return next;
  }

  /**
   * Returns the next int from the scanner.
   *
   * @return the next int
   * @throws IllegalArgumentException if the next item in the scanner is not an int
   */
  protected int getNextInt() throws IllegalArgumentException {
    String next = getNext();
    try {
      return Integer.parseInt(next);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Provided argument must be a number.");
    }
  }

  /**
   * Represents an ICommand relating to importing and exporting.
   */
  protected abstract class IOCommand implements ICommand {

    protected final HashMap<String, ICommand> IOcommands;

    /**
     * Creates a new ICommand and initializes the commands hashmap.
     */
    IOCommand() {
      IOcommands = new HashMap<>();
    }

    @Override
    public void apply() {
      String arg = getNext();
      if (!IOcommands.containsKey(arg)) {
        throw new IllegalArgumentException(
            "IO command must be followed by either 'project' or 'image'.");
      }
      IOcommands.get(arg).apply();
    }
  }

  /**
   * Represents an input command that accepts arguments "project" and "image". Imports either a
   * project or an image to the editor.
   */
  protected class ImportCommand extends IOCommand implements ICommand {

    /**
     * Initalizes the import command.
     */
    ImportCommand() {
      super();
      IOcommands.put("project", () -> importProject(getNext(), editor));
      IOcommands.put("image", () -> importPicture(getNext(), editor));
    }
  }

  /**
   * Represents an export command that accepts arguments "project" and "image. Exports either a
   * project or an image from the editor.
   */
  protected class ExportCommand extends IOCommand implements ICommand {

    /**
     * Initializes the export command.
     */
    ExportCommand() {
      super();
      IOcommands.put("project", () -> io.exportProject(getNext()));
      IOcommands.put("image", () -> io.exportPicture(getNext()));
    }
  }
}
