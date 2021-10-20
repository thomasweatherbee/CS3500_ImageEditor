package controllertest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import hw05.controller.IController;
import hw05.controller.text.TextControllerImpl;
import hw05.model.oldeditor.ILayeredPicEditor;
import hw05.model.oldeditor.LayeredPicEditorImpl;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests controller.
 */
public class ControllerTest {

  private IController controller;
  private ILayeredPicEditor editor;
  private Appendable ap;

  @Before
  public void setUp() {
    ap = new StringBuffer();
    editor = new LayeredPicEditorImpl();
  }

  private void initController(String in) {
    controller = new TextControllerImpl(new StringReader(in), ap, editor);
  }

  @Test
  public void testGenerate() {
    initController("generate 1 1 2 2 2 9 9 9 1 q");
    controller.useEditor();
    assertArrayEquals(editor.getRGBAt(0, 0), new int[]{2, 2, 2});
  }

  @Test
  public void testImportExport() {
    initController(
        "generate 1 1 2 2 2 9 9 9 1 export image a.jpg export image a.png export "
            + "image a.ppm import image a.png import image a.ppm import image a.jpg q");
    controller.useEditor();
    assertArrayEquals(editor.getRGBAtLayer(0, 0, 0), new int[]{2, 2, 2});
    assertArrayEquals(editor.getRGBAtLayer(0, 0, 1), new int[]{2, 2, 2});
    assertArrayEquals(editor.getRGBAtLayer(0, 0, 2), new int[]{2, 2, 2});
  }

  @Test
  public void testHideLayer() {
    initController("generate 1 1 2 2 2 9 9 9 1 hide 0 q");
    controller.useEditor();
    assertFalse(editor.getLayerVisibility(0));
  }

  @Test
  public void testShowLayer() {
    initController("generate 1 1 2 2 2 9 9 9 1 hide 0 show 0 q");
    controller.useEditor();
    assertTrue(editor.getLayerVisibility(0));
  }

  @Test
  public void testEdit() {
    initController("generate 5 5 255 0 0 0 0 255 1 edit sharpen q");
    controller.useEditor();
    assertArrayEquals(editor.getRGBAt(2, 2), clampIntArray(0, 255, new int[]{
        (int) (2 * (-0.125 * 255 + -0.125 * 255 + -0.125 * 255) + 2 * (0.25 * 255 + 0.25 * 255)
            + -0.125 * 255 + 255 + -0.125), 0,
        (int) (2 * (-0.125 * 255 + -0.125 * 255) + 2 * (-0.125 * 255 + 0.25 * 255 + -0.125 * 255)
            + (0.25 * 255 + 0.25 * 255))}));
  }

  @Test
  public void testHelp() {
    initController("help q");
    controller.useEditor();
    assertEquals(ap.toString(), "Loaded. Type 'help' for a list of commands.\n"
        + "help {displays list of possible commands}\n"
        + "import ['project', 'image'] [path] {imports an image to the top layer "
        + "or imports an entire previously saved project}\n"
        + "export ['project', 'image'] [path] {exports an the topmost image "
        + "or the entire project to the specified path}\n"
        + "hide [layer #] {makes the specified layer invisible}\n"
        + "show [layer #] {makes the specified layer visible}\n"
        + "edit [blur, sharpen, monochrome, sepia] {modifies the top layer with "
        + "the specified edit subcommand}\n"
        + "generate [height] [width] [r1] [g1] [b1] [r2] [g2] [b2] [squaresize] "
        + "{generates a checkerboard image}\n"
        + "q {quits}\n"
        + "Command help run successfully.\n"
        + "Editor quit.");
  }

  @Test
  public void testHideBeforeStarted() {
    initController("hide 0 q");
    controller.useEditor();
    assertEquals(ap.toString(), "Loaded. Type 'help' for a list of commands.\n"
        + "Editor cannot be used before anything is imported.\n"
        + "Editor quit.");
  }

  @Test
  public void testShowBeforeStarted() {
    initController("show 0 q");
    controller.useEditor();
    assertEquals(ap.toString(), "Loaded. Type 'help' for a list of commands.\n"
        + "Editor cannot be used before anything is imported.\n"
        + "Editor quit.");
  }

  @Test
  public void testEditBeforeStarted() {
    initController("edit blur q");
    controller.useEditor();
    assertEquals(ap.toString(), "Loaded. Type 'help' for a list of commands.\n"
        + "Editor cannot be used before anything is imported.\n"
        + "Editor quit.");
  }

  @Test
  public void testInvalidCommand() {
    initController("generate 5 5 255 0 0 0 0 255 1 boop q");
    controller.useEditor();
    assertEquals(ap.toString(), "Loaded. Type 'help' for a list of commands.\n"
        + "Command generate run successfully.\n"
        + "Invalid command. Valid commands are:\n"
        + "help {displays list of possible commands}\n"
        + "import ['project', 'image'] [path] {imports an image to the top layer or imports "
        + "an entire previously saved project}\n"
        + "export ['project', 'image'] [path] {exports an the topmost image "
        + "or the entire project to the specified path}\n"
        + "hide [layer #] {makes the specified layer invisible}\n"
        + "show [layer #] {makes the specified layer visible}\n"
        + "edit [blur, sharpen, monochrome, sepia] {modifies the top "
        + "layer with the specified edit subcommand}\n"
        + "generate [height] [width] [r1] [g1] [b1] [r2] [g2] [b2] [squaresize] "
        + "{generates a checkerboard image}\n"
        + "q {quits}\n"
        + "Editor quit.");
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
