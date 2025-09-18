package bug;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the TaskList class.
 * Tests all TaskList operations including construction, adding, deleting, retrieving, and searching tasks.
 * Covers edge cases, boundary conditions, and various task types.
 */
public class TaskListTest {

    private TaskList taskList;
    private Task todo1;
    private Task todo2;
    private Task deadline1;
    private Task deadline2;
    private Task event1;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();

        // Create test tasks
        todo1 = new ToDos("Buy groceries");
        todo2 = new ToDos("Walk the dog");
        deadline1 = new Deadlines("Submit assignment", LocalDate.of(2025, 12, 31));
        deadline2 = new Deadlines("Pay bills", LocalDate.of(2025, 11, 15));
        event1 = new Events("Team meeting",
                LocalDateTime.of(2025, 10, 20, 14, 0),
                LocalDateTime.of(2025, 10, 20, 16, 0));
    }

    // Test 1: Constructor tests
    @Test
    public void testEmptyTaskListConstructor() {
        assertEquals(0, taskList.size());
        assertNotNull(taskList);
    }

    @Test
    public void testConstructorWithInitialTasks() {
        List<Task> initialTasks = Arrays.asList(todo1, deadline1, event1);
        TaskList newTaskList = new TaskList(initialTasks);

        assertEquals(3, newTaskList.size());
        assertEquals(todo1, newTaskList.get(0));
        assertEquals(deadline1, newTaskList.get(1));
        assertEquals(event1, newTaskList.get(2));
    }

    @Test
    public void testConstructorWithEmptyList() {
        TaskList newTaskList = new TaskList(new ArrayList<>());
        assertEquals(0, newTaskList.size());
    }

    // Test 2: Add task tests
    @Test
    public void testAddSingleTask() {
        taskList.add(todo1);

        assertEquals(1, taskList.size());
        assertEquals(todo1, taskList.get(0));
    }

    @Test
    public void testAddMultipleTasks() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        assertEquals(3, taskList.size());
        assertEquals(todo1, taskList.get(0));
        assertEquals(deadline1, taskList.get(1));
        assertEquals(event1, taskList.get(2));
    }

    @Test
    public void testAddDifferentTaskTypes() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        assertTrue(taskList.get(0) instanceof ToDos);
        assertTrue(taskList.get(1) instanceof Deadlines);
        assertTrue(taskList.get(2) instanceof Events);
    }

    // Test 3: Delete task tests
    @Test
    public void testDeleteSingleTask() {
        taskList.add(todo1);

        Task deleted = taskList.delete(0);

        assertEquals(todo1, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDeleteFromMiddle() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        Task deleted = taskList.delete(1);

        assertEquals(deadline1, deleted);
        assertEquals(2, taskList.size());
        assertEquals(todo1, taskList.get(0));
        assertEquals(event1, taskList.get(1));
    }

    @Test
    public void testDeleteFirstTask() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        Task deleted = taskList.delete(0);

        assertEquals(todo1, deleted);
        assertEquals(2, taskList.size());
        assertEquals(deadline1, taskList.get(0));
        assertEquals(event1, taskList.get(1));
    }

    @Test
    public void testDeleteLastTask() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        Task deleted = taskList.delete(2);

        assertEquals(event1, deleted);
        assertEquals(2, taskList.size());
        assertEquals(todo1, taskList.get(0));
        assertEquals(deadline1, taskList.get(1));
    }

    // Test 4: Get task tests
    @Test
    public void testGetTask() {
        taskList.add(todo1);
        taskList.add(deadline1);

        assertEquals(todo1, taskList.get(0));
        assertEquals(deadline1, taskList.get(1));
    }

    @Test
    public void testGetAfterDeletion() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);

        taskList.delete(1); // Delete deadline1

        assertEquals(todo1, taskList.get(0));
        assertEquals(event1, taskList.get(1));
    }

    // Test 5: Find tasks tests
    @Test
    public void testFindTasksWithMatches() {
        Task grocery1 = new ToDos("Buy groceries");
        Task grocery2 = new ToDos("Get fresh groceries from store");
        Task homework = new ToDos("Do homework");

        taskList.add(grocery1);
        taskList.add(grocery2);
        taskList.add(homework);

        ArrayList<Task> matches = taskList.findTasks("groceries");

        assertEquals(2, matches.size());
        assertTrue(matches.contains(grocery1));
        assertTrue(matches.contains(grocery2));
        assertFalse(matches.contains(homework));
    }

    @Test
    public void testFindTasksNoMatches() {
        taskList.add(todo1);
        taskList.add(deadline1);

        ArrayList<Task> matches = taskList.findTasks("nonexistent");

        assertEquals(0, matches.size());
    }

    @Test
    public void testFindTasksCaseSensitive() {
        Task upperCase = new ToDos("Buy GROCERIES");
        Task lowerCase = new ToDos("buy groceries");

        taskList.add(upperCase);
        taskList.add(lowerCase);

        ArrayList<Task> upperMatches = taskList.findTasks("GROCERIES");
        ArrayList<Task> lowerMatches = taskList.findTasks("groceries");

        assertEquals(1, upperMatches.size());
        assertEquals(1, lowerMatches.size());
        assertTrue(upperMatches.contains(upperCase));
        assertTrue(lowerMatches.contains(lowerCase));
    }

    @Test
    public void testFindTasksWithDifferentTaskTypes() {
        Task todo = new ToDos("meeting preparation");
        Task deadline = new Deadlines("meeting deadline", LocalDate.of(2025, 12, 31));
        Task event = new Events("team meeting",
                LocalDateTime.of(2025, 10, 20, 14, 0),
                LocalDateTime.of(2025, 10, 20, 16, 0));

        taskList.add(todo);
        taskList.add(deadline);
        taskList.add(event);

        ArrayList<Task> matches = taskList.findTasks("meeting");

        assertEquals(3, matches.size());
        assertTrue(matches.contains(todo));
        assertTrue(matches.contains(deadline));
        assertTrue(matches.contains(event));
    }

    // Test 6: Size tests
    @Test
    public void testSizeEmptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void testSizeAfterAdding() {
        taskList.add(todo1);
        assertEquals(1, taskList.size());

        taskList.add(deadline1);
        assertEquals(2, taskList.size());

        taskList.add(event1);
        assertEquals(3, taskList.size());
    }

    @Test
    public void testSizeAfterDeleting() {
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);
        assertEquals(3, taskList.size());

        taskList.delete(0);
        assertEquals(2, taskList.size());

        taskList.delete(0);
        assertEquals(1, taskList.size());

        taskList.delete(0);
        assertEquals(0, taskList.size());
    }

    // Test 7: Edge cases and integration tests
    @Test
    public void testAddAndDeleteSameTask() {
        taskList.add(todo1);
        Task deleted = taskList.delete(0);

        assertEquals(todo1, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testMultipleOperationsSequence() {
        // Add tasks
        taskList.add(todo1);
        taskList.add(deadline1);
        taskList.add(event1);
        assertEquals(3, taskList.size());

        // Delete middle task
        taskList.delete(1);
        assertEquals(2, taskList.size());
        assertEquals(todo1, taskList.get(0));
        assertEquals(event1, taskList.get(1));

        // Add another task
        taskList.add(todo2);
        assertEquals(3, taskList.size());
        assertEquals(todo2, taskList.get(2));

        // Find tasks
        ArrayList<Task> matches = taskList.findTasks("dog");
        assertEquals(1, matches.size());
        assertTrue(matches.contains(todo2));
    }

    @Test
    public void testTaskStatePreservation() {
        // Mark todo1 as done before adding
        todo1.markAsDone();
        taskList.add(todo1);

        Task retrieved = taskList.get(0);
        assertEquals("X", retrieved.getStatusIcon());
        assertEquals("Buy groceries", retrieved.getDescription());
    }

    @Test
    public void testFindWithSpecialCharacters() {
        Task specialTask = new ToDos("Buy @#$%^&*() special items");
        taskList.add(specialTask);

        ArrayList<Task> matches = taskList.findTasks("@#$%^&*()");
        assertEquals(1, matches.size());
        assertTrue(matches.contains(specialTask));
    }

    @Test
    public void testFindWithNumbers() {
        Task task1 = new ToDos("Complete task 123");
        Task task2 = new ToDos("Review version 123 of document");
        Task task3 = new ToDos("Simple task");

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        ArrayList<Task> matches = taskList.findTasks("123");
        assertEquals(2, matches.size());
        assertTrue(matches.contains(task1));
        assertTrue(matches.contains(task2));
        assertFalse(matches.contains(task3));
    }
}