package bug;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // Test 1: test parsing todo
    @Test
    public void testParseTodo() {
        String input = "todo test task";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof TodoCommand);
        // TodoCommand doesn't expose description, but we can test execution
    }

    // Test 2: test parsing deadline
    @Test
    public void testParseDeadline() {
        String input = "deadline test deadline /by 2025-09-01";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof DeadlineCommand);
    }

    // Test 3: test parsing event
    @Test
    public void testParseEvent() {
        String input = "event test event /from 2025-09-01 0800 /to 2025-09-01 1200";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof EventCommand);
    }

    // Test 4: test unknown command
    @Test
    public void testUnknownCommand() {
        String input = "unknown command";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof UnknownCommand);
    }

    // Test 5: test parsing mark command
    @Test
    public void testParseMark() {
        String input = "mark 1";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof MarkCommand);
    }

    // Test 6: test parsing unmark command
    @Test
    public void testParseUnmark() {
        String input = "unmark 2";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof UnmarkCommand);
    }

    // Test 7: test parsing delete command
    @Test
    public void testParseDelete() {
        String input = "delete 3";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof DeleteCommand);
    }

    // Test 8: test parsing snooze command
    @Test
    public void testParseSnooze() {
        String input = "snooze 1 3d";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof SnoozeCommand);
    }

    // Test 9: test parsing find command
    @Test
    public void testParseFind() {
        String input = "find book";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof FindCommand);
    }

    // Test 10: test parsing bye command
    @Test
    public void testParseBye() {
        String input = "bye";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof ByeCommand);
    }

    // Test 11: test parsing list command
    @Test
    public void testParseList() {
        String input = "list";
        Command command = Parser.parse(input);

        assertNotNull(command);
        assertTrue(command instanceof ListCommand);
    }

    // Test 12: test case insensitive parsing
    @Test
    public void testCaseInsensitive() {
        String[] inputs = {"TODO test", "List", "BYE", "MARK 1"};

        for (String input : inputs) {
            Command command = Parser.parse(input);
            assertNotNull(command);
            assertFalse(command instanceof UnknownCommand,
                    "Should parse case-insensitive: " + input);
        }
    }

    // Test 13: test invalid inputs return UnknownCommand
    @Test
    public void testInvalidInputs() {
        String[] invalidInputs = {
                "",                    // Empty string
                "   ",                 // Just spaces
                null,                  // Null input
                "mark",                // Missing index
                "deadline",            // Missing description and date
                "snooze 1",           // Missing duration
                "randomcommand"        // Unknown command
        };

        for (String input : invalidInputs) {
            Command command = Parser.parse(input);
            assertNotNull(command);
            assertTrue(command instanceof UnknownCommand,
                    "Should be UnknownCommand for: " + input);
        }
    }
}