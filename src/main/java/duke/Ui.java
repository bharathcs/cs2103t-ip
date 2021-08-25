package duke;


import java.util.Scanner;

/**
 * Class to handle monitoring and taking in input.
 */
public class Ui {
    protected static void monitor() {
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
    public static void renderOutput(String output) {
        System.out.println("    ____________________________________________________________");
        output.lines().map(x -> "     " + x).forEach(System.out::println);
        System.out.println("    ____________________________________________________________");
    }
}
