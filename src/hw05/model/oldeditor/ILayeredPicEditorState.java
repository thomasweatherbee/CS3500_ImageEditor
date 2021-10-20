package hw05.model.oldeditor;

import hw05.model.picture.IPicture;

/**
 * Represents the current state of a layered picture editor. Allows the user to look in and get
 * information about height, width, layers, and pixel RGBs, but does not allow the user to modify
 * any data.
 */
public interface ILayeredPicEditorState extends IPicture {

  /**
   * Gets the specified layer's visibility.
   *
   * @param layer layer whose visibility is being returned
   * @return is the layer visible
   */
  boolean getLayerVisibility(int layer) throws IllegalArgumentException;

  /**
   * Gets the number of layers in the editor.
   *
   * @return the number of layers
   */
  int getNumLayers();

  /**
   * Gets the index of the topmost layer that is visible.
   *
   * @return index of topmost visible layer
   */
  int getTopmostVisibleLayer();

  /**
   * Gets the RGB values of a specified pixel in a specified layer.
   *
   * @param x     x-value of specified pixel
   * @param y     y-value of specified pixel
   * @param layer layer index of specified pixel
   * @return the RGB values of the specified pixel
   * @throws IllegalArgumentException if either the layer or the x or y is out of bounds
   */
  int[] getRGBAtLayer(int x, int y, int layer) throws IllegalArgumentException;

}
