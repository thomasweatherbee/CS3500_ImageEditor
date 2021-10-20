package modeltest;

import hw05.model.oldeditor.IPicEditor;
import hw05.model.oldeditor.PicEditorImpl;

/**
 * Tests base editor.
 */
public class BasePicEditorTest extends APicEditorTest {

  @Override
  public IPicEditor getEditor() {
    return new PicEditorImpl();
  }


}
