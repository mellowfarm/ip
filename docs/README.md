# Bug User Guide
Bug is a friendly task management chatbot that helps you keep track of your todo items, deadlines, and events through a simple conversational interface! :)
![Screenshot of Bug UI](Ui.png)
---
## Quick Start

1. Ensure you have **Java 17** or above installed on your computer.

2. Download the latest `bug.jar` file from the [releases page](https://github.com/yourusername/bug/releases).

3. Copy the file to the folder you want to use as the home folder for Bug.

4. Open a command terminal, navigate to the folder containing the jar file, and run:
```bash
   java -jar bug.jar
```
5. A GUI similar to the one shown above should appear in a few seconds.
6. Type commands in the command box and press Enter to execute them. For example:
    - type `list` to view all your tasks
    - type `todo buy milk` to add a simple task
7. Refer to the <u>Features</u> section below for details of each command.
---
## Features
>💡 **Notes about command format:**
>- Words in `<>` are parameters to be supplied by you
>- Parameters must be provided in the specified order
---
### Adding a todo task:`todo`
Adds a simple task without any date or time.
<br/>**Format:**`todo <description>`

<br/>**Examples:**
- `todo buy groceries`
- `todo buy brandy yay!`
- `todo do laundry`
---
### Adding a deadline task:`deadline`
Adds a task with a specific due date.
<br/>**Format:**`deadline <description> /by <date>`
- `<date>`must be in`YYYY-MM-DD`format

<br/>**Examples:**
- `deadline submit assignment /by 2025-12-31`
- `deadline pay bills /by 2025-11-15`
---
### Adding an event task:`event`
Adds a task with a specific start and end time.
<br/>**Format:**`event <description> /from <start_datetime> /to <end_datetime>`
- `<start_datetime>`and`<end_datetime>`must be in`YYYY-MM-DD HHMM`format

<br/>**Examples:**
- `event team meeting /from 2025-10-20 1400 /to 2025-10-20 1600`
- `event birthday party /from 2025-12-25 1800 /to 2025-12-25 2200`
---
### Listing all tasks:`list`
Shows a list of all tasks in your task list.
<br/>**Format:**`list`

<br/>**Sample output:**
```text
your tasks are here:
1.[T][ ] buy groceries
2.[D][ ] submit assignment (by: 31 Dec 2025)
3.[E][ ] team meeting (from: 20 Oct 2025 14:00 to: 20 Oct 2025 16:00)
```
### Task Type Indicators:
- `[T]`= Todo task
- `[D]`= Deadline task
- `[E]`= Event task
### Status Indicators:
- `[ ]`= Not completed
- `[X]`= Completed
---
### Marking a task as not done:`unmark`
Marks the specified task as not completed.
<br/>**Format:**`unmark <index>`

<br/>**Examples:**
- `unmark 1`marks the 1st task as not completed
---
### Deleting a task:`delete`
Permanently removes the specified task from your task list.
<br/>**Format:**`delete <index>`

<br/>**Examples:**
- `delete 3`deletes the 3rd task
- `list`followed by `delete 1`deletes the 1st task shown
---
### Finding tasks:`find`
Finds tasks whose descriptions contain the given keyword.
<br/>**Format:**`find <keyword>`
- The search is **case-sensitive**

<br/>**Examples:**
- `find book`returns tasks containing "book" in their description
- `find meeting`returns all tasks with "meeting" in the description

<br/>**Sample output:**
```text
your matching tasks are here:
1.[E][ ] team meeting (from: 20 Oct 2025 14:00 to: 20 Oct 2025 16:00)
2.[D][ ] prepare meeting agenda (by: 19 Oct 2025)
```
---
### Snoozing a task:`snooze`
Postpones a deadline or event task by the specified duration.
**Format:**`snooze <index duration>`

**Duration formats:**
- `Xd`for X days (eg `3d`= 3 days)
- `Xh`for X hours (eg `5h`= 5 hours)
- `Xm` for X minutes (eg `30m`= 30 minutes)

**Examples:**
- `snooze 1 2d`postpones the 1st task by 2 days
- `snooze 3 5h`postpones the 3rd task by 5 hours

**Note:**
- Cannot snooze completed tasks
- Cannot snooze todo tasks (they have no dates)
---
### Exiting the program:`bye`
Exits the Bug application.
**Format:**`bye`
---
# Command Summary

| Action | Format | Example |
|--------|--------|---------|
| Help | `help` | `help` |
| Add Todo | `todo DESCRIPTION` | `todo buy milk` |
| Add Deadline | `deadline DESCRIPTION /by DATE` | `deadline submit report /by 2025-12-31` |
| Add Event | `event DESCRIPTION /from START /to END` | `event meeting /from 2025-12-25 1400 /to 2025-12-25 1600` |
| List Tasks | `list` | `list` |
| Mark Done | `mark INDEX` | `mark 1` |
| Mark Undone | `unmark INDEX` | `unmark 2` |
| Delete Task | `delete INDEX` | `delete 3` |
| Find Tasks | `find KEYWORD` | `find meeting` |
| Snooze Task | `snooze INDEX DURATION` | `snooze 1 2d` |
| Exit | `bye` | `bye` |
