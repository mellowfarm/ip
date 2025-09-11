package bug;

public class Parser {

    public static Command parse(String input) {
        if (input == null || input.isBlank()) {
            return new UnknownCommand();
        } else {
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
                case "todo": {
                    return new TodoCommand(contents);
                }
                case "deadline": {
                    String[] parts = contents.split("/", 2);
                    assert parts.length >= 2 : "deadline command should have description and due date parts!";

                    String desc = parts[0].trim();
                    assert desc != null : "deadline description should not be null!";

                    String byStr = parts[1].trim().split("\\s+", 2)[1];
                    assert byStr != null : "deadline due date string should not be null!";

                    return new DeadlineCommand(desc, byStr);
                }
                case "event": {
                    String[] parts = contents.split("/", 3);
                    assert parts.length >= 3 : "event command should have description, start, and end parts";

                    String desc = parts[0].trim();
                    assert desc != null : "event description should not be null!";

                    String startStr = parts[1].trim().split("\\s+", 2)[1];
                    assert startStr != null : "event start datetime string should not be null!";

                    String endStr = parts[2].trim().split("\\s+", 2)[1];
                    assert endStr != null : "event end datetime string should not be null!";

                    return new EventCommand(desc, startStr, endStr);
                }
                case "mark":
                case "unmark":
                case "delete": {
                    int index = Integer.parseInt(contents) - 1;
                    assert index >= -1 : "parsed index should be valid (note: -1 will be caught by commands)";
                    return instruction.equals("mark") ? new MarkCommand(index) : instruction.equals("unmark")
                            ? new UnmarkCommand(index) : new DeleteCommand(index);
                }
                default:
                    return new UnknownCommand();

            }
        }
    }

}
