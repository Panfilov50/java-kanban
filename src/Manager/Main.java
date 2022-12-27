// Привет! Далее, что бы не теряться будут комментарии только измененным/добавленным штукам


package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


        Task task = new Task("Task1","Description");  // Вынес добалвения тасков в мейн.
        manager.addTask(task);  // В менеджер передаеться только имя и описание, там им присваиваеться ID и статус.
        task = new Task("Захватить мир", "Мировое господство");
        manager.addTask(task);
        task = new Task("Излечить рак", "Найти лекарство от рака ");
        manager.addTask(task);

        Epic epic = new Epic("Погладить кота", "Почесывать блоховоза до мурлыканья");
        manager.addEpicTusk(epic);

        Subtask subtask = new Subtask("Найти кота", "Найти блоховоза по звукам <МЯУ>", "Погладить кота");
        manager.addSubtask(subtask);
        subtask = new Subtask("Найти кота", "Найти блоховоза по звукам <МЯУ>", "Погладить кота");
        manager.addSubtask(subtask);
        subtask = new Subtask("Гладить кота", "Причинять коту беспощадные ласки ", "Погладить кота");
        manager.addSubtask(subtask);

        manager.changeStatusForNameTask("Найти кота", Status.valueOf("IN_PROGRESS"));

        epic = new Epic("Погладить бегемота", "Рискнуть жизнью ради шанса потрогать пузатого ");
        manager.addEpicTusk(epic);
        subtask = new Subtask("Найти бегемота", "Загуглить данную услугу", "Погладить бегемота");
        manager.addSubtask(subtask);

        System.out.println(manager.taskList);
        System.out.println(manager.epicTaskList);
        System.out.println(manager.subtaskList);
        System.out.println(manager.epicSubtaskList.get(4));
        System.out.println(manager.epicSubtaskList.get(6));
        System.out.println(manager.epicTaskList.get(4).getStatus());
        manager.changeStatusForNameTask("Найти кота", Status.valueOf("DONE"));
        manager.changeStatusForNameTask("Гладить кота", Status.valueOf("DONE"));
        System.out.println(manager.epicTaskList.get(4).getStatus());
        manager.getAllTask();
        manager.removeTask(1);
        System.out.println(manager.taskList);
        manager.removeTask(5);
        manager.getAllTask();

        System.out.println();
        System.out.println();

        manager.getTaskById(2); // Вызов просмотра таска по ИД с последующей записью в историю
        manager.getEpicTaskById(8);
        manager.getSubtaskById(9);

        System.out.println(); //

        inMemoryHistoryManager.getHistory();




    }
}
