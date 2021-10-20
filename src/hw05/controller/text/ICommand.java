package hw05.controller.text;

/**
 * Represents a command the controller can run. Takes no arguments and returns nothing; intended for
 * use within another class.
 */
public interface ICommand {

  /**
   * Runs this command.
   */
  void apply();
}
