package hw05.model;

import hw05.model.oldeditor.LayeredPicEditorImpl;
import hw05.model.picture.IPicture;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an improved implementation of a layered picture editor that can handle edit commands
 * that take in arguments.
 */
public class LayeredPicEditorV2 extends LayeredPicEditorImpl implements ILayeredPicEditorV2 {

  protected final List<IPicEditorV2> layers;

  /**
   * Creates a new V2 layered picture editor.
   */
  public LayeredPicEditorV2() {
    this.layers = new ArrayList<>();
  }

  @Override
  public int getNumArgs(String command) {
    return new PicEditorImplV2().getNumArgs(command);
  }

  @Override
  public String[] getCommandArgs(String op) {
    return new PicEditorImplV2().getCommandArgs(op);
  }

  @Override
  public void edit(String command, int... args) {
    assertStarted();
    if (command.equals("resize")) {
      for (IPicEditorV2 layer : layers) {
        layer.edit("resize", args);
      }
    } else {
      layers.get(getTopmostVisibleLayer()).edit(command, args);
    }
    System.out.println("complete");
  }

  @Override
  public void start(List<List<int[]>> layer) throws IllegalArgumentException {
    addLayer(layer);
    System.out.println("layers: " + layers.size());
  }

  @Override
  public void addLayer(List<List<int[]>> layer) throws IllegalArgumentException {
    if (getNumLayers() != 0) {
      if (layer.size() != getWidth() || layer.get(0).size() != getHeight()) {
        throw new IllegalArgumentException(
            "New picture's dimensions must be the same as original picture");
      }
    }
    layers.add(new PicEditorImplV2());
    layers.get(layers.size() - 1).start(layer);
    super.addLayer(new ArrayList<>());
  }

  @Override
  public void removeLayer(int layer) throws IllegalArgumentException {
    super.removeLayer(layer);
    layers.remove(layer);
  }

  @Override
  public boolean getLayerVisibility(int layer) throws IllegalArgumentException {
    return super.getLayerVisibility(layer);
  }

  @Override
  public int getNumLayers() {
    return layers.size();
  }

  @Override
  public int getTopmostVisibleLayer() {
    return super.getTopmostVisibleLayer();
  }

  @Override
  public int[] getRGBAtLayer(int x, int y, int layer) throws IllegalArgumentException {
    if (layer >= layers.size() || layer < 0) {
      throw new IllegalArgumentException("Invalid layer");
    }
    return layers.get(layer).getRGBAt(x, y);
  }

  @Override
  public int getHeight() {
    if (getNumLayers() > 0) {
      return layers.get(0).getHeight();
    }
    return -1;
  }

  @Override
  public int getWidth() {
    if (getNumLayers() > 0) {
      return layers.get(0).getWidth();
    }
    return -1;
  }

  @Override
  public int[] getRGBAt(int x, int y) throws IllegalArgumentException {
    return layers.get(super.getTopmostVisibleLayer()).getRGBAt(x, y);
  }

  @Override
  public IPicture getPicCopy() {
    return layers.get(super.getTopmostVisibleLayer()).getPicCopy();
  }

  @Override
  public String[] getCommands() {
    return new PicEditorImplV2().getCommands();
  }
}
