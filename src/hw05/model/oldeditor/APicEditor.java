package hw05.model.oldeditor;

import hw05.model.picture.IPicture;
import hw05.model.transforms.ITransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an abstract image editor.
 */
public abstract class APicEditor implements IPicEditor {

  protected final Map<EditOperation, ITransform> edits;
  protected IPicture pic;
  protected boolean started;

  /**
   * Constructs a new picture editor with all given edit operations.
   *
   * @param edits all possible edit operations of this picture editor
   */
  APicEditor(Map<EditOperation, ITransform> edits) {
    this.edits = new HashMap<>();
    this.edits.putAll(edits);
    this.started = false;
  }

  @Override
  public List<List<int[]>> generateImage(int width, int height, int[] color1,
      int[] color2, int squareSize) {
    List<List<int[]>> out = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      out.add(new ArrayList<int[]>());
      for (int j = 0; j < height; j++) {
        if (i / squareSize % 2 == 1 ^ j / squareSize % 2 == 1) {
          out.get(i).add(Arrays.copyOf(color2, 3));
        } else {
          out.get(i).add(Arrays.copyOf(color1, 3));
        }
      }
    }
    return out;
  }

  @Override
  public void edit(EditOperation op) throws IllegalStateException {
    assertStarted();
    if (op == null) {
      throw new IllegalArgumentException("Invalid edit operation");
    }
    pic = edits.get(op).apply(pic);
  }

  @Override
  public int getHeight() {
    assertStarted();
    return pic.getHeight();
  }

  @Override
  public int getWidth() {
    assertStarted();
    return pic.getWidth();
  }

  @Override
  public int[] getRGBAt(int x, int y) {
    assertStarted();
    return pic.getRGBAt(x, y);
  }

  @Override
  public IPicture getPicCopy() {
    assertStarted();
    return pic.getPicCopy();
  }

  /**
   * Ensures the editor has started.
   *
   * @throws IllegalStateException if editor has not started
   */
  protected void assertStarted() throws IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Cannot edit when no picture has been loaded");
    }
  }

}
