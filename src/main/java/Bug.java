public class Bug {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Bug() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showGreeting();
        while (true) {
            String input = ui.readLine();
            if (input == null) {
                break;
            }

            Parser parser = new Parser(input);

            if (parser.hasError()) {
                ui.showError(parser.error);
                continue;
            }

            switch (parser.type) {
                case LIST:
                    ui.showList(tasks);
                    break;
                case TODO: {
                    Task todo = new ToDos(parser.desc);
                    tasks.add(todo);
                    storage.update(tasks);
                    ui.showToDo(todo, tasks);
                    break;
                }
                case DEADLINE: {
                    Task deadline = new Deadlines(parser.desc, parser.by);
                    tasks.add(deadline);
                    storage.update(tasks);
                    ui.showDeadline(deadline, tasks);
                    break;
                }
                case EVENT: {
                    Task event = new Events(parser.desc, parser.start, parser.end);
                    tasks.add(event);
                    storage.update(tasks);
                    ui.showEvent(event, tasks);
                    break;
                }
                case MARK: {
                    if (tasks.size() < parser.index) {
                        ui.showLine();
                        ui.showError(":(! no such task available!");
                        ui.showLine();
                        break;
                    }
                    Task task = tasks.get(parser.index);
                    task.markAsDone();
                    storage.update(tasks);
                    ui.showDone(task);
                    break;
                }
                case UNMARK: {
                    if (tasks.size() < parser.index) {
                        ui.showLine();
                        ui.showError(":(! no such task available!");
                        ui.showLine();
                        break;
                    }
                    Task task = tasks.get(parser.index);
                    task.markAsUndone();
                    storage.update(tasks);
                    ui.showUndone(task);
                    break;
                }
                case DELETE: {
                    if (tasks.size() < parser.index) {
                        ui.showLine();
                        ui.showError(":(! no such task available!");
                        ui.showLine();
                        break;
                    }
                    Task task = tasks.delete(parser.index);
                    storage.update(tasks);
                    ui.showDeleted(task, tasks);
                    break;
                }
                case BYE: {
                    ui.showBye();
                    return;
                }
                case UNKNOWN: {
                    ui.showError(parser.error);
                    break;
                }
                default:
                    ui.showError(":(! i don't know what you mean! please re-enter your task :)!");
            }
        }

    }

    public static void main(String[] args) {
        Bug bug = new Bug();
        bug.run();
    }
}

