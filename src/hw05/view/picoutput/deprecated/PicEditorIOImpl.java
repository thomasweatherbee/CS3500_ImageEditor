package hw05.view.picoutput.deprecated;

import hw05.model.oldeditor.IPicEditor;
import hw05.view.picoutput.PicEditorOutImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of an IPicEditorIO that works for IPicEditors.
 */
public class PicEditorIOImpl extends PicEditorOutImpl implements IPicEditorIO {

  private final IPicEditor model;

  /**
   * Constructs a PicEditorIOImpl.
   *
   * @param model hw05.model to be used in this PicEditorIOImpl
   */
  public PicEditorIOImpl(IPicEditor model) {
    super(model);
    this.model = model;
  }

  @Override
  public void importPicture(String filename) {
    model.start(importPPM(filename));
  }

  protected List<List<int[]>> importPPM(String f) {
    List<List<int[]>> out = new ArrayList<>();

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(f));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + f + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int i = 0; i < width; i++) {
      out.add(new ArrayList<int[]>());
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        out.get(j).add(new int[]{r, g, b});
      }
    }
    return out;
  }

  @Override
  public void exportPicture(String f) {
    super.exportPPM(f);
  }
}
