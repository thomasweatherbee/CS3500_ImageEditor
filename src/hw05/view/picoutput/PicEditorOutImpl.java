package hw05.view.picoutput;

import hw05.model.picture.IPicture;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of an IPicEditorIO that works for IPicEditors.
 */
public class PicEditorOutImpl implements IPicEditorOut {

  private final IPicture model;

  /**
   * Constructs a PicEditorIOImpl.
   *
   * @param model hw05.model to be used in this PicEditorIOImpl
   */
  public PicEditorOutImpl(IPicture model) {
    this.model = model;
  }

  @Override
  public void exportPicture(String f) {
    exportPPM(f);
  }

  /**
   * Exports a PPM containing the image data for this IPicture.
   *
   * @param f path the image should be saved to
   */
  protected void exportPPM(String f) {
    FileOutputStream out;

    try {
      out = new FileOutputStream(f);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + f + " not found!");
    }

    StringBuilder toOut = new StringBuilder("");
    toOut.append("P3\n");
    toOut.append(model.getWidth() + "\n");
    toOut.append(model.getHeight() + "\n");
    toOut.append(255 + "\n");
    for (int i = 0; i < model.getHeight(); i++) {
      for (int j = 0; j < model.getWidth(); j++) {
        for (int k : model.getRGBAt(j, i)) {
          toOut.append(k + "\n");
        }
      }
    }

    try {
      out.write(toOut.toString().getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to file.");
    }

    try {
      out.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not close file.");
    }
  }
}
