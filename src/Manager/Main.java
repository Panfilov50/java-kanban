package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Manager manager = Managers.getDefault();
 //создайте две задачи, эпик с тремя подзадачами и эпик без подзадач;
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
//запросите созданные задачи несколько раз в разном порядке;
        manager.getTaskById(1);
        manager.getEpicTaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getSubtaskById(7);
        manager.getEpicTaskById(8);
        manager.getTaskById(2);
        manager.getSubtaskById(5);
        manager.getTaskById(1);

        System.out.println();
        System.out.println();
//после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        System.out.println(manager.getHistory());
        System.out.println();
        System.out.println();
//удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        manager.removeTask(1);
//удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        manager.removeTask(4);

        System.out.println(manager.getHistory());





    }
}
