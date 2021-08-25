package duke;


/**
 * CLI Task manager based on Duke.
 *
 * @author bhcs
 */
public class Duke {

    private final Ui ui;
    private final Storage storage;

    /**
     * Instantiates the Duke object and its required components.
     */
    public Duke() {
        storage = new Storage();
        DukeLogic dukeLogic = new DukeLogic(storage);
        ui = new Ui(dukeLogic);
        storage.ui = ui;
    }

    protected void run() {
        String logo =
                " ____        _        \n"
                        + "|  _ \\ _   _| | _____ \n"
                        + "| | | | | | | |/ / _ \\\n"
                        + "| |_| | |_| |   <  __/\n"
                        + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo + '\n' + "What can I do for you?");
        storage.readFromDatabase();
        ui.monitor();
        storage.writeToDatabase();
    }

    /**
     * Begins main process of Duke.
     *
     * @param args java command line arguments.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

}
