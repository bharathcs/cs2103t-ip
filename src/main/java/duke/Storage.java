package duke;

import duke.tasks.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Handle database data and operations for Duke.
 * Source of truth for the task list as well as able to write to and read from
 * the database as necessary.
 */
public class Storage {

    public final ArrayList<Task> tasksList = new ArrayList<>();
    public Ui ui;

    /**
     * Appends specified task to the end of the list.
     *
     * @param t new Task
     * @return boolean - has adding of task succeeded.
     */
    public boolean add(Task t) {
        return tasksList.add(t);
    }

    /**
     * Returns the task at the specified position in this list.
     *
     * @param index index of the task to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if there is no such index in list.
     */
    public Task get(int index) {
        return tasksList.get(index);
    }

    /**
     * Removes all tasks from internal list. List will be empty after this call.
     */
    public void clear() {
        tasksList.clear();
    }

    /**
     * Removes a specific task from internal list. List will be empty after this call.
     *
     * @param index index of the task to return
     * @throws IndexOutOfBoundsException if there is no such index in list.
     */
    public void remove(int index) {
        tasksList.remove(index);
    }

    /**
     * Returns number of tasks in list.
     *
     * @return integer number of tasks in list.
     */
    public int size() {
        return tasksList.size();
    }

    /**
     * Returns stream of Tasks in the list.
     *
     * @return stream of Tasks in the list
     */
    public Stream<Task> stream() {
        return tasksList.stream();
    }

    /**
     * Checks for the save file, and if it exists, read from it to restore tasks.
     * Relies on the Task class to handle conversions to and from a task object to
     * a string stored in the database.
     */
    public void readFromDatabase() {
        String dukeLocation = System.getProperty("user.dir");
        Path filePath = Paths.get(dukeLocation, "data", "tasks");

        if (!Files.isRegularFile(filePath)) {
            return;
        }

        ui.renderOutput("Reading last save  - " + filePath);
        ArrayList<String> failedParses = new ArrayList<>();

        try {
            Stream<String> databaseToString = Files.lines(filePath);
            tasksList.clear();
            databaseToString.forEach(
                    taskString -> {
                        try {
                            Task task = Task.stringToTask(taskString);
                            tasksList.add(task);
                        } catch (IllegalArgumentException e) {
                            failedParses.add(taskString);
                        }
                    }
            );
            databaseToString.close();
        } catch (IOException e) {
            ui.renderOutput("Save file could not be read. It will be wiped on the next save.");
        }

        if (failedParses.size() > 0) {
            String status = String.format(
                    "Save file was read partially. %d tasks added, %d tasks not understood:\n",
                    tasksList.size(),
                    failedParses.size()
            );
            status += "Failed to parse\n:";
            status += failedParses.stream().map(x -> x + "\n").reduce("", (a, b) -> a + b);
            ui.renderOutput(status);
        }
    }

    /**
     * Updates the user save file with the latest list of tasks.
     * If such a save file does not exist, it will create it.
     */
    public void writeToDatabase() {
        String dukeLocation = System.getProperty("user.dir");
        Path folderPath = Paths.get(dukeLocation, "data");
        Path filePath = Paths.get(dukeLocation, "data", "tasks");

        StringBuilder save = new StringBuilder();

        tasksList.forEach(x -> save.append(x.taskToString()).append(System.lineSeparator()));
        byte[] saveResult = save.toString().getBytes();

        ui.renderOutput("Saving tasks  - " + filePath);
        ui.renderOutput("DEBUG: Text to be saved \n" + save);

        try {
            if (!Files.isDirectory(folderPath)) {
                Files.createDirectory(folderPath);
            }
            if (Files.isRegularFile(filePath)) {
                Files.delete(filePath);
            }
            Files.write(filePath, saveResult);
        } catch (IOException e) {
            // Potentially add why it failed (e.g. no write permissions in folder)
            ui.renderOutput("File could not be saved. - " + e.getMessage());
        }
    }
}
