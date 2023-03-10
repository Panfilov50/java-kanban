package Tests;

import Manager.Manager;
import Manager.Managers;
import Manager.Status;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

class InMemoryTaskManagerTest {
    Manager manager = Managers.getDefault();

    InMemoryTaskManagerTest() throws IOException, InterruptedException {
    }

    @Test
     void addTaskTest() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        Task task3 = new Task("Task3", "Description3", LocalTime.of(11, 15), 30 );
        manager.addTask(task3);

    }

    @Test
    void addTaskBreakeTimeTest() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task3", "Description3", LocalTime.of(10, 15), 30 );
        manager.addTask(task2);
    }


    @Test
    void addEpicTusk() {
        Epic epic1 = new Epic("Epic1", "Description1", LocalTime.of(10, 0),60);
        manager.addEpicTusk(epic1);
        Epic epic2 = new Epic("Epic2", "Description2");
        manager.addEpicTusk(epic2);
        manager.getPrioritizedTasks();
    }

    @Test
    void addSubtask() {
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),30, "Epic1" );
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("SubTask2", "Description2", LocalTime.of(11, 0),30, "Epic1");
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("SubTask3", "Description3", LocalTime.of(9, 0),30, "Epic1");
        manager.addSubtask(subtask3);

        Epic epic2 = new Epic("Epic2", "Description2");
        manager.addEpicTusk(epic2);

        Subtask subtask4 = new Subtask("SubTask4", "Description1", LocalTime.of(10, 0),60, "Epic2");
        manager.addSubtask(subtask4);
        Subtask subtask5 = new Subtask("SubTask5", "Description2", "Epic2");
        manager.addSubtask(subtask5);
        Subtask subtask6 = new Subtask("SubTask6", "Description3", LocalTime.of(15, 0),60, "Epic3");
        manager.addSubtask(subtask6);

        manager.getPrioritizedTasks();
    }

    @Test
    void addTaskBreakeTimeSubtask() {
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),30, "Epic1" );
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("SubTask2", "Description2", LocalTime.of(10, 10),60, "Epic1");
        manager.addSubtask(subtask2);
    }

    @Test
    void changeStatusForNameTask() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);

        getTask();
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.changeStatusForNameTask("Task1", Status.DONE);
        manager.getTaskById(1);
        manager.getTaskById(2);
    }

    @Test
    void checkEpicStatus() {
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),60, "Epic1" );
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("SubTask2", "Description2", LocalTime.of(11, 30),60, "Epic1");
        manager.addSubtask(subtask2);

        manager.getEpicTaskById(1);
        manager.changeStatusForNameTask("SubTask1", Status.DONE);
        manager.getEpicTaskById(1);
        manager.changeStatusForNameTask("SubTask2", Status.DONE);
        manager.getEpicTaskById(1);
    }

    @Test
    void getAllTask() {
        manager.getAllTask();

        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 30 );
        manager.addTask(task1);
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);
        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(11, 0),30, "Epic1" );
        manager.addSubtask(subtask1);

        manager.getAllTask();
    }

    @Test
    void getTask() {
        manager.getTask();
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        manager.getTask();
    }

    @Test
    void getEpicTask() {
        manager.getEpicTask();
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);
        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),60, "Epic1" );
        manager.addSubtask(subtask1);
        manager.getEpicTask();
    }

    @Test
    void removeTask() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        manager.getTask();
        manager.removeTask(1);
        manager.getTask();

    }

    @Test
    void removeAllTask() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        manager.getTask();
        manager.removeAllTask();
        manager.getTask();
    }

    @Test
    void getTaskById() {
        manager.getTaskById(1);
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        manager.getTaskById(1);
    }

    @Test
    void getEpicTaskById() {
        manager.getEpicTaskById(1);
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),30, "Epic1" );
        manager.addSubtask(subtask1);
        manager.getEpicTaskById(1);
    }

    @Test
    void getSubtaskById() {
        manager.getSubtaskById(2);
        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", LocalTime.of(10, 0),30, "Epic1" );
        manager.addSubtask(subtask1);
        manager.getSubtaskById(2);
    }

    @Test
    void checkStartTimeEpic() {
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);

    }

    @Test
    void getPrioritizedTasks() {
        manager.getPrioritizedTasks();
        Task task1 = new Task("Task1","Description1", LocalTime.of(10, 0), 60 );
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Description2");
        manager.addTask(task2);
        manager.getPrioritizedTasks();
    }
}