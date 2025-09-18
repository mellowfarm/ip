package storage;

import task.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Storage class handles loading and saving tasks to and from a file.
 * It provides methods to read tasks from a file, parse them into appropriate task objects,
 * and update the file with the current task list.
 *
 * Tasks are stored in a text file in the following format:
 * <ul>
 *     <li>T | status | description</li>
 *     <li>D | status | description | due date</li>
 *     <li>E | status | description | start date | end date</li>
 * </ul>
 * This class also handles file creation if the file doesn't exist.
 */
public class Storage {
    private static final String FILE_NAME = "bug.txt";
    private final Path path = Paths.get("data", FILE_NAME);

    /**
     * Loads tasks from the storage file and returns them as a list of Task objects.
     * If the file is empty or does not exist, an empty list is returned.
     * The method parses the task data and creates appropriate task objects (e.g. Todo, Deadline, Event).
     *
     * @return a list of tasks loaded from the storage file, or an empty list if the file is empty or not found
     */
    public List<Task> load() {
        List<Task> out = new ArrayList<>(); // Accumulate loaded tasks in the desired format

        try {
            // Ensure the parent directory exists, create it if necessary
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent()); // gets the folder that should contain the data & create the folder
            }

            // If the file doesn't exist or is empty, return an empty list
            if (Files.notExists(path) || Files.size(path) == 0) {
                return out;
            }

            // Read all lines from the file
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String s = line.trim();
                if (s.isEmpty()) { // Skip blank lines
                    continue;
                }

                try {
                    // Split the line into its components
                    String[] p = s.split("\\s*\\|\\s*");

                    if (p.length < 3) {
                        System.err.println("Warning: Skipping invalid line (not enough data): " + s);
                        continue;
                    }

                    String type = p[0];
                    boolean isDone = "1".equals(p[1]);
                    String description = p[2];

                    // Create the appropriate task object based on the type
                    Task task = null;
                    switch (type) {
                        case "T":
                            if (p.length == 3) {
                                task = new ToDos(description);
                            }
                            break;
                        case "D":
                            if (p.length == 4) {
                                try {
                                    LocalDate by = LocalDate.parse(p[3]);
                                    task = new Deadlines(description, by);
                                } catch (Exception e) {
                                    System.err.println("Warning: Invalid date in line: " + s);
                                }
                            }
                            break;
                        case "E":
                            if (p.length == 5) {
                                try {
                                    LocalDateTime start = LocalDateTime.parse(p[3]);
                                    LocalDateTime end = LocalDateTime.parse(p[4]);
                                    task = new Events(description, start, end);
                                } catch (Exception e) {
                                    System.err.println("Warning: Invalid datetime in line: " + s);
                                }
                            }
                            break;
                        default:
                            System.err.println("Warning: Unknown task type in line: " + s);
                            break;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        out.add(task);
                    } else {
                        System.err.println("Warning: Could not parse line: " + s);
                    }

                } catch (Exception e) {
                    System.err.println("Warning: Skipping corrupted line: " + s);
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load tasks from file: " + e.getMessage());
            System.err.println("Starting with empty task list.");
        }
        return out;
    }

    /**
     * Updates the task file with the current list of tasks.
     * It writes each task in the list to the file in a specific format
     *
     * @param tasks the list of tasks to be written to the file
     */
    public void update(TaskList tasks) {
        try {
            // Ensure the parent directory exists, create it if necessary
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // Write each task to the file
            FileWriter fw = new FileWriter(path.toFile());
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toFileString());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to save: " + e.getMessage());
        }

    }

}