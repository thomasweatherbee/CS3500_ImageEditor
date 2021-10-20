package modeltest;

import static org.junit.Assert.assertArrayEquals;

import hw05.model.oldeditor.IPicEditorStringCommand;
import hw05.model.oldeditor.PicEditorImplSC;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests functionality specific to the string command editor.
 */
public class SCPicEditorSpecificTest {

  IPicEditorStringCommand model;

  @Before
  public void setUp() {
    model = new PicEditorImplSC();
  }

  @Test
  public void testBlurSC() {
    model.start(model.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    model.editStringCommand("blur");
    assertArrayEquals(model.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) Math.round(4 * (0.0625 * 255) + 0.25 * 255), 0,
        (int) Math.round(4 * (0.125 * 255))}));
  }

  @Test
  public void testSharpenSC() {
    model.start(model.generateImage(5, 5, new int[]{255, 0, 0}, new int[]{0, 0, 255}, 1));
    model.editStringCommand("sharpen");
    assertArrayEquals(model.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) (2 * (-0.125 * 255 + -0.125 * 255 + -0.125 * 255) + 2 * (0.25 * 255 + 0.25 * 255)
            + -0.125 * 255 + 255 + -0.125), 0,
        (int) (2 * (-0.125 * 255 + -0.125 * 255) + 2 * (-0.125 * 255 + 0.25 * 255 + -0.125 * 255)
            + (0.25 * 255 + 0.25 * 255))}));
  }

  @Test
  public void testSepiaSC() {
    model.start(model.generateImage(1, 1, new int[]{37, 193, 225}, new int[]{0, 0, 0}, 1));
    model.editStringCommand("sepia");
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{205, 183, 143});
  }

  @Test
  public void testMonochromeSC() {
    model.start(model.generateImage(1, 1, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 1));
    model.editStringCommand("monochrome");
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{122, 122, 122});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommandSC() {
    model.start(model.generateImage(1, 1, new int[]{182, 93, 230}, new int[]{255, 255, 255}, 1));
    model.editStringCommand("bonk");
  }

  @Test
  public void testGetCommands() {
    model.start(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    assertArrayEquals(model.getCommands(), new String[]{"blur", "sharpen", "monochrome", "sepia"});
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
