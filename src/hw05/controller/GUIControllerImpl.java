package hw05.controller;

import hw05.model.ILayeredPicEditorV2;
import hw05.view.IPicEditorGUI;
import hw05.view.IViewListener;
import hw05.view.picoutput.PicEditorOutLayeredImpl;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a controller for a picture editor. Functions as an in-between for view and the model.
 * This controller functions with a GUI-based view and enhanced picture editor with added support
 * for mosaic and resize functions.
 */
public class GUIControllerImpl extends AController implements IViewListener, IController {

  private final ILayeredPicEditorV2 model;
  private final IPicEditorGUI view;

  /**
   * Constructs a new GUI controller.
   *
   * @param model model this controller will operate on
   */
  public GUIControllerImpl(ILayeredPicEditorV2 model, IPicEditorGUI view) {
    this.model = model;
    this.view = view;
    this.view.setListener(this);
  }

  @Override
  public void handleOperation(String op) {
    try {
      if (Arrays.asList(model.getCommands()).contains(op)) {
        model.edit(op, view.requestArgs(model.getNumArgs(op), model.getCommandArgs(op)));
        updateImage();
        return;
      }
      switch (op) {
        case "new":
          while (model.getNumLayers() > 0) {
            model.removeLayer(0);
          }
          break;
        case "generate":
          int[] args;
          args = view.requestArgs(3, "Width", "Height", "Checker Size");
          model.addLayer(model
              .generateImage(args[0], args[1], view.requestColor(), view.requestColor(), args[2]));
          break;
        case "show":
          model.setLayerVisibility(view.requestArgs(1, "Layer #")[0], true);
          break;
        case "hide":
          model.setLayerVisibility(view.requestArgs(1, "Layer #")[0], false);
          break;
        case "remove":
          model.removeLayer(view.requestArgs(1, "Layer #")[0]);
          break;
        case "importimage":
          importPicture(view.requestFilePath(), model);
          break;
        case "importproject":
          importProject(view.requestDirectory(), model);
          break;
        case "exportproject":
          new PicEditorOutLayeredImpl(model).exportProject(view.requestDirectory());
          break;
        case "exportimage":
          new PicEditorOutLayeredImpl(model).exportPicture(view.requestFilePath());
          break;
        default:
          throw new IllegalArgumentException("Invalid operation.");
      }
    } catch (IllegalStateException e) {
      if (e.getMessage().equals("cancel")) {
        return;
      } else {
        view.displayError(e.getMessage());
      }
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

    updateImage();
  }

  /**
   * Gets all the model's layer visibilities.
   *
   * @return a list of all the model's layer visibilities
   */
  protected List<Boolean> getLayerVisibilities() {
    List<Boolean> out = new ArrayList<>();
    for (int i = 0; i < model.getNumLayers(); i++) {
      out.add(model.getLayerVisibility(i));
    }
    return out;
  }

  /**
   * Ensures that a given array is a given length.
   *
   * @param args   array to be verified
   * @param length length the array should be
   * @throws IllegalArgumentException if the array is the wrong length or the array is null
   */
  protected void ensureLength(int[] args, int length) throws IllegalArgumentException {
    if (args == null) {
      throw new IllegalArgumentException("Array cannot be null");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("Must have " + length + " args.");
    }
  }

  /**
   * Updates the with a new preview image and layer visibilities.
   */
  protected void updateImage() {
    if (model.getWidth() == -1 || model.getHeight() == -1) {
      view.update(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), new ArrayList<>());
      return;
    }
    BufferedImage out = new BufferedImage(model.getWidth(), model.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    try {
      model.getTopmostVisibleLayer();
    } catch (IllegalStateException e) {
      view.update(out, getLayerVisibilities());
      return;
    }
    for (int i = 0; i < model.getWidth(); i++) {
      for (int j = 0; j < model.getHeight(); j++) {
        int[] rgb = model.getRGBAt(i, j);
        out.setRGB(i, j, new Color(rgb[0], rgb[1], rgb[2]).getRGB());
      }
    }
    view.update(out, getLayerVisibilities());
  }

  @Override
  public void useEditor() {
    this.view.startView();
  }
}
