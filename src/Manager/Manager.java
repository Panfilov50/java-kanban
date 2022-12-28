package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public interface Manager {  // Тут согласно ТЗ создал интерфейс

    void addTask(Task task);
    void addEpicTusk(Epic epic);
    void addSubtask(Subtask subtask);
    void changeStatusForNameTask (String nameTask, Status newStatus);
    void checkEpicStatus(int id);
    void getAllTask ();
    void getTask ();
    void getEpicTask ();
    void removeTask(int id);
    void removeAllTask ();
    Task getTaskById(int id);
    Task getEpicTaskById(int id);
    Task getSubtaskById(int id);





}











