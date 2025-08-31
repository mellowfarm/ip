package bug;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_DT2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public enum Type { LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, UNKNOWN }

    public Type type;
    public String desc;
    public LocalDate by;
    public LocalDateTime start, end;
    public int index;
    public String error;

    public Parser(String input) {
        if (input == null || input.isBlank()) {
            type = Type.UNKNOWN;
            desc = null;
            by = null;
            start = null;
            end = null;
            index = -1;
            error = ":(! empty input!";
        } else {
            String[] split = input.split("\\s+", 2);
            String instruction = split[0];
            String contents = (split.length > 1) ? split[1] : "";

            switch(instruction) {
                case "bye":
                    type = Type.BYE; break;
                case "list":
                    type = Type.LIST; break;
                case "todo": {
                    type = Type.TODO;
                    desc = contents.trim();
                    if (desc.isEmpty()) {
                        error = ":(! a todo task must have a description!";
                    }
                    break;
                }
                case "deadline": {
                    type = Type.DEADLINE;
                    String[] parts = contents.split("/", 2);
                    desc = parts[0].trim();
                    if (desc.isEmpty()) {
                        error = ":(! a deadline task must have a description!";
                        break;
                    }
                    String byStr = parts[1].trim().split("\\s+", 2)[1];
                    if (byStr.isEmpty()) {
                        error = ":(! a deadline task must have a due date!";
                        break;
                    }
                    try {
                        by = LocalDate.parse(byStr, INPUT_DT2);
                    } catch (Exception e) {
                        error = ":(! invalid date. use yyyy-MM-dd (eg 2005-11-27)!";
                        break;
                    }
                    break;
                }
                case "event": {
                    type = Type.EVENT;
                    String[] parts = contents.split("/", 3);
                    desc = parts[0].trim();
                    if (desc.isEmpty()) {
                        error = ":(! an event task must have a description!";
                        break;
                    }
                    String startStr = parts[1].trim().split("\\s+", 2)[1];
                    if (startStr.isEmpty()) {
                        error = ":(! an event task must have a start date!";
                        break;
                    }
                    String endStr = parts[2].trim().split("\\s+", 2)[1];
                    if (endStr.isEmpty()) {
                        error = ":(! an event task must have an end date!";
                        break;
                    }
                    try {
                        LocalDateTime startTemp = LocalDateTime.parse(startStr, INPUT_DT);
                        LocalDateTime endTemp = LocalDateTime.parse(endStr, INPUT_DT);
                        start = startTemp;
                        end = endTemp;
                    } catch (Exception e) {
                        error = ":(! invalid datetime. use yyyy-MM-dd HHmm (eg 2005-11-27 1800)!";
                        break;
                    }
                    break;
                }
                case "mark":
                case "unmark":
                case "delete": {
                    type = instruction.equals("mark") ? Type.MARK : instruction.equals("unmark")
                            ? Type.UNMARK : Type.DELETE;
                    index = Integer.parseInt(contents) - 1;
                    break;
                }
                default:
                    type = Type.UNKNOWN;
                    error = ":(! i don't know what you mean! please re-enter your task :)!";

            }
        }
    }

    public boolean hasError() {
        return error != null;
    }

}
