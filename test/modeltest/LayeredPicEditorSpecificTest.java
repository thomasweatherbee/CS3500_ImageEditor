package modeltest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import hw05.model.oldeditor.ILayeredPicEditor;
import hw05.model.oldeditor.LayeredPicEditorImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests layered editor.
 */
public class LayeredPicEditorSpecificTest {

  ILayeredPicEditor model;

  @Before
  public void setUp() {
    model = new LayeredPicEditorImpl();
  }

  @Test
  public void testAddLayer() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    assertEquals(model.getNumLayers(), 1);
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{1, 1, 1});
  }

  @Test
  public void testAddSecondLayer() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    assertEquals(model.getNumLayers(), 2);
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{2, 2, 2});
  }

  @Test
  public void testGetRGBat() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{2, 2, 2});
  }

  @Test
  public void testRemoveLayer() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    model.removeLayer(1);
    assertEquals(model.getNumLayers(), 1);
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{1, 1, 1});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerOutOfBounds() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    model.removeLayer(2);
  }

  @Test
  public void testSetLayerInvisible() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    model.setLayerVisibility(1, false);
    assertEquals(model.getNumLayers(), 2);
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{1, 1, 1});
  }

  @Test
  public void testSetLayerVisible() {
    model.addLayer(model.generateImage(1, 1, new int[]{1, 1, 1}, new int[]{1, 1, 1}, 1));
    model.addLayer(model.generateImage(1, 1, new int[]{2, 2, 2}, new int[]{2, 2, 2}, 1));
    model.setLayerVisibility(1, false);
    model.setLayerVisibility(0, false);
    model.setLayerVisibility(1, true);
    assertEquals(model.getNumLayers(), 2);
    assertArrayEquals(model.getRGBAt(0, 0), new int[]{2, 2, 2});
  }
}
