package modeltest;

import hw05.model.oldeditor.IPicEditor;
import hw05.model.oldeditor.LayeredPicEditorImpl;

/**
 * Tests layered picture editor.
 */
public class LayerPicEditorTest extends APicEditorTest {

  @Override
  public IPicEditor getEditor() {
    return new LayeredPicEditorImpl();
  }
}
