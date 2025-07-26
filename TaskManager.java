package TaskManagerJava.src;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TaskManager {
    private List<Task> tasks;
    private int nextId;
    private static final String FILE_NAME = "tasks.ser";

    public TaskManager() {
        tasks = new ArrayList<>();
        nextId = 1;
        loadTasks();
    }

    public void addTask(String title, String description, LocalDate deadline, int priority) {
        Task task = new Task(nextId++, title, description, deadline, priority);
        tasks.add(task);
        saveTasks();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public boolean updateTask(int id, String title, String description, LocalDate deadline, int priority, boolean completed) {
        Task t = getTaskById(id);
        if (t != null) {
            t.setTitle(title);
            t.setDescription(description);
            t.setDeadline(deadline);
            t.setPriority(priority);
            t.setCompleted(completed);
            saveTasks();
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getId() == id) {
                it.remove();
                saveTasks();
                return true;
            }
        }
        return false;
    }

    public void markTaskAsComplete(int id) {
        Task t = getTaskById(id);
        if (t != null) {
            t.setCompleted(true);
            saveTasks();
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tasks = (List<Task>) ois.readObject();
            nextId = ois.readInt();
        } catch (Exception e) {
            // File not found or error reading, start fresh
            tasks = new ArrayList<>();
            nextId = 1;
        }
    }
} 