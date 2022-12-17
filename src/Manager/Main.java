package Manager;
// Добавил пакеты.
// Добавил гетеры и стеторы поменял обращения к переменным на методы, где это требовалось.
//  Нашел крупный баг в при удалении сабтаска, о котором ты не сказал. Поправил. (Manager 117)



public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();  // Ниже идет тесты работоспособности, я не стал их удалять что тебе было удобнее проверить как ЭТО работает(?)
        manager.addTask("Захватить мир", "Мировое господство"); // Создаю простую задачу
        manager.addTask("Излечить рак", "Найти лекарство от рака "); // Создаю еще простую задачу
        manager.addEpicTusk("Погладить кота", "Почесывать блоховоза до мурлыканья"); //Создание эпика
        manager.addSubtask("Найти кота", "Найти блоховоза по звукам <МЯУ>", "Погладить кота"); // Создания подзадачи
        manager.addSubtask("Гладить кота", "Причинять коту беспощадные ласки ", "Погладить кота"); // И еще одной в рамках того же эпика
        manager.changeStatusForNameTask("Найти кота", Status.valueOf("IN_PROGRESS"));  // Смена статуса подзадачи по имени задачи
        manager.addEpicTusk("Погладить бегемота", "Рискнуть жизнью ради шанса потрогать пузатого "); // Создание второго эпика
        manager.addSubtask("Найти бегемота", "Загуглить данную услугу", "Погладить бегемота"); // Создание второй под задачи к 2 эпику
        System.out.println(manager.taskList);
        System.out.println(manager.epicTaskList);
        System.out.println(manager.subtaskList);
        System.out.println(manager.epicSubtaskList.get(3)); // Проверка знают ли эпики айди своих сабтасков
        System.out.println(manager.epicSubtaskList.get(6));
        System.out.println(manager.epicTaskList.get(3).getStatus()); // проверка статуса первого эпика, после смены статуса одного из сабтасков.
        manager.changeStatusForNameTask("Найти кота", Status.valueOf("DONE")); // Смена статуса задачи на выполнена
        manager.changeStatusForNameTask("Гладить кота", Status.valueOf("DONE"));
        System.out.println(manager.epicTaskList.get(3).getStatus()); // Проверка статуса Первого эпика после выполнения всех подзадач
        manager.getAllTask(); // Дальше выполняться метод вывода всех задач (ТЗ 2.1 Получение списка всех задач.)
        manager.removeTask(1);
        System.out.println(manager.taskList);
        manager.removeTask(5);
        manager.getAllTask();



    }
}
