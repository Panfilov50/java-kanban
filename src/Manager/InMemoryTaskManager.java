package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.time.LocalTime;
import java.util.*;

public class InMemoryTaskManager implements Manager {
    public static final HashMap<Integer, Task> taskList = new HashMap<>();
    public static final HashMap<Integer, Epic> epicTaskList = new HashMap<>();
    public static final HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    public static final HashMap<Integer, ArrayList<Integer>> epicSubtaskList = new HashMap<>();

    HistoryManager historyManager = Managers.getDefaultHistory();

    int id = 1;

    @Override
    public void addTask(Task task) {
        if (task.getTimeStart() != null) {
            task.setEndTime(task.getTimeStart().plusMinutes(task.getDuration()));
        }
        if (checkFreeTime(task)) {
            task.setId(id);
            task.setStatus(Status.valueOf("NEW"));
            taskList.put(id, task);
            System.out.println("Задача : " + task.getName() + " поставлена, ее ID: " + id);

            id++;
        } else {
            System.out.println("Время выполнения задачи пересекаеться с другой задачей");
        }
    }

    @Override
    public void addEpicTusk(Epic epic) {
        if (checkFreeTime(epic)) {
            ArrayList<Integer> subtasks = new ArrayList<>();
            epic.setId(id);
            epic.setStatus(Status.valueOf("NEW"));
            epicSubtaskList.put(id, subtasks);
            epicTaskList.put(id, epic);
            System.out.println("Задача : " + epic.getName() + " поставлена, ее ID: " + id);

            checkStartTimeEpic(epic);
            id++;
        } else {
            System.out.println("Время выполнения задачи пересекаеться с другой задачей");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask.getTimeStart() != null) {
            subtask.setEndTime(subtask.getTimeStart().plusMinutes(subtask.getDuration()));
        }
        if (checkFreeTime(subtask)) {
            for (int i : epicTaskList.keySet()) {
                if (epicTaskList.get(i).getName().equals(subtask.getWhoIsEpic())) {
                    subtask.setId(id);
                    subtask.setStatus(Status.valueOf("NEW"));
                    epicSubtaskList.get(i).add(id);
                    subtaskList.put(id, subtask);
                    subtask.setIdEpic(i);
                    if (subtask.getTimeStart() != null) {
                        subtask.setEndTime(subtask.getTimeStart().plusMinutes(subtask.getDuration()));
                    }
                    checkStartTimeEpic(epicTaskList.get(i));
                    System.out.println("Задача : " + subtask.getName() + " поставлена, ее ID: " + id);
                }
            }
            id++;

        } else {
            System.out.println("Время выполнения задачи пересекаеться с другой задачей");
        }
    }

    @Override
    public void changeStatusForNameTask(String nameTask, Status newStatus) {
        for (int i : taskList.keySet()) {
            if (taskList.get(i).getName().equals(nameTask)) {
                taskList.get(i).setStatus(newStatus);
            }
        }
        for (int i : epicTaskList.keySet()) {
            if (epicTaskList.get(i).getName().equals(nameTask)) {
                epicTaskList.get(i).setStatus(newStatus);
            }
        }
        for (int i : subtaskList.keySet()) {
            if (subtaskList.get(i).getName().equals(nameTask)) {
                subtaskList.get(i).setStatus(newStatus);
            }
        }
        for (int i : epicTaskList.keySet())
            checkEpicStatus(i);
    }

    @Override
    public void checkEpicStatus(int id) {
        int count = 0;
        int count2 = 0;
        for (int i : epicSubtaskList.get(id)) {
            if (subtaskList.get(i).getStatus().equals(Status.NEW)) {
                count++;
            } else if (subtaskList.get(i).getStatus().equals(Status.DONE)) {
                count2++;
            }
        }
        if (count == epicSubtaskList.get(id).size()) {
            epicTaskList.get(id).setStatus(Status.valueOf("NEW"));
        } else if (count2 == epicSubtaskList.get(id).size()) {
            epicTaskList.get(id).setStatus(Status.valueOf("DONE"));
            System.out.println("Задача: " + epicTaskList.get(id).getName() + " Выполнена!");
        } else {
            epicTaskList.get(id).setStatus(Status.valueOf("IN_PROGRESS"));
        }
    }

    @Override
    public void getAllTask() {
        if (taskList.isEmpty() && epicTaskList.isEmpty()) {
            System.out.println("Задачи нет.");
        } else {
            System.out.println("На данный момент перед нами стоят следующие задачи: ");
            getTask();
            getEpicTask();
        }
    }

    @Override
    public void getTask() {
        if (taskList.isEmpty()){
            System.out.println("Задачи нет.");
        } else {
            for (int i : taskList.keySet()) {
                System.out.println("Задача: " + taskList.get(i).getName() + ". Описание: " + taskList.get(i).getDescription());
            }
        }
    }

    @Override
    public void getEpicTask() {
        if (epicTaskList.isEmpty()){
            System.out.println("Задачи нет.");
        } else {
            for (int i : epicTaskList.keySet()) {
                System.out.println("Большая задача: " + epicTaskList.get(i).getName() + ". Описание: " + epicTaskList.get(i).getDescription());
                System.out.println("В эту задачу входит:");
                for (int j : epicSubtaskList.get(i)) {
                    System.out.println("Маленькая задача: " + subtaskList.get(j).getName() + ". Описание: " + subtaskList.get(j).getDescription());
                }
            }
        }
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtaskList;
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return taskList;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epicTaskList;
    }
    @Override
    public HashMap<Integer, ArrayList<Integer>> epicSubtaskLists(){
        return epicSubtaskList;
    }

    @Override
    public void removeTask(int id) {     // Полностью переработал этот метод. Что тут творилось до этого, это ужас =)
        historyManager.removeHistoryTask(id);
        if (taskList.containsKey(id)) {
            taskList.remove(id);
        } else if (subtaskList.containsKey(id)) {
            epicSubtaskList.get(subtaskList.get(id).getIdEpic()).remove((Integer) id);
            subtaskList.remove(id);
        } else if (epicTaskList.containsKey(id)) {
            for (int i : epicSubtaskList.get(id)) {
                historyManager.removeHistoryTask(i);
                subtaskList.remove((Integer) i);

            }
            epicSubtaskList.remove(id);
            epicTaskList.remove(id);
        }

    }

    @Override
    public void removeAllTask() {
        taskList.clear();
        epicTaskList.clear();
        subtaskList.clear();
        epicSubtaskList.clear();
    }

    @Override
    public Task getTaskById(int id) {
        if (taskList.get(id) == null){
            System.out.println("Задачи нет.");
            return null;
        } else {
            historyManager.addHistoryList(taskList.get(id));
            System.out.println(taskList.get(id).toString());
            return taskList.get(id);
        }
    }

    @Override
    public Task getEpicTaskById(int id) {
        if (epicTaskList.get(id) == null) {
            System.out.println("Задачи нет.");
            return null;
        } else {
            historyManager.addHistoryList(epicTaskList.get(id));
            System.out.println(epicTaskList.get(id).toString());
            return epicTaskList.get(id);
        }
    }

    @Override
    public Task getSubtaskById(int id) {
        if (subtaskList.get(id) == null) {
            System.out.println("Задачи нет.");
            return null;
        } else {
            historyManager.addHistoryList(subtaskList.get(id));
            System.out.println(subtaskList.get(id).toString());
            return subtaskList.get(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    @Override
    public void checkStartTimeEpic(Epic epic) {
        LocalTime newTimeStartEpic = null;
        long duration = 0;
        LocalTime newTimeEndEpic = null;
        if (!epicSubtaskList.isEmpty()) {
            for (Integer i : epicSubtaskList.get(epic.getId())) {
                if (subtaskList.get(i).getTimeStart() != null && newTimeStartEpic == null){
                    newTimeStartEpic = subtaskList.get(i).getTimeStart();
                }
                if (subtaskList.get(i).getTimeStart() != null && subtaskList.get(i).getTimeStart().isBefore(newTimeStartEpic)) {
                    newTimeStartEpic = subtaskList.get(i).getTimeStart();
                }
                if (subtaskList.get(i).getEndTime()!= null && newTimeEndEpic == null){
                    newTimeEndEpic = subtaskList.get(i).getEndTime();
                }
                if (subtaskList.get(i).getEndTime() != null && subtaskList.get(i).getEndTime().isAfter(newTimeEndEpic)) {
                    newTimeEndEpic = subtaskList.get(i).getEndTime();
                }
                duration = duration + subtaskList.get(i).getDuration();
            }
            if (newTimeStartEpic != null) {
                epic.setTimeStart(newTimeStartEpic);
            }
            if (duration != 0) {
                epic.setDuration(duration);
            }
            if (newTimeEndEpic != null) {
                epic.setEndTime(newTimeEndEpic);
            }

        }
        // else Дописать исклчюение "Нет сабтасков"
    }

    public boolean checkFreeTime (Task task){
        if (task.getTimeStart() != null) {
            for (Task i : taskList.values()) {
                if (i.getTimeStart() != null) {
                    if (task.getTimeStart().isBefore(i.getTimeStart())
                            && task.getEndTime().isBefore(i.getTimeStart())) {
                        return true;
                    } else if (task.getTimeStart().isAfter(i.getEndTime())
                            && task.getEndTime().isAfter(i.getEndTime())) {
                        return true;
                    }
                }
                return false;
            }
            for (Task i : subtaskList.values()) {
                if (i.getTimeStart() != null) {
                    if (task.getTimeStart().isBefore(i.getTimeStart())
                            && task.getEndTime().isBefore(i.getTimeStart())) {
                        return true;
                    } else if (task.getTimeStart().isAfter(i.getEndTime())
                            && task.getEndTime().isAfter(i.getEndTime())) {
                        return true;
                    }
                }
                return false;
            }

        }
        return true;
    }
    @Override
    public ArrayList<Task> getPrioritizedTasks() {
        class ComparatorTime implements Comparator<Task> {
            public int compare(Task s1, Task s2) {
                return s1.getTimeStart().compareTo(s2.getTimeStart());
            }
        }
        TreeSet<Task> prioritizedTasks = new TreeSet<>(new ComparatorTime());
        LinkedList<Task> NullTaskList = new LinkedList<>();
        for (Task task : taskList.values()) {
            if (task.getTimeStart() == null){
                NullTaskList.add(task);
            } else {
                prioritizedTasks.add(task);
            }
        }
        for (Epic epic : epicTaskList.values()) {
            if (epic.getTimeStart() == null){
                NullTaskList.add(epic);
            } else {
                prioritizedTasks.add(epic);
            }
        }
        for (Subtask subtask : subtaskList.values()) {
            if (subtask.getTimeStart() == null){
                NullTaskList.add(subtask);
            } else {
                prioritizedTasks.add(subtask);
            }
        }
        ArrayList<Task> SortedTaskList = new ArrayList<>(prioritizedTasks);
        for (Task task : NullTaskList) {
            SortedTaskList.add(task);
        }
        return SortedTaskList;
    }
}

