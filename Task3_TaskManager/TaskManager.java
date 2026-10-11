import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TaskManager {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static int nextTaskId = 1;

    enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    static class Task {
        int id;
        String name;
        Status status;

        Task(int id, String name) {
            this.id = id;
            this.name = name;
            this.status = Status.PENDING;
        }
    }

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("========================================");
        System.out.println("              TASK MANAGER");
        System.out.println("========================================");

        while (running) {

            displayMenu();

            int choice = readInteger(
                    "Enter your choice: ", 1, 8);

            switch (choice) {

                case 1:
                    addTasks();
                    break;

                case 2:
                    viewAllTasks();
                    break;

                case 3:
                    startTasks();
                    break;

                case 4:
                    updateTasks();
                    break;

                case 5:
                    completeTasks();
                    break;

                case 6:
                    deleteTasks();
                    break;

                case 7:
                    displayProgress();
                    break;

                case 8:
                    if (confirmExit()) {
                        running = false;
                    }
                    break;
            }
        }

        System.out.println("\nProgram closed.");
        sc.close();
    }

    // ================= MENU =================

    static void displayMenu() {

        System.out.println("\n========================================");
        System.out.println("              MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Add Tasks");
        System.out.println("2. View All Tasks");
        System.out.println("3. Start Tasks");
        System.out.println("4. Update Tasks");
        System.out.println("5. Complete Tasks");
        System.out.println("6. Delete Tasks");
        System.out.println("7. View Progress");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    // ================= ADD =================

    static void addTasks() {

        System.out.println("\n----------- ADD TASKS -----------");

        int count = readPositiveInteger(
                "How many tasks do you want to add? ");

        for (int i = 1; i <= count; i++) {

            System.out.println("\nTask " + i);

            String name = readTaskName();

            Task task = new Task(nextTaskId, name);

            tasks.add(task);

            System.out.println(
                    "Task added successfully! ID: "
                    + nextTaskId);

            nextTaskId++;
        }

        System.out.println(
                "\n" + count + " task(s) added.");
    }

    // ================= VIEW =================

    static void viewAllTasks() {

        System.out.println("\n========================================");
        System.out.println("              ALL TASKS");
        System.out.println("========================================");

        if (tasks.isEmpty()) {

            System.out.println("No tasks available.");
            System.out.println("Please add a task first.");

            return;
        }

        System.out.printf(
                "%-8s %-35s %-15s%n",
                "ID",
                "TASK",
                "STATUS");

        System.out.println(
                "------------------------------------------------------------");

        for (Task task : tasks) {

            System.out.printf(
                    "%-8d %-35s %-15s%n",
                    task.id,
                    task.name,
                    getStatusText(task.status));
        }

        System.out.println(
                "------------------------------------------------------------");

        System.out.println(
                "Total Tasks: " + tasks.size());
    }

    // ================= START =================

    static void startTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available.");

            return;
        }

        System.out.println("\n----------- START TASKS -----------");

        int count = readCount(
                "How many tasks do you want to start? ");

        Set<Integer> selected = new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to start: ");

            if (selected.contains(id)) {

                System.out.println(
                        "You already selected this task.");

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found.");

                continue;
            }

            selected.add(id);
            processed++;

            if (task.status == Status.COMPLETED) {

                System.out.println(
                        "This task is already completed.");

            } else if (task.status == Status.IN_PROGRESS) {

                System.out.println(
                        "This task is already in progress.");

            } else {

                task.status = Status.IN_PROGRESS;

                System.out.println(
                        "Task \"" + task.name
                        + "\" started.");
            }
        }
    }

    // ================= UPDATE =================

    static void updateTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available.");

            return;
        }

        System.out.println("\n----------- UPDATE TASKS -----------");

        int count = readCount(
                "How many tasks do you want to update? ");

        Set<Integer> selected = new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to update: ");

            if (selected.contains(id)) {

                System.out.println(
                        "You already selected this task.");

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found.");

                continue;
            }

            selected.add(id);
            processed++;

            System.out.println(
                    "Current name: " + task.name);

            String newName = readTaskName();

            task.name = newName;

            System.out.println(
                    "Task updated successfully.");
        }
    }

    // ================= COMPLETE =================

    static void completeTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available.");

            return;
        }

        System.out.println("\n----------- COMPLETE TASKS -----------");

        int count = readCount(
                "How many tasks do you want to complete? ");

        Set<Integer> selected = new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to complete: ");

            if (selected.contains(id)) {

                System.out.println(
                        "You already selected this task.");

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found.");

                continue;
            }

            selected.add(id);
            processed++;

            if (task.status == Status.COMPLETED) {

                System.out.println(
                        "Task is already completed.");

            } else {

                task.status = Status.COMPLETED;

                System.out.println(
                        "Task \"" + task.name
                        + "\" completed.");
            }
        }
    }

    // ================= DELETE =================

    static void deleteTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available.");

            return;
        }

        System.out.println("\n----------- DELETE TASKS -----------");

        int count = readCount(
                "How many tasks do you want to delete? ");

        Set<Integer> selected = new HashSet<>();

        int processed = 0;

        while (processed < count) {

            int id = readPositiveInteger(
                    "Enter Task ID to delete: ");

            if (selected.contains(id)) {

                System.out.println(
                        "You already selected this task.");

                continue;
            }

            Task task = findTask(id);

            if (task == null) {

                System.out.println(
                        "Task ID not found.");

                continue;
            }

            selected.add(id);
            processed++;

            System.out.println(
                    "Task: " + task.name);

            String answer = readYesNo(
                    "Delete this task? (yes/no): ");

            if (answer.equals("yes")
                    || answer.equals("y")) {

                tasks.remove(task);

                System.out.println(
                        "Task deleted successfully.");

            } else {

                System.out.println(
                        "Deletion cancelled.");
            }
        }
    }

    // ================= PROGRESS =================

    static void displayProgress() {

        System.out.println("\n========================================");
        System.out.println("             TASK PROGRESS");
        System.out.println("========================================");

        int total = tasks.size();

        int completed = 0;
        int inProgress = 0;
        int pending = 0;

        // Count every task
        for (Task task : tasks) {

            if (task.status == Status.COMPLETED) {

                completed++;

            } else if (task.status == Status.IN_PROGRESS) {

                inProgress++;

            } else {

                pending++;
            }
        }

        int remaining = total - completed;

        double percentage = 0;

        if (total > 0) {

            percentage =
                    ((double) completed / total) * 100;
        }

        System.out.println(
                "Total Tasks      : " + total);

        System.out.println(
                "Completed        : " + completed);

        System.out.println(
                "In Progress      : " + inProgress);

        System.out.println(
                "Pending          : " + pending);

        System.out.println(
                "Remaining        : " + remaining);

        System.out.printf(
                "Progress         : %.2f%%%n",
                percentage);

        System.out.println("========================================");
    }

    // ================= EXIT =================

    static boolean confirmExit() {

        System.out.println(
                "\n----------- FINAL SUMMARY -----------");

        displayProgress();

        String answer = readYesNo(
                "\nAre you sure you want to exit? (yes/no): ");

        if (answer.equals("yes")
                || answer.equals("y")) {

            System.out.println(
                    "\nThank you for using Task Manager!");

            return true;

        } else {

            System.out.println(
                    "\nReturning to main menu...");

            return false;
        }
    }

    // ================= FIND TASK =================

    static Task findTask(int id) {

        for (Task task : tasks) {

            if (task.id == id) {

                return task;
            }
        }

        return null;
    }

    // ================= TASK NAME =================

    static String readTaskName() {

        while (true) {

            System.out.print("Enter task name: ");

            String name = sc.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println(
                        "Task name cannot be empty.");

                continue;
            }

            if (name.length() > 100) {

                System.out.println(
                        "Task name cannot exceed 100 characters.");

                continue;
            }

            return name;
        }
    }

    // ================= INTEGER =================

    static int readInteger(
            String message,
            int min,
            int max) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                int value = Integer.parseInt(input);

                if (value < min || value > max) {

                    System.out.println(
                            "Enter a number between "
                            + min + " and " + max + ".");

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Enter a whole number.");
            }
        }
    }

    // ================= POSITIVE INTEGER =================

    static int readPositiveInteger(String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println(
                        "Input cannot be empty.");

                continue;
            }

            try {

                int value = Integer.parseInt(input);

                if (value <= 0) {

                    System.out.println(
                            "Enter a number greater than 0.");

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Enter a whole number.");
            }
        }
    }

    // ================= COUNT =================

    static int readCount(String message) {

        while (true) {

            int count = readPositiveInteger(message);

            if (count > tasks.size()) {

                System.out.println(
                        "You only have "
                        + tasks.size()
                        + " task(s).");

            } else {

                return count;
            }
        }
    }

    // ================= YES / NO =================

    static String readYesNo(String message) {

        while (true) {

            System.out.print(message);

            String answer =
                    sc.nextLine().trim().toLowerCase();

            if (answer.equals("yes")
                    || answer.equals("y")
                    || answer.equals("no")
                    || answer.equals("n")) {

                return answer;
            }

            System.out.println(
                    "Please enter yes or no.");
        }
    }

    // ================= STATUS =================

    static String getStatusText(Status status) {

        if (status == Status.PENDING) {
            return "PENDING";
        }

        if (status == Status.IN_PROGRESS) {
            return "IN PROGRESS";
        }

        return "COMPLETED";
    }
}