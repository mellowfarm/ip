package bug;

/**
 * The Parser class is responsible for parsing user input and returning the corresponding command.
 * It interprets the input string and creates the appropriate Command object based on the instruction.
 */
public class Parser {

    /**
     * Parses the input string and returns the corresponding Command.
     *
     * @param input the user input string
     * @return the Command that corresponds to the input
     */
    public static Command parse(String input) {
        if (input == null || input.isBlank()) {
            return new UnknownCommand();
        }

        // Split the input into instruction and contents
        String[] split = input.split("\\s+", 2);
        String instruction = split[0];
        String contents = ((split.length > 1) ? split[1] : "").trim();

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
            case "todo": {
                return new TodoCommand(contents);
            }
            case "deadline": {
                String[] parts = contents.split("/", 2);
                String desc = parts[0].trim();
                String byStr = parts[1].trim().split("\\s+", 2)[1];
                return new DeadlineCommand(desc, byStr);
            }
            case "event": {
                String[] parts = contents.split("/", 3);
                String desc = parts[0].trim();
                String startStr = parts[1].trim().split("\\s+", 2)[1];
                String endStr = parts[2].trim().split("\\s+", 2)[1];
                return new EventCommand(desc, startStr, endStr);
            }
            case "mark": case "unmark": case "delete": {
                int index = Integer.parseInt(contents) - 1;
                return instruction.equals("mark") ? new MarkCommand(index) : instruction.equals("unmark")
                        ? new UnmarkCommand(index) : new DeleteCommand(index);
            }
            default:
                return new UnknownCommand();

        }
    }
}


