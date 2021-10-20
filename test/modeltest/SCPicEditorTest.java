package modeltest;

import hw05.model.oldeditor.IPicEditor;
import hw05.model.oldeditor.PicEditorImplSC;

/**
 * Tests string command editor.
 */
public class SCPicEditorTest extends APicEditorTest {

  @Override
  public IPicEditor getEditor() {
    return new PicEditorImplSC();
  }
}
