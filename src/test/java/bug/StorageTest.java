package bug;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    private Storage storage;
    private Path testFile;

    @BeforeEach
    public void setUp() throws IOException {
        testFile = Files.createTempFile("test-bug", ".txt");
        Files.write(testFile, "".getBytes());
        storage = new Storage() { /* create anonymous class to override the load and update methods so it works for my
            `                         temp test file */

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
        // delete temp file
        Files.deleteIfExists(testFile);
    }

    // test 1: test updating the file with a task list
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
