package duke;

import duke.tool.*;

import java.util.Scanner;

/** Main engine */
public class Duke {
  public Duke() {}

  public static void main(String[] args) {
    Duke duke = new Duke();
    duke.run("", false);
  }

  /**
   * Runs The engine
   *
   * @param inputString User input string used in testing
   * @param isTest True in testing
   * @return The output showin in the UI
   */
  public String run(String inputString, boolean isTest) {
    UI.UIString = "";
    TaskList taskList = new TaskList();
    Storage storage = new Storage("tasks.txt", taskList);
    UI ui = new UI(storage);

    if (!isTest) {
      storage.readFromFile();
    }

    Parser parser = new Parser(ui, taskList);

    ui.printWelcomeMessage();
    boolean isExit = false;
    Scanner stringScanner = new Scanner(inputString);

    while (!isExit) {
      String fullCommand = ui.readCommand(stringScanner, isTest);
      ui.printLine(); // show the divider line ("_______")
      Command c = parser.parse(fullCommand);
      isExit = c.execute(taskList, ui);
      ui.printLine();

      if (!isTest) {
        storage.saveToFile();
      }
    }

    return UI.UIString;
  }
}
