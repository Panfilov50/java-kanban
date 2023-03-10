package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Manager {
    void addTask(Task task);
    void addEpicTusk(Epic epic);
    void addSubtask(Subtask subtask);
    void changeStatusForNameTask (String nameTask, Status newStatus);
    void checkEpicStatus(int id);
    void getAllTask ();
    void getTask ();
    void getEpicTask ();
    HashMap<Integer, Subtask> getSubtasks();
    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Epic> getEpics();
    HashMap<Integer, ArrayList<Integer>> epicSubtaskLists();
    void removeTask(int id);
    void removeAllTask ();
    Task getTaskById(int id);
    Task getEpicTaskById(int id);
    Task getSubtaskById(int id);
    List<Task> getHistory();
    void checkStartTimeEpic(Epic epic);
    ArrayList<Task> getPrioritizedTasks();
}











