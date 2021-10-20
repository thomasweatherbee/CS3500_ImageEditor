package hw05.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Represents an implementation of a picture editor view using a visual GUI to convey information.
 */
public class PicEditorGUIView extends JFrame implements IPicEditorGUI, ActionListener {

  private JScrollPane mainImageScrollPane;
  private JPanel centralPanel;
  private JLabel editorImage;
  private JMenu layerVisMenu;

  private IViewListener controller;

  /**
   * Creates a new GUI view.
   */
  public PicEditorGUIView() {
    super();

    setTitle("Picture Editor");
    setSize(1280, 720);

    centralPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    add(centralPanel);

    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    String[] displayText = {"New", "Load Image", "Load Project", "Save Image", "Save Project"};
    String[] actionCommands = {"new", "importimage", "importproject", "exportimage",
        "exportproject"};

    for (int i = 0; i < displayText.length; i++) {
      JMenuItem item = new JMenuItem(displayText[i]);
      item.setActionCommand(actionCommands[i]);
      item.addActionListener(this);
      fileMenu.add(item);
    }

    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    displayText = new String[]{"Blur", "Sharpen", "Sepia", "Grayscale", "Mosaic", "Resize"};
    actionCommands = new String[]{"blur", "sharpen", "sepia", "monochrome", "mosaic", "resize"};

    for (int i = 0; i < displayText.length; i++) {
      JMenuItem item = new JMenuItem(displayText[i]);
      item.setActionCommand(actionCommands[i]);
      item.addActionListener(this);
      editMenu.add(item);
    }

    JMenu layerMenu = new JMenu("Layers");
    menuBar.add(layerMenu);

    displayText = new String[]{"Generate Checkerboard Layer", "Remove Layer", "Show Layer",
        "Hide Layer"};
    actionCommands = new String[]{"generate", "remove", "show", "hide"};

    for (int i = 0; i < displayText.length; i++) {
      JMenuItem item = new JMenuItem(displayText[i]);
      item.setActionCommand(actionCommands[i]);
      item.addActionListener(this);
      layerMenu.add(item);
    }

    layerVisMenu = new JMenu("Visibility");
    layerMenu.add(layerVisMenu);

    editorImage = new JLabel();
    editorImage.setIcon(new ImageIcon(new BufferedImage(700, 500, BufferedImage.TYPE_INT_RGB)));
    editorImage.setAlignmentX(CENTER_ALIGNMENT);
    mainImageScrollPane = new JScrollPane(editorImage);
    centralPanel.add(mainImageScrollPane, CENTER_ALIGNMENT);

    pack();
  }

  @Override
  public int[] requestArgs(int args, String... labels)
      throws IllegalArgumentException, IllegalStateException {
    if (args != labels.length) {
      throw new IllegalArgumentException("Unlabeled argument");
    }

    int[] out = new int[args];

    for (int i = 0; i < args; i++) {
      boolean argGotten = false;

      try {
        String in = JOptionPane.showInputDialog(
            "Please input " + labels[i] + ".\nYou may exit this window to cancel the operation.");
        if (in == null) {
          throw new IllegalStateException("cancel");
        }
        out[i] = Integer.parseInt(in);
        argGotten = true;
      } catch (NumberFormatException e) {
        argGotten = false;
      }

      while (!argGotten) {
        try {
          String in = JOptionPane.showInputDialog("Invalid integer.\nPlease input " + labels[i]
              + ".\nYou may exit this window to cancel the operation.");
          if (in == null) {
            throw new IllegalStateException("cancel");
          }
          out[i] = Integer.parseInt(in);
          argGotten = true;
        } catch (NumberFormatException e) {
          argGotten = false;
        }
      }
    }

    return out;
  }

  @Override
  public String requestFilePath() {
    int retvalue = -1;
    File f = null;
    while (retvalue != JFileChooser.APPROVE_OPTION) {
      final JFileChooser fchooser = new JFileChooser(".");
      fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      retvalue = fchooser.showSaveDialog(this);
      f = fchooser.getSelectedFile();
      if (retvalue == JFileChooser.CANCEL_OPTION) {
        throw new IllegalStateException("cancel");
      }
    }
    return f.getAbsolutePath();
  }

  @Override
  public int[] requestColor() {
    Color col = JColorChooser.showDialog(this, "Choose a color", Color.WHITE);
    return new int[]{col.getRed(), col.getGreen(), col.getBlue()};
  }

  @Override
  public void update(Image toChange, List<Boolean> vis) {
    if (toChange == null || vis == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    editorImage.setIcon(new ImageIcon(toChange));
    editorImage.revalidate();
    mainImageScrollPane.repaint();
    centralPanel.repaint();
    pack();

    layerVisMenu.removeAll();
    for (int i = 0; i < vis.size(); i++) {
      JMenuItem item = new JMenuItem("Layer " + i + (vis.get(i) ? "   ✓" : ""));
      layerVisMenu.add(item);
    }
    layerVisMenu.revalidate();
    centralPanel.repaint();
  }

  @Override
  public void displayError(String e) {
    JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setListener(IViewListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot set listener to null");
    }
    controller = listener;
  }

  @Override
  public String requestDirectory() {
    int retvalue = -1;
    File f = null;
    while (retvalue != JFileChooser.APPROVE_OPTION) {
      final JFileChooser fchooser = new JFileChooser(".");
      fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      retvalue = fchooser.showSaveDialog(this);
      f = fchooser.getSelectedFile();
      if (retvalue == JFileChooser.CANCEL_OPTION) {
        throw new IllegalStateException("cancel");
      }
    }
    return f.getAbsolutePath();
  }

  @Override
  public void startView() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    controller.handleOperation(e.getActionCommand());
  }
}