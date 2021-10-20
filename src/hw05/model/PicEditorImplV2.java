package hw05.model;

import hw05.model.oldeditor.PicEditorImplSC;
import hw05.model.transforms.ITransformFactory;
import hw05.model.transforms.MosaicTransform;
import hw05.model.transforms.ResizeTransform;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an improved picture editor implementation that can handle edits that take in arguments
 * (eg. mosaic and resize).
 */
public class PicEditorImplV2 extends PicEditorImplSC implements IPicEditorV2 {

  protected final Map<String, String[]> args;
  protected final Map<String, ITransformFactory> transforms;

  PicEditorImplV2() {
    args = new HashMap<>();
    transforms = new HashMap<>();

    for (String s : super.getCommands()) {
      args.put(s, new String[0]);
    }
    args.put("mosaic", new String[]{"Seeds"});
    args.put("resize", new String[]{"New Width", "New Height"});

    transforms.put("mosaic", (int... args) -> {
      ensureLength(args, 1);
      return new MosaicTransform(args[0]);
    });

    transforms.put("resize", (int... args) -> {
      ensureLength(args, 2);
      return new ResizeTransform(args[0], args[1]);
    });
  }

  /**
   * Ensures that a given int array is of a given length.
   *
   * @param in     array to be checked
   * @param length length the array should be
   * @throws IllegalArgumentException if in does not have the specified length
   */
  protected void ensureLength(int[] in, int length) throws IllegalArgumentException {
    if (in == null || in.length != length) {
      throw new IllegalArgumentException(
          "Expected " + length + " arguments, received " + in.length + " arguments.");
    }
  }

  @Override
  public String[] getCommands() {
    return args.keySet().toArray(new String[0]);
  }

  @Override
  public int getNumArgs(String command) {
    ensureValidCommand(command);
    return args.get(command).length;
  }

  @Override
  public String[] getCommandArgs(String op) {
    ensureValidCommand(op);
    return args.get(op);
  }

  @Override
  public void edit(String command, int... args) {
    if (command == null || args == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    ensureValidCommand(command);
    ensureLength(args, this.args.get(command).length);

    try {
      editStringCommand(command);
      return;
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("Invalid edit command")) {
        throw e;
      }
    }

    pic = transforms.get(command).get(args).apply(pic);
  }

  /**
   * Throws an exception if the requested command isn't supported.
   *
   * @param command command to be checked.
   */
  protected void ensureValidCommand(String command) {
    if (!args.containsKey(command)) {
      throw new IllegalArgumentException("Invalid command");
    }
  }
}