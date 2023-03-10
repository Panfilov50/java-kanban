package Tests;

import Manager.*;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class InMemoryHistoryManagerTest {

    FileBackedTasksManager fileManager = new FileBackedTasksManager("resources\\Test.csv");
    Manager manager = Managers.getDefault();
    @Test
    void addHistoryList() {

        System.out.println(fileManager.getHistory());
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        fileManager.getTaskById(1);
        fileManager.getTaskById(2);
        System.out.println(fileManager.getHistory());

    }

    @Test
    void removeHistoryTask() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        fileManager.getTaskById(1);
        fileManager.getTaskById(2);
        System.out.println(fileManager.getHistory());
        fileManager.removeTask(1);
        System.out.println(fileManager.getHistory());
    }

    @Test
    void getHistory() {
        System.out.println(fileManager.getHistory());
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        System.out.println(fileManager.getHistory());
    }
}