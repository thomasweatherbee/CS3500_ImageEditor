package hw05.model.oldeditor;

import hw05.model.picture.IPicture;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an implementation of a layered picture editor.
 */
public class LayeredPicEditorImpl implements ILayeredPicEditor {

  protected final List<Boolean> visibility;
  protected final IPicEditorSCFactory factory;
  private final List<IPicEditorStringCommand> layers;

  /**
   * Creates a new layered picture editor.
   *
   * @param fac factory which provides new editor layers of a specified type
   */
  public LayeredPicEditorImpl(IPicEditorSCFactory fac) {
    layers = new ArrayList<>();
    visibility = new ArrayList<>();
    factory = fac;
  }

  /**
   * Creates a new layered picture editor with the default layer being a PicEditorImpl.
   */
  public LayeredPicEditorImpl() {
    this(() -> {
      return new PicEditorImplSC();
    });
  }

  @Override
  public void addLayer(List<List<int[]>> in) {
    if (layers.size() != 0) {
      IPicEditor layer = factory.getPicEditor();
      layer.start(in);
      if (layer.getHeight() != layers.get(0).getHeight() || layer.getWidth() != layers.get(0)
          .getWidth()) {
        throw new IllegalArgumentException(
            "New picture's dimensions must be the same as original picture");
      }
    }
    layers.add(factory.getPicEditor());
    layers.get(layers.size() - 1).start(in);
    visibility.add(true);
  }

  @Override
  public void removeLayer(int layer) {
    assertStarted();
    if (layer < 0 || layer >= layers.size()) {
      throw new IllegalArgumentException("Layer is out of bounds");
    }
    layers.remove(layer);
  }

  @Override
  public void setLayerVisibility(int layer, boolean visible) throws IllegalArgumentException {
    assertStarted();
    if (layer < 0 || layer >= layers.size()) {
      throw new IllegalArgumentException("Invalid layer");
    }
    visibility.set(layer, visible);
  }

  @Override
  public boolean getLayerVisibility(int layer) {
    return visibility.get(layer);
  }

  @Override
  public int getNumLayers() {
    return layers.size();
  }

  @Override
  public int[] getRGBAtLayer(int x, int y, int layer) {
    assertStarted();
    if (layer < 0 || layer >= layers.size()) {
      throw new IllegalArgumentException("Layer index out of bounds");
    }
    return layers.get(layer).getRGBAt(x, y);
  }

  @Override
  public String[] getCommands() {
    return factory.getPicEditor().getCommands();
  }

  @Override
  public void editStringCommand(String command) {
    assertStarted();
    layers.get(getTopmostVisibleLayer()).editStringCommand(command);
  }

  @Override
  public void edit(EditOperation op) throws IllegalStateException {
    assertStarted();
    layers.get(getTopmostVisibleLayer()).edit(op);
  }

  @Override
  public List<List<int[]>> generateImage(int width, int height, int[] color1,
      int[] color2, int squareSize) {
    return factory.getPicEditor().generateImage(width, height, color1, color2, squareSize);
  }

  @Override
  public void start(List<List<int[]>> input) throws IllegalArgumentException {
    layers.clear();
    visibility.clear();
    layers.add(factory.getPicEditor());
    layers.get(0).start(input);
    visibility.add(true);
  }

  @Override
  public int getHeight() {
    assertStarted();
    return layers.get(0).getHeight();
  }

  @Override
  public int getWidth() {
    assertStarted();
    return layers.get(0).getWidth();
  }

  @Override
  public int[] getRGBAt(int x, int y) throws IllegalArgumentException {
    assertStarted();
    return layers.get(getTopmostVisibleLayer()).getRGBAt(x, y);
  }

  @Override
  public IPicture getPicCopy() {
    assertStarted();
    return layers.get(getTopmostVisibleLayer()).getPicCopy();
  }

  @Override
  public int getTopmostVisibleLayer() throws IllegalStateException {
    for (int i = layers.size() - 1; i >= 0; i--) {
      if (visibility.get(i)) {
        return i;
      }
    }
    throw new IllegalStateException("No visible layers");
  }

  /**
   * Throws an exception if the editor has not been started.
   */
  protected void assertStarted() {
    if (layers.size() < 1) {
      throw new IllegalStateException("Editor not started");
    }
  }
}