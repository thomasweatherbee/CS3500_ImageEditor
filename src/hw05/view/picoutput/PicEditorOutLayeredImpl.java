package hw05.view.picoutput;

import hw05.model.oldeditor.ILayeredPicEditorState;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents an implementation of an output for a layered picture editor.
 */
public class PicEditorOutLayeredImpl extends PicEditorOutImpl implements IPicEditorOutLayered {

  private final ILayeredPicEditorState layerModel;

  /**
   * Creates a new PicEditorOutLayeredImpl.
   *
   * @param model model this will be the output for
   */
  public PicEditorOutLayeredImpl(ILayeredPicEditorState model) {
    super(model);
    this.layerModel = model;
  }

  @Override
  public void exportPicture(String f) {
    exportPictureAtLayer(f, layerModel.getTopmostVisibleLayer());
  }

  @Override
  public void exportProject(String directory) {
    FileWriter rootFile;

    if (!new File(directory).mkdirs()) {
      throw new IllegalArgumentException(
          "Unable to create directory. Directory may already exist.");
    }

    try {
      rootFile = new FileWriter(directory + "\\root.txt");
    } catch (IOException e) {
      throw new IllegalArgumentException("FileWriter could not be instantiated");
    }

    StringBuilder outToFile = new StringBuilder("");
    outToFile.append(layerModel.getNumLayers() + "\n");

    for (int i = 0; i < layerModel.getNumLayers(); i++) {
      exportPictureAtLayer(directory + "\\layer" + i + ".jpg", i);
      outToFile.append(new File(directory).getAbsolutePath() + "\\layer" + i + ".jpg\n");
      outToFile.append(layerModel.getLayerVisibility(i) + "\n");
    }

    try {
      rootFile.append(outToFile.toString());
      rootFile.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write root file");
    }
  }

  @Override
  public void exportPictureAtLayer(String file, int layer) {
    String[] filename = file.split("\\.");
    if (filename.length != 2) {
      throw new IllegalArgumentException("Invalid file path");
    }
    if (filename[1].equals("ppm")) {
      exportPPM(file);
    } else if (filename[1].equals("jpg") || filename[1].equals("png") || filename[1]
        .equals("jpeg")) {
      BufferedImage out = new BufferedImage(layerModel.getWidth(), layerModel.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int i = 0; i < layerModel.getWidth(); i++) {
        for (int j = 0; j < layerModel.getHeight(); j++) {
          int[] rgb = layerModel.getRGBAtLayer(i, j, layer);
          out.setRGB(i, j, new Color(rgb[0], rgb[1], rgb[2]).getRGB());
        }
      }
      try {
        ImageIO.write(out, filename[1], new File(file));
      } catch (IOException e) {
        throw new IllegalArgumentException("Unable to write to specified file");
      }
    } else {
      throw new IllegalArgumentException("Invalid file path");
    }
  }
}
