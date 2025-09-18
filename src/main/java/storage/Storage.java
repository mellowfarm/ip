package storage;

import exception.BugException;
import task.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistent storage of tasks to and from files.
 * Manages file I/O operations, task serialization, and data directory creation.
 * Uses pipe-separated format: T|status|description, D|status|description|date, E|status|description|start|end
 */
public class Storage {
    private static final String FILE_NAME = "bug.txt";
    private final Path path = Paths.get("data", FILE_NAME);

    /**
     * Loads all tasks from the storage file.
     * Creates data directory if it doesn't exist, handles missing or corrupted files gracefully.
     *
     * @return list of loaded tasks, or empty list if file doesn't exist
     * @throws BugException if critical storage errors occur that prevent loading
     */
    public List<Task> load() throws BugException {
        List<Task> tasks = new ArrayList<>();

        try {
            ensureDataDirectoryExists();

            if (Files.notExists(path) || Files.size(path) == 0) {
                return tasks;
            }

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                String line = lines.get(lineNumber).trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task task = parseTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Log warning but continue processing other lines
                    System.err.println("Warning: Skipping corrupted line " + (lineNumber + 1) + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new BugException("Failed to read tasks from storage file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a single line from the storage file into a Task object.
     *
     * @param line the line to parse
     * @return the parsed Task, or null if parsing fails
     * @throws BugException if the line format is invalid
     */
    private Task parseTaskFromLine(String line) throws BugException {
        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {
            throw new BugException("Invalid task format: insufficient data");
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String description = parts[2];

        Task task;
        try {
            switch (type) {
                case "T":
                    if (parts.length != 3) {
                        throw new BugException("Invalid Todo format: expected 3 parts");
                    }
                    task = new ToDos(description);
                    break;
                case "D":
                    if (parts.length != 4) {
                        throw new BugException("Invalid Deadline format: expected 4 parts");
                    }
                    LocalDate dueDate = LocalDate.parse(parts[3]);
                    task = new Deadlines(description, dueDate);
                    break;
                case "E":
                    if (parts.length != 5) {
                        throw new BugException("Invalid Event format: expected 5 parts");
                    }
                    LocalDateTime start = LocalDateTime.parse(parts[3]);
                    LocalDateTime end = LocalDateTime.parse(parts[4]);
                    task = new Events(description, start, end);
                    break;
                default:
                    throw new BugException("Unknown task type: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (DateTimeParseException e) {
            throw new BugException("Invalid date format in task: " + e.getMessage());
        }
    }

    /**
     * Ensures the data directory exists, creating it if necessary.
     *
     * @throws BugException if directory creation fails
     */
    private void ensureDataDirectoryExists() throws BugException {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new BugException("Failed to create data directory: " + e.getMessage());
        }
    }

    /**
     * Saves all tasks to the storage file.
     * Overwrites existing file content with current task list in pipe-separated format.
     *
     * @param tasks the task list to save to file
     * @throws BugException if saving fails
     */
    public void update(TaskList tasks) throws BugException {
        try {
            ensureDataDirectoryExists();

            try (FileWriter writer = new FileWriter(path.toFile())) {
                for (int i = 0; i < tasks.size(); i++) {
                    writer.write(tasks.get(i).toFileString());
                    writer.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new BugException("Failed to save tasks to storage: " + e.getMessage());
        }
    }
}