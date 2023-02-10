package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.File;

// Оставь надежду, всяк сюда входящий...

public class Main {
    public static void main(String[] args) {
        Manager manager = Managers.getDefault();
        /* Ниже старые тесты
        Task task = new Task("Task1","Description1");
        manager.addTask(task);
        task = new Task("Task2", "Description2");
        manager.addTask(task);
        task = new Task("Task3", "Description3");
        manager.addTask(task);


        Epic epic = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic);

        Subtask subtask = new Subtask("SubTask1", "Description1", "Epic1");
        manager.addSubtask(subtask);
        subtask = new Subtask("SubTask2", "Description2", "Epic1");
        manager.addSubtask(subtask);
        subtask = new Subtask("SubTask3", "Description3", "Epic1");
        manager.addSubtask(subtask);

        epic = new Epic("Epic2", "Description2");
        manager.addEpicTusk(epic);

        subtask = new Subtask("SubTask4", "Description1", "Epic2");
        manager.addSubtask(subtask);
        subtask = new Subtask("SubTask5", "Description2", "Epic2");
        manager.addSubtask(subtask);
        subtask = new Subtask("SubTask6", "Description3", "Epic3");
        manager.addSubtask(subtask);

        epic = new Epic("Epic3", "Description3");
        manager.addEpicTusk(epic);

        System.out.println();
        System.out.println();
        manager.getTaskById(1);
        manager.getEpicTaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getSubtaskById(7);
        manager.getEpicTaskById(8);
        manager.getTaskById(2);
        manager.getSubtaskById(5);
        manager.getTaskById(1);
        System.out.println(manager.getHistory());
        System.out.println();
        System.out.println();
        manager.removeTask(1);
        manager.removeTask(4);
        */

        // Новые тесты
        FileBackedTasksManager fileManager = new FileBackedTasksManager("resources\\Test.csv");
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        Task task3 = new Task("Task 3", "Description 3");
        Task task4 = new Task("Task 4", "Description 4");
        Task task5 = new Task("Task 5", "Description 5");
        Epic epic1 = new Epic("Epic 1", "Description 1");
        Epic epic2 = new Epic("Epic 2", "Description 2");
        Epic epic3 = new Epic("Epic 3", "Description 3");

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", "Epic 1");
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", "Epic 1");
        Subtask subtask3 = new Subtask("Subtask 3", "Description 3", "Epic 1");
        Subtask subtask4 = new Subtask("Subtask 4", "Description 4", "Epic 2");
        Subtask subtask5 = new Subtask("Subtask 5", "Description 5", "Epic 3");


        fileManager.addTask(task1);
        fileManager.addTask(task2);
        fileManager.addTask(task3);
        fileManager.addTask(task4);
        fileManager.addTask(task5);
        fileManager.addEpicTusk(epic1);
        fileManager.addEpicTusk(epic2);
        fileManager.addEpicTusk(epic3);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        fileManager.addSubtask(subtask3);
        fileManager.addSubtask(subtask4);
        fileManager.addSubtask(subtask5);

        System.out.println("Список задач");
        fileManager.getTask();
        System.out.println();
        System.out.println("Список эпиков");
        fileManager.getEpicTask();
        System.out.println();
        System.out.println("Печать задачи 3:");
        System.out.println(fileManager.getTaskById(3));
        fileManager.removeTask(6);
        fileManager.changeStatusForNameTask("Task 1",Status.DONE);
        System.out.println();
        System.out.println("Список задач после удаления и обновления статуса");
        fileManager.getAllTask();

        System.out.println();
        System.out.println();
        System.out.println();


        // Совсем забыл добавить бэкап! Спасибо! =)
        File backup = new File("resources\\Backup.csv");

        FileBackedTasksManager backupManager = FileBackedTasksManager.loadFromFile(backup);
        System.out.println("Список задач восстановленных");
        backupManager.getAllTask();



    }
}
