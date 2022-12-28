package Manager;

import Tasks.Task;

import java.util.LinkedList;

public interface HistoryManager {

    void addToHistoryList(Task task);
    LinkedList<Task> getHistory();

}
