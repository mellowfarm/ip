package bug;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    private Storage storage;
    private Path testFile;

    @BeforeEach
    public void setUp() throws IOException {
        testFile = Files.createTempFile("test-bug", ".txt");
        storage = new Storage() { /* create anonymous class to override the load and update methods so it works for my
                                        temp test file */
            @Override
            public List<Task> load() {
                return super.load();
            }

            @Override
            public void update(TaskList tasks) {
                // use temp file for testing
                try {
                    Files.createDirectories(testFile.getParent());
                    FileWriter fw = new FileWriter(testFile.toFile());
                    for (int i = 0; i < tasks.size(); i++) {
                        fw.write(tasks.get(i).toFileString());
                        fw.write(System.lineSeparator());
                    }
                    fw.close();
                } catch (IOException e) {
                    System.err.println("Failed to save: " + e.getMessage());
                }
            }
        };

    }

    @AfterEach
    public void clean() throws IOException {
        // clean up temp file after each test
        Files.deleteIfExists(testFile);
    }

    // tests hehe

    // test 1: test loading from empty file
    @Test
    public void testLoadEmpty() {
        List<Task> tasks = storage.load();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty(), "task list should be empty if the file is empty!");
    }

    // test 2: test loading with pre-existing tasks
    @Test
    public void testLoadWithTasks() throws IOException {
        String taskData = "T | 0 | test task\nD | 1 | deadline task | 2025-09-01";
        Files.write(testFile, taskData.getBytes()); // write data into temp file

        List<Task> tasks = storage.load();
        assertNotNull(tasks);
        assertEquals(2, tasks.size(), "there should be 2 tasks in the loaded list!");
    }

    // test 3: test updating the file with a task list
    @Test
    public void testUpdateTasks() throws IOException {
        TaskList tasks = new TaskList();
        Task task = new ToDos("new task");
        tasks.add(task);

        storage.update(tasks);

        List<String> fileLines = Files.readAllLines(testFile);
        assertTrue(fileLines.size() > 0, "the file should contain tasks after update!");
        assertTrue(fileLines.get(0).contains("T | 0 | new task"), "the task description should be saved!");
    }
}
