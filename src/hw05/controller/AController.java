package hw05.controller;

import hw05.model.oldeditor.ILayeredPicEditor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Houses import methods used by multiple controller implementations.
 */
public abstract class AController {

  /**
   * Loads a complete project to the editor.
   *
   * @param path path of the project root file
   */
  protected void importProject(String path, ILayeredPicEditor editor) {
    FileInputStream rootIn;

    try {
      rootIn = new FileInputStream(path + "\\root.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("root.txt at " + path + " not found!");
    }

    Scanner sc = new Scanner(rootIn);

    String next = sc.next();
    int layers;
    try {
      layers = Integer.parseInt(next);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid root file");
    }

    while (editor.getNumLayers() > 0) {
      editor.removeLayer(0);
    }

    for (int i = 0; i < layers; i++) {
      String toImport = sc.next();
      importPicture(toImport, editor);
      editor.setLayerVisibility(i, Boolean.parseBoolean(sc.next()));
    }

    try {
      rootIn.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not close root file.");
    }
  }

  /**
   * Gets a PPM file in the form of a list of list of rgb values compatible with the editor.
   *
   * @param f location of the file to be imported
   * @return a list of list of rgb values representing the specified PPM file
   */
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

  protected List<List<int[]>> importJpgPng(String f) {
    List<List<int[]>> toEditor = new ArrayList<>();
    BufferedImage img;
    try {
      img = ImageIO.read(new File(f));
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to read file");
    }
    for (int i = 0; i < img.getWidth(); i++) {
      toEditor.add(new ArrayList<>());
      for (int j = 0; j < img.getHeight(); j++) {
        Color c = new Color(img.getRGB(i, j));
        toEditor.get(i).add(new int[]{c.getRed(), c.getGreen(), c.getBlue()});
      }
    }
    return toEditor;
  }

  /**
   * Loads the picture at the specified path to the editor.
   *
   * @param f path of picture to be loaded
   */
  protected void importPicture(String f, ILayeredPicEditor editor) {
    String[] filename = f.split("\\.");
    if (filename.length != 2) {
      throw new IllegalArgumentException("Invalid file path");
    }

    List<List<int[]>> toEditor;

    if (filename[1].equals("ppm")) {
      toEditor = importPPM(f);
    } else if (filename[1].equals("jpg") || filename[1].equals("png") || filename[1]
        .equals("jpeg")) {
      toEditor = importJpgPng(f);
    } else {
      throw new IllegalArgumentException("Invalid file path");
    }

    if (editor.getNumLayers() == 0) {
      editor.start(toEditor);
    } else {
      editor.addLayer(toEditor);
    }
  }
}
