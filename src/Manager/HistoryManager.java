package Manager;

import Tasks.Task;
import java.util.List;

public interface HistoryManager {


    void addHistoryList(Task task);
    List<Task> getHistory();
    void removeHistoryTask(int id);

}
