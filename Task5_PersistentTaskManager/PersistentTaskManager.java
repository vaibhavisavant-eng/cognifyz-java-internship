import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PersistentTaskManager {

    // =========================
    // GLOBAL VARIABLES
    // =========================

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Task> tasks = new ArrayList<>();

    static int nextTaskId = 1;

    static final String FILE_NAME = "tasks.txt";


    // =========================
    // TASK STATUS
    // =========================

    enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }


    // =========================
    // TASK CLASS
    // =========================

    static class Task {

        int id;
        String name;
        Status status;

        Task(int id, String name) {

            this.id = id;
            this.name = name;
            this.status = Status.PENDING;
        }

        Task(int id, String name, Status status) {

            this.id = id;
            this.name = name;
            this.status = status;
        }
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        loadTasks();

        boolean running = true;

        System.out.println();
        System.out.println("========================================");
        System.out.println("       PERSISTENT TASK MANAGER");
        System.out.println("========================================");

        if (tasks.isEmpty()) {

            System.out.println("No saved tasks found.");

        } else {

            System.out.println(
                    tasks.size()
                    + " saved task(s) loaded successfully.");
        }

        while (running) {

            displayMenu();

            int choice = readInteger(
                    "Enter your choice: ",
                    1,
                    11
            );

            switch (choice) {

                case 1:
                    addTasks();
                    break;

                case 2:
                    viewAllTasks();
                    break;

                case 3:
                    searchTasks();
                    break;

                case 4:
                    filterTasks();
                    break;

                case 5:
                    startTasks();
                    break;

                case 6:
                    updateTasks();
                    break;

                case 7:
                    completeTasks();
                    break;

                case 8:
                    deleteTasks();
                    break;

                case 9:
                    displayProgress();
                    break;

                case 10:
                    saveTasks();
                    break;

                case 11:

                    if (confirmExit()) {

                        running = false;
                    }

                    break;
            }
        }

        sc.close();
    }


    // =========================
    // MENU
    // =========================

    static void displayMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              MAIN MENU");
        System.out.println("========================================");

        System.out.println("1. Add Tasks");
        System.out.println("2. View All Tasks");
        System.out.println("3. Search Tasks");
        System.out.println("4. Filter Tasks by Status");
        System.out.println("5. Start Tasks");
        System.out.println("6. Update Tasks");
        System.out.println("7. Complete Tasks");
        System.out.println("8. Delete Tasks");
        System.out.println("9. View Progress");
        System.out.println("10. Save Tasks");
        System.out.println("11. Exit");

        System.out.println("========================================");
    }


    // =========================
    // ADD TASKS
    // =========================

    static void addTasks() {

        System.out.println();
        System.out.println("----------- ADD TASKS -----------");

        int count = readPositiveInteger(
                "How many tasks do you want to add? "
        );

        for (int i = 1; i <= count; i++) {

            System.out.println();
            System.out.println("Task " + i);

            String name = readTaskName();

            Task task = new Task(
                    nextTaskId,
                    name
            );

            tasks.add(task);

            System.out.println(
                    "Task added successfully!"
            );

            System.out.println(
                    "Task ID: " + nextTaskId
            );

            nextTaskId++;
        }

        saveTasks();

        System.out.println();
        System.out.println(
                count + " task(s) added and saved."
        );
    }


    // =========================
    // VIEW ALL TASKS
    // =========================

    static void viewAllTasks() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ALL TASKS");
        System.out.println("========================================");

        if (tasks.isEmpty()) {

            System.out.println(
                    "No tasks available."
            );

            return;
        }

        System.out.printf(
                "%-8s %-40s %-18s%n",
                "ID",
                "TASK",
                "STATUS"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Task task : tasks) {

            System.out.printf(
                    "%-8d %-40s %-18s%n",
                    task.id,
                    task.name,
                    getStatusText(task.status)
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.println(
                "Total Tasks: " + tasks.size()
        );
    }


    // =========================
    // SEARCH TASKS
    // =========================

    static void searchTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available to search."
            );

            return;
        }

        System.out.println();
        System.out.println("----------- SEARCH TASKS -----------");

        System.out.print(
                "Enter keyword to search: "
        );

        String keyword =
                sc.nextLine().trim().toLowerCase();

        if (keyword.isEmpty()) {

            System.out.println(
                    "Search keyword cannot be empty."
            );

            return;
        }

        boolean found = false;

        System.out.println();

        for (Task task : tasks) {

            if (task.name.toLowerCase().contains(keyword)) {

                if (!found) {

                    System.out.printf(
                            "%-8s %-40s %-18s%n",
                            "ID",
                            "TASK",
                            "STATUS"
                    );

                    System.out.println(
                            "--------------------------------------------------------------------------"
                    );

                    found = true;
                }

                System.out.printf(
                        "%-8d %-40s %-18s%n",
                        task.id,
                        task.name,
                        getStatusText(task.status)
                );
            }
        }

        if (!found) {

            System.out.println(
                    "No tasks found containing: "
                    + keyword
            );
        }
    }


    // =========================
    // FILTER TASKS
    // =========================

    static void filterTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println();
        System.out.println("----------- FILTER TASKS -----------");

        System.out.println("1. PENDING");
        System.out.println("2. IN PROGRESS");
        System.out.println("3. COMPLETED");
        System.out.println("4. ALL");

        int choice = readInteger(
                "Choose status: ",
                1,
                4
        );

        Status selectedStatus = null;

        if (choice == 1) {

            selectedStatus = Status.PENDING;

        } else if (choice == 2) {

            selectedStatus = Status.IN_PROGRESS;

        } else if (choice == 3) {

            selectedStatus = Status.COMPLETED;
        }

        boolean found = false;

        System.out.println();

        for (Task task : tasks) {

            if (choice == 4
                    || task.status == selectedStatus) {

                if (!found) {

                    System.out.printf(
                            "%-8s %-40s %-18s%n",
                            "ID",
                            "TASK",
                            "STATUS"
                    );

                    System.out.println(
                            "--------------------------------------------------------------------------"
                    );

                    found = true;
                }

                System.out.printf(
                        "%-8d %-40s %-18s%n",
                        task.id,
                        task.name,
                        getStatusText(task.status)
                );
            }
        }

        if (!found) {

            System.out.println(
                    "No tasks found for the selected status."
            );
        }
    }


    // =========================
    // START TASKS
    // =========================

    static void startTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println();
        System.out.println("----------- START TASKS -----------");

        int count = readCount(
                "How many tasks do you want to start? "
        );

        Set<Integer> selectedIds =
                new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to start: "
            );

            if (selectedIds.contains(id)) {

                System.out.println(
                        "You already selected this task."
                );

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found."
                );

                continue;
            }

            selectedIds.add(id);
            processed++;

            if (task.status == Status.COMPLETED) {

                System.out.println(
                        "Task is already completed."
                );

            } else if (task.status == Status.IN_PROGRESS) {

                System.out.println(
                        "Task is already in progress."
                );

            } else {

                task.status = Status.IN_PROGRESS;

                System.out.println(
                        "Task \"" + task.name
                        + "\" started."
                );
            }
        }

        saveTasks();

        System.out.println(
                "Changes saved successfully."
        );
    }


    // =========================
    // UPDATE TASKS
    // =========================

    static void updateTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println();
        System.out.println("----------- UPDATE TASKS -----------");

        int count = readCount(
                "How many tasks do you want to update? "
        );

        Set<Integer> selectedIds =
                new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to update: "
            );

            if (selectedIds.contains(id)) {

                System.out.println(
                        "You already selected this task."
                );

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found."
                );

                continue;
            }

            selectedIds.add(id);
            processed++;

            System.out.println(
                    "Current task name: "
                    + task.name
            );

            String newName =
                    readTaskName();

            task.name = newName;

            System.out.println(
                    "Task updated successfully."
            );
        }

        saveTasks();

        System.out.println(
                "Changes saved successfully."
        );
    }


    // =========================
    // COMPLETE TASKS
    // =========================

    static void completeTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "----------- COMPLETE TASKS -----------"
        );

        int count = readCount(
                "How many tasks do you want to complete? "
        );

        Set<Integer> selectedIds =
                new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to complete: "
            );

            if (selectedIds.contains(id)) {

                System.out.println(
                        "You already selected this task."
                );

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found."
                );

                continue;
            }

            selectedIds.add(id);
            processed++;

            if (task.status == Status.COMPLETED) {

                System.out.println(
                        "Task is already completed."
                );

            } else {

                task.status = Status.COMPLETED;

                System.out.println(
                        "Task \"" + task.name
                        + "\" completed."
                );
            }
        }

        saveTasks();

        System.out.println(
                "Changes saved successfully."
        );
    }


    // =========================
    // DELETE TASKS
    // =========================

    static void deleteTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "----------- DELETE TASKS -----------"
        );

        int count = readCount(
                "How many tasks do you want to delete? "
        );

        Set<Integer> selectedIds =
                new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to delete: "
            );

            if (selectedIds.contains(id)) {

                System.out.println(
                        "You already selected this task."
                );

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found."
                );

                continue;
            }

            selectedIds.add(id);
            processed++;

            System.out.println(
                    "Task: " + task.name
            );

            String answer = readYesNo(
                    "Are you sure you want to delete this task? (yes/no): "
            );

            if (answer.equals("yes")
                    || answer.equals("y")) {

                tasks.remove(task);

                System.out.println(
                        "Task deleted successfully."
                );

            } else {

                System.out.println(
                        "Deletion cancelled."
                );
            }
        }

        saveTasks();

        System.out.println(
                "Changes saved successfully."
        );
    }


    // =========================
    // PROGRESS
    // =========================

    static void displayProgress() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "             TASK PROGRESS"
        );
        System.out.println(
                "========================================"
        );

        int total = tasks.size();

        int completed = 0;
        int inProgress = 0;
        int pending = 0;

        for (Task task : tasks) {

            if (task.status == Status.COMPLETED) {

                completed++;

            } else if (task.status == Status.IN_PROGRESS) {

                inProgress++;

            } else {

                pending++;
            }
        }

        int remaining =
                total - completed;

        double percentage = 0;

        if (total > 0) {

            percentage =
                    ((double) completed / total) * 100;
        }

        System.out.println(
                "Total Tasks      : " + total
        );

        System.out.println(
                "Completed        : " + completed
        );

        System.out.println(
                "In Progress      : " + inProgress
        );

        System.out.println(
                "Pending          : " + pending
        );

        System.out.println(
                "Remaining        : " + remaining
        );

        System.out.printf(
                "Progress         : %.2f%%%n",
                percentage
        );

        System.out.println(
                "========================================"
        );
    }


    // =========================
    // SAVE TASKS
    // =========================

    static void saveTasks() {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(FILE_NAME)
                        )
        ) {

            for (Task task : tasks) {

                writer.write(
                        task.id
                        + "|"
                        + task.name
                        + "|"
                        + task.status
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error while saving tasks: "
                    + e.getMessage()
            );
        }
    }


    // =========================
    // LOAD TASKS
    // =========================

    static void loadTasks() {

        File file =
                new File(FILE_NAME);

        if (!file.exists()) {

            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            int highestId = 0;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {

                    continue;
                }

                String[] parts =
                        line.split("\\|", 3);

                if (parts.length != 3) {

                    System.out.println(
                            "Skipping invalid saved task."
                    );

                    continue;
                }

                try {

                    int id =
                            Integer.parseInt(
                                    parts[0]
                            );

                    String name =
                            parts[1];

                    Status status =
                            Status.valueOf(
                                    parts[2]
                            );

                    Task task =
                            new Task(
                                    id,
                                    name,
                                    status
                            );

                    tasks.add(task);

                    if (id > highestId) {

                        highestId = id;
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Skipping corrupted task record."
                    );
                }
            }

            nextTaskId =
                    highestId + 1;

        } catch (IOException e) {

            System.out.println(
                    "Error while loading tasks: "
                    + e.getMessage()
            );
        }
    }


    // =========================
    // FIND TASK
    // =========================

    static Task findTask(int id) {

        for (Task task : tasks) {

            if (task.id == id) {

                return task;
            }
        }

        return null;
    }


    // =========================
    // READ TASK NAME
    // =========================

    static String readTaskName() {

        while (true) {

            System.out.print(
                    "Enter task name: "
            );

            String name =
                    sc.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println(
                        "Task name cannot be empty."
                );

                continue;
            }

            if (name.length() > 100) {

                System.out.println(
                        "Task name cannot exceed "
                        + "100 characters."
                );

                continue;
            }

            // Prevent the file separator
            // from breaking the saved format.
            if (name.contains("|")) {

                System.out.println(
                        "Task name cannot contain '|'."
                );

                continue;
            }

            return name;
        }
    }


    // =========================
    // INTEGER INPUT
    // =========================

    static int readInteger(
            String message,
            int min,
            int max) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty."
                );

                continue;
            }

            try {

                int value =
                        Integer.parseInt(input);

                if (value < min
                        || value > max) {

                    System.out.println(
                            "Please enter a number between "
                            + min
                            + " and "
                            + max
                            + "."
                    );

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a whole number."
                );
            }
        }
    }


    // =========================
    // POSITIVE INTEGER
    // =========================

    static int readPositiveInteger(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty."
                );

                continue;
            }

            try {

                int value =
                        Integer.parseInt(input);

                if (value <= 0) {

                    System.out.println(
                            "Enter a number greater than 0."
                    );

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! "
                        + "Please enter a whole number."
                );
            }
        }
    }


    // =========================
    // READ COUNT
    // =========================

    static int readCount(
            String message) {

        while (true) {

            int count =
                    readPositiveInteger(message);

            if (count > tasks.size()) {

                System.out.println(
                        "You currently have only "
                        + tasks.size()
                        + " task(s)."
                );

            } else {

                return count;
            }
        }
    }


    // =========================
    // YES / NO
    // =========================

    static String readYesNo(
            String message) {

        while (true) {

            System.out.print(message);

            String answer =
                    sc.nextLine()
                            .trim()
                            .toLowerCase();

            if (answer.equals("yes")
                    || answer.equals("y")
                    || answer.equals("no")
                    || answer.equals("n")) {

                return answer;
            }

            System.out.println(
                    "Please enter yes or no."
            );
        }
    }


    // =========================
    // STATUS TEXT
    // =========================

    static String getStatusText(
            Status status) {

        if (status == Status.PENDING) {

            return "PENDING";
        }

        if (status == Status.IN_PROGRESS) {

            return "IN PROGRESS";
        }

        return "COMPLETED";
    }


    // =========================
    // CONFIRM EXIT
    // =========================

    static boolean confirmExit() {

        displayProgress();

        String answer =
                readYesNo(
                        "\nAre you sure you want to exit? (yes/no): "
                );

        if (answer.equals("yes")
                || answer.equals("y")) {

            // Final automatic save
            saveTasks();

            System.out.println();
            System.out.println(
                    "Tasks saved successfully."
            );

            System.out.println(
                    "Thank you for using "
                    + "Persistent Task Manager!"
            );

            return true;

        } else {

            System.out.println(
                    "Returning to main menu..."
            );

            return false;
        }
    }
}