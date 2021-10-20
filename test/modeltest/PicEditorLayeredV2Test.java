package modeltest;

import static org.junit.Assert.assertArrayEquals;

import hw05.model.ILayeredPicEditorV2;
import hw05.model.LayeredPicEditorV2;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests new functionality added by IPicEditorLayeredV2.
 */
public class PicEditorLayeredV2Test {

  ILayeredPicEditorV2 model;

  @Before
  public void setUp() {
    model = new LayeredPicEditorV2();
  }

  @Test
  public void testMosaic() {
    model.start(model.generateImage(3, 3, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("mosaic", 1);
    int[] expectedRGB = {(40 * 5 + 80 * 4) / 9, (60 * 5 + 60 * 4) / 9, (80 * 5 + 40 * 4) / 9};
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertArrayEquals(model.getRGBAt(i, j), expectedRGB);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaic0Seeds() {
    model.start(model.generateImage(3, 3, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("mosaic", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicMoreSeedsThanPixels() {
    model.start(model.generateImage(3, 3, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("mosaic", 10);
  }

  @Test
  public void testResize() {
    model.start(model.generateImage(5, 5, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("resize", 3, 3);
    int[] expectedRGB = {58, 60, 62};

    assertArrayEquals(model.getRGBAt(1, 1), expectedRGB);
  }

  @Test
  public void testResizeNewAspectRatio() {
    model.start(model.generateImage(5, 5, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("resize", 3, 2);
    int[] expectedRGB = {60, 60, 60};

    assertArrayEquals(model.getRGBAt(1, 1), expectedRGB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResize0x0() {
    model.start(model.generateImage(5, 5, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("resize", 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResizeUpscale() {
    model.start(model.generateImage(5, 5, new int[]{40, 60, 80}, new int[]{80, 60, 40}, 1));
    model.edit("resize", 6, 5);
  }
}
