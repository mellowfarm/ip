package bug;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ParserTest {
    // test 1: test parsing todo
    @Test
    public void testParseTodo() {
        String input = "todo test task";
        Parser parser = new Parser(input);

        assertEquals(Parser.Type.TODO, parser.type);
        assertEquals("test task", parser.desc);
    }

    // test 2: test parsing deadline
    @Test
    public void testParseDeadline() {
        String input = "deadline test deadline /by 2025-09-01";
        Parser parser = new Parser(input);

        assertEquals(Parser.Type.DEADLINE, parser.type);
        assertEquals("test deadline", parser.desc);
        assertEquals("2025-09-01", parser.by.toString());
    }

    // test 3: test parsing event
    @Test
    public void testParseEvent() {
        String input = "event test event /from 2025-09-01 0800 /to 2025/09/01 1200";
        Parser parser = new Parser(input);

        assertEquals(Parser.Type.EVENT, parser.type);
        assertEquals("test event", parser.desc);
        assertEquals("2025-09-01T08:00", parser.start.toString());
        assertEquals("2025-09-01T12:00", parser.end.toString());
    }

    // test 4: test unknown command
    @Test
    public void testUnknownCommand() {
        String input = "unknown command";
        Parser parser = new Parser(input);

        assertEquals(Parser.Type.UNKNOWN, parser.type);
        assertNotNull(parser.error);
    }

}
