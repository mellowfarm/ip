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
            return new UnknownCommand(":(! input cannot be empty!");
        }

        // Normalize multiple spaces to single spaces
        String normalizedInput = input.trim().replaceAll("\\s+", " ");

        String[] split = normalizedInput.split(" ", 2);
        assert split.length >= 1 : "split should always have at least one element!";

        String instruction = split[0].toLowerCase();
        assert instruction != null : "instruction should not be null!";

        String contents = ((split.length > 1) ? split[1] : "").trim();
        assert contents != null : "contents should not be null (can be empty)";

        // Handle special characters in instruction
        if (!instruction.matches("[a-zA-Z]+")) {
            return new UnknownCommand(":(! commands can only contain letters!");
        }

        switch (instruction) {
            case "find": {
                return parseFind(contents);
            }
            case "bye": {
                return parseBye(contents);
            }
            case "list": {
                return parseList(contents);
            }
            case "snooze": {
                return parseSnoozeCommand(contents);
            }
            case "todo": {
                return parseTodo(contents);
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
                return new UnknownCommand(":(! unknown command '" + instruction + "'!");
        }
    }

    /**
     * Parses find command.
     */
    private static Command parseFind(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand(":(! find command requires a search keyword!");
        }

        return new FindCommand(contents);
    }

    /**
     * Parses bye command, it should have no additional parameters.
     */
    private static Command parseBye(String contents) {
        if (!contents.isEmpty()) {
            return new UnknownCommand(":(! bye command should not have any parameters!");
        }
        return new ByeCommand();
    }

    /**
     * Parses list command, it should have no additional parameters.
     */
    private static Command parseList(String contents) {
        if (!contents.isEmpty()) {
            return new UnknownCommand(":(! list command should not have any parameters!");
        }
        return new ListCommand();
    }

    /**
     * Parses snooze command with format: "snooze <index> <duration>"
     * Example: "snooze 1 3d"
     */
    private static Command parseSnoozeCommand(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand(":(! snooze command requires index and duration!");
        }

        String[] parts = contents.split("\\s+", 2);
        if (parts.length < 2) {
            return new UnknownCommand(":(! snooze format: snooze <index> <duration>");
        }

        try {
            int index = Integer.parseInt(parts[0]) - 1;
            assert index >= -1 : "parsed index should be valid (note: -1 will be caught by commands)";

            if (index <= -1) {
                return new UnknownCommand(":(! task index must be positive!");
            }

            String duration = parts[1];
            return new SnoozeCommand(index, duration);
        } catch (NumberFormatException e) {
            return new UnknownCommand(":(! task index must be a number!");
        }
    }

    /**
     * Parses todo command.
     */
    private static Command parseTodo(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand(":(! todo command requires a description!");
        }

        return new TodoCommand(contents);
    }

    /**
     * Parses deadline command with format: "deadline <description> /by <date>"
     * Example: "deadline finish project /by 2025-12-31"
     */
    private static Command parseDeadlineCommand(String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand();
        }

        if (!contents.contains("/by")) {
            return new UnknownCommand(":(! deadline command missing '/by'! Format: deadline <description> /by <date>");
        }

        String[] parts = contents.split("/", 2);
        String description = parts[0].trim();
        assert description != null : "deadline description should not be null!";

        if (description.isEmpty()) {
            return new UnknownCommand(":(! deadline description cannot be empty!");
        }

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return new UnknownCommand(":(! deadline command missing date after '/by'!");
        }

        String dateStr = parts[1].trim().split("\\s+", 2)[1];
        assert dateStr != null : "deadline due date string should not be null!";


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

        if (!contents.contains("/from")) {
            return new UnknownCommand(":(! event command missing '/from'!");
        }

        if (!contents.contains("/to")) {
            return new UnknownCommand(":(! event command missing '/to'!");
        }

        String[] fromParts = contents.split("/from", 2);
        String description = fromParts[0].trim();

        if (description.isEmpty()) {
            return new UnknownCommand(":(! event description cannot be empty!");
        }

        String[] toParts = fromParts[1].split("/to", 2);
        if (toParts.length < 2) {
            return new UnknownCommand(":(! event command missing '/to'!");
        }

        String[] parts = contents.split("/", 3);
        if (parts.length < 2) {
            return new UnknownCommand();
        }

        String startStr = toParts[0].trim();
        String endStr = toParts[1].trim();

        if (startStr.isEmpty() || endStr.isEmpty()) {
            return new UnknownCommand(":(! event times cannot be empty!");
        }

        return new EventCommand(description, startStr, endStr);
    }

    /**
     * Parses index-based commands (mark, unmark, delete).
     */
    private static Command parseIndexCommand(String instruction, String contents) {
        if (contents.isEmpty()) {
            return new UnknownCommand(":(! " + instruction + " command requires a task index!");
        }

        if (contents.contains(" ")) {
            return new UnknownCommand(":(! " + instruction + " command should only have one parameter (task index)!");
        }

        try {
            int index = Integer.parseInt(contents) - 1;
            assert index >= -1 : "parsed index should be valid";

            if (index <= -1) {
                return new UnknownCommand(":(! task index must be a positive number!");
            }

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
        } catch (NumberFormatException e) {
            return new UnknownCommand(":(! task index must be a valid number, not '" + contents + "'!");
        }
    }
}


