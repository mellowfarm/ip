package bug;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    private TestStorage storage;
    private Path testDataDir;
    private Path testFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary directory for testing
        testDataDir = Files.createTempDirectory("bug-test-data");
        testFile = testDataDir.resolve("test-bug.txt");

        // Create custom storage that uses our test file
        storage = new TestStorage(testFile);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test files
        if (Files.exists(testFile)) {
            Files.delete(testFile);
        }
        if (Files.exists(testDataDir)) {
            Files.delete(testDataDir);
        }
    }

    // Test 1: Load from empty/non-existent file
    @Test
    public void testLoadEmptyFile() {
        List<Task> tasks = storage.load();

        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    // Test 2: Save and load single todo task
    @Test
    public void testSaveAndLoadTodo() {
        TaskList tasks = new TaskList();
        ToDos todo = new ToDos("buy groceries");
        tasks.add(todo);

        // Save tasks
        storage.update(tasks);

        // Load tasks back
        List<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        Task loadedTask = loadedTasks.get(0);
        assertTrue(loadedTask instanceof ToDos);
        assertEquals("buy groceries", loadedTask.getDescription());
        assertEquals(" ", loadedTask.getStatusIcon()); // Not done
    }

    // Test 3: Save and load completed todo task
    @Test
    public void testSaveAndLoadCompletedTodo() {
        TaskList tasks = new TaskList();
        ToDos todo = new ToDos("completed task");
        todo.markAsDone();
        tasks.add(todo);

        storage.update(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        Task loadedTask = loadedTasks.get(0);
        assertEquals("X", loadedTask.getStatusIcon()); // Should be marked as done
    }

    // Test 4: Save and load deadline task
    @Test
    public void testSaveAndLoadDeadline() {
        TaskList tasks = new TaskList();
        LocalDate dueDate = LocalDate.of(2025, 12, 31);
        Deadlines deadline = new Deadlines("submit report", dueDate);
        tasks.add(deadline);

        storage.update(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        Task loadedTask = loadedTasks.get(0);
        assertTrue(loadedTask instanceof Deadlines);
        assertEquals("submit report", loadedTask.getDescription());
        assertTrue(loadedTask.toString().contains("31 Dec 2025"));
    }

    // Test 5: Save and load event task
    @Test
    public void testSaveAndLoadEvent() {
        TaskList tasks = new TaskList();
        LocalDateTime start = LocalDateTime.of(2025, 12, 25, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 25, 16, 0);
        Events event = new Events("team meeting", start, end);
        tasks.add(event);

        storage.update(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        Task loadedTask = loadedTasks.get(0);
        assertTrue(loadedTask instanceof Events);
        assertEquals("team meeting", loadedTask.getDescription());
        assertTrue(loadedTask.toString().contains("25 Dec 2025"));
        assertTrue(loadedTask.toString().contains("14:00"));
        assertTrue(loadedTask.toString().contains("16:00"));
    }

    // Test 6: Save and load multiple mixed task types
    @Test
    public void testSaveAndLoadMultipleTasks() {
        TaskList tasks = new TaskList();

        // Add different task types
        ToDos todo = new ToDos("buy milk");
        todo.markAsDone();
        tasks.add(todo);

        Deadlines deadline = new Deadlines("assignment", LocalDate.of(2025, 6, 15));
        tasks.add(deadline);

        Events event = new Events("party",
                LocalDateTime.of(2025, 7, 4, 19, 0),
                LocalDateTime.of(2025, 7, 4, 23, 0));
        event.markAsDone();
        tasks.add(event);

        storage.update(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());

        // Check todo
        Task task1 = loadedTasks.get(0);
        assertTrue(task1 instanceof ToDos);
        assertEquals("X", task1.getStatusIcon());

        // Check deadline
        Task task2 = loadedTasks.get(1);
        assertTrue(task2 instanceof Deadlines);
        assertEquals(" ", task2.getStatusIcon());

        // Check event
        Task task3 = loadedTasks.get(2);
        assertTrue(task3 instanceof Events);
        assertEquals("X", task3.getStatusIcon());
    }

    // Test 7: Test file format correctness
    @Test
    public void testFileFormatCorrectness() throws IOException {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("test todo"));
        tasks.add(new Deadlines("test deadline", LocalDate.of(2025, 1, 1)));

        storage.update(tasks);

        // Read the actual file content
        List<String> lines = Files.readAllLines(testFile);

        assertEquals(2, lines.size());
        assertEquals("T | 0 | test todo", lines.get(0));
        assertEquals("D | 0 | test deadline | 2025-01-01", lines.get(1));
    }

    // Test 8: Test overwriting existing file
    @Test
    public void testOverwriteExistingFile() {
        // Save first set of tasks
        TaskList tasks1 = new TaskList();
        tasks1.add(new ToDos("first task"));
        storage.update(tasks1);

        // Save different set of tasks (should overwrite)
        TaskList tasks2 = new TaskList();
        tasks2.add(new ToDos("second task"));
        tasks2.add(new ToDos("third task"));
        storage.update(tasks2);

        // Load and verify only second set exists
        List<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("second task", loadedTasks.get(0).getDescription());
        assertEquals("third task", loadedTasks.get(1).getDescription());
    }

    // Test 10: Test empty task list save
    @Test
    public void testSaveEmptyTaskList() {
        TaskList emptyTasks = new TaskList();
        storage.update(emptyTasks);

        List<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
    }

    // Custom Storage class that actually overrides the file path
    private static class TestStorage extends Storage {
        private final Path testFilePath;

        public TestStorage(Path testFilePath) {
            this.testFilePath = testFilePath;
        }

        @Override
        public List<Task> load() {
            // Copy the logic from Storage.load() but use our test file
            List<Task> out = new java.util.ArrayList<>();

            try {
                // Ensure the parent directory exists
                if (testFilePath.getParent() != null) {
                    Files.createDirectories(testFilePath.getParent());
                }

                // If the file doesn't exist or is empty, return empty list
                if (Files.notExists(testFilePath) || Files.size(testFilePath) == 0) {
                    return out;
                }

                // Read all lines from the test file
                for (String line : Files.readAllLines(testFilePath)) {
                    String s = line.trim();
                    if (s.isEmpty()) {
                        continue;
                    }

                    try {
                        String[] p = s.split("\\s*\\|\\s*");

                        // Skip lines that don't have enough parts
                        if (p.length < 3) {
                            continue; // Skip invalid lines
                        }

                        String type = p[0];
                        boolean isDone = "1".equals(p[1]);

                        switch (type) {
                            case "T":
                                if (p.length >= 3) {
                                    Task todo = new ToDos(p[2]);
                                    if (isDone) {
                                        todo.markAsDone();
                                    }
                                    out.add(todo);
                                }
                                break;
                            case "D":
                                if (p.length >= 4) {
                                    LocalDate by = LocalDate.parse(p[3]);
                                    Task deadline = new Deadlines(p[2], by);
                                    if (isDone) {
                                        deadline.markAsDone();
                                    }
                                    out.add(deadline);
                                }
                                break;
                            case "E":
                                if (p.length >= 5) {
                                    LocalDateTime start = LocalDateTime.parse(p[3]);
                                    LocalDateTime end = LocalDateTime.parse(p[4]);
                                    Task event = new Events(p[2], start, end);
                                    if (isDone) {
                                        event.markAsDone();
                                    }
                                    out.add(event);
                                }
                                break;
                            default:
                                // Unknown task type, skip this line
                                break;
                        }
                    } catch (Exception e) {
                        // Skip any line that causes parsing errors
                        System.err.println("Skipping invalid line: " + s);
                        continue;
                    }
                }
            } catch (IOException e) {
                System.err.println("Warning: failed to load test tasks: " + e.getMessage());
            }

            return out;
        }

        @Override
        public void update(TaskList tasks) {
            // Copy the logic from Storage.update() but use our test file
            try {
                // Ensure the parent directory exists
                if (testFilePath.getParent() != null) {
                    Files.createDirectories(testFilePath.getParent());
                }

                // Write each task to the test file
                FileWriter fw = new FileWriter(testFilePath.toFile());
                for (int i = 0; i < tasks.size(); i++) {
                    fw.write(tasks.get(i).toFileString());
                    fw.write(System.lineSeparator());
                }
                fw.close();
            } catch (IOException e) {
                System.err.println("Failed to save test file: " + e.getMessage());
            }
        }
    }
}