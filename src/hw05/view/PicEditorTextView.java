package hw05.view;

import hw05.model.oldeditor.ILayeredPicEditorState;
import hw05.view.picoutput.IPicEditorOutLayered;
import hw05.view.picoutput.PicEditorOutLayeredImpl;
import java.io.IOException;

/**
 * Represents an implementation of a picture editor view using text to convey information.
 */
public class PicEditorTextView implements IPicEditorView {

  private final IPicEditorOutLayered picIO;
  private final Appendable ap;

  /**
   * Creates a new PicEditorViewImpl.
   *
   * @param editor editor this will be a view for
   * @param ap     appendable this view will write messages to
   */
  public PicEditorTextView(ILayeredPicEditorState editor, Appendable ap) {
    this.picIO = new PicEditorOutLayeredImpl(editor);
    this.ap = ap;
  }

  @Override
  public void renderText(String out) {
    try {
      ap.append(out);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to append " + out);
    }
  }

  @Override
  public void exportPicture(String f) {
    picIO.exportPicture(f);
  }

  @Override
  public void exportProject(String directory) {
    picIO.exportProject(directory);
  }

  @Override
  public void exportPictureAtLayer(String file, int layer) {
    picIO.exportPictureAtLayer(file, layer);
  }
}
