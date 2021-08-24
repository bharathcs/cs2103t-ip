import java.util.Scanner;

/** CLI Task manager based on Duke.
 *
 * @author bhcs
 */
public class Duke {

  /**
   * Begins main process of Duke.
   *
   * @param args java command line arguments.
   */
  public static void main(String[] args) {
    String logo =
      " ____        _        \n" +
      "|  _ \\ _   _| | _____ \n" +
      "| | | | | | | |/ / _ \\\n" +
      "| |_| | |_| |   <  __/\n" +
      "|____/ \\__,_|_|\\_\\___|\n";
    System.out.println("Hello from\n" + logo + '\n' + "What can I do for you?");
    monitor();
  }

  private static void monitor() {
    Scanner sc = new Scanner(System.in);

    Storage.readFromDatabase();

    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      DukeLogic.takeInput(input);
      if (input.strip().equalsIgnoreCase("bye")) {
        break;
      }
    }
    sc.close();
    Storage.writeToDatabase();
  }

  /**
   * Function to print indented, outlined output to console.
   *
   * @param output Multi-line or single line string.
   */
  protected static void renderOutput(String output) {
    System.out.println("    ____________________________________________________________");
    output.lines().map(x -> "     " + x).forEach(System.out::println);
    System.out.println("    ____________________________________________________________");
  }
}
