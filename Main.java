package TaskManagerJava.src;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    markTaskComplete();
                    break;
                case 6:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Task Management System ---");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Mark Task as Complete");
        System.out.println("6. Exit");
    }

    private static void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        LocalDate deadline = getDateInput("Deadline (YYYY-MM-DD): ");
        int priority = getIntInput("Priority (1=High, 2=Medium, 3=Low): ");
        taskManager.addTask(title, desc, deadline, priority);
        System.out.println("Task added.");
    }

    private static void viewTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task t : tasks) {
                System.out.println(t);
            }
        }
    }

    private static void updateTask() {
        int id = getIntInput("Enter Task ID to update: ");
        Task t = taskManager.getTaskById(id);
        if (t == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.print("New Title (leave blank to keep): ");
        String title = scanner.nextLine();
        if (title.isEmpty()) title = t.getTitle();
        System.out.print("New Description (leave blank to keep): ");
        String desc = scanner.nextLine();
        if (desc.isEmpty()) desc = t.getDescription();
        String dateStr = getStringInput("New Deadline (YYYY-MM-DD, leave blank to keep): ");
        LocalDate deadline = dateStr.isEmpty() ? t.getDeadline() : LocalDate.parse(dateStr);
        String prioStr = getStringInput("New Priority (1=High, 2=Medium, 3=Low, leave blank to keep): ");
        int priority = prioStr.isEmpty() ? t.getPriority() : Integer.parseInt(prioStr);
        boolean completed = t.isCompleted();
        String compStr = getStringInput("Mark as completed? (y/n, leave blank to keep): ");
        if (compStr.equalsIgnoreCase("y")) completed = true;
        else if (compStr.equalsIgnoreCase("n")) completed = false;
        if (taskManager.updateTask(id, title, desc, deadline, priority, completed)) {
            System.out.println("Task updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteTask() {
        int id = getIntInput("Enter Task ID to delete: ");
        if (taskManager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void markTaskComplete() {
        int id = getIntInput("Enter Task ID to mark as complete: ");
        taskManager.markTaskAsComplete(id);
        System.out.println("Task marked as complete.");
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
} 