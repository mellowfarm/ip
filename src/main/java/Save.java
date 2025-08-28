import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Save {
    private final Path path = Paths.get("data", "bug.txt");

    public List<Task> load() {
        List<Task> out = new ArrayList<>(); // accumulate loaded tasks in the format we want into .txt file
        try { // for first run when there's nothing in the file -> create the file!
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent()); // gets the folder that should contain the data & create the folder
            }
            if (Files.notExists(path)) {
                return out;
            }
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String s = line.trim();
                if (s.isEmpty()) { // skip blank lines
                    continue;
                }
                String[] p = s.split("\\s*\\|\\s*");
                String type = p[0];
                boolean isDone = "1".equals(p[1]);
                switch (type) {
                    case "T":
                        Task todo = new ToDos(p[2]);
                        if (isDone) {
                            todo.markAsDone();
                        }
                        out.add(todo);
                        break;
                    case "D":
                        LocalDate by = LocalDate.parse(p[3]);
                        Task deadline = new Deadlines(p[2], by);
                        if (isDone) {
                            deadline.markAsDone();
                        }
                        out.add(deadline);
                        break;
                    case "E":
                        LocalDateTime start = LocalDateTime.parse(p[3]);
                        LocalDateTime end = LocalDateTime.parse(p[4]);
                        Task event = new Events(p[2], start, end);
                        if (isDone) {
                            event.markAsDone();
                        }
                        out.add(event);
                        break;
                    default:
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: failed to load tasks: " + e.getMessage());
        }
        return out;
    }

    public void update(List<Task> tasks) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            FileWriter fw = new FileWriter(path.toFile());
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toFileString());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.err.println("Failed to save: " + e.getMessage());
        }

    }

}