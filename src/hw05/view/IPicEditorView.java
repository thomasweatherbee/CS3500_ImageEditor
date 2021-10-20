package hw05.view;

import hw05.view.picoutput.IPicEditorOutLayered;

/**
 * Represents a picture and text output for a picture editor. The view portion of the MVC.
 */
public interface IPicEditorView extends IPicEditorOutLayered {

  /**
   * Renders a message to an appendable.
   *
   * @param out message to be rendered.
   */
  void renderText(String out);
}
