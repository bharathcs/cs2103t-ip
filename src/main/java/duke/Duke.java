package duke;

import java.util.Scanner;

/**
 * CLI Task manager based on Duke.
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
                " ____        _        \n"
                        + "|  _ \\ _   _| | _____ \n"
                        + "| | | | | | | |/ / _ \\\n"
                        + "| |_| | |_| |   <  __/\n"
                        + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo + '\n' + "What can I do for you?");
        Ui.monitor();
    }

}
