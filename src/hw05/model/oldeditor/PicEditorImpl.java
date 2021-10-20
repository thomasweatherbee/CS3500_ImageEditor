package hw05.model.oldeditor;

import hw05.model.picture.IPixel;
import hw05.model.picture.PictureImpl;
import hw05.model.picture.RGBPixelImpl;
import hw05.model.transforms.ITransform;
import hw05.model.transforms.colors.GrayscaleFilter;
import hw05.model.transforms.colors.SepiaFilter;
import hw05.model.transforms.filters.BlurFilter;
import hw05.model.transforms.filters.SharpenFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents an implementation of a picture editor with blur, sharpen, monochrome, and sepia
 * filters.
 */
public class PicEditorImpl extends APicEditor {

  /**
   * Creates a new picture editor with blur, sharpen, monochrome, and sepia filters.
   */
  public PicEditorImpl() {
    super(new HashMap<EditOperation, ITransform>());
    edits.put(EditOperation.BLUR, new BlurFilter());
    edits.put(EditOperation.SHARPEN, new SharpenFilter());
    edits.put(EditOperation.MONOCHROME, new GrayscaleFilter());
    edits.put(EditOperation.SEPIA, new SepiaFilter());
  }

  @Override
  public void start(List<List<int[]>> input) {
    List<List<IPixel>> out = new ArrayList<>();

    if (input == null) {
      throw new IllegalArgumentException("Null input array");
    }
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i) == null) {
        throw new IllegalArgumentException("Null column");
      }
      if (i != 0 && input.get(i - 1).size() != input.get(i).size()) {
        throw new IllegalArgumentException("Image must have the same number of pixels per column");
      }
      out.add(new ArrayList<IPixel>());

      for (int j = 0; j < input.get(i).size(); j++) {
        for (int k : input.get(i).get(j)) {
          if (k < 0 || k > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255, inclusive");
          }
        }
        out.get(i).add(new RGBPixelImpl(input.get(i).get(j)));
      }
    }
    pic = new PictureImpl(out);
    started = true;
  }
}
