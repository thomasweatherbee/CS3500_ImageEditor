import hw05.controller.GUIControllerImpl;
import hw05.controller.IController;
import hw05.controller.text.TextControllerImpl;
import hw05.model.LayeredPicEditorV2;
import hw05.model.oldeditor.LayeredPicEditorImpl;
import hw05.view.PicEditorGUIView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Main class.
 */
public class Main {

  /**
   * Main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("Must have args.");
    }

    IController controller;

    switch (args[0]) {
      case "-script":
        if (args.length < 2) {
          throw new IllegalArgumentException("Script param must also have a script file path.");
        }
        InputStreamReader in;
        try {
          in = new InputStreamReader(new FileInputStream(args[1]));
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("Invalid script file.");
        }
        controller = new TextControllerImpl(in, System.out, new LayeredPicEditorImpl());
        break;
      case "-text":
        controller = new TextControllerImpl(new InputStreamReader(System.in),
            System.out, new LayeredPicEditorImpl());
        break;
      case "-interactive":
        PicEditorGUIView.setDefaultLookAndFeelDecorated(false);
        controller = new GUIControllerImpl(new LayeredPicEditorV2(), new PicEditorGUIView());
        break;
      default:
        throw new IllegalArgumentException("Invalid argument.");
    }

    controller.useEditor();
  }
}
