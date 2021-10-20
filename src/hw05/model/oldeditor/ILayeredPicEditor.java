package hw05.model.oldeditor;

import java.util.List;

/**
 * Represents a picture editor that affords the client the same functionality as IPicEditor (ie.
 * programmatic editing and viewing functionality), but adds the ability to view and manipulate
 * multiple layers of images.
 */
public interface ILayeredPicEditor extends IPicEditorStringCommand, ILayeredPicEditorState {

  /**
   * Adds a new layer, if possible, to this editor, composed of the specified rgb values.
   *
   * @param layer set of rgb values to be added in the form of a new layer
   * @throws IllegalArgumentException if the dimensions of the image to be added are not the same as
   *                                  the dimensions of the base image in this editor
   */
  void addLayer(List<List<int[]>> layer) throws IllegalArgumentException;

  /**
   * Deletes the specified layer.
   *
   * @param layer layer to be deleted
   * @throws IllegalArgumentException if the specified layer does not exist
   */
  void removeLayer(int layer) throws IllegalArgumentException;

  /**
   * Modifies the visibility the specified layer.
   *
   * @param layer   layer whose visibility will be modified
   * @param visible is this layer visible
   * @throws IllegalArgumentException if the specified layer does not exist
   */
  void setLayerVisibility(int layer, boolean visible);
}
