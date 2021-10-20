package modeltest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import hw05.model.oldeditor.EditOperation;
import hw05.model.oldeditor.IPicEditor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract test class representing tests for all pic editors.
 */
public abstract class APicEditorTest {

  private IPicEditor editor;

  @Before
  public void setUp() throws Exception {
    editor = getEditor();
  }

  /**
   * Gets an IPicEditor.
   *
   * @return an IPicEditor
   */
  public abstract IPicEditor getEditor();

  @Test
  public void testSharpenMiddlePixel() {
    editor.start(editor.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    editor.edit(EditOperation.SHARPEN);
    assertArrayEquals(editor.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) (2 * (-0.125 * 255 + -0.125 * 255 + -0.125 * 255) + 2 * (0.25 * 255 + 0.25 * 255)
            + -0.125 * 255 + 255 + -0.125), 0,
        (int) (2 * (-0.125 * 255 + -0.125 * 255) + 2 * (-0.125 * 255 + 0.25 * 255 + -0.125 * 255)
            + (0.25 * 255 + 0.25 * 255))}));
  }

  @Test
  public void testSharpenEdgePixel() {
    editor.start(editor.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    editor.edit(EditOperation.SHARPEN);
    assertArrayEquals(editor.getRGBAt(0, 2), clampIntArray(0, 255, new int[]{
        (int) Math.round(3 * (-0.125 * 255) + 1 * (0.25 * 255) + 255), 0,
        (int) Math.round(2 * (-0.125 * 255) + 2 * (0.25 * 255))}));
  }

  @Test
  public void testSharpenRGBOutOfBounds() {
    editor.start(editor.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    editor.edit(EditOperation.SHARPEN);
    assertArrayEquals(editor.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) (2 * (-0.125 * 255 + -0.125 * 255 + -0.125 * 255) + 2 * (0.25 * 255 + 0.25 * 255)
            + -0.125 * 255 + 255 + -0.125), 0,
        (int) (2 * (-0.125 * 255 + -0.125 * 255) + 2 * (-0.125 * 255 + 0.25 * 255 + -0.125 * 255)
            + (0.25 * 255 + 0.25 * 255))}));
  }

  @Test
  public void testBlurMiddlePixel() {
    editor.start(editor.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    editor.edit(EditOperation.BLUR);
    assertArrayEquals(editor.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) Math.round(4 * (0.0625 * 255) + 0.25 * 255), 0,
        (int) Math.round(4 * (0.125 * 255))}));
  }

  @Test
  public void testBlurEdgePixel() {
    editor.start(editor.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    editor.edit(EditOperation.BLUR);
    assertArrayEquals(editor.getRGBAt(0, 0), clampIntArray(0, 255, new int[]{
        (int) Math.round(0.0625 * 255 + 0.25 * 255), 0,
        (int) Math.round(2 * (0.125 * 255))}));
  }

  @Test
  public void testSepiaWhite() {
    editor.start(editor.generateImage(1, 1, new int[]{255, 255, 255}, new int[]{255, 255, 255}, 1));
    editor.edit(EditOperation.SEPIA);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{255, 255, 239});
  }

  @Test
  public void testSepiaBlack() {
    editor.start(editor.generateImage(1, 1, new int[]{0, 0, 0}, new int[]{0, 0, 0}, 1));
    editor.edit(EditOperation.SEPIA);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{0, 0, 0});
  }

  @Test
  public void testSepiaRGB() {
    editor.start(editor.generateImage(1, 1, new int[]{37, 193, 225}, new int[]{0, 0, 0}, 1));
    editor.edit(EditOperation.SEPIA);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{205, 183, 143});
  }

  @Test
  public void testSepiaRGBOutOfBounds() {
    editor.start(editor.generateImage(1, 1, new int[]{255, 255, 255}, new int[]{255, 255, 255}, 1));
    editor.edit(EditOperation.SEPIA);
    assertNotEquals(editor.getRGBAt(0, 0)[0], Math.round((0.393 + 0.769 + 0.189) * 255.0));
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{255, 255, 239});
  }

  @Test
  public void testMonochromeWhite() {
    editor.start(editor.generateImage(1, 1, new int[]{255, 255, 255}, new int[]{255, 255, 255}, 1));
    editor.edit(EditOperation.MONOCHROME);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{255, 255, 255});
  }

  @Test
  public void testMonochromeBlack() {
    editor.start(editor.generateImage(1, 1, new int[]{0, 0, 0}, new int[]{0, 0, 0}, 1));
    editor.edit(EditOperation.MONOCHROME);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{0, 0, 0});
  }

  @Test
  public void testMonochromeRGB() {
    editor.start(editor.generateImage(1, 1, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 1));
    editor.edit(EditOperation.MONOCHROME);
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{122, 122, 122});
  }

  @Test(expected = IllegalStateException.class)
  public void testEditBeforeStarted() {
    editor.edit(EditOperation.SHARPEN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEditCommand() {
    editor.start(editor.generateImage(5, 5, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    editor.edit(null);
  }

  @Test
  public void testGetRGBAt() {
    editor.start(editor.generateImage(5, 5, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{182, 93, 230});
    assertArrayEquals(editor.getRGBAt(2, 0), new int[]{255, 255, 255});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRGBOutOfBounds() {
    editor.start(editor.generateImage(5, 5, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    editor.getRGBAt(-1, -1);
  }

  @Test
  public void testGetHeight() {
    editor.start(editor.generateImage(5, 10, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    assertEquals(editor.getHeight(), 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetHeightNotStarted() {
    editor.getHeight();
  }

  @Test
  public void testGetWidth() {
    editor.start(editor.generateImage(5, 10, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    assertEquals(editor.getWidth(), 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWidthNotStarted() {
    editor.getWidth();
  }

  @Test
  public void testGetPicCopy() {
    editor.start(editor.generateImage(5, 10, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 2));
    assertNotEquals(editor.getPicCopy(), editor.getPicCopy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNullInput() {
    editor.start(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNullColumn() {
    List<List<int[]>> in = new ArrayList<>();
    in.add(null);
    editor.start(in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartNonRectangularImage() {
    List<List<int[]>> in = new ArrayList<>();
    in.add(new ArrayList<int[]>());
    in.get(0).add(new int[]{0, 0, 0});
    in.add(new ArrayList<int[]>());
    editor.start(in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartRGBOutOfBounds() {
    List<List<int[]>> in = new ArrayList<>();
    in.add(new ArrayList<int[]>());
    in.get(0).add(new int[]{266, 0, 0});
    editor.start(in);
  }

  @Test
  public void testGenerateImage() {
    editor.start(editor.generateImage(3, 3, new int[]{255, 0, 0}, new int[]{0, 255, 255}, 1));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i / 1 % 2 == 1 ^ j / 1 % 2 == 1) {
          assertArrayEquals(editor.getRGBAt(i, j), new int[]{0, 255, 255});
        } else {
          assertArrayEquals(editor.getRGBAt(i, j), new int[]{255, 0, 0});
        }
      }
    }
  }

  protected int[] clampIntArray(int min, int max, int[] in) {
    int[] out = in.clone();
    for (int i = 0; i < in.length; i++) {
      if (in[i] < min) {
        out[i] = min;
      } else if (in[i] > max) {
        out[i] = max;
      } else {
        out[i] = in[i];
      }
    }
    return out;
  }
}