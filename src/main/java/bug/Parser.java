package bug;

/**
 * Parser class responsible for parsing user input and converting it into Command objects.
 * Handles validation and error cases gracefully by returning appropriate commands.
 */
public class Parser {

    /**
     * Parses user input string and returns the appropriate Command object.
     *
     * @param input the user input string to parse
     * @return a Command object corresponding to the input, or UnknownCommand if invalid
     */
    public static Command parse(String input) {
        if (input == null || input.isBlank()) {
            return new UnknownCommand();
        }

        String[] split = input.split("\\s+", 2);
        assert split.length >= 1 : "split should always have at least one element!";

        String instruction = split[0];
        assert instruction != null : "instruction should not be null!";

        String contents = ((split.length > 1) ? split[1] : "").trim();
        assert contents != null : "contents should not be null (can be empty)";

        switch (instruction) {
            case "find": {
                return new FindCommand(contents);
            }
            case "bye": {
                return new ByeCommand();
            }
            case "list": {
                return new ListCommand();
            }
            case "snooze": {
                return parseSnoozeCommand(contents);
            }
            case "todo": {
                return new TodoCommand(contents);
            }
            case "deadline": {
                return parseDeadlineCommand(contents);
            }
            case "event": {
                return parseEventCommand(contents);
            }
            case "mark":
            case "unmark":
            case "delete": {
                return parseIndexCommand(instruction, contents);
            }
            default:
                return new UnknownCommand();
        }
    }

    /**
     * Parses snooze command with format: "snooze <index> <duration>"
     * Example: "snooze 1 3d"
     */
    private static Command parseSnoozeCommand(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand();
        }

        String[] parts = contents.split("\\s+", 2);
        if (parts.length < 2) {
            return new UnknownCommand();
        }

        try {
            int index = Integer.parseInt(parts[0]) - 1;
            assert index >= -1 : "parsed index should be valid (note: -1 will be caught by commands)";
            String duration = parts[1];
            return new SnoozeCommand(index, duration);
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }

    /**
     * Parses deadline command with format: "deadline <description> /by <date>"
     * Example: "deadline finish project /by 2025-12-31"
     */
    private static Command parseDeadlineCommand(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand();
        }

        String[] parts = contents.split("/", 2);
        if (parts.length < 2) {
            return new UnknownCommand();
        }

        String description = parts[0].trim();
        assert description != null : "deadline description should not be null!";

        String dateStr = parts[1].trim().split("\\s+", 2)[1];
        assert dateStr != null : "deadline due date string should not be null!";

        if (description.isEmpty() || dateStr.isEmpty()) {
            return new UnknownCommand();
        }

        return new DeadlineCommand(description, dateStr);
    }

    /**
     * Parses event command with format: "event <description> /from <start> /to <end>"
     * Example: "event team meeting /from 2025-12-31 1400 /to 2025-12-31 1500"
     */
    private static Command parseEventCommand(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand();
        }

        String[] parts = contents.split("/", 3);
        if (parts.length < 2) {
            return new UnknownCommand();
        }
        assert parts.length >= 3 : "event command should have description, start, and end parts";

        String description = parts[0].trim();
        assert description != null : "event description should not be null!";

        String startStr = parts[1].trim().split("\\s+", 2)[1];
        assert startStr != null : "event start datetime string should not be null!";

        String endStr = parts[2].trim().split("\\s+", 2)[1];
        assert endStr != null : "event end datetime string should not be null!";

        if (description.isEmpty() || startStr.isEmpty() || endStr.isEmpty()) {
            return new UnknownCommand();
        }

        return new EventCommand(description, startStr, endStr);
    }

    private static Command parseIndexCommand(String instruction, String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand();
        }

        try {
            int index = Integer.parseInt(contents) - 1;
            assert index >= -1 : "parsed index should be valid (note: -1 will be caught by commands)";

            switch (instruction) {
                case "mark":
                    return new MarkCommand(index);
                case "unmark":
                    return new UnmarkCommand(index);
                case "delete":
                    return new DeleteCommand(index);
                default:
                    return new UnknownCommand();
            }
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }
}


