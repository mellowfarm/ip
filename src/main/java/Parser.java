import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_DT2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public enum Type { LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, UNKNOWN };

    public Type type;
    public String desc;
    public LocalDate by;
    public LocalDateTime start, end;
    public int index;
    public String error;

    public Parser(String input) {
        if (input == null || input.isBlank()) {
            this.type = Type.UNKNOWN;
            this.desc = null;
            this.by = null;
            this.start = null;
            this.end = null;
            this.index = -1;
            this.error = ":(! empty input!";
            return;
        } else {
            String[] split = input.split("\\s+", 2);
            String instruction = split[0];
            String contents = (split.length > 1) ? split[1] : "";

            switch(instruction) {
                case "bye": this.type = Type.BYE; break;
                case "list": this.type = Type.LIST; break;
                case "todo": {
                    this.type = Type.TODO;
                    this.desc = contents.trim();
                    if (this.desc.isEmpty()) {
                        this.error = ":(! a todo task must have a description!";
                    }
                    break;
                }
                case "deadline": {
                    this.type = Type.DEADLINE;
                    String[] parts = contents.split("/", 2);
                    this.desc = parts[0].trim();
                    if (this.desc.isEmpty()) {
                        this.error = ":(! a deadline task must have a description!";
                        break;
                    }
                    String byStr = parts[1].trim().split("\\s+", 2)[1];
                    if (byStr.isEmpty()) {
                        this.error = ":(! a deadline task must have a due date!";
                        break;
                    }
                    try {
                        LocalDate byTemp = LocalDate.parse(byStr, INPUT_DT2);
                        this.by = byTemp;
                    } catch (Exception e) {
                        this.error = ":(! invalid date. use yyyy-MM-dd (eg 2005-11-27)!";
                        break;
                    }
                    break;
                }
                case "event": {
                    this.type = Type.EVENT;
                    String[] parts = contents.split("/", 3);
                    this.desc = parts[0].trim();
                    if (this.desc.isEmpty()) {
                        this.error = ":(! an event task must have a description!";
                        break;
                    }
                    String startStr = parts[1].trim().split("\\s+", 2)[1];
                    if (startStr.isEmpty()) {
                        this.error = ":(! an event task must have a start date!";
                        break;
                    }
                    String endStr = parts[2].trim().split("\\s+", 2)[1];
                    if (endStr.isEmpty()) {
                        this.error = ":(! an event task must have an end date!";
                        break;
                    }
                    try {
                        LocalDateTime startTemp = LocalDateTime.parse(startStr, INPUT_DT);
                        LocalDateTime endTemp = LocalDateTime.parse(endStr, INPUT_DT);
                        this.start = startTemp;
                        this.end = endTemp;
                    } catch (Exception e) {
                        this.error = ":(! invalid datetime. use yyyy-MM-dd HHmm (eg 2005-11-27 1800)!";
                        break;
                    }
                    break;
                }
                case "mark":
                case "unmark":
                case "delete": {
                    this.type = instruction.equals("mark") ? Type.MARK : instruction.equals("unmark")
                            ? Type.UNMARK : Type.DELETE;
                    this.index = Integer.parseInt(contents) - 1;
                    break;
                }
                default:
                    this.type = Type.UNKNOWN;
                    this.error = ":(! i don't know what you mean! please re-enter your task :)!";

            }
        }
    }

    public boolean hasError() {
        return error != null;
    }

}
