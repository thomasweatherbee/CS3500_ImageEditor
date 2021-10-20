All images used in this submission were created/taken by me.

Design:
MODEL
Base editor model -> IPicEditor [inherits all methods from IPicture] - provides editing functionality detailed in javadoc
     (note: edit works with function objects [ITransform], which take in an image and return a new image with
     pixel-by-pixel modifications)
Base read only model -> IPicture - allows client to look at the current state of a PicEditor
     (ie. width, height, and RGB at specific locations)
String command base editor model -> IPicEditorStringCommand [inherits all methods from IPicEditor]
     - allows the client to pass a string in to an edit command instead of an EditOperation.
Layered editor model -> ILayeredPicEditor [inherits all methods from IPicEditorStringCommand and ILayeredPicEditorState]
     - in addition to the view functionality from ILayeredPicEditorState, this adds the ability to manipulate multiple
     picture editors stacked on top of each other. May take in a factory that is able to specify the types of new created
     layers.
Layered read only model -> ILayeredPicEditorState [inherits all methods from IPicture] - allows the client to look at
     the current state of a layered picture editor. In addition to IPicture methods, also lets the client look at details
     about layers.

VIEW
Base editor out -> IPicEditorOut - able to export images contained in a view only model in .ppm format
Layered editor out -> IPicEditorOutLayered [implements IPicEditorOut] - able to export entire project
     and individual images in .png and .jpg format, in addition to IPicEditorOut functionality.
Base editor view -> IPicEditorView [implements IPicEditorOutLayered] - in addition to implemented interface
     functionality, also able to render messages to an appendable

CONTROLLER
Base controller -> IController - starts and passes user-generated commands to an ILayeredPicEditor
     (note: uses function objects in the form of ICommand, implementations of which are inside ControllerImpl,
     which are commands either to the view or model corresponding to certain user-generated commands)

Changes:
- Abstracted import commands into an abstract controller
- Added IPicEditorGUI to implement GUI features
- Added IViewListener to allow the controller to handle events taking place in the GUI
- Fixed bug in PictureImpl where empty images would throw an out of bounds exception in getHeight();

Changed for extra credit to work:
- Added ILayeredPicEditorV2 and IPicEditorV2, which both extend their V1 counterparts to allow for
  edit operations to take in arguments (mosaic takes in 1 argument and resize takes in 2)
- Added ITransformFactory to avoid changing ITransform, allowing transform classes to be constructed
  with specific arguments
