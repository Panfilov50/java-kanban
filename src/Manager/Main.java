package Manager;

import Server.HttpTaskServer;
import Server.KVServer;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = Managers.getDefault();
        Task task1= new Task("Task1", "Description1");
        manager.addTask(task1);
        Task task2= new Task("Task2", "Description2");
        manager.addTask(task2);

        Epic epic1 = new Epic("Epic1", "Description1");
        manager.addEpicTusk(epic1);

        Subtask subtask1 = new Subtask("SubTask1", "Description1", "Epic1" );
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("SubTask2", "Description2", "Epic1");
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("SubTask3", "Description3", "Epic1");
        manager.addSubtask(subtask3);

        Epic epic2 = new Epic("Epic2", "Description2");
        manager.addEpicTusk(epic2);

        Subtask subtask4 = new Subtask("SubTask4", "Description1", "Epic2");
        manager.addSubtask(subtask4);
        Subtask subtask5 = new Subtask("SubTask5", "Description2", "Epic2");
        manager.addSubtask(subtask5);



        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();

    }
}
